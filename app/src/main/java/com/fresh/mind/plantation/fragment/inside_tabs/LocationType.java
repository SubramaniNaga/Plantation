package com.fresh.mind.plantation.fragment.inside_tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Location;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.R.string.treeType;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class LocationType extends Fragment {

    private View rootView;
    private ListView locationListView;
    public static ArrayList<HashMap<String, String>> mLocation;
    private CustomTextView mNodata;
    private DistrictNameList districtNameList;

    @Override
    public void onDestroy() {
        super.onDestroy();
        districtNameList.close();

        Log.d("CloseLocationType", "Objects");
    }
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
        ((MainActivity) getActivity()).setLocation("Choose By Location", "Choose By Location");
        rootView = inflater.inflate(R.layout.plant_location_type, null);
        locationListView = (ListView) rootView.findViewById(R.id.locationListView);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        districtNameList = new DistrictNameList(getActivity());

        mLocation = districtNameList.getDistrictNames(languages);
        Config.SELECTED_ID = 0;
        if (mLocation.size() >= 1) {
            mNodata.setVisibility(View.GONE);
            locationListView.setVisibility(View.VISIBLE);
            locationListView.setAdapter(new Adapter_Location(getActivity(), mLocation));
        } else {
            mNodata.setVisibility(View.VISIBLE);
            locationListView.setVisibility(View.GONE);
        }

        return rootView;
    }
}

