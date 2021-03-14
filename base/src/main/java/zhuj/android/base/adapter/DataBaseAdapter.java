package zhuj.android.base.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class DataBaseAdapter<T> extends BaseAdapter {
    protected List<T> dataList;

    public DataBaseAdapter() {
        dataList = new ArrayList<>();
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
        super.notifyDataSetChanged();
    }
    public List<T> getDataList() {
        return dataList;
    }
    public void addAll(List<T> t) {
        if (t == null || t.size() == 0) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        this.dataList.addAll(t);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
