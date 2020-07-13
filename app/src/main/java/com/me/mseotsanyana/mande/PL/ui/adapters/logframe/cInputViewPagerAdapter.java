package com.me.mseotsanyana.mande.PL.ui.adapters.logframe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class cInputViewPagerAdapter  extends FragmentPagerAdapter {
    private final List<Fragment>  fragmentList = new ArrayList<>();
    private final List<String> titleList= new ArrayList<>();

    public cInputViewPagerAdapter(FragmentManager manager, int behavior) {
        super(manager, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
