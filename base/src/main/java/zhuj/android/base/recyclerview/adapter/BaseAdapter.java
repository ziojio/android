package zhuj.android.base.recyclerview.adapter;

import android.util.SparseIntArray;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final ArrayList<T> data = new ArrayList<>();
    // 存储 itemType 对应的 layout id
    private final SparseIntArray layouts = new SparseIntArray();

    /**
     * @param viewType 0 代表默认的单布局
     * @param layoutId
     */
    public void addViewType(int viewType, @LayoutRes int layoutId) {
        layouts.put(viewType, layoutId);
    }

    public int getLayoutRes(int viewType) {
        return layouts.get(viewType);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(Collection<T> data) {
        clear();
        addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    public void clear() {
        this.data.clear();
        super.notifyItemRangeRemoved(0, this.data.size());
    }

    public void add(T item) {
        if (item == null) return;
        int idx = data.size();
        data.add(item);
        super.notifyItemInserted(idx);
    }

    public void addAll(Collection<T> items) {
        if (items == null || items.isEmpty()) return;
        int idx = data.size();
        this.data.addAll(items);
        super.notifyItemRangeInserted(idx, items.size());
    }

}