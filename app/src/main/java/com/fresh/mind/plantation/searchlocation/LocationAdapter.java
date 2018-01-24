package com.fresh.mind.plantation.searchlocation;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.fragment.menu_page.FeedBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by AND I5 on 21-03-2016.
 */
public class LocationAdapter extends ArrayAdapter<HashMap<String, String>> {
    private ArrayList<HashMap<String, String>> mLocationList;
    private Context mContext;
    private int resource;
    public TextView mPlaceNameTxt, mSubPlaceNameTxt;
    private double mLat, mLng;

    public LocationAdapter(Context context, int resource, ArrayList<HashMap<String, String>> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.mLocationList = objects;
        Log.d("mLocationListSize ", "" + mLocationList.size());
    }


    public View getView(int position, View v, ViewGroup parent) {

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(resource, null);
            mPlaceNameTxt = (TextView) v.findViewById(R.id.mLocatioTitle);
            mSubPlaceNameTxt = (TextView) v.findViewById(R.id.mLocationSubTitle);
            if (mLocationList.size() > 0) {
                String mPlaceName = mLocationList.get(position).get("mPlaceName");
                String[] mLocatioTitle = mPlaceName.split(",");
                String mHead = mLocatioTitle[0];
                mPlaceNameTxt.setText(" " + mHead);
                mSubPlaceNameTxt.setText("" + mPlaceName);
                Log.d("mSubPlaceNameTxt", "" + mPlaceName);

            /*LatLng latLng = PlaceJSONParsersA.getLocationFromAddress(mContext, mPlaceName);
            mLng = latLng.longitude;
            mLat = latLng.latitude;
            Log.d("LatLng", "" + latLng);
            String mAddress = getAddressFromLatLng(mContext, mLat, mLng);
            Log.d("LatLngAdd ", "" + mAddress);*/
            } else {
                Log.d("No", "No");
            }
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placeName = mSubPlaceNameTxt.getText().toString();
                FeedBack.acLocation.setText("" + placeName);
                if (placeName != null && !placeName.isEmpty()) {
                    FeedBack.acLocation.setSelection(placeName.length());
                }
            }
        });

        return v;
    }

    public String getAddressFromLatLng(Context mContext, double mLat, double mLng) {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    mLat, mLng, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                String mArea = address.getAdminArea();
                Log.d("Area name ", " " + mArea);
                String mLocatlity = address.getLocality();
                String mCountryName = address.getCountryName();
                result = sb.toString();
                Log.d("Location ", " " + result);
                mPlaceNameTxt.setText(" " + mLocatlity);
                mSubPlaceNameTxt.setText(mLocatlity + "," + mCountryName);

            }
        } catch (IOException e) {

        }
        return result;
    }

}
