package com.fresh.mind.plantation.adapter.base_adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Holder;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.menu_page.SplashScreen;
import com.fresh.mind.plantation.tab_pager.ViewDetailsTabView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION;
import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION_2;
import static com.fresh.mind.plantation.R.id.bold;
import static com.fresh.mind.plantation.R.id.cardView2;
import static com.fresh.mind.plantation.R.id.imageView;
import static com.fresh.mind.plantation.R.id.mRefresh;
import static com.fresh.mind.plantation.R.id.mSubName;
import static com.fresh.mind.plantation.activity.SplashActivity.mEnglish;
import static com.fresh.mind.plantation.activity.SplashActivity.mTamil;

/**
 * Created by AND I5 on 18-01-2017.
 */
public class Adapter_Tree_Species extends BaseAdapter implements Filterable {
    private FragmentActivity mContext;
    private ArrayList<HashMap<String, String>> mListItemValies;
    private String bySortType;
    private String views;
    private ArrayList<HashMap<String, String>> mListItemValiesFilter;

    private ItemFilter mFilter = new ItemFilter();


    public Adapter_Tree_Species(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, String bySortType/*, ArrayList<HashMap<String, byte[]>> treImages*//**//*, ArrayList<HashMap<String, String>> mImagePthas*/, String views) {
        this.mContext = activity;
        this.mListItemValies = mLocation;
        this.mListItemValiesFilter = mLocation;
        this.views = views;
        this.bySortType = bySortType;

    }

    public Adapter_Tree_Species(FragmentActivity activity) {
        this.mContext = activity;
        mListItemValies = new ArrayList<>();
        mListItemValiesFilter = new ArrayList<>();
    }

    public void addMore(ArrayList<HashMap<String, String>> mLocation, String bySortType, String species) {
        this.mListItemValies = mLocation;
        this.mListItemValiesFilter = mLocation;
        this.views = species;
        this.bySortType = bySortType;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder;
        if (convertView == null) {

            holder = new Holder();
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_tree_specis, null);
            holder.cardView1 = (LinearLayout) convertView.findViewById(cardView2);
            holder.cardView2 = (LinearLayout) convertView.findViewById(R.id.cardView3);
            holder.treeName = (CustomTextView) convertView.findViewById(R.id.mTreeName);
            holder.mSubName = (CustomTextView) convertView.findViewById(mSubName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.mPart2Title = (CustomTextView) convertView.findViewById(R.id.textView2);
            holder.mPart2SubName = (CustomTextView) convertView.findViewById(R.id.textView4);
            holder.mPart2Img = (ImageView) convertView.findViewById(R.id.imageView3);
            holder.cnsLayout2 = (ConstraintLayout) convertView.findViewById(R.id.cnsLayout2);
            holder.cnsLayout1 = (ConstraintLayout) convertView.findViewById(R.id.cnsLayout1);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (Config.checkMaterial() == 1) {
            holder.cnsLayout1.setBackground(mContext.getDrawable(R.drawable.ripple_empty));
            holder.cnsLayout2.setBackground(mContext.getDrawable(R.drawable.ripple_empty));
        }
        Typeface externalFont = Typeface.createFromAsset(mContext.getAssets(), "proximanovabold.otf");
        Typeface externalFontRegular = Typeface.createFromAsset(mContext.getAssets(), "proximanovaregular.otf");

        if (bySortType != null) {
            //z-a is normal name
            if (bySortType.equals("Z-A")) {

                /*holder.treeName.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
                holder.mSubName.setText("" + mListItemValiesFilter.get(position).get("treeName"));
                // holder.mSubName.setText("" + position);
                holder.mPart2Title.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
                holder.mPart2SubName.setText("" + mListItemValiesFilter.get(position).get("treeName"));*/
                setText(position, holder);
                holder.mSubName.setTypeface(externalFont);
                holder.mPart2SubName.setTypeface(externalFont);

                holder.treeName.setTypeface(externalFontRegular);
                holder.mPart2Title.setTypeface(externalFontRegular);
            } else if (bySortType.equals("A-Z")) {
                setText(position, holder);
                holder.treeName.setTypeface(externalFont);
                holder.mPart2Title.setTypeface(externalFont);

                holder.mSubName.setTypeface(externalFontRegular);
                holder.mPart2SubName.setTypeface(externalFontRegular);
            } else {
                setText(position, holder);
            }
        } else {
            setText(position, holder);
        }
        String storagePath = mListItemValies.get(position).get("storagePath");
        //  Log.d("storagePathAdapterssssss", "" + storagePath);
        try {
            if (storagePath == null || storagePath.isEmpty()) {
                holder.imageView.setImageResource(R.drawable.no_thumbnail);
                holder.mPart2Img.setImageResource(R.drawable.no_thumbnail);
            } else {
                try {
                    Picasso.with(mContext).load((Uri.fromFile(new File(storagePath)))).error(R.drawable.no_thumbnail).into(holder.mPart2Img);
                    Picasso.with(mContext).load((Uri.fromFile(new File(storagePath)))).error(R.drawable.no_thumbnail).into(holder.imageView);
                } catch (OutOfMemoryError ex) {
                    handleException(ex, position);
                }
            }
            if (getItemViewType(position) == 1) {
                holder.cardView1.setVisibility(View.GONE);
                holder.cardView2.setVisibility(View.VISIBLE);
            } else {
                holder.cardView1.setVisibility(View.VISIBLE);
                holder.cardView2.setVisibility(View.GONE);
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
            Config.COMMON_KEY = mListItemValiesFilter.get(position).get("common_key");
            bundle.putString("treeName", "" + mListItemValiesFilter.get(position).get("treeName"));
            viewTreeDetails.setArguments(bundle);
            mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).commit();
            //mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
            ex.printStackTrace();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String search = MainActivity.mSearchViewEdit.getText().toString();
                Log.d("titleNameAdate", "titleName");

                if (search.length() == 0) {
                    Config.SEARCH_STR_VALUES = "";
                } else {
                    Config.SEARCH_STR_VALUES = search;
                }
                // ViewTreeDetails viewTreeDetails = new ViewTreeDetails();
                ViewDetailsTabView viewTreeDetails = new ViewDetailsTabView();

                Bundle bundle = new Bundle();
                if (views.equals("Search")) {

                    LISTVIEW_SMOOTH_VIEW_POSITION_2 = position;
                    bundle.putString("titleName", "Tree Species1");

                } else {
                    LISTVIEW_SMOOTH_VIEW_POSITION = position;
                    bundle.putString("titleName", "Tree Species");

                }
                Config.TREE_NAME = mListItemValiesFilter.get(position).get("treeName");
                Config.COMMON_KEY = mListItemValiesFilter.get(position).get("common_key");
                bundle.putString("treeName", "" + mListItemValiesFilter.get(position).get("treeName"));

                viewTreeDetails.setArguments(bundle);
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, viewTreeDetails).addToBackStack(null).commit();
                //}
                  /*  }
                }, 300);*/
            }
        });
        return convertView;
    }

    private void setText(int position, Holder holder) {
        holder.treeName.setText("" + mListItemValiesFilter.get(position).get("treeName"));
        holder.mSubName.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
        holder.mPart2Title.setText("" + mListItemValiesFilter.get(position).get("treeName"));
        holder.mPart2SubName.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
    }

    private void handleException(OutOfMemoryError ex, int position) {

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
/*
    class Holder {
        TextView treeName, mSubName, mPart2Title, mPart2SubName;
        ImageView imageView;
        ImageView mPart2Img;
        LinearLayout cardView1, cardView2;

    }*/

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
