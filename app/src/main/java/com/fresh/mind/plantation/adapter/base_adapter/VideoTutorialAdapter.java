package com.fresh.mind.plantation.adapter.base_adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.PlayingVideo;
import com.fresh.mind.plantation.customized.CustomTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.R.id.imageView;

/**
 * Created by AND I5 on 01-03-2017.
 */

public class VideoTutorialAdapter extends BaseAdapter {
    private final ArrayList<HashMap<String, String>> mDetails;
    private final FragmentActivity mContext;
    private final ArrayList<HashMap<String, byte[]>> mVideoImagePah;

    public VideoTutorialAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> mAllVidoe, ArrayList<HashMap<String, byte[]>> mVideoImagePah) {
        this.mContext = activity;
        this.mDetails = mAllVidoe;
        this.mVideoImagePah = mVideoImagePah;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adapter_tree_specis, null);
        LinearLayout cardView = (LinearLayout) convertView.findViewById(R.id.cardView3);
        cardView.setVisibility(View.GONE);
        CustomTextView mSubtitle = (CustomTextView) convertView.findViewById(R.id.mSubName);
        CustomTextView mTreeName = (CustomTextView) convertView.findViewById(R.id.mTreeName);
        ImageView mVideoThumb = (ImageView) convertView.findViewById(R.id.imageView2);
        final File file = new File(mDetails.get(position).get("path"));
        mTreeName.setText("" + file.getName());
        Log.d("skjdahh", "" + mDetails.get(position).get("Description"));
        mSubtitle.setText("" + mDetails.get(position).get("Description"));


        //mVideoThumb.setImageBitmap(ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND));
        byte[] mImagePath = mVideoImagePah.get(position).get("ThumbImage");
        Bitmap bmp = BitmapFactory.decodeByteArray(mImagePath, 0, mImagePath.length);
        try {
            if (bmp != null) {
                mVideoThumb.setImageBitmap(Bitmap.createScaledBitmap(bmp, 64, 64, false));
            }

        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, PlayingVideo.class);
                intent.putExtra("videofilename", "" + file.getAbsolutePath());
                mContext.startActivity(intent);


                /*
                String filPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Plantation/" + file1.getName();
                File file2 = new File(filPath);

                Log.d("Asddvideoasd", "" + video + "  " + file2.length());
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_video);
                VideoView mVideoview = (VideoView) dialog.findViewById(R.id.mVideoview);
                mVideoview.setVideoPath("" + file1.getAbsoluteFile());
                mVideoview.setMediaController(new MediaController(mContext));
                mVideoview.requestFocus();
                mVideoview.start();
                dialog.show();*/
            }
        });
        return convertView;
    }


}
