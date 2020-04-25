
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;

import com.zhuj.android.view.R;


public class BottomDialog extends Dialog {

    public BottomDialog(Context context, @LayoutRes int resLayout) {
        super(context, R.style.dialog_bottom);
        setContentView(resLayout);
    }

    public void addOnClickListener(int[] viewId, View.OnClickListener clickListener) {
        for (int id : viewId) {
            findViewById(id).setOnClickListener(clickListener);
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
