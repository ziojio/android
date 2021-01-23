package zhuj.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

public class ViewModelApplication extends Application implements ViewModelStoreOwner,
        HasDefaultViewModelProviderFactory {

    private ViewModelStore mAppViewModelStore;
    private ViewModelProvider mAppViewModelProvider;
    private ViewModelProvider.Factory mDefaultFactory;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppViewModelStore = new ViewModelStore();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    @NonNull
    public ViewModelProvider getViewModelProvider() {
        if (mAppViewModelProvider == null) {
            mAppViewModelProvider = new ViewModelProvider(this);
        }
        return mAppViewModelProvider;
    }

    @NonNull
    @Override
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (mDefaultFactory == null) {
            mDefaultFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this);
        }
        return mDefaultFactory;
    }
}
