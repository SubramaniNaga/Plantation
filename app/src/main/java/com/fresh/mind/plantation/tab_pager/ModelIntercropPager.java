package com.fresh.mind.plantation.tab_pager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fresh.mind.plantation.fragment.ModelInter_tabs.Intercropstab;
import com.fresh.mind.plantation.fragment.ModelInter_tabs.Models;

public class ModelIntercropPager extends FragmentPagerAdapter {

    public ModelIntercropPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Models();
            case 1:
                // Games fragment activity
                return new Intercropstab();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
