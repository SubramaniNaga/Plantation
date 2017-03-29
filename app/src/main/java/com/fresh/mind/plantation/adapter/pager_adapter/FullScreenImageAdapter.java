package com.fresh.mind.plantation.adapter.pager_adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.ImagesView;
import com.fresh.mind.plantation.tab_pager.ViewDetailsTabView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.fragment.Inside.ImagesView.size;


public class FullScreenImageAdapter extends PagerAdapter {

    private final ArrayList<HashMap<String, byte[]>> mListOfImages;
    private FragmentActivity _activity;
    private int[] _imagePaths;
    private LayoutInflater inflater;
    //int[] images = {R.drawable.grevillea_robusta, R.drawable.thespesia_populnnea, R.drawable.tectona_gradis, R.drawable.tree_timber, R.drawable.acacia, R.drawable.acacia_holosericea, R.drawable.acrocarpus, R.drawable.ailanthus, R.drawable.toona_siliata, R.drawable.thespesia_populnnea, R.drawable.tectona_gradis, R.drawable.pink_edar};
    String[] fileName = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    // constructor
    public FullScreenImageAdapter(FragmentActivity activity, ArrayList<HashMap<String, byte[]>> mListOfImages) {
        this._activity = activity;
        //this._imagePaths = imagePaths;
        this.mListOfImages = mListOfImages;
    }

    @Override
    public int getCount() {
        return this.mListOfImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ConstraintLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.adapter_fullscreen_image, container, false);
        ImageView imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        CustomTextView count = (CustomTextView) viewLayout.findViewById(R.id.count);
        byte[] imag = mListOfImages.get(position).get("storagePath");
        //Log.d("imagimag", mListOfImages.size() + " " + imag);
        if (imag.equals("null")) {
        } else {
            try {
                Bitmap bmp = BitmapFactory.decodeByteArray(imag, 0, imag.length);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                imgDisplay.setImageBitmap(bmp);
                count.setText("" + (position + 1) + "/" + mListOfImages.size());
                //ImagesView.size.setText("" + (position + 1) + "/" + mListOfImages.size());
                ((ViewPager) container).addView(viewLayout);
            } catch (OutOfMemoryError ex) {

                ViewDetailsTabView viewTreeDetails = new ViewDetailsTabView();
                Bundle bundle = new Bundle();

                bundle.putString("titleName", "Tree Species");
                Config.TREE_NAME = Config.TREE_NAME;
                bundle.putString("treeName", "" + Config.TREE_NAME);
                viewTreeDetails.setArguments(bundle);
                _activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).commit();
                ex.printStackTrace();
                ex.printStackTrace();
            }
        }
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ConstraintLayout) object);

    }

}
