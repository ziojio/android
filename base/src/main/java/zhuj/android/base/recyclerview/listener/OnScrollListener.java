package zhuj.android.base.recyclerview.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.flexbox.FlexboxLayoutManager;


public abstract class OnScrollListener extends RecyclerView.OnScrollListener {

    int mScrollState = 0;
    boolean hasMore = true;
    boolean isLoading = false;

    /**
     * Callback method to be invoked when RecyclerView's scroll state changes.
     *
     * @param recyclerView The RecyclerView whose scroll state has changed.
     * @param newState     The updated scroll state. One of {@link RecyclerView#SCROLL_STATE_IDLE},
     *                     {@link RecyclerView#SCROLL_STATE_DRAGGING} or {@link RecyclerView#SCROLL_STATE_SETTLING}.
     */
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        this.mScrollState = newState;
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled. This will be
     * called after the scroll has completed.
     * <p>
     * This callback will also be called if visible item range changes after a layout
     * calculation. In that case, dx and dy will be 0.
     *
     * @param recyclerView The RecyclerView which scrolled.
     * @param dx           The amount of horizontal scroll.
     * @param dy           The amount of vertical scroll.
     */
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

            int itemCount = layoutManager.getItemCount();
            if (itemCount <= 0) return;

            int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();

            if (itemCount == lastVisiblePosition + 1 &&
                    (mScrollState == RecyclerView.SCROLL_STATE_DRAGGING || mScrollState == RecyclerView.SCROLL_STATE_SETTLING)) {
                dispatchLoadMore();
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;

            int itemCount = layoutManager.getItemCount();
            if (itemCount <= 0) return;
            int[] lastVisiblePositionArray = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
            int lastVisiblePosition = lastVisiblePositionArray[lastVisiblePositionArray.length - 1];

            if (itemCount == lastVisiblePosition + 1 &&
                    (mScrollState == RecyclerView.SCROLL_STATE_DRAGGING || mScrollState == RecyclerView.SCROLL_STATE_SETTLING)) {
                dispatchLoadMore();
            }
        } else if (layoutManager instanceof FlexboxLayoutManager) {
            FlexboxLayoutManager flexboxLayoutManager = (FlexboxLayoutManager) layoutManager;
            int itemCount = layoutManager.getItemCount();
            if (itemCount <= 0) return;

            int lastVisiblePosition = flexboxLayoutManager.findLastVisibleItemPosition();

            if (itemCount == lastVisiblePosition + 1 &&
                    (mScrollState == RecyclerView.SCROLL_STATE_DRAGGING || mScrollState == RecyclerView.SCROLL_STATE_SETTLING)) {
                dispatchLoadMore();
            }
        }
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        isLoading = false;
    }

    protected void dispatchLoadMore() {
        if (!isLoading && hasMore) {
            isLoading = true;
            loadMore();
        }
    }

    public abstract void loadMore();
}
