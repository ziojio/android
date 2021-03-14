package zhuj.android.base.recyclerview.adapter;

import android.util.SparseIntArray;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import zhuj.android.base.recyclerview.listener.OnItemClickListener;


public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private RecyclerView mRecyclerView;

    protected ArrayList<T> data = new ArrayList<>();
    protected SparseIntArray layouts = new SparseIntArray();

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

    protected OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(Collection<T> data) {
        this.data = data != null && data.size() > 0 ? new ArrayList<>(data) : new ArrayList<>();
        super.notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    public void clear() {
        this.data.clear();
        super.notifyDataSetChanged();
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}