package com.fresh.mind.plantation.adapter.base_adapter;

import android.net.Uri;
import android.os.Handler;
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
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.SelectedTreeType;
import com.fresh.mind.plantation.tab_pager.HomeTabView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION;
import static com.fresh.mind.plantation.R.id.cardView;
import static com.fresh.mind.plantation.R.id.holo_dark;
import static com.fresh.mind.plantation.R.id.view;

/**
 * Created by AND I5 on 18-01-2017.
 */
public class Adapter_Tree_Type extends BaseAdapter implements Filterable {
    private final FragmentActivity mContext;
    private final ArrayList<HashMap<String, String>> mListItemValies;
    private ArrayList<HashMap<String, String>> mListItemValiesFilter;
    // private final ArrayList<HashMap<String, byte[]>> mImageList;
    private ArrayList<HashMap<String, byte[]>> mImageList1;
    ItemFilter itemFilter = new ItemFilter();

    public Adapter_Tree_Type(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation) {

        this.mContext = activity;
        this.mListItemValies = mLocation;
        this.mListItemValiesFilter = mLocation;
        // this.mImageList = mImageList;
        //this.mImageList1 = mImageList;
        // Log.d("mListItemValiesFilter", "" + mListItemValiesFilter.size() + " " + mImageList.size() + " " + mImageList1.size() + "  " + mListItemValies.size());
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(convertView);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_location_seleted_tree, parent, false);
            viewHolder.treeName = (CustomTextView) convertView.findViewById(R.id.textView5);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView4);
            viewHolder.cardView = (LinearLayout) convertView.findViewById(R.id.cardView);
            viewHolder.cnsLayoutLocation = (ConstraintLayout) convertView.findViewById(R.id.cnsLayoutLocation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (Config.checkMaterial() == 1) {
            viewHolder.cardView.setBackground(mContext.getDrawable(R.drawable.ripple_location));
        }
        viewHolder.treeName.setText("" + mListItemValiesFilter.get(position).get("treeType"));
        String mImg = mListItemValiesFilter.get(position).get("storagePath");
      //  Log.d("photo", "" + mImg);
        if (mImg != null) {
            try {
                Picasso.with(mContext).load((Uri.fromFile(new File(mImg)))).error(R.drawable.no_thumbnail).into(viewHolder.imageView);
            } catch (IndexOutOfBoundsException outOfMemoryError) {
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                outOfMemoryError.printStackTrace();
            } catch (OutOfMemoryError outOfMemoryError) {
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                outOfMemoryError.printStackTrace();
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Config.SELECTED_TREE_TYPE = mListItemValiesFilter.get(position).get("treeType");
                        LISTVIEW_SMOOTH_VIEW_POSITION = position;
                        mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SelectedTreeType()).addToBackStack(null).commit();

            }
        });

        return convertView;
    }

    static class ViewHolder {

        CustomTextView treeName;
        private ImageView imageView;
        private ConstraintLayout cnsLayoutLocation;
        private LinearLayout cardView;

        public ViewHolder(View view) {
            //ButterKnife.bind(this, view);
        }
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = null;
            // Log.d("constraint", "" + constraint.length());
            if (constraint.length() >= 1) {
                String filterString = constraint.toString().toLowerCase();
                results = new FilterResults();
                final ArrayList<HashMap<String, String>> list = mListItemValies;
                int count = list.size();
                final ArrayList<HashMap<String, String>> nlist = new ArrayList<HashMap<String, String>>(count);
                String filterableString, filterableString1;
                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).get("treeType");
                    filterableString1 = list.get(i).get("subTreeName");
                    // Log.d("filterableString1", filterString + "  " + filterableString1 + "  " + filterableString);
                    if (filterableString.toLowerCase().startsWith(filterString)) {
                        HashMap<String, String> mHas = new HashMap<>();
                        mHas.put("treeName", filterableString);
                        mHas.put("subTreeName", "" + list.get(i).get("subTreeName"));
                        mHas.put("treeIcon", "" + list.get(i).get("treeIcon"));
                        nlist.add(mHas);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
                //  Log.d("results", "" + results.count);
                return results;
            } else {
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
                //  Log.d("results else", "" + results.count);
                return results;
            }


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
