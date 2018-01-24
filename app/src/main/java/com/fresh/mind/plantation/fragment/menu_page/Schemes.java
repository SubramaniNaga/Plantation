package com.fresh.mind.plantation.fragment.menu_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


import com.fresh.mind.plantation.Constant.AppData;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.SchemeExpandableListAdapter;
import com.fresh.mind.plantation.adapter.recycler_adapter.AdapterSchemes;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.SchecmesDb;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class Schemes extends Fragment {
    private View rootView;
    private RecyclerView mSchemsView;
    private SchecmesDb schecmesDb;
    private CustomTextView sch1text, mistralDetails, sch1text2, mistralDetails2, projectTeamDetails, projectTeamDetails2, acknowledgementDetails, acknowledgementDetails2;
    private ExpandableListView expandableListView;
    private CustomTextView mNoData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();

        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setOtherPage(getActivity().getResources().getString(R.string.Schemes));

        rootView = inflater.inflate(R.layout.plant_schemes, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        mNoData = (CustomTextView) rootView.findViewById(R.id.sch_nodata);

        schecmesDb = new SchecmesDb(getActivity());
        mSchemsView = (RecyclerView) rootView.findViewById(R.id.mSchemsView);
        sch1text = (CustomTextView) rootView.findViewById(R.id.sch1text);
        mistralDetails = (CustomTextView) rootView.findViewById(R.id.mistralDetails);
        sch1text2 = (CustomTextView) rootView.findViewById(R.id.sch1text2);
        mistralDetails2 = (CustomTextView) rootView.findViewById(R.id.mistralDetails2);
        projectTeamDetails2 = (CustomTextView) rootView.findViewById(R.id.projectTeamDetails2);
        projectTeamDetails = (CustomTextView) rootView.findViewById(R.id.projectTeamDetails);
        acknowledgementDetails = (CustomTextView) rootView.findViewById(R.id.acknowledgementDetails);
        acknowledgementDetails2 = (CustomTextView) rootView.findViewById(R.id.acknowledgementDetails2);
        ArrayList<HashMap<String, String>> schems = schecmesDb.getSchemes();
        checkCondition(schems);
        String language = AppData.checkLanguage(getActivity());
        HashMap<String, List<String>> expandableListDetail = schecmesDb.getExpandableValues(language);
        final List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        Log.d("expandableListTitle", "" + expandableListTitle.size() + "  " + expandableListDetail.size() + "  \n" + expandableListDetail);
        if (expandableListDetail.size() >= 1) {
            SchemeExpandableListAdapter schemeExpandableListAdapter = new SchemeExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(schemeExpandableListAdapter);
           // expandableListView.setIndicatorBounds(expandableListView.getWidth() -, expandableListView.getWidth() );
            expandableListView.setVisibility(View.VISIBLE);
            mNoData.setVisibility(View.GONE);
        } else {
            mNoData.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.GONE);
        }
        if (schems != null) {
            if (schems.size() >= 1) {
                AdapterSchemes adapterSchemes = new AdapterSchemes(getActivity(), schems);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mSchemsView.setLayoutManager(mLayoutManager);
                mSchemsView.setItemAnimator(new DefaultItemAnimator());
                mSchemsView.setAdapter(adapterSchemes);
            }
        }

        final int[] prevExpandPosition = {-1};
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //    Toast.makeText(getActivity(), expandableListTitle.get(groupPosition) + " List Expanded.", Toast.LENGTH_SHORT).show();
                if (prevExpandPosition[0] >= 0 && prevExpandPosition[0] != groupPosition) {
                    expandableListView.collapseGroup(prevExpandPosition[0]);
                }
                prevExpandPosition[0] = groupPosition;
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                //Toast.makeText(getActivity(), expandableListTitle.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void checkCondition(ArrayList<HashMap<String, String>> schems) {
        Log.d("descriptionLegnthj", "" + schems.size());
        String language = AppData.checkLanguage(getActivity());
        if (schems != null) {
            if (language.equals("1")) {

                if (schems.size() >= 1) {
                    for (int i = 0; i < schems.size(); i++) {
                        String sch1 = schems.get(1).get("sch1Tamil");
                        String sch11 = schems.get(0).get("sch1Tamil");

                        String sch2 = schems.get(1).get("sch2Tamil");
                        String sch22 = schems.get(0).get("sch2Tamil");

                        String sch3 = schems.get(1).get("sch3Tamil");
                        String sch33 = schems.get(0).get("sch3Tamil");

                        String sch4 = schems.get(1).get("sch4Tamil");
                        String sch44 = schems.get(0).get("sch4Tamil");

                        Log.d("schedb", "" + schems.size());
                        Log.d("sch1xxxx", "  1  " + sch1);
                        Log.d("sch1xxxxyyy", "  1  " + sch22);

                        sch1text.setText(sch11);
                        sch1text2.setText(sch1);
                        mistralDetails.setText(sch22);
                        mistralDetails2.setText(sch2);
                        projectTeamDetails.setText(sch33);
                        projectTeamDetails2.setText(sch3);
                        acknowledgementDetails.setText(sch44);
                        acknowledgementDetails2.setText(sch4);
                    }
                }
            } else {
                if (schems.size() >= 1) {
                    for (int i = 0; i < schems.size(); i++) {
                        String sch1 = schems.get(1).get("sch1");
                        String sch11 = schems.get(0).get("sch1");

                        String sch2 = schems.get(1).get("sch2");
                        String sch22 = schems.get(0).get("sch2");

                        String sch3 = schems.get(1).get("sch3");
                        String sch33 = schems.get(0).get("sch3");

                        String sch4 = schems.get(1).get("sch4");
                        String sch44 = schems.get(0).get("sch4");

                        Log.d("schedb", "" + schems.size());

                        Log.d("sch1xxxx", "  1  " + sch1);

                        Log.d("sch1xxxxyyy", "  1  " + sch22);
                        sch1text.setText(sch11);
                        sch1text2.setText(sch1);
                        mistralDetails.setText(sch22);
                        mistralDetails2.setText(sch2);
                        projectTeamDetails.setText(sch33);
                        projectTeamDetails2.setText(sch3);
                        acknowledgementDetails.setText(sch44);
                        acknowledgementDetails2.setText(sch4);
                    }
                }
            }
        }

    }
}
