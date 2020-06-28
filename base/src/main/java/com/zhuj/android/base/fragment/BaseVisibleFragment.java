package com.zhuj.android.base.fragment;


public abstract class BaseVisibleFragment extends IFragment {

    /**
     * 当前页面是否可见(生命周期)
     */
    protected boolean isFragmentVisible = false;


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isFragmentVisible = !hidden;
    }

    @Override
    public void onStart() {
        super.onStart();
        isFragmentVisible = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isFragmentVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentVisible = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        isFragmentVisible = false;
    }


}
