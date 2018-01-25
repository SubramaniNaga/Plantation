package com.fresh.mind.plantation.adapter.base_adapter;

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

import static com.fresh.mind.plantation.R.id.cardView2;
import static com.fresh.mind.plantation.R.id.mLocationBasedSeletedTree;
import static com.fresh.mind.plantation.R.id.mSubName;
import static com.fresh.mind.plantation.R.id.oval;


/**
 * Created by AND I5 on 17-01-2017.
 */
public class Adapter_LocationBasedSeletedTree extends BaseAdapter {
    private final ArrayList<HashMap<String, String>> mListItemValies;
    private final FragmentActivity mContext;
    private final ArrayList<HashMap<String, String>> mImages;


    public Adapter_LocationBasedSeletedTree(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, ArrayList<HashMap<String, String>> mImages) {
        this.mContext = activity;
        this.mListItemValies = mLocation;
        this.mImages = mImages;
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_tree_specis, null);
            int viewType = getItemViewType(position);
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

        String imag = mListItemValies.get(position).get("storagePath");
        //Log.d("imag", "" + imag);
        if (getItemViewType(position) == 0) {
            holder.treeName.setText("" + mListItemValies.get(position).get("treeName"));
            holder.mSubName.setText("" + mListItemValies.get(position).get("subTreeName"));
            if (imag != null) {
                Picasso.with(mContext).load((Uri.fromFile(new File(imag)))).error(R.drawable.no_thumbnail).into(holder.imageView);
                // Picasso.with(mContext).load((Uri.fromFile(new File(imag)))).error(R.drawable.logo_3).into(holder.mPart2Img);
            }
            holder.cardView1.setVisibility(View.VISIBLE);
            holder.cardView2.setVisibility(View.GONE);

        } else if (getItemViewType(position) == 1) {
            holder.mPart2Title.setText("" + mListItemValies.get(position).get("treeName"));
            holder.mPart2SubName.setText("" + mListItemValies.get(position).get("subTreeName"));
            if (imag != null) {
                //   Picasso.with(mContext).load((Uri.fromFile(new File(imag)))).error(R.drawable.logo_3).into(holder.imageView);
                Picasso.with(mContext).load((Uri.fromFile(new File(imag)))).error(R.drawable.no_thumbnail).into(holder.mPart2Img);
            }
            holder.cardView1.setVisibility(View.GONE);
            holder.cardView2.setVisibility(View.VISIBLE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Config.LISTVIEW_SMOOTH_VIEW_POSITION_2 = position;
                ViewDetailsTabView viewTreeDetails = new ViewDetailsTabView();
                Bundle bundle = new Bundle();
                bundle.putString("titleName", "Choose By Location1");
                Config.TREE_NAME = mListItemValies.get(position).get("treeName");
                Config.COMMON_KEY = mListItemValies.get(position).get("common_key");
                bundle.putString("treeName", "" + mListItemValies.get(position).get("treeName"));

                viewTreeDetails.setArguments(bundle);
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).addToBackStack(null).commit();

            }
        });

        return convertView;
    }
}
