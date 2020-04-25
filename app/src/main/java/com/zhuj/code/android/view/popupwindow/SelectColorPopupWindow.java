package com.jbzh.android.popupwindow;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jbzh.android.util.DisplayUtil;
import com.jbzh.android.util.ResUtil;
import com.jbzh.android.view.R;

public class SelectColorPopupWindow extends PopupWindow implements View.OnClickListener {
    private final int[] colorArray = new int[]{
            -1, -556671, -370600, -119250, -65536, -2162431, -4979708, -722344, -667479, -91602, -6293631, -8323328, -5906688, -5906688, -13697436,
            -16468943, -10945840, -16468818, -10955785, -16668197, -8282121, -16490572, -13750530, -3395846, -370516, -370516, -11859830, -14022103};
    //    List<String> s = Arrays.asList(
//            "#FFFFFF", "#F78181", "#FA5858", "#FE2E2E", "#FF0000", "#DF0101", "#B40404",
//            "#F4FA58", "#F5D0A9", "#FE9A2E","#9FF781", "#80FF00", "#A5DF00", "#A5DF00",
//            "#2EFE64", "#04B431", "#58FAD0", "#04B4AE", "#58D3F7", "#01A9DB", "#819FF7",
//            "#045FB4", "#2E2EFE", "#CC2EFA", "#FA58AC", "#FA58AC", "#4B088A", "#2A0A29");
    private final int[] PenSizeArray = new int[]{3, 8, 15, 25, 35, 55};
    private Context mContext;
    private int itemSpace = 12; // 在这里是px
    private int itemSize = 64; // 在这里是px
    private int column = 7;
    private SeekBar penSizeSeekbar;
    private TextView curPenSize;
    private int initPenSize;

    public interface ICallback {
        void getPenColor(int color);

        void getPenSize(int width);
    }

    private ICallback callback;

    public void setColorCallBack(ICallback callback) {
        this.callback = callback;
    }

    public void setPenSizeChangeListener(SeekBar.OnSeekBarChangeListener listener) {
        penSizeSeekbar.setProgress(initPenSize);
        penSizeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0)
                    return;
                if (listener != null) {
                    listener.onProgressChanged(seekBar, progress, fromUser);
                }
                curPenSize.setText(progress + " px");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (listener != null) {
                    listener.onStartTrackingTouch(seekBar);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (listener != null) {
                    listener.onStopTrackingTouch(seekBar);
                }
            }
        });
    }

    public SelectColorPopupWindow(Context context, int initPenSize) {
        super(context);
        mContext = context;
        this.initPenSize = initPenSize;
        View view = LayoutInflater.from(context).inflate(R.layout._popupwindow_select_color_layout, null);
        setContentView(view);
        initRecyclerView(view);
        initListener(view);
        penSizeSeekbar = view.findViewById(R.id.pen_size_seekBar);
        curPenSize = view.findViewById(R.id.cur_pen_size);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        setWidth(itemSize * (column + 4));
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

//        StringBuilder a = new StringBuilder();
//        for (String sa : s) {
//            a.append(String.valueOf(Color.parseColor(sa))).append(',');
//        }
//        Log.i("sssssssss", a.toString());
    }


    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.pop_color_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, column));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        params.setMargins(0, itemSpace, 0, itemSpace);
        recyclerView.setAdapter(new ImgRecyclerViewAdapter());
    }

    private void initListener(View view) {
        view.findViewById(R.id.paint_width_1).setOnClickListener(this);
        view.findViewById(R.id.paint_width_2).setOnClickListener(this);
        view.findViewById(R.id.paint_width_3).setOnClickListener(this);
        view.findViewById(R.id.paint_width_4).setOnClickListener(this);
        view.findViewById(R.id.paint_width_5).setOnClickListener(this);

        view.findViewById(R.id.pensize_dec).setOnClickListener(v -> {
            if (penSizeSeekbar.getProgress() > 1)
                penSizeSeekbar.setProgress(penSizeSeekbar.getProgress() - 1);
        });
        view.findViewById(R.id.pensize_plus).setOnClickListener(v -> penSizeSeekbar.setProgress(penSizeSeekbar.getProgress() + 1));

        setSelectedSizedBg(view.findViewById(R.id.paint_width_1));


    }

    @Override
    public void onClick(View v) {
       int width = initPenSize;
        int id = v.getId();
        if (id == R.id.paint_width_1) {
            width = PenSizeArray[0];
        } else if (id == R.id.paint_width_2) {
            width = PenSizeArray[1];
        } else if (id == R.id.paint_width_3) {
            width = PenSizeArray[2];
        } else if (id == R.id.paint_width_4) {
            width = PenSizeArray[3];
        } else if (id == R.id.paint_width_5) {
            width = PenSizeArray[4];
        }
        callback.getPenSize(width);
        penSizeSeekbar.setProgress(width);
        curPenSize.setText(width + " px");
        setSelectedSizedBg(v);
        dismiss();
    }

    private View preColorView;
    private View preSizeView;

    private void setSelectedSizedBg(View curSizeView) {
        if (preSizeView != null)
            preSizeView.setBackgroundResource(R.color.shape_toolbar_unSelect_bg);
        curSizeView.setBackgroundResource(R.color.shape_toolbar_select_bg);
        preSizeView = curSizeView;
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
        } else {
            dismiss();
        }
    }

    private class ImgRecyclerViewAdapter extends RecyclerView.Adapter<ImgRecyclerViewAdapter.ImgViewHolder> {
        class ImgViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ImgViewHolder(@NonNull View itemView, ImageView child) {
                super(itemView);
                imageView = child;
            }
        }

        @NonNull
        @Override
        public ImgRecyclerViewAdapter.ImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout linearLayout = new LinearLayout(parent.getContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER);

            ImageView imageView = new ImageView(parent.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemSize, itemSize);
            params.setMargins(0, itemSpace, 0, itemSpace);
            imageView.setLayoutParams(params);
            imageView.setPadding(1, 1, 1, 1);
            imageView.setBackgroundColor(ResUtil.getColor(R.color.scE4));
            linearLayout.addView(imageView);
            return new ImgRecyclerViewAdapter.ImgViewHolder(linearLayout, imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImgRecyclerViewAdapter.ImgViewHolder holder, final int position) {
            holder.imageView.setImageDrawable(new ColorDrawable(colorArray[position]));
            holder.imageView.setOnClickListener(v -> {
                callback.getPenColor(colorArray[position]);
                dismiss();
            });
        }

        @Override
        public int getItemCount() {
            return colorArray.length;
        }
    }
}
