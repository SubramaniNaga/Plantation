package com.fresh.mind.plantation.fragment.inside_tabs;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fresh.mind.plantation.Constant.AppData;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.SelectedTreeType;
import com.fresh.mind.plantation.receiver.NetworkChechReceiver;
import com.fresh.mind.plantation.service.GPSTracker;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.sorting.SortOrder;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.sqlite.sorting.SortOrderAZ;
import com.fresh.mind.plantation.tab_pager.HomeTabView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION;
import static com.fresh.mind.plantation.Constant.Config.celsiu;

import static com.fresh.mind.plantation.R.id.imageView;
import static com.fresh.mind.plantation.fragment.menu_page.Weathers.convertStreamToString;


/**
 * Created by AND I5 on 13-01-2017.
 */
public class TreeType extends Fragment implements LocationListener {
    private LocationManager lm;
    private View rootView;
    private CustomTextView mNodata;
    private ListView mTreeSpecies;

    private TreeTypeNameList treeType;
    private double lat, lng;
    private Location location;
    private SortOrderAZ sortOrder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        treeType.close();
    }

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
        // ((MainActivity) getActivity()).setTreeType(getActivity().getResources().getString(R.string.treeType), "1");

        rootView = inflater.inflate(R.layout.plant_tree_type, null);

        sortOrder = new SortOrderAZ(getActivity());
        mTreeSpecies = (ListView) rootView.findViewById(R.id.mTreeType);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);

        RecyclerView rvTreeType = (RecyclerView) rootView.findViewById(R.id.rvTreeType);
        rvTreeType.setHasFixedSize(true);

        GridLayoutManager gaggeredGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvTreeType.setLayoutManager(gaggeredGridLayoutManager);

        treeType = new TreeTypeNameList(getActivity());
        String bySortType = sortOrder.getSort();

        if (Config.mTREE_TYPES_IMAGES.size() >= 1) {

            mTreeSpecies.setVisibility(View.GONE);
            mNodata.setVisibility(View.GONE);
            rvTreeType.setAdapter(new AdapterTreeType(getActivity(), Config.mTREE_TYPES));
            rvTreeType.setVisibility(View.VISIBLE);
        } else {
            Config.mTREE_TYPES = treeType.getTreTypeeNames(AppData.checkLanguage(getActivity()));
            // Log.d("sdasdFrom", "" + Config.mTREE_TYPES.size());

            if (Config.mTREE_TYPES.size() >= 1) {
                if (bySortType != null) {
                    if (bySortType.equals("Z-A")) {
                        Collections.sort(Config.mTREE_TYPES, new Comparator<HashMap<String, String>>() {
                            @Override
                            public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                                return s2.get("treeType").compareTo(s1.get("treeType"));
                            }
                        });
                    } else {
                        Collections.sort(Config.mTREE_TYPES, new Comparator<HashMap<String, String>>() {
                            @Override
                            public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                                return s1.get("treeType").compareTo(s2.get("treeType"));
                            }
                        });
                    }
                }
                mTreeSpecies.setVisibility(View.GONE);
                mNodata.setVisibility(View.GONE);
                rvTreeType.setAdapter(new AdapterTreeType(getActivity(), Config.mTREE_TYPES));
                rvTreeType.setVisibility(View.VISIBLE);
            } else {
                mTreeSpecies.setVisibility(View.GONE);
                mNodata.setVisibility(View.VISIBLE);
                rvTreeType.setVisibility(View.GONE);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTreeSpecies.setSelectionFromTop(LISTVIEW_SMOOTH_VIEW_POSITION, 0);
            LISTVIEW_SMOOTH_VIEW_POSITION = 0;
        }
        return rootView;
    }


    @Override
    public void onLocationChanged(Location location) {
        if (Config.checkInternetConenction(getActivity())) {
            //  Log.d("dsjfhl", "" + location.getLatitude() + "  " + location.getLongitude());
            if (Config.celsiu == -1) {
                //Log.d("StopLocation", "Running");
                lat = location.getLatitude();
                lng = location.getLongitude();
                String address = getAddress(location.getLatitude(), location.getLongitude());
                // Log.d("addre123324ss ", "" + address);
                if (address != null) {

                    setAddress(address);
                } else {
                    //  Log.d("addre123324ss ", "" + address);
                }
            } else {
                // Log.d("StopLocation", "Stop" + lm);
                lm.removeUpdates(this);

            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public static String getWeatherJSON(String address) throws IOException {
//test  ea574594b9d36ab688642d5fbeab847e;
        URL url = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + address + "&APPID=ea574594b9d36ab688642d5fbeab847e");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //   Log.d("asasaqw", "" + url);
        String response = null;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (ProtocolException e) {
            e.printStackTrace();

        }
        // read the response
        // Log.d("asdsad", "" + response);
        return response;

    }

    private void setAddress(String address) {

        try {
            String response = getWeatherJSON(address);
            JSONObject jsonObject = new JSONObject(response);
            Log.d("jsonObject", "" + jsonObject);
            JSONObject coord = jsonObject.getJSONObject("coord");
            JSONArray weather = jsonObject.getJSONArray("weather");
            JSONObject jsonObject1 = weather.getJSONObject(0);
            Config.main = jsonObject1.getString("main");
            String base = jsonObject.getString("base");
            JSONObject weatherMain = jsonObject.getJSONObject("main");
            double temp = weatherMain.getDouble("temp");
            celsiu = (int) (temp - 273.15);
            MainActivity.mAddressTxt.setText("" + address);
            if (celsiu >= 0) {
                MainActivity.tempTxt.setText("" + celsiu + "℃");
                MainActivity.tempTxt.setVisibility(View.VISIBLE);
                if (Config.main.toLowerCase().contains("Sunny".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.sunny);
                } else if (Config.main.toLowerCase().contains("Clouds".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.partly_cloudy_29);
                } else if (Config.main.toLowerCase().equals("Thunderstorm".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.partly_sunny_weather);
                } else if (Config.main.toLowerCase().equals("Clear".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.clear);
                } else if (Config.main.toLowerCase().equals("Rain".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.rain);
                } else if (Config.main.toLowerCase().equals("Smoke".toLowerCase()) || Config.main.toLowerCase().equals("miste".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.cloudy_10);
                } else if (Config.main.toLowerCase().equals("Haze".toLowerCase()) || Config.main.toLowerCase().equals("Fog".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.haze);
                } else {
                    MainActivity.weatherImage.setImageResource(R.drawable.partly_cloudy_29);
                }
            } else {
                MainActivity.weatherImage.setImageResource(R.drawable.vector_drawable_questions);
                MainActivity.tempTxt.setVisibility(View.GONE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //if (Config.checkInternetConenction(getActivity())) {
        if (Config.celsiu == -1) {
            lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }
            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }
            if (!gps_enabled && !network_enabled) {
                Log.d("sadd", "eeeeee");
                // notify user
                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getActivity());
                dialog.setMessage(getActivity().getResources().getString(R.string.not_available_location));
                dialog.setCancelable(false);
                dialog.setPositiveButton(getActivity().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                        //get gps
                    }
                });

                dialog.show();
            } else {
                lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = lm.getBestProvider(criteria, true);
                //  Log.d("provider", "" + provider);
                if (provider != null) {
                    // Log.d("provideproviderr", "" + provider);
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        location = lm.getLastKnownLocation(provider);
                    if (location != null) {
                        //Log.d("asdsadsadsa", "Locations");
                        onLocationChanged(location);
                    }
                    GPSTracker gpsTracker = new GPSTracker(getActivity());
                    if (gpsTracker.canGetLocation()) {
                        lat = gpsTracker.getLatitude();
                        lng = gpsTracker.getLongitude();
                        Log.d("sddfsd", "" + lat + "  " + lng);
                        if (lat == 0.0 && lng == 0.0) {
                            lm.requestLocationUpdates(provider, 0, 0, this);
                        } else {
                            String address = getAddress(lat, lng);
                            // Log.d("addre123324ssUsignGpsTracker ", "" + address);
                            if (address != null) {
                                setAddress(address);
                            } else {
                                //  Log.d("addre123324ssUsignGpsTracker", "" + address);
                            }
                        }
                    }
                }
            }
        }
      /*  }
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getActivity());
        dialog.setMessage(getActivity().getResources().getString(R.string.not_available_location));
        dialog.setCancelable(false);
        dialog.setPositiveButton(getActivity().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
            }
        });
        dialog.setNegativeButton(getActivity().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();*/
    }

    public String getAddress(double latitude, double longitude) {
        String result = null;
        if (Config.checkInternetConenction(getActivity())) {
            Context context = getActivity();
            if (context == null) {
                return null;
            } else {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                Log.d("geocoder", "" + geocoder + "  " + latitude + "" + longitude);
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                /*for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append(",");

                }*/


                        String local = address.getSubLocality();
                        if (local != null) {
                            sb.append(local).append(" ");
                        }
                        String locality = address.getLocality();
                        if (locality != null) {
                            sb.append(locality).append(" ");
                        } else {
                            sb.append("Unknown Name").append(" ");
                        }

                        String adminArea = address.getAdminArea();
                        if (adminArea != null) {
                            sb.append(adminArea).append(" ");
                        }

                        String postal = address.getPostalCode();
                        if (postal != null) {
                            sb.append(postal).append(" ");
                        }

                        String countryName = address.getCountryName();

                        if (countryName != null) {
                            sb.append(countryName);
                        }

                        result = sb.toString();
                    }

                    //  Log.d("result", "" + result);
                } catch (IOException e) {
                    Log.e("UnableGeoCoder", "Unable connect to Geocoder", e);
                }
                // Log.d("resultAddress", "" + result);
                return result;
            }

        } else {
            Log.d("dsadsad", "NotConect");
        }
        return result;
    }


    class AdapterTreeType extends RecyclerView.Adapter<SolventViewHolders> {
        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> listViewValues;

        public AdapterTreeType(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation) {
            this.mContext = activity;
            this.listViewValues = mLocation;
        }

        @Override
        public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_gird, parent, false);
            return new SolventViewHolders(view);
        }


        @Override
        public void onBindViewHolder(final SolventViewHolders holder, final int position) {

           /* int positionView = getItemViewType(position);
            switch (positionView) {
                case 0:
                    holder.llContactLayout.setPaddingRelative(18, 14, 18, 14);
                    break;
                case 1:
                    holder.llContactLayout.setPaddingRelative(8, 4, 8, 4);
                    break;
            }*/
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(8, 8, 8, 8);
            holder.llContactLayout.setLayoutParams(lp);


            if (Config.checkMaterial() == 1) {
                holder.llContactLayout.setBackground(mContext.getDrawable(R.drawable.ripple_location));
            }
            holder.mSPinnerText.setText("" + listViewValues.get(position).get("treeType"));
            String mImg = listViewValues.get(position).get("storagePath");
            //Log.d("photo", "" + mImg);
            if (mImg != null) {
                try {
                    Picasso.with(mContext).load((Uri.fromFile(new File(mImg)))).error(R.drawable.logo_3).into(holder.imTreeImage);

                    /*Glide.with(mContext).load((Uri.fromFile(new File(mImg)))).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imTreeImage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            holder.imTreeImage.setImageDrawable(circularBitmapDrawable);
                        }
                    });*/
                } catch (IndexOutOfBoundsException outOfMemoryError) {
                    mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                    outOfMemoryError.printStackTrace();
                } catch (OutOfMemoryError outOfMemoryError) {
                    mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                    outOfMemoryError.printStackTrace();
                }
            }

            holder.llContactLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Config.SELECTED_TREE_TYPE = listViewValues.get(position).get("treeType");
                    LISTVIEW_SMOOTH_VIEW_POSITION = position;
                    mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SelectedTreeType()).addToBackStack(null).commit();

                }
            });
        }

        @Override
        public int getItemCount() {
            return listViewValues.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }

    }

    class SolventViewHolders extends RecyclerView.ViewHolder {
        public CustomTextView mSPinnerText;
        public CircleImageView imTreeImage;
        public LinearLayout llContactLayout;

        public SolventViewHolders(View convertView) {
            super(convertView);
            mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);

            imTreeImage = (CircleImageView) convertView.findViewById(R.id.imTreeImage);
            llContactLayout = (LinearLayout) convertView.findViewById(R.id.llContactLayout);
        }
    }
}

