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
    private int iconSize = 0;
    private int textSize = 0;
    private LinearLayout linearLayout;
    private GifImageView imageView;
    private TextView textView;

    public SimpleProgressDialog() {
    }

    public void setOrientation(int orientation) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putInt("orientation", orientation);
        setArguments(bundle);
    }

    public void setIconSize(int iconSize) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putInt("iconSize", iconSize);
        setArguments(bundle);
    }

    public void setTextSize(int textSize) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putInt("textSize", textSize);
        setArguments(bundle);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_simple_progress;
    }

    @Override
    public void initView() {
        linearLayout = findViewById(R.id.linearlayout);
        imageView = findViewById(R.id.image_icon);
        textView = findViewById(R.id.text_message);
        linearLayout.setOrientation(orientation);
        if (iconSize > 0) {
            setViewLayoutParams(imageView, iconSize, iconSize);
        }
        if (textSize > 0) {
            textView.setTextSize(textSize);
        }
        if (orientation == LinearLayout.HORIZONTAL) {
            setViewMargin(textView, 20, 0, 0, 0);
        } else if (orientation == LinearLayout.VERTICAL) {
            setViewMargin(textView, 0, 20, 0, 0);
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
        }
    }


}