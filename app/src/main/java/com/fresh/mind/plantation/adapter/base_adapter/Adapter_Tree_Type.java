package com.fresh.mind.plantation.adapter.base_adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.SelectedTreeType;
import com.fresh.mind.plantation.tab_pager.HomeTabView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.R.id.imageView;

/**
 * Created by AND I5 on 18-01-2017.
 */
public class Adapter_Tree_Type extends BaseAdapter implements Filterable {
    private final FragmentActivity mContext;
    private final ArrayList<HashMap<String, String>> mListItemValies;
    private ArrayList<HashMap<String, String>> mListItemValiesFilter;
    private final ArrayList<HashMap<String, byte[]>> mImageList;
    private ArrayList<HashMap<String, byte[]>> mImageList1;
    ItemFilter itemFilter = new ItemFilter();

    public Adapter_Tree_Type(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, ArrayList<HashMap<String, byte[]>> mImageList) {

        this.mContext = activity;
        this.mListItemValies = mLocation;
        this.mListItemValiesFilter = mLocation;
        this.mImageList = mImageList;
        this.mImageList1 = mImageList;
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

            //LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            //convertView = layoutInflater.inflate(R.layout.adapter_location_seleted_tree, parent, false);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_location_seleted_tree, parent, false);
            viewHolder.treeName = (CustomTextView) convertView.findViewById(R.id.textView5);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView4);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.treeName.setText("" + mListItemValiesFilter.get(position).get("treeType"));
        byte[] photo = mImageList.get(position).get("storagePath");
        //Log.d("photo", "" + photo);
        if (photo != null) {
            try {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                if (theImage != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    theImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    viewHolder.imageView.setImageBitmap(Bitmap.createScaledBitmap(theImage, 64, 64, false));
                    ///viewHolder.imageView.setImageBitmap(theImage);
                }
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
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SelectedTreeType()).commit();
            }
        });

        return convertView;
    }

    static class ViewHolder {

        CustomTextView treeName;
        private ImageView imageView;

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
