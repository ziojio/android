package zhuj.android.base.recyclerview.viewholder;

import android.util.SparseIntArray;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractViewModel<MODEL, VH extends RecyclerView.ViewHolder> {
    protected final SparseIntArray layouts = new SparseIntArray();
    public MODEL model;
    public VH viewHolder;

    public int getItemViewType(int position) {
        return getLayoutRes();
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    public MODEL getModel() {
        return model;
    }

    public void setModel(MODEL model) {
        this.model = model;
    }

    public VH getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(VH viewHolder) {
        this.viewHolder = viewHolder;
    }

    public abstract void onBindView(RecyclerView.Adapter<?> adapter, int position);

}