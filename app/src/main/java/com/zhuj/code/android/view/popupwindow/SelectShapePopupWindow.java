package com.jbzh.android.popupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jbzh.android.util.DisplayUtil;
import com.jbzh.android.util.ResUtil;
import com.jbzh.android.view.R;

import java.util.List;

public class SelectShapePopupWindow extends PopupWindow {
    private Context mContext;
    private List<String> svgStrList;
    private List<Bitmap> bmpList;
    private int mWidth;
    private int mHeight;
    private int itemWidth;
    private int itemHeight;
    private int column;
    private Rect margin;
    private Rect padding;
    private int ckbgColor;

    public interface OnItemClickListener {
        void onClickItem(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SelectShapePopupWindow(Context context, int width, int height,
                                  int itemWidth, int itemHeight, Rect margin, Rect padding, int ckbgColor, int column,
                                  List<Bitmap> strList) {
        super(context);
        if (width < itemWidth * column || height < itemHeight)
            throw new IllegalArgumentException("item's size is 大于给定的宽度或高度！");
        this.mContext = context;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.column = column;
        this.mWidth = width;
        this.mHeight = height;
        this.margin = margin;
        this.padding = padding;
        this.ckbgColor = ckbgColor;
        this.bmpList = strList;
        setContentView(initRecyclerView());
        setHeight(height);
        setWidth(width);
        ResUtil.init(context);
        LayerDrawable drawable = (LayerDrawable) ResUtil.getDrawable(R.drawable.style_layout_rect_shadow_3px);
        drawable.addLayer(new ColorDrawable(Color.parseColor("#f5f6f8")));
        setBackgroundDrawable(drawable);
        setOutsideTouchable(true);
    }

    private RecyclerView initRecyclerView() {
        RecyclerView recyclerView = new RecyclerView(mContext);
        int padding = (mWidth - itemWidth * column) / (column * 2);
        recyclerView.setPadding(padding, padding, padding, 0);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, column));
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setAdapter(new ImgRecyclerViewAdapter());
        return recyclerView;
    }

    public void show(View parent) {
        if (!isShowing()) {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            int i = DisplayUtil.dpToPx(14);
            showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + parent.getWidth() + i, location[1]);
        } else {
            dismiss();
        }
    }

    public void show(View parent, int x, int y) {
        if (!isShowing()) {
            showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
        }
    }

    private class ImgRecyclerViewAdapter extends RecyclerView.Adapter<ImgRecyclerViewAdapter.ImgViewHolder> {
        class ImgViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ImgViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = (ImageView) ((LinearLayout) itemView).getChildAt(0);
            }
        }

        @NonNull
        @Override
        public ImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER);
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemHeight);
            params.setMargins(margin.left, margin.top, margin.right, margin.bottom);
            imageView.setLayoutParams(params);
            imageView.setPadding(padding.left, padding.top, padding.right, padding.bottom);
            linearLayout.addView(imageView);
            return new ImgViewHolder(linearLayout);
        }

        @Override
        public void onBindViewHolder(@NonNull ImgViewHolder holder, final int position) {
            holder.imageView.setImageBitmap(bmpList.get(position));
            holder.imageView.setOnClickListener(v -> {
//                v.setBackground(new ColorDrawable(ckbgColor));
                onItemClickListener.onClickItem(position);
                dismiss();
//                new Handler().postDelayed(() -> v.setBackground(new ColorDrawable(Color.TRANSPARENT)), 250);
            });
        }

        @Override
        public int getItemCount() {
            return bmpList.size();
        }
    }
}
