package com.fresh.mind.plantation.adapter.base_adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;


import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.activity.GlossaryCompare.mSelectedPosition;


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
        LinearLayout headerLayout = (LinearLayout) convertView.findViewById(R.id.headerLayout);
        CustomTextView description = (CustomTextView) convertView.findViewById(R.id.description);
       /*WebView textJustified = (WebView) convertView.findViewById(R.id.textJustified);*/
        CustomTextView title = (CustomTextView) convertView.findViewById(R.id.title);

        title.setText("" + descriptionValues.get(position).get("word"));
        description.setText("\t\t" + descriptionValues.get(position).get("meaning"));

        /*JustifiedTextView justify2 = (JustifiedTextView) convertView.findViewById(R.id.justify2);
        justify2.setText(String.valueOf(Html.fromHtml("\t\t" + descriptionValues.get(position).get("meaning"))));
        justify2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        justify2.setLineSpacing(12);
        justify2.setAlignment(Paint.Align.LEFT);
        justify2.setTypeFace(Typeface.createFromAsset(mContext.getAssets(), "proximanovaregular.otf"));*/


        if (mSelectedPosition == position && mSelectedPosition != -1) {
            headerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.shadow_login_layout));
            title.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            title.setTextColor(mContext.getResources().getColor(R.color.white));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(8, 8, 8, 8);
            headerLayout.setLayoutParams(lp);
        } else {
            headerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.app_bar_layout_searchview));
            title.setBackground(mContext.getResources().getDrawable(R.drawable.layout_shadow));
            title.setTextColor(mContext.getResources().getColor(R.color.black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(4, 4, 4, 4);
            headerLayout.setLayoutParams(lp);
        }

        /*textJustified.loadData(String.format(" %s ", "\t\t" + descriptionValues.get(position).get("meaning")), "text/html", "utf-8");*/
        return convertView;
    }
}
