package com.fresh.mind.plantation.fragment.Inside;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Selected_tree_Type;

import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.sorting.SortOrder;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.TreeList;
import com.fresh.mind.plantation.sqlite.sorting.SortOrderSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION_2;
import static com.fresh.mind.plantation.Constant.Config.mSELECTED_TREETYPE_IMAGES;
import static com.fresh.mind.plantation.Constant.Config.mSELECTED_TREETYPE_NAMES;

/**
 * Created by AND I5 on 18-01-2017.
 */
public class SelectedTreeType extends Fragment {
    private View rootView;
    private CustomTextView mNodata;
    private ListView mTreeSpecies;
    private SortOrderSelection sortOrder;
    public static ArrayList<HashMap<String, String>> mImagesList;
    //private TreeNames treeNames;
    String bySortType;
    private TreeList treeList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        sortOrder = new SortOrderSelection(getActivity());
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).viewTreeDetails("" + Config.SELECTED_TREE_TYPE, getActivity().getResources().getString(R.string.SelectedTreeType));

        rootView = inflater.inflate(R.layout.plant_tree_species, null);

        MainActivity.menuItem.setVisible(true);
        MainActivity.menuItem1.setVisible(true);
        MainActivity.menuItem.setTitle(getString(R.string.menuCientificName));
        MainActivity.menuItem1.setTitle(getString(R.string.menuNormalName));
        mTreeSpecies = (ListView) rootView.findViewById(R.id.mTreeSpecies);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        treeList = new TreeList(getActivity());
        mSELECTED_TREETYPE_IMAGES.clear();
        mSELECTED_TREETYPE_NAMES = treeList.getSelectedTreeName(Config.SELECTED_TREE_TYPE, AppData.checkLanguage(getActivity()));
        //   HomeTabView.setTitle(Config.SELECTED_TAB_VIEW_POSITION, getActivity());
        bySortType = sortOrder.getSort();
        if (mSELECTED_TREETYPE_NAMES.size() >= 1) {

            if (bySortType != null) {
                if (bySortType.equals("Z-A")) {
                    Collections.sort(mSELECTED_TREETYPE_NAMES, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("subTreeName").compareTo(s2.get("subTreeName"));
                        }
                    });
                } else {

                    Collections.sort(mSELECTED_TREETYPE_NAMES, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("treeName").compareTo(s2.get("treeName"));
                        }
                    });
                }
            }
            Adapter_Selected_tree_Type adapter_tree_species = new Adapter_Selected_tree_Type(getActivity(), mSELECTED_TREETYPE_NAMES, bySortType);
            mTreeSpecies.setAdapter(adapter_tree_species);
            mTreeSpecies.setVisibility(View.VISIBLE);
            mNodata.setVisibility(View.GONE);
        } else {
            mTreeSpecies.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
        }
        //}
        mTreeSpecies.setSelectionFromTop(LISTVIEW_SMOOTH_VIEW_POSITION_2, 0);
        LISTVIEW_SMOOTH_VIEW_POSITION_2 = 0;
        return rootView;
    }


}
