package zhuj.android.load;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.HashMap;

import zhuj.android.R;

public class LoadStateManager {
    public static final int LOADING = 1;
    public static final int LOAD_EMPTY = 2;
    public static final int LOAD_ERROR = 3;
    public static final int LOAD_MESSAGE = 4;
    public static final int LOADING_PROGRESS_MESSAGE = 5;

    private boolean isShow;
    private int showState;
    private View showView;
    private ViewGroup loadView;
    private final HashMap<Integer, Integer> mStateLayoutMap = new HashMap<>();

    public LoadStateManager() {
        initialize();
    }

    public LoadStateManager(ViewGroup loadView) {
        initialize();
        setLoadView(loadView);
    }

    private void initialize() {
        addStateLayout(LOADING, R.layout.loading);
        addStateLayout(LOAD_EMPTY, R.layout.load_empty);
        addStateLayout(LOAD_ERROR, R.layout.load_error);
        addStateLayout(LOAD_MESSAGE, R.layout.load_message);
        addStateLayout(LOADING_PROGRESS_MESSAGE, R.layout.loading_progress_message);
    }

    public int getCurrentState() {
        return showState;
    }

    public View getShowView() {
        return showView;
    }

    public void setText(int viewId, String text) {
        if (showView == null) {
            return;
        }
        TextView view = getShowView().findViewById(viewId);
        if (view == null) {
            return;
        }
        view.setText(text);
    }

    public TextView getTextView(int viewId) {
        if (showView == null) {
            return null;
        }
        return getShowView().findViewById(viewId);
    }

    public ImageView getImageView(int viewId) {
        if (showView == null) {
            return null;
        }
        return getShowView().findViewById(viewId);
    }


    public void showLoading() {
        showState(LOADING);
    }

    public void showLoadError() {
        showState(LOAD_ERROR);
    }

    public void showLoadEmpty() {
        showState(LOAD_EMPTY);
    }

    public void showLoadMessage() {
        showState(LOAD_MESSAGE);
    }

    public void showLoadingProgressMessage() {
        showState(LOADING_PROGRESS_MESSAGE);
    }

    public void setLoadView(ViewGroup loadView) {
        this.loadView = loadView;
        checkViewGroup();
    }

    public ViewGroup getLoadView() {
        return loadView;
    }

    public void addStateLayout(int stateId, @LayoutRes int layoutRes) {
        mStateLayoutMap.put(stateId, layoutRes);
    }

    private void checkViewGroup() {
        if (loadView == null) {
            throw new IllegalStateException("state root layout must be not null");
        }
    }

    private void checkState(int state) {
        if (!mStateLayoutMap.containsKey(state)) {
            throw new IllegalStateException("state is not find");
        }
        Integer res = mStateLayoutMap.get(state);
        if (res == null) {
            throw new IllegalStateException("state layout must be not null");
        }
    }

    public void showState(int state) {
        checkState(state);
        checkViewGroup();
        if (isShow) {
            if (showState == state) {
                return;
            }
            loadView.removeAllViews();
        }
        int layout = mStateLayoutMap.get(state);
        LayoutInflater.from(loadView.getContext()).inflate(layout, loadView, true);
        showView = loadView.getChildAt(0);
        loadView.setVisibility(View.VISIBLE);
        isShow = true;
        showState = state;
        Log.d("LoadStateManager", "show");
    }

    public void hide() {
        checkViewGroup();
        loadView.removeAllViews();
        loadView.setVisibility(View.GONE);
        isShow = false;
        showState = 0;
        Log.d("LoadStateManager", "hide");
    }

}
