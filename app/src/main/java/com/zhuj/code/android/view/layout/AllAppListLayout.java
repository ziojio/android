package com.jbzh.android.layout;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbzh.android.view.R;
import com.xuexiang.xutil.app.AppUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AllAppListLayout extends LinearLayout {
    public static HashMap<String, String> showApps;

    static {
        showApps = new HashMap<>();
        showApps.put("com.mediatek.camera", "");
        showApps.put("com.android.chrome", "");
        showApps.put("com.android.settings", "");
        showApps.put("com.android.gallery3d", "");
    }

    public AllAppListLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LinearLayout rootView = (LinearLayout) getRootView();
        initRecyclerView(context, rootView);
    }

    private void initRecyclerView(Context context, LinearLayout rootView) {
        RecyclerView appListRecyclerView = new RecyclerView(context);
        appListRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 6);
        appListRecyclerView.setLayoutManager(gridLayoutManager);
        AppItemAdapter appItemAdapter = new AppItemAdapter();
        appListRecyclerView.setAdapter(appItemAdapter);

        rootView.addView(appListRecyclerView);
    }


    private class AppItemAdapter extends RecyclerView.Adapter<AppItemAdapter.AppItemViewHolder> {
        private List<AppUtils.AppInfo> appInfos;

        private AppItemAdapter() {
            appInfos = AppUtils.getAppsInfo();
            Iterator<AppUtils.AppInfo> iterator = appInfos.iterator();
            while (iterator.hasNext()) {
                AppUtils.AppInfo appInfo = iterator.next();
                if (appInfo.isSystem() && !showApps.containsKey(appInfo.getPackageName())) {
                    iterator.remove();
                }
            }
        }

        class AppItemViewHolder extends RecyclerView.ViewHolder {
            ImageView appIcon;
            TextView appName;

            AppItemViewHolder(@NonNull View itemView) {
                super(itemView);
                appIcon = itemView.findViewById(R.id.app_list_item_app_icon);
                appName = itemView.findViewById(R.id.app_list_item_app_name);
            }
        }

        @NonNull
        @Override
        public AppItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._all_app_list_recycler_view_item, viewGroup, false);
            return new AppItemViewHolder(view);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onBindViewHolder(@NonNull AppItemViewHolder viewHolder, int i) {
            viewHolder.appIcon.setImageDrawable(appInfos.get(i).getIcon());
            viewHolder.appIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewHolder.appName.setText(appInfos.get(i).getName());
            viewHolder.appIcon.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        viewHolder.appIcon.setImageAlpha(200);
                        viewHolder.appIcon.setScaleX(1.2f);
                        viewHolder.appIcon.setScaleY(1.2f);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        if (x <= 125 && x >= -15 && y <= 125 && y >= -15) {
                            AppUtils.launchApp(appInfos.get(i).getPackageName());
                        }
                        viewHolder.appIcon.setImageAlpha(255);
                        viewHolder.appIcon.setScaleX(1.0f);
                        viewHolder.appIcon.setScaleY(1.0f);
                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                        viewHolder.appIcon.setImageAlpha(255);
                        viewHolder.appIcon.setScaleX(1.0f);
                        viewHolder.appIcon.setScaleY(1.0f);
                    }
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return appInfos.size();
        }
    }
}
