package zhuj.base.fragment;


import android.view.View;

public abstract class BaseFragment extends IFragment   {

    protected void addClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    protected void addClickListener(View.OnClickListener listener, int... viewIds) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

}
