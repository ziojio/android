// package zhuj.android.app.ui.activity;
//
//
// import android.annotation.SuppressLint;
// import android.app.ActivityManager;
// import android.os.Build;
// import android.os.Bundle;
//
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;
//
// import com.zhuj.android.R;
// import zhuj.android.app.model.DeviceInfo;
// import zhuj.android.app.adapter.DeviceInfoAdapter;
//
// import java.util.ArrayList;
// import java.util.HashMap;
//
// public class DeviceInfoActivity extends AppCompatActivity {
//     RecyclerView deviceinfo_recycleview;
//     // 适配器
//     private DeviceInfoAdapter mDeviceInfoAdapter;
//     // 获取设备信息列表
//     private ArrayList<DeviceInfo> mDeviceInfoList = new ArrayList<>();
//
//
//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_device_info);
//         initViews();
//         // 发送请求获取
//         new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 // 获取设备信息
//                 getDeviceInfos();
//             }
//         }).start();
//     }
//
//
//     public void initViews() {
//         // 初始化适配器并绑定
//         mDeviceInfoAdapter = new DeviceInfoAdapter(this);
//         deviceinfo_recycleview = findViewById(R.id.device_info_recyclerview);
//         deviceinfo_recycleview.setAdapter(mDeviceInfoAdapter);
//         LinearLayoutManager manager = new LinearLayoutManager(this);
//         deviceinfo_recycleview.setLayoutManager(manager);
//     }
//
//     /**
//      * 滑动到顶部
//      */
//     public void scrollTop() {
//         if (deviceinfo_recycleview != null) {
//             deviceinfo_recycleview.smoothScrollToPosition(0);
//         }
//     }
//
//     /**
//      * 滑动到序列号
//      */
//     public void scrollToSerial() {
//         if (deviceinfo_recycleview != null) {
//             int x = findItemPosition(R.string.serial);
//             if (x != -1) {
//                 deviceinfo_recycleview.smoothScrollToPosition(x);
//             }
//         }
//     }
//
//     /**
//      * 滑动到序列号
//      */
//     public int findItemPosition(int findId) {
//         for (int i = 0, size = mDeviceInfoList.size(); i < size; i++) {
//             if (mDeviceInfoList.get(i).getResId() == findId) {
//                 return i;
//             }
//         }
//         return -1;
//     }
//
//
//     /**
//      * View 操作Handler
//      */
//     @SuppressLint("HandlerLeak")
//     Handler vHandler = new Handler() {
//         @Override
//         public void handleMessage(Message msg) {
//             super.handleMessage(msg);
//             // 如果页面已经关闭,则不进行处理
//             if (ActivityManager.isFinishingCtx(DeviceInfoActivity.this)) {
//                 return;
//             }
//             // 判断通知类型
//             switch (msg.what) {
//                 case NotifyConstants.H_QUERY_DEVICE_INFO_END_NOTIFY:
//                     // 刷新适配器
//                     mDeviceInfoAdapter.setListDatas(mDeviceInfoList);
//                     break;
//                 case NotifyConstants.H_EXPORT_DEVICE_MSG_NOTIFY:
//                     // 导出数据
//                     boolean result = FileUtils.saveFile(ProConstants.EXPORT_PATH, "deviceinfo.txt", DeviceInfoBean.obtain(mDeviceInfoList));
//                     // 获取提示内容
//                     String tips = getString(result ? R.string.export_suc : R.string.export_fail);
//                     // 判断结果
//                     if (result) {
//                         // 拼接保存路径
//                         tips += " " + ProConstants.EXPORT_PATH + "deviceinfo.txt";
//                     }
//                     // 提示结果
//                     ToastUtils.showShort(DeviceInfoActivity.this, tips);
//                     break;
//             }
//         }
//     };
//
//     /**
//      * 获取手机信息
//      */
//     private void getDeviceInfos() {
//         // https://blog.csdn.net/xx326664162/article/details/52438706
//         // https://blog.csdn.net/litianquan/article/details/78572617
//         // https://blog.csdn.net/lchad/article/details/43716893
//
//         // 获取手机尺寸
//         // https://blog.csdn.net/lincyang/article/details/42679589
//
//         // https://blog.csdn.net/loongggdroid/article/details/12304695
//
//         // http://blog.51cto.com/xujpxm/1961072
//
//         // 设备信息
//         HashMap<String, String> mapDeviceInfos = new HashMap<>();
//         // 进行初始化获取
//         DeviceUtils.getDeviceInfo2(mapDeviceInfos);
//
//         mDeviceInfoList.clear();
//         // 获取手机型号
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.model, Build.MODEL));
//         // 获取设备制造商
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.manufacturer, Build.MANUFACTURER));
//         // 获取设备品牌
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.brand, Build.BRAND));
//         // 获取Android 系统版本
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.version_release, Build.VERSION.RELEASE));
//         // 获取屏幕尺寸(英寸)
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.screen, ScreenUtils.getScreenSizeOfDevice()));
//         // 获取屏幕分辨率
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.screen_size, ScreenUtils.getScreenSize()));
//         // 获取手机总空间
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.sdcard_total, SDCardUtils.getSDTotalSize()));
//         // 获取手机可用空间
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.sdcard_available, SDCardUtils.getSDAvailableSize()));
//         // 获取手机总内存
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.memory_total, MemoryUtils.getTotalMemory()));
//         // 获取手机可用内存
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.memory_available, MemoryUtils.getMemoryAvailable()));
//         // 获取设备版本号
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.id, Build.ID));
//         // 获取设备版本
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.display, Build.DISPLAY));
//         // 获取设备名
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.device, Build.DEVICE));
//         // 获取产品名称
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.product, Build.PRODUCT));
//         try {
//             // 判断是否模拟器
//             String result = mapDeviceInfos.get("IS_EMULATOR".toLowerCase());
//             // 存在结果才显示
//             if (!TextUtils.isEmpty(result)) {
//                 mDeviceInfoList.add(new DeviceInfoItem(R.string.is_emulator, result));
//             }
//         } catch (Exception ignored) {
//         }
//         try {
//             // 判断是否允许debug调试
//             String result = mapDeviceInfos.get("IS_DEBUGGABLE".toLowerCase());
//             // 存在结果才显示
//             if (!TextUtils.isEmpty(result)) {
//                 mDeviceInfoList.add(new DeviceInfoItem(R.string.is_debuggable, result));
//             }
//         } catch (Exception ignored) {
//         }
//         // 获取基带版本
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.baseband_version, DeviceUtils.getBaseband_Ver() + ""));
//         // 获取内核版本
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.linuxcode_version, DeviceUtils.getLinuxCore_Ver() + ""));
//         // 获取序列号
//
//         // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//         //     mDeviceInfoList.add(new DeviceInfoItem(R.string.serial, Build.getSerial()));
//         // } else {
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.serial, Build.SERIAL));
//         // }
//         // 设备唯一标识,由设备的多个信息拼接合成.
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.fingerprint, Build.FINGERPRINT + ""));
//         // 获取设备基板名称
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.board, Build.BOARD + ""));
//         // 获取设备硬件名称,一般和基板名称一样（BOARD）
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.hardware, Build.HARDWARE + ""));
//         // 获取CPU 型号
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.cpuinfo, CPUUtils.getCpuInfo() + ""));
//         // CPU指令集
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.cpu_abi1, Build.CPU_ABI + ""));
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.cpu_abi2, Build.CPU_ABI2 + ""));
//         try {
//             // 判断支持的指令集
//             String result = mapDeviceInfos.get("SUPPORTED_ABIS".toLowerCase());
//             // 存在结果才显示
//             if (!TextUtils.isEmpty(result)) {
//                 mDeviceInfoList.add(new DeviceInfoItem(R.string.supported_abis, result));
//             }
//         } catch (Exception ignored) {
//         }
//         // 获取 CPU 数量
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.cpu_number, CPUUtils.getCoresNumbers() + ""));
//         // 获取 CPU 最高 HZ
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.cpu_max, CPUUtils.getMaxCpuFreq() + ""));
//         // 获取 CPU 最底 HZ
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.cpu_min, CPUUtils.getMinCpuFreq() + ""));
//         // 获取 CPU 当前 HZ
//         mDeviceInfoList.add(new DeviceInfoItem(R.string.cpu_cur, CPUUtils.getCurCpuFreq() + ""));
//
//         // 发送通知
//         vHandler.sendEmptyMessage(NotifyConstants.H_QUERY_DEVICE_INFO_END_NOTIFY);
//     }
// }