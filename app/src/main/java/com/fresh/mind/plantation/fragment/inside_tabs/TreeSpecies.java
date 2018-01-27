package com.fresh.mind.plantation.fragment.inside_tabs;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.Adapter_Tree_Species;
import com.fresh.mind.plantation.adapter.recycler_adapter.Adapter_TreeSpecies;
import com.fresh.mind.plantation.customized.CustomTextView;


import com.fresh.mind.plantation.sqlite.server.ContactUs;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.server.GlossaryTable;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.RainfallType;
import com.fresh.mind.plantation.sqlite.server.SoilType;
import com.fresh.mind.plantation.sqlite.sorting.SortOrder;
import com.fresh.mind.plantation.sqlite.server.TerrainType;
import com.fresh.mind.plantation.sqlite.server.TreeTypeInfo;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;

import com.fresh.mind.plantation.sqlite.LanguageChange;

import com.fresh.mind.plantation.sqlite.server.TreeList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;

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

import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION;

import static com.fresh.mind.plantation.fragment.menu_page.Weathers.convertStreamToString;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class TreeSpecies extends Fragment implements LocationListener {

    private CustomTextView mNodata;
    public static ListView mTreeSpecies;
    private Location location;
    private LocationManager lm;

    public static Adapter_TreeSpecies adapter_ttreeSpecies;
    private String bySortType;

    private DistrictNameList districtNameList;
    private TreeTypeNameList treeTypeNameList;
    private SoilType soilType;
    private VerifyDetails verifyDetails;
    private TerrainType terrainType;
    private RainfallType rainfallType;
    private TreeTypeInfo treeTypeInfo;
    private ImageDb imageDb;
    private ContactUs contactUs;
    private GlossaryTable glossaryTable;
    private ImageView imageView8;

    private LanguageChange languageChange;
    private String languages;
    private RecyclerView mTreeSpecies_RecyclerView;
    private ArrayList<HashMap<String, String>> Receiversss;
    private TreeList treeList;
    private double lat, lng;
    private int pageCount = 0;
    int lastInScreen = 0;
    private Adapter_Tree_Species adapter_tree_species;
    private SortOrder sortOrder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Log.d("Firstssss", "onCreateView");
        languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        //((MainActivity) getActivity()).species(getActivity().getResources().getString(R.string.treeSpecies));

        sortOrder = new SortOrder(getActivity());
        View rootView = inflater.inflate(R.layout.plant_tree_species, null);
        treeList = new TreeList(getActivity());
        districtNameList = new DistrictNameList(getActivity());
        treeTypeNameList = new TreeTypeNameList(getActivity());
        verifyDetails = new VerifyDetails(getActivity());
        soilType = new SoilType(getActivity());
        rainfallType = new RainfallType(getActivity());
        terrainType = new TerrainType(getActivity());
        treeTypeInfo = new TreeTypeInfo(getActivity());
        imageDb = new ImageDb(getActivity());
        languageChange = new LanguageChange(getActivity());
        contactUs = new ContactUs(getActivity());
        glossaryTable = new GlossaryTable(getActivity());
        Receiversss = new ArrayList<>();

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        mTreeSpecies = (ListView) rootView.findViewById(R.id.mTreeSpecies);
        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        mTreeSpecies_RecyclerView = (RecyclerView) rootView.findViewById(R.id.mTreeSpecies_RecyclerView);

       /* intentFilter = new IntentFilter();
        intentFilter.addAction("refresh");*/

        mTreeSpecies.setVisibility(View.GONE);
        Receiversss = treeList.getTreTypeeNames(languages);
        adapter_tree_species = new Adapter_Tree_Species(getActivity());
        mTreeSpecies.setAdapter(adapter_tree_species);
        bySortType = sortOrder.getSort();
        Log.d("Receiversss", "" + Receiversss.size());
        if (Receiversss.size() >= 1) {
            if (bySortType != null) {
                if (bySortType.equals("Z-A")) {
                    Log.d("sdd", "come");
                    Collections.sort(Receiversss, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("subTreeName").compareTo(s2.get("subTreeName"));
                        }
                    });
                } else {
                    Log.d("sdd", "not come");
                    Collections.sort(Receiversss, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("treeName").compareTo(s2.get("treeName"));
                        }
                    });
                }
            }

            adapter_tree_species.addMore(Receiversss, bySortType,/*, Config.mImages,*/ "Species");
            adapter_tree_species.notifyDataSetChanged();
            //  new LoaderAsync(getActivity(), Config.mLocation, treeList.getTreImages(), "Species").execute();
            mTreeSpecies_RecyclerView.setVisibility(View.GONE);
            mTreeSpecies.setVisibility(View.VISIBLE);
            mNodata.setVisibility(View.GONE);
            if (Config.SEARCH_STR_VALUES != "") {
                adapter_ttreeSpecies.getFilter().filter(Config.SEARCH_STR_VALUES);
                MainActivity.mSearchViewEdit.requestFocus();
                MainActivity.mSearchViewEdit.setFocusableInTouchMode(true);
                MainActivity.mSearchViewEdit.setSelection(Config.SEARCH_STR_VALUES.length());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(MainActivity.mSearchViewEdit, InputMethodManager.SHOW_FORCED);
            }
        } else {
            // new Asyn().execute();
            mTreeSpecies_RecyclerView.setVisibility(View.GONE);
            mTreeSpecies.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
        }
        mTreeSpecies.setTextFilterEnabled(true);
        mTreeSpecies.setSelectionFromTop(LISTVIEW_SMOOTH_VIEW_POSITION, 0);
        LISTVIEW_SMOOTH_VIEW_POSITION = 0;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (Config.checkInternetConenction(getActivity())) {
            if (Config.celsiu == -1) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                String address = getAddress(TreeSpecies.this, location.getLatitude(), location.getLongitude());
                if (address != null) {
                    setAddress(address);
                }
            } else {
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

    @Override
    public void onResume() {
        super.onResume();
        //if (Config.checkInternetConenction(getActivity())) {
        Log.d("Receiversss", "onResume");


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

                // notify user

            } else {
                lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = lm.getBestProvider(criteria, true);

                if (provider != null) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        location = lm.getLastKnownLocation(provider);
                    if (location != null) {

                        onLocationChanged(location);
                    }

                    lm.requestLocationUpdates(provider, 0, 0, this);

                }
            }
        }
      /*  Receiversss = treeList.getTreTypeeNames(languages);
        Log.d("Receiversss", "onResume " + Receiversss.size());
        Config.mLocation = Receiversss;
        bySortType = sortOrder.getSort();

        if (Receiversss.size() >= 1) {
            if (bySortType != null) {
                if (bySortType.equals("Z-A")) {
                    Log.d("sdd", "come");
                    Collections.sort(Receiversss, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("subTreeName").compareTo(s2.get("subTreeName"));
                        }
                    });
                } else {
                    Log.d("sdd", "not come");
                    Collections.sort(Receiversss, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("treeName").compareTo(s2.get("treeName"));
                        }
                    });
                }
            }
            adapter_tree_species.addMore(Receiversss, bySortType,*//*, Config.mImages,*//* "Species");
            adapter_tree_species.notifyDataSetChanged();
            //  new LoaderAsync(getActivity(), Config.mLocation, treeList.getTreImages(), "Species").execute();
            mTreeSpecies_RecyclerView.setVisibility(View.GONE);
            mTreeSpecies.setVisibility(View.VISIBLE);
            mNodata.setVisibility(View.GONE);

        } else {
            // new Asyn().execute();
            mTreeSpecies_RecyclerView.setVisibility(View.GONE);
            mTreeSpecies.setVisibility(View.GONE);
            mNodata.setVisibility(View.VISIBLE);
        }*/

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
        //   Log.d("asdsad", "" + response);
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


    public String getAddress(TreeSpecies homeTabView, double latitude, double longitude) {
        String result = null;
        if (Config.checkInternetConenction(getActivity())) {
            Context context = getActivity();
            // Log.d("asdfsdaf", "" + context);
            if (context == null) {
                return null;
            } else {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

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
                    // Log.d("result", "" + result);
                } catch (IOException e) {
                    Log.e("UnableGeoCoder", "Unable connect to Geocoder", e);
                }

                return result;
            }
        }
        return result;
    }

/*
    @Override
    public void onKittenClicked(ViewHolder holder, int position, int viewType) {

        Log.d("titleNamedsd", "opop");
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
                    .replace(R.id.container_body, kittenDetails)*//*.addToBackStack(null)*//*
                    .commit();

        } else if (viewType == 1) {
            Config.TREE_NAME = holder.mPart2Title.getText().toString();
            Config.SEARCH_STR_VALUES = MainActivity.mSearchViewEdit.getText().toString();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(holder.mPart2Img, "anim")
                    .replace(R.id.container_body, kittenDetails)*//*.addToBackStack(null)*//*
                    .commit();
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        treeList.close();

    }

}

