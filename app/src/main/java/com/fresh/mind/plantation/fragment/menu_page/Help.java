package com.fresh.mind.plantation.fragment.menu_page;

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
public class Help extends Fragment {

    private View rootView;
    private CustomTextView mLang;
    LanguageChange languageChange;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }

        ((MainActivity) getActivity()).setHelp(getActivity().getResources().getString(R.string.help));
        rootView = inflater.inflate(R.layout.plant_help, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        Config.SELECTED_TAB_VIEW_POSITION = 0;

        return rootView;
    }
}
