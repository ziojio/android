package zhuj.android.base.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import zhuj.android.utils.helper.ViewHelper;


public abstract class BottomDialog extends BottomSheetDialogFragment {
    protected final String TAG = getClass().getSimpleName();

    protected DialogInterface.OnCancelListener onCancelListener;
    protected DialogInterface.OnDismissListener onDismissListener;
    protected ViewHelper mViewHelper;

    public BottomDialog() {
        // setStyle(STYLE_NO_TITLE, R.style.ZUI_Dialog);
    }

    /**
     * 自己创建 Dialog 时, return 0, 并且 Override onCreateDialog
     */
    protected abstract int getLayoutRes();

    /**
     *
     */
    protected abstract void initWindow();

    /**
     * This method will be called after {@link #onCreate(Bundle)} and before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * DialogFragment own the {@link Dialog#setOnCancelListener } and {@link Dialog#setOnDismissListener} callbacks.
     * You must not set them yourself. override {@link #onCancel(DialogInterface)} and {@link #onDismiss(DialogInterface)}.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutRes() != 0 ? inflater.inflate(getLayoutRes(), container, false) : super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewHelper();
        initWindow();
    }

    protected void initViewHelper() {
        mViewHelper = new ViewHelper(requireView());
    }

    public ViewHelper getViewHelper() {
        return mViewHelper;
    }


    protected <T extends View> T findViewById(int id) {
        return requireView().findViewById(id);
    }

    public void show(FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(), this.toString());
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener cancelListener) {
        this.onCancelListener = cancelListener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.onDismissListener = dismissListener;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (this.onCancelListener != null) {
            onCancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

}