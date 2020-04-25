
import android.support.annotation.NonNull;

public class ErrorMessage {
    public int code;
    public String msg;
    public Throwable e;

    public ErrorMessage(int code, String msg, Throwable e) {
        this.code = code;
        this.msg = msg;
        this.e = e;
    }

    public ErrorMessage(int errorCode, String msg) {
        this.code = errorCode;
        this.msg = msg;
    }

    public ErrorMessage(Throwable cause) {
        e = cause;
    }

    @NonNull
    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorCode=" + code +
                ", msg='" + msg + '\'' +
                ", e=" + e +
                '}';
    }
}
