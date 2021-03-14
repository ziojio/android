package zhuj.android.base.recyclerview.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridItemSpaceDecoration extends RecyclerView.ItemDecoration {
    int spanCount = 0;
    int lineSpace = 0;
    int itemSpace = 0;
    int topMargin;
    int bottomMargin;
    //
    // public GridItemDecoration() {
    // }
    //
    // public GridItemDecoration setLineSpace(int lineSpace) {
    //     this.lineSpace = lineSpace;
    //     return this;
    // }
    //
    // public GridItemDecoration setItemSpace(int itemSpace) {
    //     this.itemSpace = itemSpace;
    //     return this;
    // }
    //
    // public GridItemDecoration setTopMargin(int topMargin) {
    //     this.topMargin = topMargin;
    //     return this;
    // }
    //
    // public GridItemDecoration setBottomMargin(int bottomMargin) {
    //     this.bottomMargin = bottomMargin;
    //     return this;
    // }
    //
    private int spacing;
    private boolean includeEdge;

    // public GridItemDecoration(int spanCount, int spacing, boolean includeEdge) {
    //     this.spanCount = spanCount;
    //     this.spacing = spacing;
    //     this.includeEdge = includeEdge;
    // }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;
            if (position < spanCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        } else {
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
            if (position < spanCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        }
    }
}