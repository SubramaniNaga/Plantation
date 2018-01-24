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
import com.fresh.mind.plantation.adapter.base_adapter.AdapterContact;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.ContactUs;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class ContactInformation extends Fragment {

    private View rootView;
    private ListView mContactList;
    private ContactUs contactUs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ((MainActivity) getActivity()).setContact(getActivity().getResources().getString(R.string.ContactUs));
        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        contactUs = new ContactUs(getActivity());

        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        rootView = inflater.inflate(R.layout.plant_contact, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        mContactList = (ListView) rootView.findViewById(R.id.contactDetals);
        rootView.findViewById(R.id.mLine).setVisibility(View.VISIBLE);
        CustomTextView districtName, contactDetails;
        districtName = (CustomTextView) rootView.findViewById(R.id.districtName);
        contactDetails = (CustomTextView) rootView.findViewById(R.id.contactDetails);
        districtName.setTextColor(getActivity().getResources().getColor(R.color.white));
        contactDetails.setTextColor(getActivity().getResources().getColor(R.color.white));
        Config.SELECTED_TAB_VIEW_POSITION = 0;
        ArrayList<HashMap<String, String>> mContactDetails = contactUs.getContactDetails(languages);
        AdapterContact adapterContact = new AdapterContact(getActivity(), mContactDetails);
        mContactList.setAdapter(adapterContact);

        return rootView;
    }
}

