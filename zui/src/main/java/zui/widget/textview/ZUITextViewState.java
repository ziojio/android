package zui.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import zui.R;


public class ZUITextViewState extends ZUITextViewIcon {

    public ZUITextViewState(Context context) {
        this(context, null, 0);
    }

    public ZUITextViewState(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZUITextViewState(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ZUITextViewState, 0, 0);
        ta.recycle();
    }

}
