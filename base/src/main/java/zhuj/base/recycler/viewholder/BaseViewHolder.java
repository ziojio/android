package zhuj.base.recycler.viewholder;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public abstract class BaseViewHolder<T> extends  DefaultViewHolder {

    public BaseViewHolder(@NonNull ViewGroup parent, int layoutId) {
        super(parent, layoutId);
    }

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void onBind(T data, int position);
}