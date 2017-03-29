package com.fresh.mind.plantation.adapter.base_adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.tab_pager.HomeTabView;
import com.fresh.mind.plantation.tab_pager.ViewDetailsTabView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.bitmap;

/**
 * Created by AND I5 on 18-01-2017.
 */
public class Adapter_Tree_Species extends BaseAdapter implements Filterable {
    private final FragmentActivity mContext;
    private final ArrayList<HashMap<String, String>> mListItemValies;
    //private final ArrayList<HashMap<String, byte[]>> mImageList;
    private final ArrayList<HashMap<String, byte[]>> mImageList;
    private String views;

    private ArrayList<HashMap<String, String>> mListItemValiesFilter;
    //ArrayList<HashMap<String, String>> mImagePthas;
    private ItemFilter mFilter = new ItemFilter();


    public Adapter_Tree_Species(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, ArrayList<HashMap<String, byte[]>> treImages/*, ArrayList<HashMap<String, String>> mImagePthas*/, String views) {
        this.mContext = activity;

        this.mListItemValies = mLocation;
        this.mListItemValiesFilter = mLocation;
        this.mImageList = treImages;
        this.views = views;
        // this.mImagePthas = mImagePthas;
    }


    @Override
    public int getCount() {
        return mListItemValiesFilter.size();
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView treeName, mSubName, mPart2Title, mPart2SubName;
        final ImageView imageView;
        final ImageView mPart2Img;
        LinearLayout cardView1, cardView2;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adapter_tree_specis, null);
        final int viewType = getItemViewType(position);
        cardView1 = (LinearLayout) convertView.findViewById(R.id.cardView2);
        cardView2 = (LinearLayout) convertView.findViewById(R.id.cardView3);

        if (viewType == 0) {
            cardView1.setVisibility(View.VISIBLE);
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
        treeName.setText("" + mListItemValiesFilter.get(position).get("treeName"));
        mSubName.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
        //Log.d("storagePathFromTreeSpecAdapter", "" + mListItemValies.get(position).get("storagePath"));

        try {
            /*if (views.equals("Search")) {
                String mImagePath = mImageList.get(position).get("storagePath");
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mImagePath), 64, 64);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ThumbImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(ThumbImage, 64, 64, false));
                mPart2Img.setImageBitmap(Bitmap.createScaledBitmap(ThumbImage, 64, 64, false));

            } else {*/
            byte[] mImge = mImageList.get(position).get("ThumbImage");
            //Log.d("mImgeFrmTreeSpeAdater", "" + mImge + " " + mListItemValies.get(position).get("storagePath"));
            if (mImge == null) {
                imageView.setImageResource(R.drawable.logo_3);
                mPart2Img.setImageResource(R.drawable.logo_3);
            } else {
                Bitmap bmp = BitmapFactory.decodeByteArray(mImge, 0, mImge.length);
                try {
                    if (bmp != null) {
                        imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 64, 64, false));
                        mPart2Img.setImageBitmap(Bitmap.createScaledBitmap(bmp, 64, 64, false));
                    } else {
                        imageView.setImageResource(R.drawable.logo_3);
                        mPart2Img.setImageResource(R.drawable.logo_3);
                    }
                } catch (OutOfMemoryError ex) {

                    ViewDetailsTabView viewTreeDetails = new ViewDetailsTabView();
                    Bundle bundle = new Bundle();
                    if (views.equals("Search")) {
                        bundle.putString("titleName", "Tree Species1");
                    } else {
                        bundle.putString("titleName", "Tree Species");
                    }
                    Config.TREE_NAME = mListItemValiesFilter.get(position).get("treeName");
                    bundle.putString("treeName", "" + mListItemValiesFilter.get(position).get("treeName"));
                    viewTreeDetails.setArguments(bundle);
                    mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).commit();
                    ex.printStackTrace();

                }
                // }

