package com.fresh.mind.plantation.fragment.inside_tabs;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Tree_Species;
import com.fresh.mind.plantation.adapter.recycler_adapter.Adapter_TtreeSpecies;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.holder.DetailsTransition;
import com.fresh.mind.plantation.holder.KittenClickListener;
import com.fresh.mind.plantation.holder.ViewHolder;
import com.fresh.mind.plantation.tab_pager.HomeTabView;
import com.fresh.mind.plantation.tab_pager.ViewDetailsTabView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.not_need.TreeNames;
import com.fresh.mind.plantation.sqlite.server.TreeList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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

import static com.fresh.mind.plantation.Constant.Config.mImagePaths;
import static com.fresh.mind.plantation.fragment.Weathers.convertStreamToString;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class TreeSpecies extends Fragment implements KittenClickListener, LocationListener {
    private View rootView;
    private CustomTextView mNodata;
    public static ListView mTreeSpecies;
    private String sdCardLocation = "sdcard/Treepedia/";
    private TreeNames treeNames;
    private Location location;
    private LocationManager lm;
    private File[] mp3List;
    public static Adapter_TtreeSpecies adapter_ttreeSpecies;
    //private VerifiedDatabase verifiedDatabase;
    //private TreeTypeDatabase treeTypeDatabase;
    //private DistrictList districtList;
    private RecyclerView mTreeSpecies_RecyclerView;
    private TreeList treeList;
    private double lat, lng;

    @Override
    public void onDestroy() {
        super.onDestroy();
        treeList.close();
        treeNames.close();

        Log.d("CloseTreeSpecies", "Objects");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Firstssss", "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Firstssss", "onActivityCreated");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Firstssss", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Log.d("Firstssss", "onCreateView");
        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).species(getActivity().getResources().getString(R.string.treeSpecies));
        String lang = AppData.checkLanguage(getActivity());
        rootView = inflater.inflate(R.layout.plant_tree_species, null);
        mTreeSpecies = (ListView) rootView.findViewById(R.id.mTreeSpecies);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        mTreeSpecies_RecyclerView = (RecyclerView) rootView.findViewById(R.id.mTreeSpecies_RecyclerView);
        treeNames = new TreeNames(getActivity());
        //  verifiedDatabase = new VerifiedDatabase(getActivity());
        // districtList = new DistrictList(getActivity());
        // treeTypeDatabase = new TreeTypeDatabase(getActivity());
        treeList = new TreeList(getActivity());
        mTreeSpecies.setVisibility(View.GONE);

        //Log.d("mImagessadfdfd", "" + Config.mImages.size() + "  " + mImagePaths.size());
        if (Config.mImages.size() >= 1) {
            //Log.d("viewsaaasd", "alreadyGet");
            mTreeSpecies.setVisibility(View.VISIBLE);
            mNodata.setVisibility(View.GONE);
            Adapter_Tree_Species adapter_tree_species = new Adapter_Tree_Species(getActivity(), Config.mLocation, Config.mImages, "Species");
            mTreeSpecies.setAdapter(adapter_tree_species);
        } else {
            //  Log.d("viewsaaasd", "two");
            Config.mLocation = treeList.getTreTypeeNames(languages);
            //Config.mImagePaths = treeList.getImagePaths(languages);
            //  Log.d("viewsaaasd", "two  " + Config.mLocation.size() + "  " + Config.mImagePaths.size());
            if (Config.mLocation.size() >= 1) {

                Collections.sort(Config.mLocation, new Comparator<HashMap<String, String>>() {
                    @Override
                    public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                        return s1.get("treeName").compareTo(s2.get("treeName"));
                    }
                });
                new LoaderAsync(getActivity(), Config.mLocation, treeList.getTreImages(), "Species").execute();
                mTreeSpecies_RecyclerView.setVisibility(View.GONE);
                mTreeSpecies.setVisibility(View.VISIBLE);
                mNodata.setVisibility(View.GONE);
                if (Config.SEARCH_STR_VALUES != "") {
                    Log.d("viewsaaasd", "alreadyGet4444");
                    adapter_ttreeSpecies.getFilter().filter(Config.SEARCH_STR_VALUES);
                    MainActivity.mSearchViewEdit.requestFocus();
                    MainActivity.mSearchViewEdit.setFocusableInTouchMode(true);
                    MainActivity.mSearchViewEdit.setSelection(Config.SEARCH_STR_VALUES.length());
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(MainActivity.mSearchViewEdit, InputMethodManager.SHOW_FORCED);
                }
            } else {
                Log.d("viewsaaasd", "alreadyGet555");
                //  mTreeSpecies.setVisibility(View.GONE);
                mTreeSpecies_RecyclerView.setVisibility(View.GONE);
                mTreeSpecies.setVisibility(View.GONE);
                mNodata.setVisibility(View.VISIBLE);
            }


        }

        return rootView;
    }


    @Override
    public void onLocationChanged(Location location) {

       // Log.d("dsjfhl", "" + location.getLatitude() + "  " + location.getLongitude());
        if (Config.celsiu == -1) {
            //Log.d("StopLocation", "Running");
            lat = location.getLatitude();
            lng = location.getLongitude();
            String address = getAddress(TreeSpecies.this, location.getLatitude(), location.getLongitude());
            /// Log.d("addre123324ss ", "" + address);
            if (address != null) {
                setAddress(address);
            } else {
                //  Log.d("addre123324ss ", "" + address);
            }
        } else {
            //Log.d("StopLocation", "Stop");

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

    public static String getWeatherJSON(String address) throws IOException {
//test  ea574594b9d36ab688642d5fbeab847e;
        URL url = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + address + "&APPID=ea574594b9d36ab688642d5fbeab847e");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //     Log.d("asasaqw", "" + url);
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
        Log.d("asdsad", "" + response);
        return response;

    }

    private void setAddress(String address) {

        try {
            String response = getWeatherJSON(address);
            JSONObject jsonObject = new JSONObject(response);
            JSONObject coord = jsonObject.getJSONObject("coord");
            JSONArray weather = jsonObject.getJSONArray("weather");
            JSONObject jsonObject1 = weather.getJSONObject(0);
            Config.main = jsonObject1.getString("main");
            JSONObject weatherMain = jsonObject.getJSONObject("main");
            double temp = weatherMain.getDouble("temp");
            Config.celsiu = (int) (temp - 273.15);
//            Log.d("Config.main", "" + Config.main);
            if (Config.celsiu >= 0) {
                MainActivity.tempTxt.setText("" + Config.celsiu + "℃");
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

            //mSample.setText("Temperacture " + celsiu + " ℃ ");
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
        if (Config.celsiu == 0) {

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
            /*android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getActivity());
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

            dialog.show();*/
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

                    lm.requestLocationUpdates(provider, 0, 0, this);

                }
            }
        }
        /*} else {
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getActivity());
            dialog.setMessage(getActivity().getResources().getString(R.string.not_available_location));
            dialog.setCancelable(false);
            dialog.setPositiveButton(getActivity().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    *//*Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);*//*
                    startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                    //get gps
                }
            });
            dialog.setNegativeButton(getActivity().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }*/

    }

    public String getAddress(TreeSpecies homeTabView, double latitude, double longitude) {
        Context context = getActivity();
        Log.d("asdfsdaf", "" + context);
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
                    //Log.d("postalpostal", "" + locality);
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
                    //  Log.d("postalcountryName", "" + countryName);
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


    @Override
    public void onKittenClicked(ViewHolder holder, int position, int viewType) {

        ViewDetailsTabView kittenDetails = ViewDetailsTabView.newInstance(position, holder, viewType);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            kittenDetails.setSharedElementEnterTransition(new DetailsTransition());
            kittenDetails.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            kittenDetails.setSharedElementReturnTransition(new DetailsTransition());
        }

        if (viewType == 0) {
            Config.TREE_NAME = holder.treeName.getText().toString();
            Config.SEARCH_STR_VALUES = MainActivity.mSearchViewEdit.getText().toString();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(holder.imageView, "anim")
                    .replace(R.id.container_body, kittenDetails)
                    .commit();

        } else if (viewType == 1) {
            Config.TREE_NAME = holder.mPart2Title.getText().toString();
            Config.SEARCH_STR_VALUES = MainActivity.mSearchViewEdit.getText().toString();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(holder.mPart2Img, "anim")
                    .replace(R.id.container_body, kittenDetails)
                    .commit();
        }
    }

    class LoaderAsync extends AsyncTask<ArrayList<HashMap<String, byte[]>>, Void, ArrayList<HashMap<String, byte[]>>> {
        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mLocation;
        private final ArrayList<HashMap<String, byte[]>> treImages;
        // private final ArrayList<HashMap<String, String>> mImagePaths;
        private final String species;
        private ProgressDialog progressDialog;

        public LoaderAsync(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, ArrayList<HashMap<String, byte[]>> treImages, /*ArrayList<HashMap<String, String>> mImagePaths,*/ String species) {
            this.mContext = activity;
            this.mLocation = mLocation;
            this.treImages = treImages;
            //this.mImagePaths = mImagePaths;
            this.species = species;
            // Log.d("mImagePaths", "" + mImagePaths.size());
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
            byte[] byteArray;
            HashMap<String, byte[]> mHashBitMap;
            for (int i = 0; i < mLocation.size(); i++) {
                mHashBitMap = new HashMap<>();
                String storagePath = mLocation.get(i).get("storagePath");
                //Log.d("sad2324", "s " + storagePath);
                // Log.d("FromTreByte", "" + new File(storagePath).getName() + "  " + new File(storagePath).length());
                try {
                    if (storagePath != null || storagePath != "") {
                        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(storagePath), 64, 64);
                        //   Log.d("mImgeFrmTreeSpeAdater gradleThumbImage", "" + ThumbImage);
                        if (ThumbImage != null) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            ThumbImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byteArray = stream.toByteArray();
                            mHashBitMap.put("ThumbImage", byteArray);
                            Config.mImages.add(mHashBitMap);
                        } else {
                            mHashBitMap.put("ThumbImage", null);
                            Config.mImages.add(mHashBitMap);
                        }
                    } else {
                        mHashBitMap.put("ThumbImage", null);
                        Config.mImages.add(mHashBitMap);
                    }
                } catch (OutOfMemoryError outOfMemoryError) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView());
                    outOfMemoryError.printStackTrace();
                }
            }
            // Log.d("asasasa", "" + Config.mImages.size());
            return Config.mImages;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, byte[]>> mImages) {
            super.onPostExecute(mImages);
            // Log.d("sdfjaghdfg", "" + mImages.size());
            if (mImages.size() >= 1) {
                Adapter_Tree_Species adapter_tree_species = new Adapter_Tree_Species(getActivity(), Config.mLocation, mImages/*, Config.mImagePaths*/, "Species");
                mTreeSpecies.setAdapter(adapter_tree_species);
            }
            progressDialog.dismiss();
        }
    }
}

