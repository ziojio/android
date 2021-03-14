package zhuj.java.callback;

public interface SimpleCallback<T> {

    void onSuccess(T t);

    void onFailure(Exception e);

}
