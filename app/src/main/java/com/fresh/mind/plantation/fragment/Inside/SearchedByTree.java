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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Tree_Species;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.not_need.VerifiedDatabase;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.TreeList;
import com.fresh.mind.plantation.sqlite.server.TreeTypeInfo;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.fresh.mind.plantation.Constant.Config.mSELECTED_TREE_NAME;


/**
 * Created by AND I5 on 17-01-2017.
 */
public class SearchedByTree extends Fragment {
    private View rootView;
    private CustomTextView mSelectedDisticName, mNodata;
    private Spinner mLocationBasedSeletedTree;
    private ListView listView;
    private VerifiedDatabase verifiedDatabase;
    ArrayList<HashMap<String, String>> mTreeTypeValues;
    private VerifyDetails verifyDetails;
    private TreeTypeNameList treeType;
    private TreeList treeList;
    private ArrayList<HashMap<String, byte[]>> mImageList;
    private ImageDb imageDb;
    private TreeTypeInfo treeTypeInfo;

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
        ((MainActivity) getActivity()).viewTreeDetails(getResources().getString(R.string.searchResult), getResources().getString(R.string.searchResult));
        rootView = inflater.inflate(R.layout.plant_selecte_location, null);

        mSelectedDisticName = (CustomTextView) rootView.findViewById(R.id.mSelectedDisticName);
        mSelectedDisticName.setVisibility(View.VISIBLE);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        mLocationBasedSeletedTree = (Spinner) rootView.findViewById(R.id.LocationBasedTreeTypeSpr);
        listView = (ListView) rootView.findViewById(R.id.mLocationBasedSeletedTree);
        verifiedDatabase = new VerifiedDatabase(getActivity());
        verifyDetails = new VerifyDetails(getActivity());
        treeType = new TreeTypeNameList(getActivity());
        treeList = new TreeList(getActivity());
        imageDb = new ImageDb(getActivity());
        treeTypeInfo = new TreeTypeInfo(getActivity());

        //  mTreeTypeValues = treeType.getTreTypeeNames(AppData.checkLanguage(getActivity()));
        mTreeTypeValues = treeTypeInfo.getTreeType(Config.DISTRICT_NAME, Config.SOIL_TYPE, AppData.checkLanguage(getActivity()));
        Log.d("sadddas52198418", "   " + mTreeTypeValues.size());
        mLocationBasedSeletedTree.setAdapter(new TreeTypeAdapter(getActivity(), mTreeTypeValues));
        mLocationBasedSeletedTree.setSelected(false);
        mLocationBasedSeletedTree.setSelection(Config.SELECTE_TREE_TYPE);
        mSelectedDisticName.setText(Config.DISTRICT_NAME + "(Dt), " + Config.SOIL_TYPE);

        mLocationBasedSeletedTree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Config.SELECTE_TREE_TYPE = mLocationBasedSeletedTree.getSelectedItemPosition();
                String treeType = mTreeTypeValues.get(position).get("Treetype");
                //  mDistrinListName = verifiedDatabase.getQureyBaseTreeName(treeType, Config.DISTRICT_NAME, Config.RAIN_FALL, Config.TERRAIN_TYPE, Config.SOIL_TYPE);
                /*if (mSELECTED_TREE_NAME.size() >= 1) {
                    listView.setAdapter(new Adapter_Tree_Species(getActivity(), Config.mSELECTED_TREE_NAME, mImageList, mTreeImagePath, "Search"));
                    mNodata.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    mSelectedDisticName.setVisibility(View.VISIBLE);
                } else {*/
                Config.mSELECTED_TREE_NAME = verifyDetails.getQureyBaseTreeName(treeType, Config.DISTRICT_NAME, Config.RAIN_FALL, Config.TERRAIN_TYPE, Config.SOIL_TYPE, AppData.checkLanguage(getActivity()));
                // mImageList = treeList.getTreeByImages(mTreeTypeValues.get(Config.SELECTE_TREE_TYPE).get("Treetype"), Config.DISTRICT_NAME, AppData.checkLanguage(getActivity()));

