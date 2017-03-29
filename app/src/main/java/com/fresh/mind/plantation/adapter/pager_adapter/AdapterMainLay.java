package com.fresh.mind.plantation.adapter.pager_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.fresh.mind.plantation.fragment.inside_tabs.LocationType;
import com.fresh.mind.plantation.fragment.inside_tabs.ToolsSettings;
import com.fresh.mind.plantation.fragment.inside_tabs.TreeSpecies;
import com.fresh.mind.plantation.fragment.inside_tabs.TreeType;

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
        if (position == 0) {
            return new TreeSpecies();
        } else if (position == 1) {
            return new TreeType();
        } else if (position == 2) {
            return new LocationType();
        } else if (position == 3) {
            return new ToolsSettings();
        }
        return null;
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
        Log.d("asas", "" + object);
        return super.getItemPosition(object);

    }
}
