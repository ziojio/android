package zhuj.android.base.dialog;

import android.view.View;

public class CustomLayoutDialog extends BaseDialogFragment {
    protected int layoutId;
    protected int[] viewIds;
    protected View.OnClickListener clickListener;

    public void setLayoutRes(int layoutId) {
        this.layoutId = layoutId;
    }

    public  CustomLayoutDialog setViewClick(View.OnClickListener click, int... ids) {
        viewIds = ids;
        clickListener = click;
        return this;
    }

    @Override
    public int getLayoutRes() {
        return layoutId;
    }

    @Override
    public void initView() {
        if (viewIds != null && viewIds.length > 0) {
            for (int id : viewIds) {
                View view = findViewById(id);
                if (view != null) {
                    view.setOnClickListener(clickListener);
                }
            }
        }
    }

}