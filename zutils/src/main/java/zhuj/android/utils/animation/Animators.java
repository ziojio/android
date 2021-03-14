package zhuj.android.utils.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

public class Animators {
    public static final int SHORT_DURATION = 300;
    public static final int MEDIUM_DURATION = 600;
    public static final int LONG_DURATION = 1000;

    public static final int DEFAULT_DURATION = SHORT_DURATION;

    public static abstract class AbstractAnimatorListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

    public static ValueAnimator.AnimatorUpdateListener ViewUpdateListener;
    ViewPropertyAnimator viewPropertyAnimator;

    private Animators() {
    }



    /**
     * 动画生硬
     *
     * @param v
     * @param values
     */
    public static ValueAnimator scaleAnimator(View v, int... values) {
        ValueAnimator animator = ValueAnimator.ofInt(values);
        animator.setDuration(DEFAULT_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    /**
     * 动画生硬
     *
     * @param v
     * @param values
     */
    public static ValueAnimator heightAnimator(final View v, int... values) {
        ValueAnimator animator = ValueAnimator.ofInt(values);
        animator.setDuration(DEFAULT_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public static ValueAnimator widthAnimator(final View v, int... values) {
        ValueAnimator animator = ValueAnimator.ofInt(values);
        animator.setDuration(DEFAULT_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.width = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

}
