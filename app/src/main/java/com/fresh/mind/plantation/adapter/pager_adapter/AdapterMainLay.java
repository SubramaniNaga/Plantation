package com.fresh.mind.plantation.adapter.pager_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.fresh.mind.plantation.fragment.inside_tabs.LocationType;
import com.fresh.mind.plantation.fragment.inside_tabs.TreeSpecies;
import com.fresh.mind.plantation.fragment.inside_tabs.TreeType;
import com.fresh.mind.plantation.fragment.inside_tabs.MultipleSearch;
import com.fresh.mind.plantation.imagemap.LocationImageMap;

/**
 * Created by AND I5 on 13-02-2017.
 */

public class AdapterMainLay extends FragmentStatePagerAdapter {

    public AdapterMainLay(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        //  Log.d("243-05890 a90923", "" + position);
        /*position = 1 - position;
        position = Math.abs(position);

        Log.d("position", "" + position);*/
        Fragment fragment;
        switch (position) {
            case (0):
                fragment = new TreeSpecies();
                break;
            case (1):
                fragment = new TreeType();
                break;
            case (2):
                fragment = new LocationType();
                break;
            case (3):
                fragment = new MultipleSearch();
                break;
            default:
                fragment = new TreeSpecies();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
/*
    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }*/

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);

    }
}
