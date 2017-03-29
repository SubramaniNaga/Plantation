package com.fresh.mind.plantation.fragment.Inside;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.adapter.pager_adapter.FullScreenImageAdapter;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


import static com.fresh.mind.plantation.Constant.Config.mImagesFromDescription;


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
    ArrayList<HashMap<String, byte[]>> mImageList;
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
        rootView = inflater.inflate(R.layout.view_images, null);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);

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
        mImageList = imageDb.getImages(Config.TREE_NAME, AppData.checkLanguage(getActivity()));
        Config.mImagesFromDescription.clear();
        ArrayList<HashMap<String, String>> mImagePath = imageDb.getImagePaths(languages, Config.TREE_NAME);
        Log.d("mImagePath", "" + mImagePath.size());
        new LoaderImages(getActivity(), mImagePath).execute();

/*
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 3000);
*/


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
        });
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


    class LoaderImages extends AsyncTask<HashMap<String, byte[]>, Void, ArrayList<HashMap<String, byte[]>>> {

        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mImagePath;
        ProgressDialog progressDialog;

        public LoaderImages(FragmentActivity activity, ArrayList<HashMap<String, String>> mImagePath) {

            this.mContext = activity;
            this.mImagePath = mImagePath;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle(null);
            progressDialog.setMessage(mContext.getString(R.string.fetchingData));
            progressDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, byte[]>> doInBackground(HashMap<String, byte[]>... params) {

            for (int i = 0; i < mImagePath.size(); i++) {
                String storagePath = mImagePath.get(i).get("storagePath");

                if (storagePath != null) {
                    HashMap<String, byte[]> mHashBitMap = new HashMap<>();
                    /*Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(storagePath), 64, 64);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ThumbImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();*/
                    //Log.d("storagePathstoragePath", "" + storagePath);
                    try {
                        Bitmap bmp = BitmapFactory.decodeFile(storagePath);
                        if (bmp != null) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            mHashBitMap.put("storagePath", byteArray);
                            mImagesFromDescription.add(mHashBitMap);
                        }
                    } catch (OutOfMemoryError outOfMemoryError) {

                        outOfMemoryError.printStackTrace();
                    }
                }
            }

            return mImagesFromDescription;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, byte[]>> hashMaps) {
            super.onPostExecute(hashMaps);

            if (hashMaps.size() >= 1) {
                FullScreenImageAdapter fullScreenImageAdapter = new FullScreenImageAdapter(getActivity(), hashMaps);
                viewPager.setAdapter(fullScreenImageAdapter);
                viewPager.setCurrentItem(0);
                mNodata.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            } else {
                viewPager.setVisibility(View.GONE);
                mNodata.setVisibility(View.VISIBLE);
            }
            progressDialog.dismiss();
        }

    }
}
