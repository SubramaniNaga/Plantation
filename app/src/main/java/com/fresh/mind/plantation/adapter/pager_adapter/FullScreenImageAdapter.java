package com.fresh.mind.plantation.adapter.pager_adapter;


import android.content.Context;
import android.net.Uri;
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
import com.fresh.mind.plantation.tab_pager.ViewDetailsTabView;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class FullScreenImageAdapter extends PagerAdapter {

    private final ArrayList<HashMap<String, String>> mListOfImages;
    private FragmentActivity _activity;

    // constructor
    public FullScreenImageAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> mListOfImages) {
        this._activity = activity;
        //this._imagePaths = imagePaths;
        this.mListOfImages = mListOfImages;
        Log.d("mListOfImages", "" + mListOfImages.size());
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
        PhotoView imgDisplay = (PhotoView) viewLayout.findViewById(R.id.imgDisplay);
        CustomTextView count = (CustomTextView) viewLayout.findViewById(R.id.count);
        String imag = mListOfImages.get(position).get("storagePath");
        Log.d("imagimag", "" + imag);
        //Log.d("imagimag", mListOfImages.size() + " " + imag);
        if (imag == null) {
        } else {
            try {
                /*Bitmap bmp = BitmapFactory.decodeByteArray(imag, 0, imag.length);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                imgDisplay.setImageBitmap(bmp);*/

              /*  Glide.with(_activity).load(Uri.fromFile(new File(imag)))
                        .into(imgDisplay);*/
                Picasso.with(_activity)
                        .load((Uri.fromFile(new File(imag))))
                        .error(R.drawable.no_thumbnail)         // optional
                        .into(imgDisplay);
                count.setText("" + (position + 1) + "/" + mListOfImages.size());
                Log.d("dsf234", "" + (position + 1) + "/" + mListOfImages.size());
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
