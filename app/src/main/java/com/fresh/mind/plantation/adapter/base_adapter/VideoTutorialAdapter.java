package com.fresh.mind.plantation.adapter.base_adapter;

import android.content.Intent;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Holder;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.CustomVideoPlayer;
import com.fresh.mind.plantation.activity.PlayingVideo;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.R.id.imgDisplay;
import static com.fresh.mind.plantation.R.id.mTreeName;

/**
 * Created by AND I5 on 01-03-2017.
 */

public class VideoTutorialAdapter extends BaseAdapter {
    private final ArrayList<HashMap<String, String>> mDetails;
    private final FragmentActivity mContext;
    // private final ArrayList<HashMap<String, byte[]>> mVideoImagePah;

    public VideoTutorialAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> mAllVidoe) {
        this.mContext = activity;
        this.mDetails = mAllVidoe;
        //this.mVideoImagePah = mVideoImagePah;
    }

    @Override
    public int getCount() {
        return mDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
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
            LinearLayout cardView = (LinearLayout) convertView.findViewById(R.id.cardView3);
            cardView.setVisibility(View.GONE);
            holder.mSubName = (CustomTextView) convertView.findViewById(R.id.mSubName);
            holder.treeName = (CustomTextView) convertView.findViewById(mTreeName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.cnsLayout1 = (ConstraintLayout) convertView.findViewById(R.id.cnsLayout1);
            convertView.getTag();
        } else {
            holder = (Holder) convertView.getTag();
        }
       /* if (Config.checkMaterial() == 1) {
            holder.cnsLayout1.setBackground(mContext.getDrawable(R.drawable.ripple_location));
        }*/
        try {
            final File file = new File(mDetails.get(position).get("path"));
            String tutorial_title = mDetails.get(position).get("tutorial_title");
            //holder.treeName.setText("" + file.getName());
            holder.treeName.setText("" + tutorial_title);
            holder.mSubName.setText("" + mDetails.get(position).get("Description"));

            String mImagePath = mDetails.get(position).get("path");
            //Log.d("mImagePath", "" + mImagePath);
            if (mImagePath != null) {
                //Picasso.with(mContext).load(Uri.fromFile(new File(mImagePath))).into(holder.imageView);
                holder.imageView.setImageBitmap(ThumbnailUtils.createVideoThumbnail(mImagePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND));
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mContext, CustomVideoPlayer.class);
                            intent.putExtra("videofilename", "" + file.getAbsolutePath());
                            mContext.startActivity(intent);
                        }
                    }, 300);

                }
            });
        } catch (NullPointerException e) {
            Log.d("34r554", "" + e);
        }

        return convertView;
    }

}
