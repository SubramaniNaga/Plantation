package com.fresh.mind.plantation.fragment.Inside;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_LocationBasedSeletedTree;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.sorting.SortOrder;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.TreeList;
import com.fresh.mind.plantation.sqlite.server.TreeTypeInfo;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION_2;


/**
 * Created by AND I5 on 17-01-2017.
 */
public class LocationSelected extends Fragment {
    private View rootView;
    private CustomTextView mSelectedDisticName, mNodata;
    private ListView mLocationBasedSeletedTree;
    private Spinner LocationBasedTreeTypeSpr;
    //ArrayList<HashMap<String, String>> mTreeTypeNames = null;
    ArrayList<HashMap<String, String>> mTreeNames = null;
    ArrayList<String> mDistrictWithTreeType;
    private SortOrder sortOrder;
    private TreeTypeInfo treeTypeInfo;
    private DistrictNameList districtNameList;
    private TreeList treeList;
    private String[] mSpinnerArray;
    private TreeTypeNameList treeTypeNameList;


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
        sortOrder = new SortOrder(getActivity());
        ((MainActivity) getActivity()).viewTreeDetails(Config.SELECTED_DISTRICT_NAME, "Location Selection");
        rootView = inflater.inflate(R.layout.plant_selecte_location, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);

        LocationBasedTreeTypeSpr = (Spinner) rootView.findViewById(R.id.LocationBasedTreeTypeSpr);
        mLocationBasedSeletedTree = (ListView) rootView.findViewById(R.id.mLocationBasedSeletedTree);
        mSelectedDisticName = (CustomTextView) rootView.findViewById(R.id.mSelectedDisticName);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        mSelectedDisticName.setText("" + Config.SELECTED_DISTRICT_NAME + " " + getActivity().getString(R.string.district));

        treeTypeNameList = new TreeTypeNameList(getActivity());
        treeTypeInfo = new TreeTypeInfo(getActivity());
        treeList = new TreeList(getActivity());
        districtNameList = new DistrictNameList(getActivity());
        //mTreeTypeNames = treeTypeInfo.getTreTypeeNamesFromDistrict(Config.SELECTED_DISTRICT_NAME, AppData.checkLanguage(getActivity()));

        mDistrictWithTreeType = districtNameList.getDistrictBaseTreeType(languages, Config.SELECTED_DISTRICT_NAME);

