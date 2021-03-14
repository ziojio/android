package zhuj.android.base.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import zhuj.android.base.R;
import zhuj.android.utils.helper.ViewHelper;


public abstract class IDialogFragment extends DialogFragment {
    protected final String TAG = getClass().getSimpleName();

    protected DialogInterface.OnCancelListener onCancelListener;
    protected DialogInterface.OnDismissListener onDismissListener;
    protected ViewHelper mViewHelper;


    public IDialogFragment() {
        setStyle(STYLE_NO_TITLE, R.style.AppDialog);
    }

    /**
     * 自己创建 Dialog 时, return 0, 并且 Override onCreateDialog
     */
    public abstract int getLayoutRes();

    public abstract void initView();

    public void initWindow() {
    }

    protected Bundle getArgumentsNotNull() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle;
        }
        return new Bundle();
    }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWindow();
    }

    public ViewHelper getViewHelper() {
        if (mViewHelper == null) {
            mViewHelper = new ViewHelper(requireView());
        }
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
