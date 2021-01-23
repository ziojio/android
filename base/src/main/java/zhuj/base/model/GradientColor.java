package zhuj.base.model;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.Log;


public class GradientColor {
    int id;
    int tag;
    String startColor;
    String centerColor;
    String endColor;
    float radius;
    int angle; // Angle of the gradient, used only with linear gradient. Must be a multiple of 45 in the range [0, 315]

    GradientDrawable.Orientation orientation;
    GradientDrawable drawable;

    public GradientDrawable getGradientColorDrawable() {
        if (drawable != null) {
            return drawable;
        }
        if (TextUtils.isEmpty(startColor) || TextUtils.isEmpty(startColor)) {
            throw new IllegalStateException("没有开始颜色或结束颜色");
        }

        GradientDrawable drawable = new GradientDrawable();
        if (radius != 0) {
            drawable.setCornerRadius(radius);
        }
        if (angle != 0) {
            drawable.setOrientation(getOrientation());
        }
        if (TextUtils.isEmpty(centerColor)) {
            drawable.setColors(new int[]{Color.parseColor(startColor), Color.parseColor(endColor)});
        } else {
            drawable.setColors(new int[]{Color.parseColor(startColor), Color.parseColor(centerColor), Color.parseColor(endColor)});
        }
        return drawable;
    }

    private GradientDrawable.Orientation getOrientation() {
        if (angle % 45 != 0) {
            Log.w("GradientColor", "Linear gradient requires 'angle' attribute "
                    + "to be a multiple of 45");
        }
        switch (angle) {
            case 0:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 45:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 90:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 135:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 180:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 225:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case 270:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 315:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
            default:
                // Should not get here as exception is thrown above if angle is not multiple
                // of 45 degrees
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
        }
        return orientation;
    }

    private int getAngleFromOrientation(GradientDrawable.Orientation orientation) {
        if (orientation != null) {
            switch (orientation) {
                default:
                case LEFT_RIGHT:
                    return 0;
                case BL_TR:
                    return 45;
                case BOTTOM_TOP:
                    return 90;
                case BR_TL:
                    return 135;
                case RIGHT_LEFT:
                    return 180;
                case TR_BL:
                    return 225;
                case TOP_BOTTOM:
                    return 270;
                case TL_BR:
                    return 315;
            }
        } else {
            return 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartColor() {
        return startColor;
    }

    public void setStartColor(String startColor) {
        this.startColor = startColor;
    }

    public String getCenterColor() {
        return centerColor;
    }

    public void setCenterColor(String centerColor) {
        this.centerColor = centerColor;
    }

    public String getEndColor() {
        return endColor;
    }

    public void setEndColor(String endColor) {
        this.endColor = endColor;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "GradientColor{" +
                "id=" + id +
                ", tag=" + tag +
                ", startColor='" + startColor + '\'' +
                ", centerColor='" + centerColor + '\'' +
                ", endColor='" + endColor + '\'' +
                ", radius=" + radius +
                ", angle=" + angle +
                ", drawable=" + drawable +
                '}';
    }
}
