package com.fresh.mind.plantation.adapter.recycler_adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.holder.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 17-04-2017.
 */

public class AdapterSchemes extends RecyclerView.Adapter<ViewHolder> {
    private final ArrayList<HashMap<String, String>> mValues;
    private final FragmentActivity mContext;

    public AdapterSchemes(FragmentActivity activity, ArrayList<HashMap<String, String>> hashMaps) {

        this.mContext = activity;
        this.mValues = hashMaps;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spinner, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.text.setText("" + mValues.get(position).get("sch1"));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
