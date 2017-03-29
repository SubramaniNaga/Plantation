package com.fresh.mind.plantation.adapter.recycler_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.holder.KittenClickListener;
import com.fresh.mind.plantation.holder.ViewHolder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 02-02-2017.
 */

public class Adapter_TtreeSpecies extends RecyclerView.Adapter<ViewHolder> implements Filterable {
    private final KittenClickListener mContext;
    private final ArrayList<HashMap<String, byte[]>> mimages;
    private final ArrayList<HashMap<String, byte[]>> mimagesFilter;
    private ArrayList<HashMap<String, String>> mListItemValiesFilter;
    private final ArrayList<HashMap<String, String>> mListItemValies;
    ItemFilter mFilter = new ItemFilter();
    FragmentActivity con;
    int lastPosition = -1;

    public Adapter_TtreeSpecies(KittenClickListener activity, ArrayList<HashMap<String, String>> mLocation, ArrayList<HashMap<String, byte[]>> mimages, FragmentActivity ac) {
        this.mListItemValiesFilter = mLocation;
        this.mimagesFilter = mimages;
        this.mContext = activity;
        this.mListItemValies = mLocation;
        this.mimages = mimages;
        con = ac;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_tree_specis, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int viewType = getItemViewType(position);

        Log.d("viewType", "" + viewType);
        if (viewType == 0) {
            holder.cardView1.setVisibility(View.VISIBLE);
            holder.cardView2.setVisibility(View.GONE);
        } else if (viewType == 1) {
            holder.cardView1.setVisibility(View.GONE);
            holder.cardView2.setVisibility(View.VISIBLE);
        }
        holder.treeName.setText("" + mListItemValiesFilter.get(position).get("treeName"));
        holder.mSubName.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
        try {
            byte[] photo = mimagesFilter.get(position).get("treeIcon");
           /* if (imgpath != null) {
                int img = Integer.parseInt(imgpath);
                holder.imageView.setImageResource(img);
                holder.mPart2Img.setImageResource(img);
            }*/

            // Log.d("photo", "" + photo);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            holder.imageView.setImageBitmap(theImage);
            holder.mPart2Img.setImageBitmap(theImage);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        holder.mPart2Title.setText("" + mListItemValiesFilter.get(position).get("treeName"));
        holder.mPart2SubName.setText("" + mListItemValiesFilter.get(position).get("subTreeName"));
        if (viewType == 0) {
            ViewCompat.setTransitionName(holder.imageView, String.valueOf(position) + "anim");
            holder.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.onKittenClicked(holder, position, viewType);
                }
            });
        } else if (viewType == 1) {
            ViewCompat.setTransitionName(holder.mPart2Img, String.valueOf(position) + "anim");
            holder.cardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.onKittenClicked(holder, position, viewType);
                }
            });
        }
      /*  if (position > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(con, R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }*/


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {

        return position % 2;
    }


    @Override
    public int getItemCount() {
        return mListItemValiesFilter.size();
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
