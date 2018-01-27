package com.fresh.mind.plantation.fragment.ModelInter_tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.pager_adapter.FullScreenImageAdapter;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.Intercrops;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.Modelinfo;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.R.id.mNodata;

/**
 * Created by AND I5 on 25-01-2018.
 */
public class VIewAgreoForestryImage extends Fragment {
    private String languages;
    private ViewPager viewPager;
    private Intercrops intercropsinfo;
    private CustomTextView mNodata;
    private Modelinfo modelinfo;
    private String cals, des;

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
        Bundle bundle = getArguments();
        cals = bundle.getString("class");
        des = bundle.getString("des");

        ((MainActivity) getActivity()).viewTreeDetails("" + des, "View Details");
        return inflater.inflate(R.layout.plant_view_images, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        mNodata = (CustomTextView) view.findViewById(R.id.mNodata);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        intercropsinfo = new Intercrops(getActivity());
        modelinfo = new Modelinfo(getActivity());

        ArrayList<HashMap<String, String>> mImagePath;
        if ("inter".equals(cals)) {
            mImagePath = intercropsinfo.getImges(des, languages);
        } else {
            mImagePath = modelinfo.getImges(des, languages);
        }
        //Log.d("mImagePath", "" + mImagePath.size());
        if (mImagePath.size() >= 1) {
            FullScreenImageAdapter fullScreenImageAdapter = new FullScreenImageAdapter(getActivity(), mImagePath);
            viewPager.setAdapter(fullScreenImageAdapter);
            viewPager.setCurrentItem(0);
            mNodata.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
        }

    }
}
