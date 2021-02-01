package zhuj.android.base.recycler.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    int spanCount = 0;
    int itemSize = 0;
    int lineSpace = 0;
    int itemSpace = 0;
    int topMargin;
    int bottomMargin;



    public GridItemDecoration() {
    }

    public GridItemDecoration setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
        return this;
    }

    public GridItemDecoration setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
        return this;
    }

    public GridItemDecoration setTopMargin(int topMargin) {
        this.topMargin = topMargin;
        return this;
    }

    public GridItemDecoration setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
        return this;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Logger.d("spanCount: " + spanCount);
        if (spanCount == 0) {
            RecyclerView.LayoutManager g = parent.getLayoutManager();
            if (g instanceof GridLayoutManager) {
                spanCount = ((GridLayoutManager) g).getSpanCount();
            }
        }
        if (spanCount == 0) return;

        RecyclerView.Adapter<?> g = parent.getAdapter();
        itemSize = g != null ? g.getItemCount() : 0;
        if (itemSize == 0) return;

        int index = parent.getChildAdapterPosition(view);
         // Logger.d("position: " + view.getWidth());
         // Logger.d("position: " + index);
        int start = 0, top = 0, end = 0, bottom = 0;
        if (index >= spanCount) {
            top = lineSpace;
        } else {
            top = topMargin;
        }
        if (itemSize < spanCount || index >= (itemSize - itemSize % spanCount)) {
            bottom = bottomMargin;
        }
        int rowpos = index % spanCount;
        if (rowpos < spanCount - 1) {
            end = itemSpace;
        }

        outRect.set(start, top, end, bottom);
        // Logger.d("outRect: " + outRect);
    }
}