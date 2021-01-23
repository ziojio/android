package zhuj.android.app.ui.dialog;


import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import zhuj.base.dialog.BaseDialogFragment;


public class CenterDialog extends BaseDialogFragment {

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initWindow() {
        Window window = requireDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度自适应，宽度全屏
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

}
