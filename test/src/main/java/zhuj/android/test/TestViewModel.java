package zhuj.android.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {

    private final MutableLiveData<String> stringLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getStringLiveData() {
        return stringLiveData;
    }

    public TestViewModel setStringLiveData(String liveData) {
        this.stringLiveData.setValue(liveData);
        return this;
    }
}
