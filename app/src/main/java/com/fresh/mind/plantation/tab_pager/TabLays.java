package com.fresh.mind.plantation.tab_pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.adapter.pager_adapter.AdapterMainLay;

/**
 * Created by AND I5 on 13-02-2017.
 */

public class TabLays extends Fragment {
    AdapterMainLay TabAdapter;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        rootView = inflater.inflate(R.layout.tab, null);

      /*  CustomTabLayout tabLayout = (CustomTabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("TREE SPECIES"));
        tabLayout.addTab(tabLayout.newTab().setText("TREE TYPE"));
        tabLayout.addTab(tabLayout.newTab().setText("LOCATION"));
        tabLayout.addTab(tabLayout.newTab().setText("SEARCH"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setVisibility(View.VISIBLE);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);


        // final PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        TabAdapter = new AdapterMainLay(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(TabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
        });*/
        return rootView;
    }

}
