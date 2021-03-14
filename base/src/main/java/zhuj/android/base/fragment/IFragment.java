package zhuj.android.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import zhuj.android.utils.helper.ViewHelper;


public abstract class IFragment extends Fragment {
    protected final String TAG = getClass().getSimpleName();
    protected ViewHelper mViewHelper;

    public abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutRes() != 0 ? inflater.inflate(getLayoutRes(), container, false) : super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void initViewHelper() {
        mViewHelper = new ViewHelper(requireView());
    }

    public ViewHelper getViewHelper() {
        if (mViewHelper == null) {
            initViewHelper();
        }
        return mViewHelper;
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return requireView().findViewById(id);
    }

}
