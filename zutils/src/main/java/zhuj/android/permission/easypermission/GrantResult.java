package zhuj.android.permission.easypermission;

/**
 * @className: GrantResult
 * @classDescription:申请权限结果枚举类
 * @author: Pan_
 * @createTime: 2018/10/25
 */
public enum GrantResult {

    /**
     * 授予权限
     */
    GRANT(0),

    /**
     * 拒接权限
     */
    DENIED(-1),

    /**
     * 之前在 request Permission Rational 里面的 next 接口返回了 IGNORE
     */
    IGNORE(-2);

    private int type;

    GrantResult(int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }
}