        //mTreeTypeNames = treeTypeNameList.getTreTypeeNames(AppData.checkLanguage(getActivity()));
        String bySortType = sortOrder.getSort();
        if (mDistrictWithTreeType.size() >= 1) {
            SelectedLocationAdapter selectedLocationAdapter = new SelectedLocationAdapter(getActivity(), mDistrictWithTreeType);
            LocationBasedTreeTypeSpr.setAdapter(selectedLocationAdapter);
        }
        if (Config.SELECTED_ID == 0) {
            Config.SELECTED_ID = LocationBasedTreeTypeSpr.getSelectedItemPosition();
        }
        // HomeTabView.setTitle(Config.SELECTED_TAB_VIEW_POSITION, getActivity());
        if (mDistrictWithTreeType.size() >= 1) {
            mNodata.setVisibility(View.GONE);
            mTreeNames = treeList.getSelectedTreeName(mDistrictWithTreeType.get(Config.SELECTED_ID), Config.SELECTED_DISTRICT_NAME, AppData.checkLanguage(getActivity()));
            //mImageList = treeList.getTreeByImages(mTreeTypeNames.get(Config.SELECTED_ID).get("Treetype"), Config.SELECTED_DISTRICT_NAME, AppData.checkLanguage(getActivity()));
            ArrayList<HashMap<String, String>> mImageList = treeList.getTreImagePath(mDistrictWithTreeType.get(Config.SELECTED_ID), Config.SELECTED_DISTRICT_NAME, AppData.checkLanguage(getActivity()));

            /*if (bySortType != null) {
                if (bySortType.equals("Z-A")) {

                    Collections.sort(mTreeNames, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s2.get("treeName").compareTo(s1.get("treeName"));
                        }
                    });
                } else {

                    Collections.sort(mTreeNames, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("treeName").compareTo(s2.get("treeName"));
                        }
                    });
                }
            }*/
            mLocationBasedSeletedTree.setAdapter(new Adapter_LocationBasedSeletedTree(getActivity(), mTreeNames, mImageList));
        } else {


            mSpinnerArray = new String[]{getActivity().getString(R.string.noTreeType)};
            LocationBasedTreeTypeSpr.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.adapter_spinner, R.id.mSPinnerText, mSpinnerArray));
            mNodata.setVisibility(View.VISIBLE);
        }

        //LocationBasedTreeTypeSpr.setSelected(false);
        LocationBasedTreeTypeSpr.setSelection(Config.SELECTED_ID);
        //LocationBasedTreeTypeSpr.setSelection(0, false);
        LocationBasedTreeTypeSpr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Config.SELECTED_ID = LocationBasedTreeTypeSpr.getSelectedItemPosition();
                if (mDistrictWithTreeType.size() >= 1) {
                    mTreeNames = treeList.getSelectedTreeName(mDistrictWithTreeType.get(Config.SELECTED_ID), Config.SELECTED_DISTRICT_NAME, AppData.checkLanguage(getActivity()));
                    // mImageList = treeList.getTreeByImages(mTreeTypeNames.get(Config.SELECTED_ID).get("Treetype"), Config.SELECTED_DISTRICT_NAME, AppData.checkLanguage(getActivity()));
                    ArrayList<HashMap<String, String>> mImageList = treeList.getTreImagePath(mDistrictWithTreeType.get(Config.SELECTED_ID), Config.SELECTED_DISTRICT_NAME, AppData.checkLanguage(getActivity()));
                    /*Collections.sort(mTreeNames, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("treeName").compareTo(s2.get("treeName"));
                        }
                    });*/
                    if (mTreeNames.size() >= 1) {
                        mNodata.setVisibility(View.GONE);
                        mLocationBasedSeletedTree.setVisibility(View.VISIBLE);

                        mLocationBasedSeletedTree.setAdapter(new Adapter_LocationBasedSeletedTree(getActivity(), mTreeNames, mImageList));
                    } else {
                        mNodata.setVisibility(View.VISIBLE);
                        mLocationBasedSeletedTree.setVisibility(View.GONE);
                    }
                } else {
                    mSpinnerArray = new String[]{getActivity().getString(R.string.noTreeType)};
                    LocationBasedTreeTypeSpr.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.adapter_spinner, R.id.mSPinnerText, mSpinnerArray));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mLocationBasedSeletedTree.setSelectionFromTop(LISTVIEW_SMOOTH_VIEW_POSITION_2, 0);
        LISTVIEW_SMOOTH_VIEW_POSITION_2 = 0;

        return rootView;
    }

    class SelectedLocationAdapter extends BaseAdapter {
        private final ArrayList<String> mListOfValues;
        private final FragmentActivity mContext;

        public SelectedLocationAdapter(FragmentActivity activity, ArrayList<String> mLocation) {
            this.mContext = activity;
            this.mListOfValues = mLocation;
        }

        @Override
        public int getCount() {
            return mListOfValues.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_spinner, null);
            CustomTextView customTextView = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            customTextView.setText("" + mListOfValues.get(position));
           /* convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Config.SELECTED_ID = position;
                    mTreeNames = treeNames.getSelectedTreeName(mTreeTypeNames.get(Config.SELECTED_ID).get("treeName"));
                    mLocationBasedSeletedTree.setAdapter(new Adapter_LocationBasedSeletedTree(getActivity(), mTreeNames));
                }
            });*/
            return convertView;
        }
    }
}
