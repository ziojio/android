package zui.widget.badge;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class BadgeUtils {
    private BadgeUtils() {
    }

    /**
     * 使用目标 View.getParent() 的父容器布局, 操作目标 View
     * 把目标 View 和 Badge 添加到 FrameLayout, 添加到原位置
     * ## 不是Activity embed View, View.getParent() == null, 使用 {@link #createBadgeLayout(View, BadgeDrawable) }
     * @param target        目标 View
     * @param badgeDrawable
     * @return
     */
    public static BadgeView bindTargetView(View target, BadgeDrawable badgeDrawable) {
        BadgeView badgeView = new BadgeView(target.getContext());
        badgeView.setText(badgeDrawable.toSpannable());
        badgeView.bindTargetView(target);
        return badgeView;
    }

    public static FrameLayout createBadgeLayout(View target, BadgeDrawable badgeDrawable) {
        FrameLayout badgeContainer = new FrameLayout(target.getContext());
        badgeContainer.setLayoutParams(target.getLayoutParams());

        target.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        badgeContainer.addView(target);

        BadgeView badgeView = new BadgeView(target.getContext());
        badgeView.setText(badgeDrawable.toSpannable());
        badgeContainer.addView(badgeView);

        return badgeContainer;
    }

    public static BadgeView bindTargetView(View target, BadgeDrawable... badgeDrawables) {
        BadgeView badgeView = new BadgeView(target.getContext());
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (BadgeDrawable badgeDrawable : badgeDrawables) {
            ssb.append(badgeDrawable == null ? " " : badgeDrawable.toSpannable());
        }
        badgeView.setText(new SpannableString(ssb));
        badgeView.bindTargetView(target);
        return badgeView;
    }
}
