package zhuj.android.test.base.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    private LiveData<String> stringLiveData;
    private MutableLiveData<Integer> integerMutableLiveData;

}
