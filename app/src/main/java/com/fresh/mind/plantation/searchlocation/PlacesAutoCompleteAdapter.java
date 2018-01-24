package com.fresh.mind.plantation.searchlocation;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.R.id.view;

/**
 * Created by AND I5 on 10-07-2017.
 */

public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> resultList;

    Context mContext;
    int mResource;

    PlaceAPI mPlaceAPI = new PlaceAPI();

    public PlacesAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);

        mContext = context;
        mResource = resource;
    }



    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        //if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (position != (resultList.size() - 1))
            view = inflater.inflate(R.layout.adapter_single_view, null);

        //}
        //else {
        //    view = convertView;
        //}


            CustomTextView autocompleteTextView = (CustomTextView) view.findViewById(R.id.mSPinnerText);
            autocompleteTextView.setText(resultList.get(position));



        return view;
    }
    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }



    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = mPlaceAPI.autocomplete(constraint.toString());

                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}
