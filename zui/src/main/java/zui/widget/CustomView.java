package zui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * *********************************************************************************************
     * defStyleAttr：这个是当前Theme中的一个attribute，是指向style的一个引用，当layout.xml和xml中的style中都没有为View指定属性时，
     * 会从Theme中这个attribute指向的Style中查找相应的属性值
     * attribute name定义在attrs.xml文件中，为引用格式，在styles.xml文件的Theme中添加attribute name指向一个的 style
     * *********************************************************************************************
     *
     * @param context
     * @param attrs        在XML文件中定义的属性集
     * @param defStyleAttr 当前主题中的一个属性，其中包含对样式资源的引用，该样式资源为视图提供了默认值。可以为0表示不查找默认值。
     * @param defStyleRes  为视图提供默认值的样式资源的资源标识符，仅在defStyleAttr为0或在主题中找不到时使用。可以为0以不查找默认值。
     */
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

//        context.getTheme().obtainStyledAttributes(attrs, R.styleable.View, defStyleAttr, defStyleRes);
    }

}
