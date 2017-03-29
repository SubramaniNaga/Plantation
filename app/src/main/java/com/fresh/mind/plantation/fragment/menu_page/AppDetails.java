package com.fresh.mind.plantation.fragment.menu_page;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class AppDetails extends Fragment {

    private View rootView;
    private CustomTextView about_plantantion, ref1, ref2, ref3, ref4, ref5, ref6, ref7, mistral;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setAppDetails(getActivity().getResources().getString(R.string.appDetails));
        rootView = inflater.inflate(R.layout.plant_app_details, null);
        init();

        Config.SELECTED_TAB_VIEW_POSITION = 0;
        ref1.onClick(getActivity(), ref1);
        ref2.onClick(getActivity(), ref2);
        ref3.onClick(getActivity(), ref3);
        ref4.onClick(getActivity(), ref4);
        ref5.onClick(getActivity(), ref5);
        ref6.onClick(getActivity(), ref6);
        ref7.onClick(getActivity(), ref7);

        return rootView;
    }

    private void init() {
        about_plantantion = (CustomTextView) rootView.findViewById(R.id.about_plantantion);
        ref1 = (CustomTextView) rootView.findViewById(R.id.ref1);
        ref2 = (CustomTextView) rootView.findViewById(R.id.ref2);
        ref3 = (CustomTextView) rootView.findViewById(R.id.ref3);
        ref4 = (CustomTextView) rootView.findViewById(R.id.ref4);
        ref5 = (CustomTextView) rootView.findViewById(R.id.ref5);
        ref6 = (CustomTextView) rootView.findViewById(R.id.ref6);
        ref7 = (CustomTextView) rootView.findViewById(R.id.ref7);
        mistral = (CustomTextView) rootView.findViewById(R.id.mistral);

        ref1.setPaintFlags(about_plantantion.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ref2.setPaintFlags(about_plantantion.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ref3.setPaintFlags(about_plantantion.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ref4.setPaintFlags(about_plantantion.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ref5.setPaintFlags(about_plantantion.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ref6.setPaintFlags(about_plantantion.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ref7.setPaintFlags(about_plantantion.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}

