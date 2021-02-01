package zhuj.android.base.livedata;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;

import java.math.BigDecimal;

public class StockLiveData extends LiveData<BigDecimal> {
    private static StockLiveData sInstance;
    private StockManager stockManager;

    private SimplePriceListener listener = new SimplePriceListener() {
        @Override
        public void onPriceChanged(BigDecimal price) {
            StockLiveData.this.setValue(price);
        }
    };

    @MainThread
    public static StockLiveData get(String symbol) {
        if (sInstance == null) {
            sInstance = new StockLiveData(symbol);
        }
        return sInstance;
    }

    private StockLiveData(String symbol) {
        stockManager = new StockManager(symbol);
    }

    @Override
    protected void onActive() {
        // 在活动的时候，可以让外部更新 live data 的值
        stockManager.requestPriceUpdates(listener);
    }

    @Override
    protected void onInactive() {
        stockManager.removeUpdates(listener);
    }

    private abstract static class SimplePriceListener {
        public abstract void onPriceChanged(BigDecimal price);
    }

    private static class StockManager {
        public StockManager(String symbol) {

        }

        public void requestPriceUpdates(SimplePriceListener listener) {

        }

        public void removeUpdates(SimplePriceListener listener) {

        }
    }
}