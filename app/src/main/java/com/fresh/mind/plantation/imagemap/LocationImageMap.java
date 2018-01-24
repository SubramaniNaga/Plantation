package com.fresh.mind.plantation.imagemap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;

/**
 * Created by AND I5 on 16-11-2017.
 */

public class LocationImageMap extends Fragment {
    private ImageMap mImageMap, map_tamil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_location_imag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        DistrictNameList districtNameList = new DistrictNameList(getActivity());
        Log.d("locationssss", "" + districtNameList.getDistrictNames(languages));


        if (languages.equals("1")) {
            map_tamil = (ImageMap) view.findViewById(R.id.map_tamil);
            map_tamil.setImageResource(R.drawable.tn_map);
            map_tamil.setVisibility(View.VISIBLE);
            map_tamil.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {
                @Override
                public void onImageMapClicked(int id, ImageMap imageMap) {

                    map_tamil.passActivity(getActivity(), id);
                }

                @Override
                public void onBubbleClicked(int id) {

                }
            });
        } else {
            mImageMap = (ImageMap) view.findViewById(R.id.map);
            mImageMap.setVisibility(View.VISIBLE);
            mImageMap.setImageResource(R.drawable.tn_map);
            mImageMap.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {
                @Override
                public void onImageMapClicked(int id, ImageMap imageMap) {
                    // mImageMap.showBubble(id);
                    mImageMap.passActivity(getActivity(), id);
                }

                @Override
                public void onBubbleClicked(int id) {

                }
            });
        }


    }
}
