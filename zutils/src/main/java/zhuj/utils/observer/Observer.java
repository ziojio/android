package zhuj.utils.observer;


public interface Observer<T> extends IObservable {
    void update(T t);
}
