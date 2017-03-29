package com.fresh.mind.plantation.adapter.base_adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.Constant.AppData.title;

/**
 * Created by AND I5 on 11-02-2017.
 */
public class AdapterGlossery extends BaseAdapter {
    private final FragmentActivity mContext;

    private final ArrayList<HashMap<String, String>> descriptionValues;

    public AdapterGlossery(FragmentActivity activity, ArrayList<HashMap<String, String>> hashMaps) {
        this.mContext = activity;

        this.descriptionValues = hashMaps;
    }

    @Override
    public int getCount() {
        return descriptionValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return descriptionValues.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adapter_glossery, null);
        CustomTextView title, description;
        description = (CustomTextView) convertView.findViewById(R.id.description);
        title = (CustomTextView) convertView.findViewById(R.id.title);

        title.setText("" + descriptionValues.get(position).get("word"));
        description.setText("" + descriptionValues.get(position).get("meaning"));
        return convertView;
    }
}
