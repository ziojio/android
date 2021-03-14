package zhuj.android.base.dialog;


import zhuj.android.base.R;

public class ShowProgressDialog extends BaseDialogFragment {
    protected int layoutId;

    public void setLayoutRes(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public int getLayoutRes() {
        return layoutId == 0 ? R.layout.dialog_show_progress : layoutId;
    }

    @Override
    public void initView() {
    }

}