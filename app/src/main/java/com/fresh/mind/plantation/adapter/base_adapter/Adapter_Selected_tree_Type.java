package com.fresh.mind.plantation.adapter.base_adapter;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Holder;
import com.fresh.mind.plantation.R;

import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.tab_pager.ViewDetailsTabView;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION;
import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION_2;
import static com.fresh.mind.plantation.R.id.cardView2;

import static com.fresh.mind.plantation.R.id.mSPinnerText;
import static com.fresh.mind.plantation.R.id.mSubName;


/**
 * Created by AND I5 on 18-01-2017.
 */
public class Adapter_Selected_tree_Type extends BaseAdapter {
    private final FragmentActivity mContext;
    private final ArrayList<HashMap<String, String>> mListItemValies;
    private final String bySortType;
    // private final ArrayList<HashMap<String, byte[]>> mImagesList;

    public Adapter_Selected_tree_Type(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, String bySortType/*, ArrayList<HashMap<String, byte[]>> mImagesList*/) {
        this.mContext = activity;
        this.mListItemValies = mLocation;
        this.bySortType = bySortType;
        // this.mImagesList = mImagesList;
        // Log.d("mImagesList", "" + mImagesList.size() + "  " + mImagesList);
    }

    @Override
    public int getCount() {
        return mListItemValies.size();
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

        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_tree_specis, null);
            holder.cardView1 = (LinearLayout) convertView.findViewById(cardView2);
            holder.cardView2 = (LinearLayout) convertView.findViewById(R.id.cardView3);
            holder.cardView1.setVisibility(View.VISIBLE);
            holder.cardView2.setVisibility(View.GONE);

            holder.treeName = (CustomTextView) convertView.findViewById(R.id.mTreeName);
            holder.mSubName = (CustomTextView) convertView.findViewById(mSubName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.mPart2Title = (CustomTextView) convertView.findViewById(R.id.textView2);
            holder.mPart2SubName = (CustomTextView) convertView.findViewById(R.id.textView4);
            holder.mPart2Img = (ImageView) convertView.findViewById(R.id.imageView3);
            holder.cnsLayout1 = (ConstraintLayout) convertView.findViewById(R.id.cnsLayout1);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (Config.checkMaterial() == 1) {
            holder.cnsLayout1.setBackground(mContext.getDrawable(R.drawable.ripple_location));
        }
        holder.treeName.setText("" + mListItemValies.get(position).get("treeName"));
        holder.mSubName.setText("" + mListItemValies.get(position).get("subTreeName"));
        holder.mPart2Title.setText("" + mListItemValies.get(position).get("treeName"));
        holder.mPart2SubName.setText("" + mListItemValies.get(position).get("subTreeName"));

        String mImg = mListItemValies.get(position).get("storagePath");
        if (mImg != null) {
            Picasso.with(mContext).load((Uri.fromFile(new File(mImg)))).error(R.drawable.no_thumbnail).into(holder.imageView);
            Picasso.with(mContext).load((Uri.fromFile(new File(mImg)))).error(R.drawable.no_thumbnail).into(holder.mPart2Img);
        }

        if (getItemViewType(position) == 0) {
            holder.cardView1.setVisibility(View.VISIBLE);
            holder.cardView2.setVisibility(View.GONE);

        } else if (getItemViewType(position) == 1) {
            holder.cardView1.setVisibility(View.GONE);
            holder.cardView2.setVisibility(View.VISIBLE);
        }
        Typeface externalFont = Typeface.createFromAsset(mContext.getAssets(), "proximanovabold.otf");
        Typeface externalFontRegular = Typeface.createFromAsset(mContext.getAssets(), "proximanovaregular.otf");

        if (bySortType != null) {
            //z-a is normal name
            if (bySortType.equals("Z-A")) {

                /*holder.treeName.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
                holder.mSubName.setText("" + mListItemValiesFilter.get(position).get("treeName"));
                // holder.mSubName.setText("" + position);
                holder.mPart2Title.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
                holder.mPart2SubName.setText("" + mListItemValiesFilter.get(position).get("treeName"));*/
                setText(position, holder);
                holder.mSubName.setTypeface(externalFont);
                holder.mPart2SubName.setTypeface(externalFont);

                holder.treeName.setTypeface(externalFontRegular);
                holder.mPart2Title.setTypeface(externalFontRegular);
            } else if (bySortType.equals("A-Z")) {
                setText(position, holder);
                holder.treeName.setTypeface(externalFont);
                holder.mPart2Title.setTypeface(externalFont);

                holder.mSubName.setTypeface(externalFontRegular);
                holder.mPart2SubName.setTypeface(externalFontRegular);
            } else {
                setText(position, holder);
            }
        } else {
            setText(position, holder);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LISTVIEW_SMOOTH_VIEW_POSITION_2 = position;
                ViewDetailsTabView viewTreeDetails = new ViewDetailsTabView();
                Bundle bundle = new Bundle();
                bundle.putString("titleName", "Tree Type11");
                Config.TREE_NAME = mListItemValies.get(position).get("treeName");
                Config.COMMON_KEY = mListItemValies.get(position).get("common_key");
                bundle.putString("treeName", "" + mListItemValies.get(position).get("treeName"));
                viewTreeDetails.setArguments(bundle);
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).addToBackStack(null).commit();

            }
        });
        return convertView;
    }

    private void setText(int position, Holder holder) {
        holder.treeName.setText("" + mListItemValies.get(position).get("treeName"));
        holder.mSubName.setText("" + mListItemValies.get(position).get("subTreeName"));
        holder.mPart2Title.setText("" + mListItemValies.get(position).get("treeName"));
        holder.mPart2SubName.setText("" + mListItemValies.get(position).get("subTreeName"));
    }

}