                if (mSELECTED_TREE_NAME.size() >= 1) {
                    // Config.mTreeImagePath = verifyDetails.getTreeByImagePath(treeType, Config.DISTRICT_NAME, Config.RAIN_FALL, Config.TERRAIN_TYPE, Config.SOIL_TYPE, AppData.checkLanguage(getActivity()));
                    Log.d("mDistrinListNameSearchedByTree", "" + Config.mSELECTED_TREE_NAME.size() + "  " + Config.mTreeImagePath.size());
                    mSelectedDisticName.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    mNodata.setVisibility(View.GONE);
                    new LoaderAsync(getActivity(), Config.mSELECTED_TREE_NAME, treeList.getTreImages(),/*, Config.mTreeImagePath,*/ "Search").execute();
                } else {
                    listView.setVisibility(View.GONE);
                    mNodata.setVisibility(View.VISIBLE);
                }
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }


    class LoaderAsync extends AsyncTask<ArrayList<HashMap<String, byte[]>>, Void, ArrayList<HashMap<String, byte[]>>> {
        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mLocationFromHere;
        private final ArrayList<HashMap<String, byte[]>> treImagesFromHere;

        // private final ArrayList<HashMap<String, String>> mImagePathsFromHere;
        private final String species;
        private ProgressDialog progressDialog;

        public LoaderAsync(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, ArrayList<HashMap<String, byte[]>> treImages,/* ArrayList<HashMap<String, String>> mImagePaths,*/ String species) {
            this.mContext = activity;
            this.mLocationFromHere = mLocation;
            this.treImagesFromHere = treImages;
            // this.mImagePathsFromHere = mImagePaths;
            this.species = species;
            //Log.d("mImagePathstrey456 ", "" + mImagePaths.size());
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(getActivity().getString(R.string.fetchingData));
            progressDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, byte[]>> doInBackground(ArrayList<HashMap<String, byte[]>>... params) {
            HashMap<String, byte[]> mHashBitMap;
            ArrayList<HashMap<String, byte[]>> treImagesFromHereAdded = new ArrayList<>();
            for (int i = 0; i < mLocationFromHere.size(); i++) {
                String storagePath = mLocationFromHere.get(i).get("storagePath");
                if (storagePath != null || storagePath != "") {

                    mHashBitMap = new HashMap<>();
                    Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(storagePath), 64, 64);
                    if (ThumbImage != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        ThumbImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        mHashBitMap.put("ThumbImage", byteArray);
                        treImagesFromHereAdded.add(mHashBitMap);
                    } else {

                        mHashBitMap.put("ThumbImage", null);
                        treImagesFromHereAdded.add(mHashBitMap);
                    }
                } else {
                    mHashBitMap = new HashMap<>();
                    mHashBitMap.put("ThumbImage", null);
                    treImagesFromHereAdded.add(mHashBitMap);
                }
            }
            Log.d("smpleee", "size " + treImagesFromHereAdded.size());
            return treImagesFromHereAdded;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, byte[]>> treImagesFromHere) {
            super.onPostExecute(treImagesFromHere);
            Log.d("sdfjaghdfg", "" + treImagesFromHere.size());

            if (treImagesFromHere.size() >= 1) {
                Adapter_Tree_Species adapter_tree_species = new Adapter_Tree_Species(getActivity(), mLocationFromHere, treImagesFromHere, "Search");
                listView.setAdapter(adapter_tree_species);
            }
            progressDialog.dismiss();
        }
    }

    private class TreeTypeAdapter extends BaseAdapter {
        private final ArrayList<HashMap<String, String>> mListOfTreeType;
        private final FragmentActivity mContext;

        public TreeTypeAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> mTreeTypeValues) {

            this.mContext = activity;
            this.mListOfTreeType = mTreeTypeValues;

        }

        @Override
        public int getCount() {
            return mListOfTreeType.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_spinner, null);
            CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            mSPinnerText.setText("" + mListOfTreeType.get(position).get("Treetype"));
            return convertView;
        }
    }
}
