package com.fresh.mind.plantation.adapter.base_adapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;

import java.util.HashMap;
import java.util.List;


/**
 * Created by AND I5 on 03-01-2018.
 */

public class SchemeExpandableListAdapter extends BaseExpandableListAdapter {
    private final FragmentActivity mContext;
    private final HashMap<String, List<String>> expandableListDetail;
    private final List<String> expandableListTitle;

    public SchemeExpandableListAdapter(FragmentActivity activity, List<String> strings, HashMap<String, List<String>> expandableListDetail) {
        this.mContext = activity;
        this.expandableListTitle = strings;
        this.expandableListDetail = expandableListDetail;
    }


    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return expandableListDetail.get(this.expandableListTitle.get(groupPosition)).size() - 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final String expandedListText = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_spinner_1, null);
        }
        final CustomTextView expandedListTextView = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
        expandedListTextView.setText("\t\t\t\t " + expandedListText);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Log.d("isExpandeddsdklskisLastChild", "" + isLastChild);
        final String expandedListText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_single_view, null);
        }
        final CustomTextView expandedListTextView = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
        expandedListTextView.setText(expandedListText);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 8, 8, 8);
        convertView.setLayoutParams(lp);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
