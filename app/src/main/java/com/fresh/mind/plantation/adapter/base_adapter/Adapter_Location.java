package com.fresh.mind.plantation.adapter.base_adapter;

import android.icu.text.LocaleDisplayNames;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.LocationSelected;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 17-01-2017.
 */
public class Adapter_Location extends BaseAdapter {
    private final ArrayList<HashMap<String, String>> mLocation;
    private final FragmentActivity mContext;

    public Adapter_Location(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation) {
        this.mContext = activity;
        this.mLocation = mLocation;
    }

    @Override
    public int getCount() {
        return mLocation.size();
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
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adapter_single_view, null);
        CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
        mSPinnerText.setText("" + mLocation.get(position).get("District"));
        mSPinnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.SELECTED_DISTRICT_NAME = mLocation.get(position).get("District");
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LocationSelected()).commit();
            }
        });

        return convertView;

    }
}
