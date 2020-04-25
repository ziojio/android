

public interface HttpCallback<T> {

    void onFailure(ErrorMessage e);

    void onSuccess(T body);
}
