package com.fresh.mind.plantation.fragment.Inside;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Tree_Species;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;

import com.fresh.mind.plantation.sqlite.sorting.SortOrder;
import com.fresh.mind.plantation.sqlite.server.TreeTypeInfo;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;
import com.fresh.mind.plantation.sqlite.sorting.SortOrderAZ;

import java.util.ArrayList;
import java.util.HashMap;


import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION_2;
import static com.fresh.mind.plantation.Constant.Config.SOIL_TYPE;
import static com.fresh.mind.plantation.Constant.Config.districtName;
import static com.fresh.mind.plantation.Constant.Config.rainFallType;
import static com.fresh.mind.plantation.Constant.Config.soillType;
import static com.fresh.mind.plantation.Constant.Config.terrainType;
import static com.fresh.mind.plantation.Constant.Config.treeType;


/**
 * Created by AND I5 on 17-01-2017.
 */
public class SearchedByTree extends Fragment {
    private View rootView;
    private CustomTextView mSelectedDisticName, mNodata;
    private Spinner mLocationBasedSeletedTree;
    private ListView listView;
    private ArrayList<HashMap<String, String>> mTreeTypeValues;
    private VerifyDetails verifyDetails;
    private TreeTypeInfo treeTypeInfo;
    private SortOrderAZ sortOrder;
    private String bySortType;
    private LinearLayout mSpner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        sortOrder = new SortOrderAZ(getActivity());

        ((MainActivity) getActivity()).viewTreeDetails(getResources().getString(R.string.searchResult), getResources().getString(R.string.searchResult));
        rootView = inflater.inflate(R.layout.plant_selecte_location, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        mSelectedDisticName = (CustomTextView) rootView.findViewById(R.id.mSelectedDisticName);
        mSelectedDisticName.setVisibility(View.VISIBLE);
        mSpner = (LinearLayout) rootView.findViewById(R.id.mSpner);
        mSpner.setVisibility(View.GONE);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        mLocationBasedSeletedTree = (Spinner) rootView.findViewById(R.id.LocationBasedTreeTypeSpr);
        listView = (ListView) rootView.findViewById(R.id.mLocationBasedSeletedTree);
        verifyDetails = new VerifyDetails(getActivity());
        treeTypeInfo = new TreeTypeInfo(getActivity());
        mTreeTypeValues = treeTypeInfo.getTreeType(Config.DISTRICT_NAME, SOIL_TYPE, AppData.checkLanguage(getActivity()));
        mLocationBasedSeletedTree.setAdapter(new TreeTypeAdapter(getActivity(), mTreeTypeValues));
        mLocationBasedSeletedTree.setSelected(false);
        mLocationBasedSeletedTree.setSelection(Config.SELECTE_TREE_TYPE);
        mSelectedDisticName.setText(Config.DISTRICT_NAME + "(Dt), " + SOIL_TYPE);
        mSelectedDisticName.setVisibility(View.GONE);
        bySortType = sortOrder.getSort();

        mSelectedDisticName.setVisibility(View.GONE);
        mLocationBasedSeletedTree.setVisibility(View.GONE);
        Config.mSELECTED_TREE_NAME = verifyDetails.getQureyBaseTreeName(treeType, districtName, rainFallType, terrainType, soillType, AppData.checkLanguage(getActivity()));
        if (Config.mSELECTED_TREE_NAME.size() >= 1) {
            Adapter_Tree_Species adapter_tree_species = new Adapter_Tree_Species(getActivity(), Config.mSELECTED_TREE_NAME, "null"/*, treImagesFromHere*/, "Search");
            listView.setAdapter(adapter_tree_species);
            mNodata.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        } else {
            mNodata.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }


/*

        mLocationBasedSeletedTree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, lo   ng id) {
                Config.SELECTE_TREE_TYPE = mLocationBasedSeletedTree.getSelectedItemPosition();
                String treeType = mTreeTypeValues.get(position).get("Treetype");

                Config.mSELECTED_TREE_NAME = verifyDetails.getQureyBaseTreeName(treeType, Config.DISTRICT_NAME, RAIN_FALL, Config.TERRAIN_TYPE, SOIL_TYPE, AppData.checkLanguage(getActivity()));

                if (mSELECTED_TREE_NAME.size() >= 1) {
                    if (bySortType != null) {
                        if (bySortType.equals("Z-A")) {
                            Log.d("LocationSelected", "come");
                            Collections.sort(mSELECTED_TREE_NAME, new Comparator<HashMap<String, String>>() {
                                @Override
                                public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                                    return s2.get("treeName").compareTo(s1.get("treeName"));
                                }
                            });
                        } else {
                            Log.d("LocationSelected", "not come");
                            Collections.sort(mSELECTED_TREE_NAME, new Comparator<HashMap<String, String>>() {
                                @Override
                                public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                                    return s1.get("treeName").compareTo(s2.get("treeName"));
                                }
                            });
                        }
                    }
                    mSelectedDisticName.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    mNodata.setVisibility(View.GONE);
                    Adapter_Tree_Species adapter_tree_species = new Adapter_Tree_Species(getActivity(), Config.mSELECTED_TREE_NAME *//*
*/
/*, treImagesFromHere*//*
*/
/*, "Search");
                            listView.setAdapter(adapter_tree_species);
                } else {
                    listView.setVisibility(View.GONE);
                    mNodata.setVisibility(View.VISIBLE);
                }
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        listView.setSelectionFromTop(LISTVIEW_SMOOTH_VIEW_POSITION_2, 0);
        LISTVIEW_SMOOTH_VIEW_POSITION_2 = 0;
        return rootView;
    }


    private class TreeTypeAdapter extends BaseAdapter {
        private final ArrayList<HashMap<String, String>> mListOfTreeType;
        private final FragmentActivity mContext;

        public TreeTypeAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> mTreeTypeValues) {

            this.mContext = activity;
            this.mListOfTreeType = mTreeTypeValues;

        }

        @Override
        public int getCount() {
            return mListOfTreeType.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_spinner, null);
            CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            mSPinnerText.setText("" + mListOfTreeType.get(position).get("Treetype"));
            return convertView;
        }
    }
}
