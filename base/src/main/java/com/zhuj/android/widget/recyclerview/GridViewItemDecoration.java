package com.zhuj.android.widget.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridViewItemDecoration extends RecyclerView.ItemDecoration {
    int spanCount = -1;
    int itemSize = -1;
    int lineSpace = 0;
    int itemSpace = 0;
    int topMargin;
    int bottomMargin;

    public GridViewItemDecoration() {
    }

    public GridViewItemDecoration(int spanCount, int lineSpace, int itemSpace) {
        this.spanCount = spanCount;
        this.lineSpace = lineSpace;
        this.itemSpace = itemSpace;
    }

    public GridViewItemDecoration(int spanCount, int lineSpace) {
        this.spanCount = spanCount;
        this.lineSpace = lineSpace;
    }

    public GridViewItemDecoration(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    public GridViewItemDecoration setTopMargin(int topMargin) {
        this.topMargin = topMargin;
        return this;
    }

    public GridViewItemDecoration setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
        return this;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Logger.d("spanCount: " + spanCount);
        if (spanCount <= 0) {
            RecyclerView.LayoutManager g = parent.getLayoutManager();
            if (g instanceof GridLayoutManager) {
                spanCount = ((GridLayoutManager) g).getSpanCount();
            }
        }
        if (spanCount <= 0) return;// 不处理不是GridLayoutManager的情况

        RecyclerView.Adapter<?> adapter = parent.getAdapter();
        if (adapter == null) return;
        itemSize = adapter.getItemCount();
        if (itemSize <= 0) return;

        int index = parent.getChildAdapterPosition(view);
        // Logger.d("position: " + index);
        int start = 0, top = 0, end = 0, bottom = 0;
        if (index >= spanCount) {
            top = lineSpace;
        } else if (topMargin != 0) {
            top = topMargin;
        }
        if (itemSize < spanCount) {
            bottom = bottomMargin;
        } else if (index >= itemSize - itemSize % spanCount) {
            bottom = bottomMargin;
        }

        if(index % spanCount < spanCount - 1){
            end = itemSpace;
        }
        outRect.set(start, top, end, bottom);
        // Logger.d("outRect: " + outRect);
    }
}