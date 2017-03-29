package com.fresh.mind.plantation.fragment.inside_tabs;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fresh.mind.plantation.Constant.AppData;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Tree_Type;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.service.GPSTracker;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.fresh.mind.plantation.Constant.Config.celsiu;

import static com.fresh.mind.plantation.Constant.Config.mTREE_TYPES_IMAGES;
import static com.fresh.mind.plantation.fragment.Weathers.convertStreamToString;


/**
 * Created by AND I5 on 13-01-2017.
 */
public class TreeType extends Fragment implements LocationListener {
    private LocationManager lm;
    private View rootView;
    private CustomTextView mNodata;
    private ListView mTreeSpecies;
    public static Adapter_Tree_Type adapter_tree_type;
    private TreeTypeNameList treeType;
    private double lat, lng;
    private Location location;

    @Override
    public void onDestroy() {
        super.onDestroy();
        treeType.close();

        Log.d("CloseTreeType", "Objects");
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
        ((MainActivity) getActivity()).setTreeType(getActivity().getResources().getString(R.string.treeType), "1");

        rootView = inflater.inflate(R.layout.plant_tree_type, null);

        mTreeSpecies = (ListView) rootView.findViewById(R.id.mTreeType);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        treeType = new TreeTypeNameList(getActivity());

        Log.d("Config.mTREE_TYPES_IMAGES", "" + Config.mTREE_TYPES_IMAGES.size());
        if (Config.mTREE_TYPES_IMAGES.size() >= 1) {
            adapter_tree_type = new Adapter_Tree_Type(getActivity(), Config.mTREE_TYPES, mTREE_TYPES_IMAGES);
            mTreeSpecies.setAdapter(adapter_tree_type);
            mTreeSpecies.setVisibility(View.VISIBLE);
            //flabbyListView.setAdapter(adapter_tree_type);
            mNodata.setVisibility(View.GONE);
        } else {
            Config.mTREE_TYPES = treeType.getTreTypeeNames(AppData.checkLanguage(getActivity()));
            Log.d("sdasdFrom", "" + Config.mTREE_TYPES.size());

            if (Config.mTREE_TYPES.size() >= 1) {

                new LoaderAsync(getActivity(), Config.mTREE_TYPES).execute();
                mTreeSpecies.setVisibility(View.VISIBLE);
                //flabbyListView.setAdapter(adapter_tree_type);
                mNodata.setVisibility(View.GONE);

            } else {
                mTreeSpecies.setVisibility(View.GONE);
                mNodata.setVisibility(View.VISIBLE);
            }
        }

        return rootView;
    }


    @Override
    public void onLocationChanged(Location location) {

        Log.d("dsjfhl", "" + location.getLatitude() + "  " + location.getLongitude());
        if (Config.celsiu == -1) {
            Log.d("StopLocation", "Running");
            lat = location.getLatitude();
            lng = location.getLongitude();
            String address = getAddress(location.getLatitude(), location.getLongitude());
            Log.d("addre123324ss ", "" + address);
            if (address != null) {
                setAddress(address);
            } else {
                Log.d("addre123324ss ", "" + address);
            }
        } else {
            Log.d("StopLocation", "Stop" + lm);

            lm.removeUpdates(this);

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

    class LoaderAsync extends AsyncTask<ArrayList<HashMap<String, byte[]>>, Void, ArrayList<HashMap<String, byte[]>>> {
        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mLocation;
        private ProgressDialog progressDialog;

        public LoaderAsync(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation) {
            this.mContext = activity;
            this.mLocation = mLocation;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(null);
            progressDialog.setMessage(mContext.getString(R.string.fetchingData));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, byte[]>> doInBackground(ArrayList<HashMap<String, byte[]>>... params) {
            HashMap<String, byte[]> mHashBitMap;

            for (int i = 0; i < mLocation.size(); i++) {
                String storagePath = mLocation.get(i).get("storagePath");
                // Log.d("storagePath", mLocation.size() + " " + storagePath);
                if (storagePath != null || storagePath != "") {
                    mHashBitMap = new HashMap<>();
                    Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(storagePath), 64, 64);
                    // Log.d("ThumbImageFormstas", "" + ThumbImage);
                    byte[] byteArray = new byte[0];
                    if (ThumbImage != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        ThumbImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        mHashBitMap.put("storagePath", byteArray);
                        mTREE_TYPES_IMAGES.add(mHashBitMap);
                    } else {
                        mHashBitMap.put("storagePath", null);
                        mTREE_TYPES_IMAGES.add(mHashBitMap);
                    }
                } else {
                    mHashBitMap = new HashMap<>();
                    mHashBitMap.put("storagePath", null);
                    mTREE_TYPES_IMAGES.add(mHashBitMap);
                }
            }
            // Log.d("mTREE_TYPES_IMAGES", "" + mTREE_TYPES_IMAGES.size());

            return mTREE_TYPES_IMAGES;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, byte[]>> mImages) {
            super.onPostExecute(mImages);
            progressDialog.dismiss();

            if (mImages.size() >= 1) {
                mTREE_TYPES_IMAGES = mImages;
                progressDialog = null;
                //   Log.d("asasas5789", "" + mTREE_TYPES_IMAGES.size() + "  " + mImages.size());
                adapter_tree_type = new Adapter_Tree_Type(getActivity(), Config.mTREE_TYPES, mTREE_TYPES_IMAGES);
                mTreeSpecies.setAdapter(adapter_tree_type);
                mTreeSpecies.setVisibility(View.VISIBLE);

            }
        }
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
                Log.d("provider", "" + provider);
                if (provider != null) {
                    Log.d("provideproviderr", "" + provider);
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        location = lm.getLastKnownLocation(provider);
                    if (location != null) {
                        Log.d("asdsadsadsa", "Locations");
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
                            Log.d("addre123324ssUsignGpsTracker ", "" + address);
                            if (address != null) {
                                setAddress(address);
                            } else {
                                Log.d("addre123324ssUsignGpsTracker", "" + address);
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
        Context context = getActivity();
        if (context == null) {
            return null;
        } else {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            String result = null;
            try {
                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);
                    StringBuilder sb = new StringBuilder();
                /*for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append(",");

                }*/
                    String locality = address.getLocality();
                    Log.d("postalpostal", "" + locality);
                    if (locality != null) {
                        sb.append(locality).append(" ");
                    } else {
                        sb.append("Unknown Name").append(" ");
                    }
                    String postal = address.getPostalCode();
                    Log.d("", "" + postal);
                    if (postal != null) {
                        sb.append(postal).append(" ");
                    }
                    String countryName = address.getCountryName();
                    Log.d("postalcountryName", "" + countryName);
                    if (countryName != null) {
                        sb.append(countryName);
                    } else {
                    }
                    result = sb.toString();
                }

                Log.d("result", "" + result);
            } catch (IOException e) {
                Log.e("UnableGeoCoder", "Unable connect to Geocoder", e);
            }

            return result;
        }
    }

}

