package zhuj.android.base.recycler.itemdecoration;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private int color = Color.parseColor("#ededed");
    private int width = 1;
    private Paint borderPaint;
    int itemSize = 0;
    int lineSpace = 0;
    int topMargin;
    int bottomMargin;

    public LinearItemDecoration() {
        borderPaint = new Paint();
        borderPaint.setColor(color);
        borderPaint.setColor(width);
        borderPaint.setStyle(Paint.Style.STROKE);
    }

    public LinearItemDecoration(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    public LinearItemDecoration setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
        return this;
    }

    public LinearItemDecoration setBorderWidth(int width) {
        this.width = width;
        borderPaint.setStrokeWidth(width);
        return this;
    }

    public LinearItemDecoration setBorderColor(int color) {
        this.color = color;
        borderPaint.setColor(color);
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
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }
        itemSize = parent.getAdapter().getItemCount();
        if (itemSize == 0) {
            return;
        }
        int index = parent.getChildAdapterPosition(view);
        int start = 0, top = 0, end = 0, bottom = 0;
        if (index == 0) {
            top = topMargin;
        } else if (index == itemSize - 1) {
            bottom = bottomMargin;
        }
        if (index > 0) {
            top = lineSpace;
        }
        outRect.set(start, top, end, bottom);
    }

}