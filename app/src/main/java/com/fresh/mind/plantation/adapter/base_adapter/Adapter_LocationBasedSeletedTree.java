package com.fresh.mind.plantation.adapter.base_adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.tab_pager.ViewDetailsTabView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;


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
        // return the total number of view types. this value should never change at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView treeName, mSubName, mPart2Title, mPart2SubName;
        ImageView imageView, mPart2Img;
        LinearLayout cardView1, cardView2;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adapter_tree_specis, null);
        int viewType = getItemViewType(position);
        cardView1 = (LinearLayout) convertView.findViewById(R.id.cardView2);
        cardView2 = (LinearLayout) convertView.findViewById(R.id.cardView3);

        if (viewType == 0) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.GONE);
            cardView2.setVisibility(View.GONE);
        } else if (viewType == 1) {
            cardView1.setVisibility(View.GONE);
            cardView2.setVisibility(View.VISIBLE);
        }
        treeName = (TextView) convertView.findViewById(R.id.mTreeName);
        mSubName = (TextView) convertView.findViewById(R.id.mSubName);
        imageView = (ImageView) convertView.findViewById(R.id.imageView2);
        mPart2Title = (TextView) convertView.findViewById(R.id.textView2);
        mPart2SubName = (TextView) convertView.findViewById(R.id.textView4);
        mPart2Img = (ImageView) convertView.findViewById(R.id.imageView3);

        treeName.setText("" + mListItemValies.get(position).get("treeName"));
        mSubName.setText("" + mListItemValies.get(position).get("subTreeName"));
        mPart2Title.setText("" + mListItemValies.get(position).get("treeName"));
        mPart2SubName.setText("" + mListItemValies.get(position).get("subTreeName"));
        String imag = mListItemValies.get(position).get("storagePath");
        //Log.d("imag", "" + imag);
        if (imag != null) {
                                /*ByteArrayInputStream imageStream = new ByteArrayInputStream(imag);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);*/
            for (int i = 0; i < mImages.size(); i++) {
                String storagePath = mImages.get(i).get("storagePath");
                if (storagePath.equals(imag)) {
                    Bitmap theImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imag), 64, 64);
                    imageView.setImageBitmap(theImage);
                    mPart2Img.setImageBitmap(theImage);
                }
            }
        }
        //  Log.d("imgpath", "" + imgpath);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDetailsTabView viewTreeDetails = new ViewDetailsTabView();
                Bundle bundle = new Bundle();
                bundle.putString("titleName", "Choose By Location1");
                Config.TREE_NAME = mListItemValies.get(position).get("treeName");
                bundle.putString("treeName", "" + mListItemValies.get(position).get("treeName"));
                viewTreeDetails.setArguments(bundle);
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).commit();

            }
        });

        return convertView;
    }
}
