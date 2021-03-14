package zhuj.android.base.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import zhuj.android.base.R;


public class SimpleMessageDialog extends BaseDialogFragment {

    View.OnClickListener onConfirmListener;
    View.OnClickListener onCancelListener;

    String title;
    String content;
    String cancel;
    String confirm;
    boolean showConfirm = true;
    boolean showCancel = false;
    boolean showTitle = true;

    ViewGroup layout_button;
    TextView button_confirm;
    TextView button_cancel;
    TextView text_title;
    TextView text_content;
    View divider_title;
    View divider_content;
    View divider_button;

    public void setTitle(String title) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putString("title", title);
        setArguments(bundle);
    }
    public void setContent(String content) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putString("content", content);
        setArguments(bundle);
    }

    public void setCancel(String cancel) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putString("cancel", cancel);
        bundle.putBoolean("showCancel", true);
        setArguments(bundle);
    }

    public void setConfirm(String confirm) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putString("confirm", confirm);
        bundle.putBoolean("showConfirm", true);
        setArguments(bundle);
    }

    public void setShowConfirm(boolean showConfirm) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putBoolean("showConfirm", showConfirm);
        setArguments(bundle);
    }

    public void setShowCancel(boolean showCancel) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putBoolean("showCancel", showCancel);
        setArguments(bundle);
    }

    public void setShowTitle(boolean showTitle) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putBoolean("showTitle", showTitle);
        setArguments(bundle);
    }

    public void setOnConfirmListener(View.OnClickListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public void setOnCancelListener(View.OnClickListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            content = bundle.getString("content");
            confirm = bundle.getString("confirm");
            cancel = bundle.getString("cancel");
            showConfirm = bundle.getBoolean("showConfirm", showConfirm);
            showCancel = bundle.getBoolean("showCancel", showCancel);
            showTitle = bundle.getBoolean("showTitle", showTitle);
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_simple_message;
    }

    @Override
    public void initView() {
        layout_button = findViewById(R.id.layout_button);
        button_confirm = findViewById(R.id.button_confirm);
        button_cancel = findViewById(R.id.button_cancel);
        text_title = findViewById(R.id.text_title);
        text_content = findViewById(R.id.text_content);

        divider_title = findViewById(R.id.divider_title);
        divider_content = findViewById(R.id.divider_content);
        divider_button = findViewById(R.id.divider_button);

        if (title != null) {
            text_title.setText(title);
        }
        text_content.setText(content);
        if (cancel != null) {
            button_cancel.setText(cancel);
        }
        if (confirm != null) {
            button_confirm.setText(confirm);
        }

        showButton(showConfirm, showCancel);
        showTitle(showTitle);

        button_confirm.setOnClickListener(v -> {
            if (onConfirmListener != null) {
                onConfirmListener.onClick(v);
            }
            dismissAllowingStateLoss();
        });
        button_cancel.setOnClickListener(v -> {
            if (onCancelListener != null) {
                onCancelListener.onClick(v);
            }
            dismissAllowingStateLoss();
        });
    }


    void showButton(boolean showConfirm, boolean showCancel) {
        if (showCancel && showConfirm) {
            button_cancel.setVisibility(View.VISIBLE);
            button_confirm.setVisibility(View.VISIBLE);
            divider_button.setVisibility(View.VISIBLE);
            button_cancel.setBackgroundResource(R.drawable.shape_rect_bottom_left_round_16dp_white);
            button_confirm.setBackgroundResource(R.drawable.shape_rect_bottom_right_round_16dp_white);
        } else if (showCancel || showConfirm) {
            if (showCancel) {
                button_cancel.setVisibility(View.VISIBLE);
                button_cancel.setBackgroundResource(R.drawable.shape_rect_bottom_round_16dp_white);

                button_confirm.setVisibility(View.GONE);
            } else {
                button_confirm.setVisibility(View.VISIBLE);
                button_confirm.setBackgroundResource(R.drawable.shape_rect_bottom_round_16dp_white);

                button_cancel.setVisibility(View.GONE);
            }
            divider_button.setVisibility(View.GONE);
        } else {
            button_cancel.setVisibility(View.GONE);
            button_confirm.setVisibility(View.GONE);
            divider_button.setVisibility(View.GONE);
            layout_button.setVisibility(View.GONE);
        }
    }

    void showTitle(boolean show) {
        if (show) {
            text_title.setVisibility(View.VISIBLE);
            divider_title.setVisibility(View.VISIBLE);
        } else {
            text_title.setVisibility(View.GONE);
            divider_title.setVisibility(View.GONE);
        }
    }
}