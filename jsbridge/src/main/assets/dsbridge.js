let jsBridge = {
    default:this, // for typescript
    call: function (method, args, cb) {
        var ret = '';
        if (typeof args == 'function') {
            cb = args;
            args = {};
        }
        var arg = {
            data:args===undefined ? null : args;
        }
        if (typeof cb == 'function') {
            var cbName = '_jscbid' + window._jscbid++;
            window[cbName] = cb;
            arg['_jscbmn'] = cbName;
        }
        arg = JSON.stringify(arg)

        if (window._jsBridge){ // addJavascriptInterface name is _jsBridge
           ret = _jsBridge.call(method, arg)
        } else if(window._dswk||navigator.userAgent.indexOf("_jsBridge")!=-1){
           ret = prompt("_jsBridge=" + method, arg);
        }
       return JSON.parse(ret||'{}').data
    },
    register: function (name, fun, isAsync) {
        let func = isAsync ? window._asyncFun : window._syncFun

        if (typeof fun == "object") {
            func.list[name] = fun; // 有命名空间
        } else {
            func[name] = fun
        }
        if (!window._dsInit) {
            window._dsInit = true;
            // notify native that js apis register successfully on next event loop
            setTimeout(function () {
                jsBridge.call("_dsb.dsinit");
            }, 0)
        }
    },
    registerAsync: function (name, fun) {
        this.register(name, fun, true);
    },
    registerSync: function (name, fun) {
        this.register(name, fun, false);
    },
    hasNativeMethod: function (name, type) {
        return this.call("_dsb.hasNativeMethod", { name: name, type: type||"all" });
    },
    disableJavascriptDialogBlock: function (disable) {
        this.call("_dsb.disableJavascriptDialogBlock", {
            disable: disable !== false
        })
    }
};

!function () {
    if (window._syncFun) return;
    let ob = {
//        _bridge: bridge,
        close: function () {
            if(bridge.hasNativeMethod('_dsb.closePage')){
                bridge.call("_dsb.closePage");
            } else {
                window.close();
            }
        },
        // 保存 Javascript 同步方法
        _syncFun: {
            list: {}
        },
        // 保存 Javascript 异步方法
        _asyncFun: {
            list: {}
        },
        _jscbid: 0,

        _handleMessageFromNative: function (info) { //Java 调用 JavaScript
            let arg = JSON.parse(info.data);
            let ret = {
                id: info.callbackId,
                complete: true
            }
            let f = this._syncFun[info.method];
            let af = this._asyncFun[info.method]

            let callSync = function (f, ob) {
                ret.data = f.apply(ob, arg)
                bridge.call("_dsb.returnValue", ret)
            }
            let callAsync = function (f, ob) {
                arg.push(function (data, complete) {
                    ret.data = data;
                    ret.complete = complete!==false;
                    bridge.call("_dsb.returnValue", ret)
                })
                f.apply(ob, arg)
            }
            if (f) {
                callSync(f, this._syncFun);
            } else if (af) {
                callAsync(af, this._asyncFun);
            } else {
                // with namespace
                let name = info.method.split('.');
                if (name.length<2) return;
                let method=name.pop();
                let namespace=name.join('.')
                let obs = this._syncFun.list;
                let ob = obs[namespace] || {};
                let m = ob[method];
                if (m && typeof m == "function") {
                    callSync(m, ob);
                    return;
                }
                obs = this._asyncFun.list;
                ob = obs[namespace] || {};
                m = ob[method];
                if (m && typeof m == "function") {
                    callAsync(m, ob);
                    return;
                }
            }
        }
    },
    for (let attr in ob) {
        window[attr] = ob[attr];
    }
    bridge.registerSync("_hasJavascriptMethod", function (method, tag) {
         let name = method.split('.');
         if (name.length < 2) { // 只有函数名
           return !(!_syncFun[name] || !_asyncFun[name]);
         } else {
           // with namespace
           let method = name.pop();
           let namespace = name.join('.');
           let nameFunc = _syncFun.list[namespace] || _asyncFun.list[namespace]; // 找出命名空间的对象
           return nameFunc && !!nameFunc[method];
         }
    })
}();

module.exports = jsBridge;