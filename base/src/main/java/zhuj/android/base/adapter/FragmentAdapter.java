package zhuj.android.base.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment的适配器, 详情参考{@link FragmentPagerAdapter}
 */
public class FragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private long baseId = 0;
    private List<T> mFragmentList = new ArrayList<>();

    private List<String> mTitleList = new ArrayList<>();

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public FragmentAdapter(@NonNull FragmentManager fm, List<T> fragments) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        setFragments(fragments);
    }

    public FragmentAdapter<T> setFragments(List<T> fragments) {
        if (fragments != null && fragments.size() > 0) {
            mFragmentList.clear();
            mFragmentList.addAll(fragments);
        }
        return this;
    }

    public FragmentAdapter<T> addFragments(List<T> fragments) {
        if (fragments != null && fragments.size() > 0) {
            mFragmentList.addAll(fragments);
        }
        return this;
    }

    public FragmentAdapter<T> setTitles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            mTitleList.clear();
            mTitleList.addAll(titles);
        }
        return this;
    }

    public FragmentAdapter<T> addTitles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            mTitleList.addAll(titles);
        }
        return this;
    }

    public FragmentAdapter<T> addFragment(T fragment, String title) {
        if (fragment != null) {
            mFragmentList.add(fragment);
            mTitleList.add(title);
        }
        return this;
    }

    public void clear() {
        notifyChangeInPosition(getCount());
        if (mFragmentList != null) {
            mFragmentList.clear();
        }
        if (mTitleList != null) {
            mTitleList.clear();
        }
    }

    @NonNull
    @Override
    public T getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return baseId + position;
    }

    /**
     * Notify that the position of a fragment has been changed.
     * Create a new ID for each position to force recreation of the fragment
     *
     * @param n number of items which have been changed
     */
    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getCount() + n;
    }

    // this is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(@NonNull Object object) {
        // int index = mFragmentList.indexOf(object);
        // if (index == -1) {
        return PagerAdapter.POSITION_NONE;
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    public List<T> getFragmentList() {
        return mFragmentList;
    }

    public List<String> getTitleList() {
        return mTitleList;
    }
}