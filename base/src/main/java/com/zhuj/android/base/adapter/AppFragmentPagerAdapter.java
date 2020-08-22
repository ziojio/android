package com.zhuj.android.base.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class AppFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;

    public AppFragmentPagerAdapter(FragmentActivity activity, List<Fragment> fragments) {
        super(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position> 0 && position < fragments.size()) {
            return fragments.get(position);
        }
        return fragments.get(0);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
