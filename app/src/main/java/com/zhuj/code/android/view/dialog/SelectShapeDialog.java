package com.jbzh.android.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbzh.android.bean.SvgLibClass;
import com.jbzh.android.util.DisplayUtil;
import com.jbzh.android.util.ResUtil;
import com.jbzh.android.view.R;

import java.util.ArrayList;
import java.util.List;

public class SelectShapeDialog extends Dialog {
    private Context mContext;
    private List<String> libItemsName; // 一个图库的 itemName list
    private List<Bitmap> libItemsBmp;  // 一个图库的 itemBmp list
    private List<SvgLibClass> svgLibList;
    private RecyclerView recyclerView;
    private int itemSpace = 12; // 在这里是px
    private int itemSize = 48; // 在这里是px
    private int column = 8; // 在这里是px

    private int curIdx = 0;
    private ImgRecyclerViewAdapter adapter;
    public interface OnItemClickListener {
        void onClickItem(SvgLibClass svgLibClass, String itemName);
    }

    private OnItemClickListener onItemClickListener;

    public static class Builder {
        private Context mContext;
        private List<SvgLibClass> svgLibList;
        private OnItemClickListener onItemClickListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder add(@NonNull SvgLibClass svgLibClass) {
            if (svgLibList == null)
                svgLibList = new ArrayList<>();
            svgLibList.add(svgLibClass);
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener1) {
            onItemClickListener = onItemClickListener1;
            return this;
        }

        public SelectShapeDialog build() {
            if (svgLibList.size() < 1)
                return null;
            return new SelectShapeDialog(this);
        }
    }

    private SelectShapeDialog(@NonNull Builder builder) {
        super(builder.mContext);
        mContext = builder.mContext;
        onItemClickListener = builder.onItemClickListener;
        svgLibList = builder.svgLibList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initLibItem(svgLibList.get(curIdx));
        initLibsName();
    }

    private void initLibsName() {
        LinearLayout libNameRoot = findViewById(R.id.lib_name);
        int i = svgLibList.size();
        for (SvgLibClass libClass : svgLibList) {
            i--;
            TextView textView = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setPadding(DisplayUtil.dpToPx(10), DisplayUtil.dpToPx(10), DisplayUtil.dpToPx(10), DisplayUtil.dpToPx(10));
            textView.setText(libClass.getName());
            textView.setTextSize(DisplayUtil.spToPx(20));
            textView.setTextColor(ResUtil.getColor(R.color.scE1));
            textView.setOnClickListener(v -> {
                String iname = ((TextView) v).getText().toString();
                if (! svgLibList.get(curIdx).getName().equals(iname)) {
                    for (int j = 0; j< svgLibList.size(); j++) {
                        if(svgLibList.get(j).getName().equals(iname)){
                            curIdx = j;
                            initLibItem(svgLibList.get(curIdx));
                        }
                    }
                }
            });
            libNameRoot.addView(textView);

            if (i != 0) {
                View view = new View(mContext);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params1.setMarginStart(DisplayUtil.dpToPx(10));
                params1.setMarginEnd(DisplayUtil.dpToPx(10));
                view.setLayoutParams(params1);
                view.setBackgroundColor(ResUtil.getColor(R.color.scE4));
                libNameRoot.addView(view);
            }
        }
    }

    private void initLibItem(SvgLibClass first) {
        if (libItemsBmp == null) {
            libItemsName = new ArrayList<>();
            libItemsBmp = new ArrayList<>();
        } else {
            libItemsName.clear();
            libItemsBmp.clear();
        }
        libItemsName.addAll(first.getSmallBmpMap().keySet());
        for (String name : libItemsName) {
            libItemsBmp.add(first.getSmallBmpMap().get(name));
        }
        adapter.notifyDataSetChanged();
    }

    private void initViews() {
        setContentView(R.layout.dialog_shape_libs);
        initRecycler();
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(ResUtil.getDrawable(R.drawable.style_layout_rect_radius_8dp)); // 设置window的背景色
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = 0.98f;
            params.width = 1200;
            params.height = 800;
            window.setAttributes(params);
        }
        setCanceledOnTouchOutside(true);
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.lib_recycler_view);
        recyclerView.setPadding(itemSpace / 2, itemSpace / 2, itemSpace / 2, itemSpace / 2);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, column));
        adapter = new ImgRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    private class ImgRecyclerViewAdapter extends RecyclerView.Adapter<ImgRecyclerViewAdapter.ImgViewHolder> {
        class ImgViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ImgViewHolder(@NonNull View itemView, ImageView imageView) {
                super(itemView);
                this.imageView = imageView;
            }
        }

        @NonNull
        @Override
        public ImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER);
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemSize, itemSize);
            params.setMargins(0, itemSpace, 0, itemSpace);
            imageView.setLayoutParams(params);
            linearLayout.addView(imageView);
            return new ImgViewHolder(linearLayout, imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImgViewHolder holder, final int position) {
            holder.imageView.setImageBitmap(libItemsBmp.get(position));
            holder.imageView.setOnClickListener(v -> {
                onItemClickListener.onClickItem(svgLibList.get(curIdx), libItemsName.get(position));
                cancel();
            });
        }

        @Override
        public int getItemCount() {
            return libItemsBmp.size();
        }
    }
}
