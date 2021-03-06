package zhuj.android.utils.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public final class ViewUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private ViewUtils() {
        throw new Error("Do not need instantiate!");
    }

      /**
     * get descended views from parent.
     *
     * @param parent
     * @param filter          Type of views which will be returned.
     * @param includeSubClass Whether returned list will include views which are subclass of
     *                        filter or not.
     * @return
     */
    public static <T extends View> List<T> getDescendants(ViewGroup parent, Class<T> filter, boolean includeSubClass) {
        List<T> descendedViewList = new ArrayList<>();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            Class<? extends View> childsClass = child.getClass();
            if ((includeSubClass && filter.isAssignableFrom(childsClass))
                    || (!includeSubClass && childsClass == filter)) {
                descendedViewList.add(filter.cast(child));
            }
            if (child instanceof ViewGroup) {
                descendedViewList.addAll(getDescendants((ViewGroup) child,
                        filter, includeSubClass));
            }
        }
        return descendedViewList;
    }

    /**
     * Helps determine if the app is running in a Tablet context.
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * ??????activity?????????????????????
     * <p>@android:id/content ??????????????????FrameLayout</p>
     *
     * @param activity
     * @return
     */
    public static FrameLayout getContentView(@NonNull final Activity activity) {
        ViewGroup view = (ViewGroup) activity.getWindow().getDecorView();
        return view.findViewById(android.R.id.content);
    }

    /**
     * View????????????
     *
     * @param context
     * @param view
     * @param res
     */
    public static void setBackground(Context context, View view, int res) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), res);
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bmp);
        if (view != null) {
            view.setBackground(bd);
        }
    }

    /**
     * ??????????????????
     *
     * @param v
     */
    public static void recycleBackground(@NonNull View v) {
        Drawable d = v.getBackground();
        v.setBackgroundResource(0);//????????????????????????null?????????onDraw????????????????????????used a recycled bitmap??????
        if (d != null && d instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) d).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
            }
        }
        if (d != null) {
            d.setCallback(null);
        }
    }

    /**
     * ??????View,????????????ImageView?????????
     *
     * @param view
     */
    public static void clearImageView(@NonNull View view) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                clearImageView(parent.getChildAt(i));
            }
        } else if (view instanceof ImageView) {
            clearImgMemory((ImageView) view);
        }
    }

    /**
     * ?????????????????????
     */
    public static void clearImgMemory(@NonNull ImageView imageView) {
        Drawable d = imageView.getDrawable();
        if (d != null && d instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) d).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
            }
        }
        imageView.setImageBitmap(null);
        if (d != null) {
            d.setCallback(null);
        }
    }

    //=====================View ????????????==============================//

    /**
     * ????????????????????????
     *
     * @param view   ??????
     * @param isShow ????????????
     */
    public static void setVisibility(View view, boolean isShow) {
        if (view != null) {
            view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * ????????????????????????
     *
     * @param view       ??????
     * @param visibility
     */
    public static void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }


    /**
     * ????????????????????????
     *
     * @param view    ??????
     * @param enabled ????????????
     */
    public static void setEnabled(View view, boolean enabled) {
        if (view != null) {
            view.setEnabled(enabled);
            if (view instanceof EditText) {
                view.setFocusable(enabled);
                view.setFocusableInTouchMode(enabled);
            }
        }
    }

    /**
     * ?????????????????????
     *
     * @param view ??????
     * @param text ??????
     */
    public static void setText(TextView view, String text) {
        if (view != null) {
            view.setText(text);
        }
    }

    /**
     * ?????????????????????
     *
     * @param view   ??????
     * @param textId ????????????
     */
    public static void setText(TextView view, @StringRes int textId) {
        if (view != null) {
            view.setText(textId);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param view    ??????
     * @param colorId ????????????
     */
    public static void textColorId(TextView view, @ColorRes int colorId) {
        if (view != null) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), colorId));
        }
    }

    /**
     * ???????????????????????????
     *
     * @param view    ??????
     * @param imageId ????????????ID
     */
    public static void setImageResource(ImageView view, @DrawableRes int imageId) {
        if (view != null) {
            view.setImageResource(imageId);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param view     ??????
     * @param drawable ????????????
     */
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        if (view != null) {
            view.setImageDrawable(drawable);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param view ??????
     * @param uri  ????????????
     */
    public static void setImageURI(ImageView view, Uri uri) {
        if (view != null) {
            view.setImageURI(uri);
        }
    }

    /**
     * ?????????????????????
     *
     * @param view  ??????
     * @param level ????????????
     */
    public static void setImageLevel(ImageView view, int level) {
        if (view != null) {
            view.setImageLevel(level);
        }
    }

    /**
     * ???????????????
     *
     * @param view ??????
     * @param tint ??????
     */
    public static void setImageTint(ImageView view, ColorStateList tint) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setImageTintList(tint);
            }
        }
    }

    /**
     * ???????????????????????????
     *
     * @param view    ??????
     * @param isCheck ????????????
     */
    public static void setChecked(CompoundButton view, boolean isCheck) {
        if (view != null) {
            view.setChecked(isCheck);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param view                  ??????
     * @param checkedChangeListener ????????????
     */
    public static void setOnCheckedChangeListener(CompoundButton view, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        if (view != null) {
            view.setOnCheckedChangeListener(checkedChangeListener);
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param view                  ??????
     * @param isCheck               ????????????
     * @param checkedChangeListener ????????????
     */
    public static void setCheckedSilent(CompoundButton view, boolean isCheck, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        if (view != null) {
            view.setOnCheckedChangeListener(null);
            view.setChecked(isCheck);
            view.setOnCheckedChangeListener(checkedChangeListener);
        }
    }


    //=====================??????Padding==============================//

    /**
     * ???????????????????????????
     *
     * @param view       ?????????????????????????????????????????????????????????
     * @param expendSize ???????????????????????????xp???????????????
     */
    public static void expendTouchArea(final View view, final int expendSize) {
        if (view != null) {
            final View parentView = (View) view.getParent();

            parentView.post(new Runnable() {
                @Override
                public void run() {
                    //?????????????????????????????????
                    Rect rect = new Rect();
                    view.getHitRect(rect); //???????????????????????????????????????rect?????????????????????UI??????????????????????????????????????????????????????
                    rect.left -= expendSize;
                    rect.top -= expendSize;
                    rect.right += expendSize;
                    rect.bottom += expendSize;
                    parentView.setTouchDelegate(new TouchDelegate(rect, view));
                }
            });
        }
    }

    /**
     * ???????????????padding
     *
     * @param view    ??????
     * @param padding
     */
    public static void setPadding(View view, int padding) {
        if (view != null) {
            view.setPadding(padding, padding, padding, padding);
        }
    }

    /**
     * ??? View ?????? paddingLeft
     *
     * @param view  ?????????????????? View
     * @param value ????????????
     */
    public static void setPaddingLeft(View view, int value) {
        if (value != view.getPaddingLeft()) {
            view.setPadding(value, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /**
     * ??? View ?????? paddingTop
     *
     * @param view  ?????????????????? View
     * @param value ????????????
     */
    public static void setPaddingTop(View view, int value) {
        if (value != view.getPaddingTop()) {
            view.setPadding(view.getPaddingLeft(), value, view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /**
     * ??? View ?????? paddingRight
     *
     * @param view  ?????????????????? View
     * @param value ????????????
     */
    public static void setPaddingRight(View view, int value) {
        if (value != view.getPaddingRight()) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), value, view.getPaddingBottom());
        }
    }

    /**
     * ??? View ?????? paddingBottom
     *
     * @param view  ?????????????????? View
     * @param value ????????????
     */
    public static void setPaddingBottom(View view, int value) {
        if (value != view.getPaddingBottom()) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), value);
        }
    }

    //=====================??????Margin==============================//

    /**
     * ??????view???margin<br>
     * ???????????? ??????start,top,end,bottom ??????????????? ??????????????????????????????????????????requestlayout
     *
     * @param view   ??????
     * @param start  ????????????
     * @param top    ????????????
     * @param end    ????????????
     * @param bottom ????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setViewMargin(View view, int start, int top, int end, int bottom) {
        ViewGroup.MarginLayoutParams lp = getLayoutParams(view, ViewGroup.MarginLayoutParams.class);
        if (lp != null) {
            int oldStart = lp.getMarginStart();
            int oldTop = lp.topMargin;
            int oldEnd = lp.getMarginEnd();
            int oldBottom = lp.bottomMargin;

            if (oldStart != start || oldTop != top || oldEnd != end || oldBottom != bottom) {
                lp.setMarginStart(start);
                lp.topMargin = top;
                lp.setMarginEnd(end);
                lp.bottomMargin = bottom;
                setLayoutParams(view, lp);
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param view  ??????
     * @param clazz ??????????????????
     * @param <T>   ??????????????????
     * @return ??????????????????
     */
    public static <T> T getLayoutParams(View view, Class<? extends ViewGroup.LayoutParams> clazz) {
        if (view == null || clazz == null) {
            return null;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (clazz.isInstance(layoutParams)) {
            return (T) layoutParams;
        }
        return null;
    }

    /**
     * ????????????????????????
     *
     * @param view   ??????
     * @param params ????????????
     */
    public static void setLayoutParams(View view, ViewGroup.LayoutParams params) {
        if (view != null && params != null) {
            view.setLayoutParams(params);
        }
    }

}
