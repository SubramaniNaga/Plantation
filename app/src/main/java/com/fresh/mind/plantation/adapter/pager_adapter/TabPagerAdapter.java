package com.fresh.mind.plantation.adapter.pager_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fresh.mind.plantation.fragment.Inside.DescriptionView;
import com.fresh.mind.plantation.fragment.Inside.ImagesView;

/**
 * Created by AND I5 on 03-02-2017.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragement for Android Tab
                return new ImagesView();

            case 1:
                //Fragment for Windows Tab
                return new DescriptionView();
        }
        return null;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 2; //No of Tabs
    }
}
