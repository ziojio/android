package zhuj.android.base.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    int lineSpace;
    int topMargin;
    int bottomMargin;

    public LinearItemDecoration(int lineSpace) {
        this.lineSpace = lineSpace;
        this.topMargin = lineSpace;
        this.bottomMargin = lineSpace;
    }

    public LinearItemDecoration setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
        return this;
    }

    public LinearItemDecoration setTopMargin(int topMargin) {
        this.topMargin = topMargin;
        return this;
    }

    public LinearItemDecoration setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
        return this;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemSize = parent.getAdapter().getItemCount();
        int index = parent.getChildAdapterPosition(view);
        int top = 0, bottom = 0;
        if (index == 0) {
            top = topMargin;
        } else if (index == itemSize - 1) {
            bottom = bottomMargin;
        } else {
            top = lineSpace;
        }
        outRect.set(0, top, 0, bottom);
    }

}