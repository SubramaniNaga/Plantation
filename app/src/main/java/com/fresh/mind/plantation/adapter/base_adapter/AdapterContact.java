package com.fresh.mind.plantation.adapter.base_adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.Directions;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.R.id.HeaderLayout;
import static com.fresh.mind.plantation.R.id.contactNo;

/**
 * Created by AND I5 on 11-02-2017.
 */
public class AdapterContact extends BaseAdapter {

    private final FragmentActivity mContext;
    private final ArrayList<HashMap<String, String>> destrictName;


    public AdapterContact(FragmentActivity activity, ArrayList<HashMap<String, String>> mDetails) {
        this.mContext = activity;

        this.destrictName = mDetails;

    }

    @Override
    public int getCount() {
        return destrictName.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return destrictName.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.plant_contact, null);
        ListView listView = (ListView) convertView.findViewById(R.id.contactDetals);
        listView.setVisibility(View.GONE);
        CustomTextView mDes, mAdd, mNo, mMapDirecrion;
        mDes = (CustomTextView) convertView.findViewById(R.id.districtName);
        mAdd = (CustomTextView) convertView.findViewById(R.id.contactDetails);
        mNo = (CustomTextView) convertView.findViewById(contactNo);
        mMapDirecrion = (CustomTextView) convertView.findViewById(R.id.mMapDirecrion);
        mMapDirecrion.setVisibility(View.VISIBLE);
        LinearLayout HeaderLayout = (LinearLayout) convertView.findViewById(R.id.HeaderLayout);
        HeaderLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        mDes.setText("" + destrictName.get(position).get("districtName"));
        mAdd.setText("" + destrictName.get(position).get("address") + ", " + destrictName.get(position).get("phoneNo"));
        // mNo.setText("" + contactNo[position]);
        mDes.setTextSize(14);
        mAdd.setTextSize(12);
        mNo.setTextSize(12);
        mDes.setTextColor(mContext.getResources().getColor(R.color.light_black));
        mAdd.setTextColor(mContext.getResources().getColor(R.color.light_black));
        mNo.setTextColor(mContext.getResources().getColor(R.color.light_black));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("direction", "" + destrictName.get(position).get("direction"));
                Fragment fragment = new Directions();
                fragment.setArguments(bundle);
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).addToBackStack("").commit();

            }
        });
        return convertView;
    }
}
