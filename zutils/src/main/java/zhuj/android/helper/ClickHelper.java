package zhuj.android.helper;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;

import zhuj.android.Preconditions;


public class ClickHelper {

    public static void addClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    public static void addClickListener(View rootView, View.OnClickListener listener, int... viewIds) {
        View view = rootView;
        if (view == null) {
            if (listener instanceof Activity) {
                view = ((Activity) listener).getWindow().getDecorView();
            } else if (listener instanceof Fragment) {
                view = ((Fragment) listener).requireView();
            } else if (listener instanceof android.app.Fragment) {
                view = ((android.app.Fragment) listener).getView();
            } else if (listener instanceof PopupWindow) {
                view = ((PopupWindow) listener).getContentView();
            } else if (listener instanceof Dialog) {
                Window window = ((Dialog) listener).getWindow();
                if (window != null) {
                    view = window.getDecorView();
                }
            }
        }
        Preconditions.checkNotNull(view, "rootView must be not null");
        for (int id : viewIds) {
            view.findViewById(id).setOnClickListener(listener);
        }
    }

}