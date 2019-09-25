package com.me.mseotsanyana.mande.BRBAC.PL;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.me.mseotsanyana.viewpagerlibrary.cCustomViewPager;
import com.me.mseotsanyana.viewpagerlibrary.cWrappingViewPager;

import java.util.ArrayList;
import java.util.List;

public class cFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = cFragmentPagerAdapter.class.getSimpleName();

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    private int currentPosition = -1;

    public cFragmentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        //Log.d(TAG, " FRAG " + fragmentList.get(position));
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void addFrag(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

/*
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        if (!(container instanceof cCustomViewPager)) {
            throw new UnsupportedOperationException("ViewPager is not a cCustomViewPager");
        }

        if (position != currentPosition) {
            Fragment fragment = (Fragment) object;
            cCustomViewPager pager = (cCustomViewPager) container;
            if (fragment != null && fragment.getView() != null) {
                currentPosition = position;
                //Log.d(TAG, " FRAGMENT PAGER ADAPTER 1 " + fragment.getView());
                pager.onPageChanged(currentPosition);
            }
        }
    }
 */
}