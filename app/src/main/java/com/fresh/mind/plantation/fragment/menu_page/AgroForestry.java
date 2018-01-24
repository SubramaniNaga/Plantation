package com.fresh.mind.plantation.fragment.menu_page;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.pager_adapter.TabPagerAdapter;
import com.fresh.mind.plantation.customized.CustomTabLayout;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.ModelInter_tabs.Intercropstab;
import com.fresh.mind.plantation.fragment.ModelInter_tabs.Models;

import com.fresh.mind.plantation.sqlite.LanguageChange;

/**
 * Created by AND I5 on 08-07-2017.
 */
public class AgroForestry extends Fragment {
    private View rootView;
    private String languages;
    private MyAdapter myAdapter;
    public static CustomTabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setAgro(getActivity().getString(R.string.agroForestry));
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        rootView = inflater.inflate(R.layout.tab, null);
        CustomTabLayout tabLayout = (CustomTabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.agro_model)));
        tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.agro_intercorp)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setVisibility(View.VISIBLE);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        MyAdapter TabAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(TabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                CustomTextView tabTextView = new CustomTextView(getActivity());
                tab.setCustomView(tabTextView);
                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setText(tab.getText());
                Typeface typface2 = Typeface.createFromAsset(getActivity().getAssets(), "proximanovabold.otf");
                tabTextView.setTypeface(typface2, Typeface.NORMAL);
            }
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

    class MyAdapter extends FragmentStatePagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Models();
                case 1:
                    return new Intercropstab();
            }
            return null;
        }

        @Override
        public int getCount() {

            return 2;

        }
/*
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getActivity().getString(R.string.agro_model);
                case 1:
                    return getActivity().getString(R.string.agro_intercorp);
            }
            return null;
        }*/

    }

}
