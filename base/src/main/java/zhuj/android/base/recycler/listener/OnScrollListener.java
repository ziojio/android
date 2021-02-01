package zhuj.android.base.recycler.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class OnScrollListener extends RecyclerView.OnScrollListener {
    int scrollDirection = 0; // 手指滑动方向  1 向上 2 向下
    boolean hasMore = true;

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        // Logger.e("newState=" + newState);
        if (!hasMore) {
            return;
        }
        RecyclerView.LayoutManager lm = (RecyclerView.LayoutManager) recyclerView.getLayoutManager();
        int itemCount = recyclerView.getAdapter().getItemCount();
        int visibleItemCount = recyclerView.getChildCount();
        if (visibleItemCount == itemCount) {
            return;
        }
        if (lm instanceof LinearLayoutManager) {
            LinearLayoutManager linear = (LinearLayoutManager) lm;
            int lastVisibleItemPosition = linear.findLastVisibleItemPosition();
            // if (scrollDirection == 1 && newState == RecyclerView.SCROLL_STATE_DRAGGING
            if (scrollDirection == 1 && lastVisibleItemPosition + 1 == itemCount) {
                // 在倒数第1行不完全可见时就加载下一页
                loadMore();
            }
        } else if (lm instanceof GridLayoutManager) {
            GridLayoutManager grid = (GridLayoutManager) lm;
            int lastVisibleItemPosition = grid.findLastVisibleItemPosition();
            if (scrollDirection == 1 && lastVisibleItemPosition + 1 == itemCount) {
                loadMore();
            }
        }
    }


    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        // Logger.e("dx=" + dx + ", dy=" + dy);
        scrollDirection = dy > 0 ? 1 : 2;
    }

    public abstract void loadMore();
}
