package zhuj.android.zui.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import androidx.annotation.Nullable;

public class AttrHelper {

    private static TypedValue sTmpValue;

    private static void requireTypedValue() {
        if (sTmpValue == null) {
            sTmpValue = new TypedValue();
        }
    }

    @Nullable
    public static String getAttrString(Context context, int attrRes) {
        if (sTmpValue == null) sTmpValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(attrRes, sTmpValue, true)) {
            return null;
        }
        CharSequence str = sTmpValue.string;
        return str == null ? null : str.toString();
    }

    @Nullable
    public static String getAttrString(Context context, int attrRes, String defValue) {
        if (sTmpValue == null) sTmpValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(attrRes, sTmpValue, true)) {
            return defValue;
        }
        CharSequence str = sTmpValue.string;
        return str == null ? null : str.toString();
    }

    public static int getAttrInt(Context context, int attrRes) {
        if (sTmpValue == null) sTmpValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, sTmpValue, true);
        return sTmpValue.data;
    }

    public static int getAttrInt(Context context, int attrRes, int defValue) {
        if (sTmpValue == null) sTmpValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(attrRes, sTmpValue, true)) {
            return defValue;
        }
        return sTmpValue.data;
    }

    public static float getAttrFloatValue(Context context, int attr) {
        return getAttrFloatValue(context.getTheme(), attr, 0);
    }

    public static float getAttrFloatValue(Context context, int attr, float defValue) {
        return getAttrFloatValue(context.getTheme(), attr, defValue);
    }

    public static float getAttrFloatValue(Resources.Theme theme, int attr, float defValue) {
        if (sTmpValue == null) sTmpValue = new TypedValue();

        if (!theme.resolveAttribute(attr, sTmpValue, true)) {
            return defValue;
        }
        return sTmpValue.getFloat();
    }

    public static int getAttrColor(Context context, int attrRes) {
        return getAttrColor(context.getTheme(), attrRes, 0);
    }

    public static int getAttrColor(Context context, int attrRes, int defValue) {
        return getAttrColor(context.getTheme(), attrRes, defValue);
    }

    public static int getAttrColor(Resources.Theme theme, int attr, int defValue) {
        if (sTmpValue == null) sTmpValue = new TypedValue();
        if (!theme.resolveAttribute(attr, sTmpValue, true)) {
            return defValue;
        }
        if (sTmpValue.type == TypedValue.TYPE_ATTRIBUTE) {
            return getAttrColor(theme, sTmpValue.data, defValue);
        }
        return sTmpValue.data;
    }
}
