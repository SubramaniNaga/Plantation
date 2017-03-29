package com.fresh.mind.plantation.fragment.menu_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.AdapterGlossery;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.GlossaryTable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class Glossary extends Fragment {

    private View rootView;
    private ListView listView;
    private GlossaryTable glossaryTable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        glossaryTable = new GlossaryTable(getActivity());
        String languages = languageChange.getStatus();

        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setGlossary(getActivity().getResources().getString(R.string.glossary));
        rootView = inflater.inflate(R.layout.plant_glossary, null);
        Config.SELECTED_TAB_VIEW_POSITION = 0;
        ArrayList<HashMap<String, String>> hashMaps = glossaryTable.getContactDetails(languages);
        listView = (ListView) rootView.findViewById(R.id.listView);
        AdapterGlossery adapterGlossery = new AdapterGlossery(getActivity(),hashMaps);
        listView.setAdapter(adapterGlossery);
        return rootView;
    }
}

