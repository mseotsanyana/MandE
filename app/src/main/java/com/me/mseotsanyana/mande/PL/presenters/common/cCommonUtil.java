package com.me.mseotsanyana.mande.PL.presenters.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.me.mseotsanyana.mande.PL.ui.adapters.common.cCommonFragmentAdapter;
import com.me.mseotsanyana.mande.R;

public class cCommonUtil {
    static public void setViewPager(FragmentManager fragmentManager, ViewPager viewPager,
                                    TabLayout tabLayout) {
        /*cCommonFragmentAdapter viewPagerAdapter = new cCommonFragmentAdapter(fragmentManager);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);*/
    }
}
