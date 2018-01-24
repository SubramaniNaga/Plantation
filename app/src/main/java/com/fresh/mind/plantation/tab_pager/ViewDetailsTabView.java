package com.fresh.mind.plantation.tab_pager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.pager_adapter.TabPagerAdapter;
import com.fresh.mind.plantation.customized.CustomTabLayout;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.holder.ViewHolder;
import com.fresh.mind.plantation.sqlite.LanguageChange;

/**
 * Created by AND I5 on 03-02-2017.
 */

public class ViewDetailsTabView extends Fragment {

    private static final String ARG_KITTEN_NUMBER = "view";
    private View rootView;
    private TabPagerAdapter TabAdapter;
    public static String titleName;
    public static String glossary;
/*
    public static ViewDetailsTabView newInstance(@IntRange int kittenNumber, ViewHolder holder, int viewType) {
        Bundle args = new Bundle();

        args.putInt(ARG_KITTEN_NUMBER, kittenNumber);
        args.putString("titleName", "Tree Species");
        args.putString("treeIcon", "" + Config.mLocation.get(kittenNumber).get("treeIcon"));
        if (viewType == 0) {
            args.putString("treeName", "" + holder.treeName.getText().toString());
        } else if (viewType == 1) {
            args.putString("treeName", "" + holder.mPart2Title.getText().toString());
        }
        ViewDetailsTabView fragment = new ViewDetailsTabView();
        fragment.setArguments(args);

        return fragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        titleName = bundle.getString("titleName");


        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).viewTreeDetails(Config.TREE_NAME, titleName + 1);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        rootView = inflater.inflate(R.layout.tab, null);

        CustomTabLayout tabLayout = (CustomTabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.images)));
        tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.descriptions)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setVisibility(View.VISIBLE);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);


        // final PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        TabAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(TabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //viewPager.getAdapter().notifyDataSetChanged();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {

                CustomTextView tabTextView = new CustomTextView(getActivity());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                tabTextView.setText(tab.getText());

                // First tab is the selected tab, so if i==0 then set BOLD typeface

                Typeface typface2 = Typeface.createFromAsset(getActivity().getAssets(), "proximanovabold.otf");
                tabTextView.setTypeface(typface2, Typeface.NORMAL);

            }

        }
/*
        tabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.black),
                ContextCompat.getColor(getActivity(), R.color.white));*/

       /* ColorStateList colors;
        if (Build.VERSION.SDK_INT >= 23) {
            colors = getResources().getColorStateList(R.drawable.tab_icon, getActivity().getTheme());
        } else {
            colors = getResources().getColorStateList(R.color.white);
        }
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            CharSequence icon = tab.getText();

            if (icon != null) {
                icon = (CharSequence) CharacterStyle.wrap((CharacterStyle) icon);


            }
        }*/

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

}
