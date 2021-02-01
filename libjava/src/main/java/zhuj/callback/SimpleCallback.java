package zhuj.callback;

public abstract class SimpleCallback<T> {

    public abstract void onSuccess(T t);

    public void onFailure(Exception e) {
    }

}
