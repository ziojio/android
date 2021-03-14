
package zhuj.android.test.viewmodel;


import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class ViewModelActivity extends AppCompatActivity {

    private ViewModelProvider mActivityProvider;
    private ViewModelProvider.Factory mFactory;

    public boolean isDebug() {
        return getApplicationContext().getApplicationInfo() != null &&
                (getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    protected ViewModelProvider getApplicationViewModelProvider() {
        checkApplication(this);
        return ((ViewModelApplication) getApplication()).getViewModelProvider();
    }

    protected <T extends ViewModel> T getApplicationViewModel(@NonNull Class<T> modelClass) {
        return getApplicationViewModelProvider().get(modelClass);
    }

    private void checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
    }

}
