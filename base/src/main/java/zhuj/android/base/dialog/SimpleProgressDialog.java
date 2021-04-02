package zhuj.android.base.dialog;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import pl.droidsonroids.gif.GifImageView;
import zhuj.android.base.R;


/**
 * 简单的进度提示， 只有一个 icon 和 text
 */
public class SimpleProgressDialog extends BaseDialogFragment {
    private int orientation = LinearLayout.HORIZONTAL;
    private String message;
    private int iconSize;
    private int textSize;
    private int textMaxWidth;
    private int iconTextPadding = 20;

    public SimpleProgressDialog() {
    }

    public void setOrientation(int orientation) {
        Bundle bundle = getArguments() != null ? getArguments() : new Bundle();
        bundle.putInt("orientation", orientation);
        setArguments(bundle);
    }

    public void setIconSize(int iconSize) {
        Bundle bundle = getArguments() != null ? getArguments() : new Bundle();
        bundle.putInt("iconSize", iconSize);
        setArguments(bundle);
    }

    public void setMessage(String message) {
        Bundle bundle = getArguments() != null ? getArguments() : new Bundle();
        bundle.putString("message", message);
        setArguments(bundle);
    }

    public void setTextSize(int textSize) {
        Bundle bundle = getArguments() != null ? getArguments() : new Bundle();
        bundle.putInt("textSize", textSize);
        setArguments(bundle);
    }

    public void setTextMaxWidth(int textMaxWidth) {
        Bundle bundle = getArguments() != null ? getArguments() : new Bundle();
        bundle.putInt("textMaxWidth", textMaxWidth);
        setArguments(bundle);
    }

    public void setIconTextPadding(int iconTextPadding) {
        Bundle bundle = getArguments() != null ? getArguments() : new Bundle();
        bundle.putInt("iconTextPadding", iconTextPadding);
        setArguments(bundle);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_simple_progress;
    }

    @Override
    public void initView() {
        LinearLayout linearLayout = findViewById(R.id.linearlayout);
        GifImageView imageView = findViewById(R.id.image_icon);
        TextView textView = findViewById(R.id.text_message);
        if (iconSize > 0) {
            setViewLayoutParams(imageView, iconSize, iconSize);
        }
        if (textSize > 0) {
            textView.setTextSize(textSize);
        }
        if (textMaxWidth > 0) {
            textView.setMaxWidth(textMaxWidth);
        }
        textView.setText(message);

        if (orientation != LinearLayout.HORIZONTAL) {
            linearLayout.setOrientation(orientation);
        }
        if (orientation == LinearLayout.HORIZONTAL) {
            setViewMargin(textView, iconTextPadding, 0, 0, 0);
        } else if (orientation == LinearLayout.VERTICAL) {
            setViewMargin(textView, 0, iconTextPadding, 0, 0);
        }
    }

    private void setViewLayoutParams(View view, int width, int height) {
        if (view != null) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params != null) {
                params.width = width;
                params.height = height;
                view.setLayoutParams(params);
            }
        }
    }

    private void setViewMargin(View view, int start, int top, int end, int bottom) {
        if (view != null) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
                marginLayoutParams.setMargins(start, top, end, bottom);
                view.setLayoutParams(marginLayoutParams);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            orientation = bundle.getInt("orientation", orientation);
            textSize = bundle.getInt("textSize", textSize);
            iconSize = bundle.getInt("iconSize", iconSize);
            textMaxWidth = bundle.getInt("textMaxWidth", textMaxWidth);
            iconTextPadding = bundle.getInt("iconTextPadding", iconTextPadding);
            message = bundle.getString("message");
        }
    }


}