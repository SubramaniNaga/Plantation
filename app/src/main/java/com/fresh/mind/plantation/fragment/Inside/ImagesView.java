package com.fresh.mind.plantation.fragment.Inside;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.pager_adapter.FullScreenImageAdapter;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.ImageDb;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by AND I5 on 03-02-2017.
 */
public class ImagesView extends Fragment {
    View rootView;
    ViewPager viewPager;
    ImageView mPreview, mNext, mCurrentImg;
    // int[] images = {R.drawable.grevillea_robusta, R.drawable.thespesia_populnnea, R.drawable.tectona_gradis, R.drawable.tree_timber, R.drawable.acacia, R.drawable.acacia_holosericea, R.drawable.acrocarpus, R.drawable.ailanthus, R.drawable.toona_siliata, R.drawable.thespesia_populnnea           , R.drawable.tectona_gradis, R.drawable.pink_edar};
    private Utils utils;
    public static TextView treeNameTxt, ImageType, size;

    private CustomTextView mNodata;

    ImageDb imageDb;
    int[] mResources = {R.drawable.logo_3, R.drawable.logo_3, R.drawable.logo_3};
    int currentPage = 0;
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }

        rootView = inflater.inflate(R.layout.plant_view_images, null);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        imageDb = new ImageDb(getActivity());

        mPreview = (ImageView) rootView.findViewById(R.id.imageView6);
        mNext = (ImageView) rootView.findViewById(R.id.imageView7);
        mCurrentImg = (ImageView) rootView.findViewById(R.id.imageView5);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        treeNameTxt = (TextView) rootView.findViewById(R.id.textView7);
        treeNameTxt.setText("" + Config.TREE_NAME);
        ImageType = (TextView) rootView.findViewById(R.id.textView10);
        size = (TextView) rootView.findViewById(R.id.textView8);
        size.setVisibility(View.GONE);
        //mImageList = imageDb.getImages(Config.TREE_NAME, AppData.checkLanguage(getActivity()));
        Config.mImagesFromDescription.clear();

        Log.d("sd23549879", "" + Config.TREE_NAME + "  " + Config.COMMON_KEY);
        ArrayList<HashMap<String, String>> mImagePath = imageDb.getImagePaths(languages, Config.COMMON_KEY);
        Log.d("sd23549879Size", "" + mImagePath.size());

        //new LoaderImages(getActivity(), mImagePath).execute();
        if (mImagePath.size() >= 1) {
            FullScreenImageAdapter fullScreenImageAdapter = new FullScreenImageAdapter(getActivity(), mImagePath);
            viewPager.setAdapter(fullScreenImageAdapter);
            viewPager.setCurrentItem(0);
            mNodata.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
        }

/*
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 3000);
*/

/*
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (0 == Config.POSITION) {
                    Toast.makeText(getActivity(), "This is First Image", Toast.LENGTH_SHORT).show();
                } else {
                    Config.POSITION = Config.POSITION - 1;
                    byte[] imag = mImageList.get(Config.POSITION).get("treeIcon");
                    if (imag.equals(null)) {
                    } else {
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(imag);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        mCurrentImg.setImageBitmap(theImage);
                    }
                }
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageList.size() - 1 == Config.POSITION) {
                    Toast.makeText(getActivity(), "This is Last Image", Toast.LENGTH_SHORT).show();
                } else {
                    Config.POSITION = Config.POSITION + 1;
                    byte[] imag = mImageList.get(Config.POSITION).get("treeIcon");

                    if (imag.equals(null)) {
                    } else {
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(imag);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        mCurrentImg.setImageBitmap(theImage);
                    }
                }
            }
        });*/
        return rootView;
    }


    Runnable update = new Runnable() {
        public void run() {
            if (currentPage == mResources.length - 1) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        }
    };


}