                /*Glide.with(mContext).load(Uri.fromFile(new File(mListItemValies.get(position).get("storagePath"))))
                        .thumbnail(0.5f)
                        .into(imageView);
                Glide.with(mContext).load(Uri.fromFile(new File(mListItemValies.get(position).get("storagePath"))))
                        .thumbnail(0.5f)
                        .into(mPart2Img);*/

            }


        } catch (OutOfMemoryError ex) {

            ViewDetailsTabView viewTreeDetails = new ViewDetailsTabView();
            Bundle bundle = new Bundle();
            if (views.equals("Search")) {
                bundle.putString("titleName", "Tree Species1");
            } else {
                bundle.putString("titleName", "Tree Species");
            }
            Config.TREE_NAME = mListItemValiesFilter.get(position).get("treeName");
            bundle.putString("treeName", "" + mListItemValiesFilter.get(position).get("treeName"));
            viewTreeDetails.setArguments(bundle);
            mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).commit();
            //mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
            ex.printStackTrace();
        }
        mPart2Title.setText("" + mListItemValiesFilter.get(position).get("treeName"));
        mPart2SubName.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = MainActivity.mSearchViewEdit.getText().toString();
                if (search.length() == 0) {
                    Config.SEARCH_STR_VALUES = "";
                } else {
                    Config.SEARCH_STR_VALUES = search;
                }
                // ViewTreeDetails viewTreeDetails = new ViewTreeDetails();
                ViewDetailsTabView viewTreeDetails = new ViewDetailsTabView();
                ImageView img = null;
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (viewType == 0) {
                    img = imageView;
                } else if (viewType == 1) {
                    img = mPart2Img;
                }
                Bundle bundle = new Bundle();
                if (views.equals("Search")) {
                    bundle.putString("titleName", "Tree Species1");
                } else {
                    bundle.putString("titleName", "Tree Species");
                }
                Config.TREE_NAME = mListItemValiesFilter.get(position).get("treeName");
                bundle.putString("treeName", "" + mListItemValiesFilter.get(position).get("treeName"));
                viewTreeDetails.setArguments(bundle);
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).commit();

                //}

            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = null;
            //Log.d("constraint", "" + constraint.length());
            /*if (constraint.length() >= 1) {*/
            String filterString = constraint.toString().toLowerCase();
            //Config.SEARCH_STR_VALUES = filterString;
            results = new FilterResults();
            final ArrayList<HashMap<String, String>> list = mListItemValies;
            int count = list.size();
            final ArrayList<HashMap<String, String>> nlist = new ArrayList<HashMap<String, String>>(count);
            String filterableString, filterableString1;
            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).get("treeName");
                filterableString1 = list.get(i).get("subTreeName");
                //  Log.d("filterableString1", filterString + "  " + filterableString1 + "  " + filterableString);
                if (filterableString.toLowerCase().startsWith(filterString)) {
                    HashMap<String, String> mHas = new HashMap<>();
                    mHas.put("treeName", filterableString);
                    mHas.put("subTreeName", "" + list.get(i).get("subTreeName"));
                    mHas.put("treeIcon", "" + list.get(i).get("treeIcon"));
                    nlist.add(mHas);
                } else if (filterableString1.toLowerCase().startsWith(filterString)) {
                    HashMap<String, String> mHas = new HashMap<>();
                    mHas.put("treeName", filterableString);
                    mHas.put("subTreeName", "" + list.get(i).get("subTreeName"));
                    mHas.put("treeIcon", "" + list.get(i).get("treeIcon"));
                    nlist.add(mHas);
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            //Log.d("results", "" + results.count);
            return results;
         /*   } else {
                String filterString = constraint.toString().toLowerCase();
                results = new FilterResults();
                final ArrayList<HashMap<String, String>> list = mListItemValies;
                int count = list.size();
                final ArrayList<HashMap<String, String>> nlist = new ArrayList<HashMap<String, String>>(count);
                String filterableString, filterableString1;
                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).get("treeName");

                    HashMap<String, String> mHas = new HashMap<>();
                    mHas.put("treeName", filterableString);
                    mHas.put("subTreeName", "" + list.get(i).get("subTreeName"));
                    mHas.put("treeIcon", "" + list.get(i).get("treeIcon"));
                    nlist.add(mHas);
                }
                results.values = nlist;
                results.count = nlist.size();
                Log.d("results else", "" + results.count);
                return results;
            }
*/

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null) {
                mListItemValiesFilter = (ArrayList<HashMap<String, String>>) results.values;
            }
            notifyDataSetChanged();
        }
    }
}
