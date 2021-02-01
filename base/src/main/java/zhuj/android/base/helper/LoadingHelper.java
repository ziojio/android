package zhuj.android.base.helper;

import android.view.View;


import zhuj.android.Preconditions;

import zhuj.android.base.R;
import zhuj.android.helper.ViewHelper;

public class LoadingHelper {
    public static final int ID_LAYOUT_LOADING_ROOT = R.id.layout_loading_root;
    public static final int ID_LAYOUT_LOADING_PROGRESS = R.id.layout_loading_progress;
    public static final int ID_LAYOUT_LOAD_EMPTY = R.id.layout_load_empty;
    public static final int ID_LAYOUT_LOAD_ERROR = R.id.layout_load_error;
    public static final int ID_LAYOUT_CLICK_REFRESH = R.id.layout_click_refresh;

    private int curShowId;
    private final ViewHelper viewHelper;

    public LoadingHelper(View view) {
        Preconditions.checkNotNull(view);
        viewHelper = new  ViewHelper(view);
        Preconditions.checkNotNull(viewHelper.getView(ID_LAYOUT_LOADING_ROOT));
    }

    public  ViewHelper getViewHelper() {
        return viewHelper;
    }

    public void showById(int id) {
        if (curShowId == 0) {
            getViewHelper().setVisible(ID_LAYOUT_LOADING_ROOT, true);
        }
        if (curShowId != 0 && curShowId != id) {
            getViewHelper().setVisible(curShowId, false);
        }
        curShowId = id;
        getViewHelper().setVisible(curShowId, true);
    }

    public void hide() {
        if (curShowId != 0) {
            getViewHelper().setVisible(curShowId, false);
        }
        getViewHelper().setVisible(ID_LAYOUT_LOADING_ROOT, false);
    }

    public void showLoadingProgress() {
        showById(ID_LAYOUT_LOADING_PROGRESS);
    }

    public void showLoadError() {
        showById(ID_LAYOUT_LOAD_ERROR);
    }

    public void showLoadEmpty() {
        showById(ID_LAYOUT_LOAD_EMPTY);
    }

    public void showClickRefresh() {
        showById(ID_LAYOUT_CLICK_REFRESH);
    }


}
