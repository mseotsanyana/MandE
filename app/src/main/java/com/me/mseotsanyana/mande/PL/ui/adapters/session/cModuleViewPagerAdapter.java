package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class cModuleViewPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment>  fragmentList = new ArrayList<>();
    private final List<String> titleList= new ArrayList<>();

    public cModuleViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addFrag(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    public Fragment getPageFragment(int position){
        return fragmentList.get(position);
    }

    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
