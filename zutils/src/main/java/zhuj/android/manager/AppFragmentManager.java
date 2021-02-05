package zhuj.android.manager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class AppFragmentManager {
    private FragmentManager mFragmentManager;
    private int mContainerId;
    private List<Fragment> mFragmentList;
    private int mCurrentIndex = -1;

    public AppFragmentManager(FragmentActivity activity, int containerId) {
        mFragmentManager = activity.getSupportFragmentManager();
        mContainerId = containerId;
        mFragmentList = new ArrayList<>();
    }

    public void add(Fragment... baseFragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (Fragment fragment : baseFragment) {
            fragmentTransaction.add(mContainerId, fragment);
            mFragmentList.add(fragment);
        }
        fragmentTransaction.commit();
    }

    public void remove(int position) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.remove(mFragmentList.get(position));
        fragmentTransaction.commit();
    }

    public void removeAll() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        int size = mFragmentList.size();
        for (int i = 0; i < size; i++) {
            fragmentTransaction.remove(mFragmentList.get(i));
        }
        fragmentTransaction.commit();
    }

    public void show(int position) {
        if (position == mCurrentIndex)
            return;
        mCurrentIndex = position;
        showFragment(mFragmentList.get(position));
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        int size = mFragmentList.size();
        for (int i = 0; i < size; i++) {
            fragmentTransaction.hide(mFragmentList.get(i));
        }
    }

    public Fragment getShowFragment() {
        return mFragmentList.get(mCurrentIndex);
    }
}
