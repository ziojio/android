// package zhuj.base;
//
// import java.lang.ref.WeakReference;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;
//
// public abstract class ObservableWeakReference<T>{
//     /**
//      * 对象锁
//      */
//     private final Object mLock = new Object();
//     /**
//      * The list of observers.  An observer can be in the list at most
//      * once and will never be null.
//      */
//     private ArrayList<WeakReference<T>> mObservers = new ArrayList<>();
//
//     /**
//      * @param observer the observer to register
//      */
//     public boolean register(T observer) {
//         if (observer != null) {
//             WeakReference<T> obs = new WeakReference<>(observer);
//             return mObservers.add(obs);
//         } else {
//             return false;
//         }
//     }
//
//     /**
//      * @param observer the observer to unregister
//      */
//     public boolean unregisterObserver(T observer) {
//         if (observer != null) {
//             synchronized (mLock) {
//                 Iterator<WeakReference<T>> it = mObservers.iterator();
//                 while (it.hasNext()) {
//                     T sb = it.next().get();
//                     if (sb == observer) {
//                         it.remove();
//                         return true;
//                     }
//                 }
//             }
//         }
//         return false;
//     }
//
//     public synchronized void unregisterAllObserver() {
//             mObservers.clear();
//     }
//
//     public synchronized int countObservers() {
//         return mObservers.size();
//     }
// }
