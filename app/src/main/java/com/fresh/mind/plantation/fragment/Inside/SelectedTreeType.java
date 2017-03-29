package com.fresh.mind.plantation.fragment.Inside;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Selected_tree_Type;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Tree_Species;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.tab_pager.HomeTabView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.TreeList;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


import static com.fresh.mind.plantation.Constant.Config.mSELECTED_TREETYPE_IMAGES;
import static com.fresh.mind.plantation.Constant.Config.mSELECTED_TREETYPE_NAMES;

/**
 * Created by AND I5 on 18-01-2017.
 */
public class SelectedTreeType extends Fragment {
    private View rootView;
    private CustomTextView mNodata;
    private ListView mTreeSpecies;

    public static ArrayList<HashMap<String, String>> mImagesList;
    //private TreeNames treeNames;

    private TreeList treeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).viewTreeDetails(getActivity().getResources().getString(R.string.treeType), getActivity().getResources().getString(R.string.SelectedTreeType));

        rootView = inflater.inflate(R.layout.plant_tree_species, null);
        mTreeSpecies = (ListView) rootView.findViewById(R.id.mTreeSpecies);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        treeList = new TreeList(getActivity());
        mSELECTED_TREETYPE_IMAGES.clear();
        //treeNames = new TreeNames(getActivity());
        //mLocation = treeNames.getSelectedTreeName(Config.SELECTED_TREE_TYPE, "");
        if (mSELECTED_TREETYPE_IMAGES.size() >= 1) {
            Adapter_Selected_tree_Type adapter_tree_species = new Adapter_Selected_tree_Type(getActivity(), mSELECTED_TREETYPE_NAMES, mSELECTED_TREETYPE_IMAGES);
            mTreeSpecies.setAdapter(adapter_tree_species);
            mTreeSpecies.setVisibility(View.VISIBLE);
            mNodata.setVisibility(View.GONE);
        } else {
            mSELECTED_TREETYPE_NAMES = treeList.getSelectedTreeName(Config.SELECTED_TREE_TYPE, AppData.checkLanguage(getActivity()));
            mImagesList = treeList.getTreeByImages(Config.SELECTED_TREE_TYPE, AppData.checkLanguage(getActivity()));

            HomeTabView.setTitle(Config.SELECTED_TAB_VIEW_POSITION, getActivity());
            if (mSELECTED_TREETYPE_NAMES.size() >= 1) {
                new LoaderAsync(getActivity(), mSELECTED_TREETYPE_NAMES, mImagesList).execute();
            } else {
                mTreeSpecies.setVisibility(View.GONE);
                mNodata.setVisibility(View.VISIBLE);
            }
        }
        return rootView;
    }


    class LoaderAsync extends AsyncTask<ArrayList<HashMap<String, byte[]>>, Void, ArrayList<HashMap<String, byte[]>>> {
        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mSELECTED_TREETYPE_NAMES;
        private final ArrayList<HashMap<String, String>> mImagePaths;
        private ProgressDialog progressDialog;

        public LoaderAsync(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, ArrayList<HashMap<String, String>> mImagePaths) {
            this.mContext = activity;
            this.mSELECTED_TREETYPE_NAMES = mLocation;

            this.mImagePaths = mImagePaths;

            // Log.d("mImagePaths", "" + mImagePaths.size());
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle(null);
            progressDialog.setMessage("Fetching Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, byte[]>> doInBackground(ArrayList<HashMap<String, byte[]>>... params) {
            Log.d("mImagePaths", "" + mImagePaths.size());
            for (int i = 0; i < mImagePaths.size(); i++) {
                String storagePath = mImagePaths.get(i).get("storagePath");
                Log.d("storagePath", "" + storagePath);
                if (storagePath != null) {
                    byte[] byteArray = new byte[0];
                    HashMap<String, byte[]> mHashBitMap = new HashMap<>();
                    Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(storagePath), 64, 64);
                    if (ThumbImage != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        ThumbImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byteArray = stream.toByteArray();
                        mHashBitMap.put("storagePath", byteArray);
                        mSELECTED_TREETYPE_IMAGES.add(mHashBitMap);
                    } else {
                        mHashBitMap.put("storagePath", byteArray);
                        mSELECTED_TREETYPE_IMAGES.add(mHashBitMap);
                    }
                }
            }
            return mSELECTED_TREETYPE_IMAGES;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, byte[]>> mImages) {
            super.onPostExecute(mImages);
            Log.d("sdfjaghdfg", "" + mImages.size());
            if (mImages.size() >= 1) {
                Adapter_Selected_tree_Type adapter_tree_species = new Adapter_Selected_tree_Type(mContext, mSELECTED_TREETYPE_NAMES, mImages);
                mTreeSpecies.setAdapter(adapter_tree_species);
                mTreeSpecies.setVisibility(View.VISIBLE);
                mNodata.setVisibility(View.GONE);
            }
            progressDialog.dismiss();
        }
    }
}
