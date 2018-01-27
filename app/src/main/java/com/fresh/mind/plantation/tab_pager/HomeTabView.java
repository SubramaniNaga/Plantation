package com.fresh.mind.plantation.tab_pager;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.SessionManager;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.pager_adapter.AdapterMainLay;
import com.fresh.mind.plantation.customized.CustomTabLayout;

import com.fresh.mind.plantation.fragment.menu_page.Weathers;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.LOCATION_SERVICE;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.fresh.mind.plantation.Constant.Config.SELECTED_TAB_VIEW_POSITION;
import static com.fresh.mind.plantation.Constant.Config.celsiu;

/**
 * Created by AND I5 on 13-02-2017.
 */
@SuppressWarnings("ResourceType")
public class HomeTabView extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ListView listView;
    private SessionManager sessionManager;
    private AdapterMainLay TabAdapter;
    private ViewPager viewPager;
    private LocationManager lm;
    private double lat, lng;
    private Location location;
    private static final long INTERVAL = 1000 * 60 * 1; //1 minute
    private static final long FASTEST_INTERVAL = 1000 * 60 * 1; // 1 minute
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private double currentLatitude;
    public double currentLongitude;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public void initGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionAddListener)
                .addOnConnectionFailedListener(connectionFailedListener)
                .build();
        mGoogleApiClient.connect();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        createLocationRequest();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resp == ConnectionResult.SUCCESS) {

            initGoogleAPIClient();

        } else {
            Log.e(TAG, "Your Device doesn't support Google Play Services.");
        }
        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setHomePage();
        View rootView = inflater.inflate(R.layout.plant_home_tab_view, null);

        sessionManager = new SessionManager(getActivity());


        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        final CustomTabLayout tabLayout = (CustomTabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("TREE SPECIES"));
        tabLayout.addTab(tabLayout.newTab().setText("TREE TYPE"));
        tabLayout.addTab(tabLayout.newTab().setText("LOCATION"));
        tabLayout.addTab(tabLayout.newTab().setText("TOOLS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);

        TabAdapter = new AdapterMainLay(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(TabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        TabAdapter.notifyDataSetChanged();
        tabLayout.getTabAt(0).setIcon(R.drawable.vector_drawable_tree_spec);
        tabLayout.getTabAt(1).setIcon(R.drawable.vector_drawable_tree_type);
        tabLayout.getTabAt(2).setIcon(R.drawable.vector_drawablelocation);
        tabLayout.getTabAt(3).setIcon(R.drawable.vector_drawable_magnifier);
        Config.pass = "splash";
        viewPager.setCurrentItem(SELECTED_TAB_VIEW_POSITION);
        setTitle(SELECTED_TAB_VIEW_POSITION, getActivity());

//        viewPager.getAdapter().notifyDataSetChanged();

        ColorStateList colors;
        if (Build.VERSION.SDK_INT >= 23) {
            colors = getActivity().getResources().getColorStateList(R.drawable.tab_icon, getActivity().getTheme());
        } else {
            colors = getActivity().getResources().getColorStateList(R.color.white);
//            colors = getResources().getColorStateList(R.drawable.tab_icon, getActivity().getTheme());
        }
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable icon = tab.getIcon();

            if (icon != null) {
                icon = DrawableCompat.wrap(icon);
                DrawableCompat.setTintList(icon, colors);
                //DrawableCompat.setTint(getResources().getDrawable(R.drawable.tab_color_selector));
            }
        }


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   SELECTED_TAB_VIEW_POSITION = tab.getPosition();
                                                   viewPager.setCurrentItem(tab.getPosition());
                                                   setTitle(tab.getPosition(), getActivity());
                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {
                                                   //  tab.getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {

                                               }
                                           }

        );


        //Log.d("s2342354", "" + Config.main);
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

    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.fresh.mind.plantation.service".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void setTitle(int selectedTabViewPosition, FragmentActivity act) {
        Log.d("tabeSelctionPosi", "" + selectedTabViewPosition);
        if (selectedTabViewPosition == 0) {
            MainActivity.title.setText(act.getResources().getString(R.string.treeSpecies));
            MainActivity.title.setVisibility(View.VISIBLE);
            MainActivity.menuItem.setVisible(true);
            MainActivity.menuItem1.setVisible(true);
            MainActivity.menuItem.setTitle(act.getString(R.string.menuNormalName));
            MainActivity.menuItem1.setTitle(act.getString(R.string.menuCientificName));
            MainActivity.previouseTxt = act.getResources().getString(R.string.treeSpecies);
            ((MainActivity) act).sortOrderFragment(act.getResources().getString(R.string.treeSpecies));
        } else if (selectedTabViewPosition == 1) {
            ((MainActivity) act).sortOrderFragment(act.getResources().getString(R.string.treeType));
            MainActivity.title.setText(act.getResources().getString(R.string.treeType));
            MainActivity.menuItem.setVisible(true);
            MainActivity.menuItem1.setVisible(true);
            MainActivity.menuItem.setTitle(act.getString(R.string.a_z));
            MainActivity.menuItem1.setTitle(act.getString(R.string.z_a));
            MainActivity.previouseTxt = act.getResources().getString(R.string.treeType);
        } else if (selectedTabViewPosition == 2) {
            ((MainActivity) act).sortOrderFragment(act.getResources().getString(R.string.location));
            MainActivity.title.setText(act.getResources().getString(R.string.chooseByLocation));
            MainActivity.menuItem.setVisible(false);
            MainActivity.menuItem1.setVisible(false);
            MainActivity.previouseTxt = act.getResources().getString(R.string.chooseByLocation);

        } else if (selectedTabViewPosition == 3) {
            ((MainActivity) act).sortOrderFragment(act.getResources().getString(R.string.search));
            MainActivity.title.setText(act.getResources().getString(R.string.search));
            MainActivity.menuItem.setVisible(false);
            MainActivity.menuItem1.setVisible(false);
            MainActivity.previouseTxt = act.getResources().getString(R.string.search);

        }
    }

    private static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            p.setMarginEnd(16);
            view.requestLayout();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        Log.d("StopLocation", "" + location.getLatitude() + "  " + location.getLongitude());
        if (Config.checkInternetConenction(getActivity())) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            String address = getAddress(HomeTabView.this, location.getLatitude(), location.getLongitude());
            Log.d("addre123324ss ", "" + address);
            if (address != null) {
                setAddress(address);
            }
        }

    }


    private void setAddress(String address) {

        try {
            String response = Weathers.getWeatherJSON(address);
            JSONObject jsonObject = new JSONObject(response);
            Log.d("jsonObject", "" + jsonObject);
            JSONObject coord = jsonObject.getJSONObject("coord");
            double lon = coord.getDouble("lon");
            double lat = coord.getDouble("lat");

            JSONArray weather = jsonObject.getJSONArray("weather");

            JSONObject jsonObject1 = weather.getJSONObject(0);
            String description = jsonObject1.getString("description");
            String main = jsonObject1.getString("main");

            String base = jsonObject.getString("base");

            JSONObject weatherMain = jsonObject.getJSONObject("main");
            double temp = weatherMain.getDouble("temp");
            Log.d("temp", "" + temp);
            Config.celsiu = (int) temp;
            //Config.celsiu = Math.abs((int) (temp - 273.15));
/*

            double pressure = weatherMain.getDouble("pressure");
            double humidity = weatherMain.getDouble("humidity");
            double temp_min = weatherMain.getDouble("temp_min");
            double temp_max = weatherMain.getDouble("temp_max");

            JSONObject wind = jsonObject.getJSONObject("wind");
            double speed = wind.getDouble("speed");
            double deg = wind.getDouble("deg");

            JSONObject clouds = jsonObject.getJSONObject("clouds");
            String all = clouds.getString("all");

            long millis = jsonObject.getLong("dt");
            millis = millis * 1000;
            Date date = new Date(millis);
*/

            Log.d("jsonObjectcelsiu", "" + celsiu);
            if (Config.celsiu >= 0) {
                MainActivity.tempTxt.setText("" + Config.celsiu + "℃");
                MainActivity.tempTxt.setVisibility(View.VISIBLE);
                if (main.toLowerCase().contains("Sunny".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.sunny);
                } else if (main.toLowerCase().contains("Clouds".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.partly_cloudy_29);
                } else if (main.toLowerCase().equals("Thunderstorm".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.partly_sunny_weather);
                } else if (main.toLowerCase().equals("Clear".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.clear);
                } else if (main.toLowerCase().equals("Rain".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.rain);
                } else if (main.toLowerCase().equals("Somke".toLowerCase())) {
                    MainActivity.weatherImage.setImageResource(R.drawable.cloudy_10);
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
    }

    public String getAddress(HomeTabView homeTabView, double latitude, double longitude) {
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
                    Log.d("postalpostal", "" + locality);
                    if (locality != null) {
                        sb.append(locality).append(" ");
                    } else {
                        sb.append("Unknown Name").append(" ");
                    }
                   /* String postal = address.getPostalCode();
                    Log.d("", "" + postal);
                    if (postal != null) {
                        sb.append(postal).append(" ");
                    }
                    String countryName = address.getCountryName();
                    Log.d("postalcountryName", "" + countryName + "  " + address.getAdminArea() + "  " + address.getFeatureName());
                    if (countryName != null) {
                        sb.append(countryName);
                    } else {
                    }*/
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
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private GoogleApiClient.ConnectionCallbacks connectionAddListener = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
            try {
                Log.i(TAG, "onConnected");
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (location == null) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) getActivity());
                } else {
                    //If everything went fine lets get latitude and longitude
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    String address = getAddress(HomeTabView.this, location.getLatitude(), location.getLongitude());
                    Log.d("addre123324ss ", "" + address);
                    if (address != null) {
                        setAddress(address);
                    } else {
                        Log.d("addre123324ss ", "" + address);
                    }
                }

            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConnectionSuspended(int i) {
            Log.e(TAG, "onConnectionSuspended");
        }
    };

    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.e(TAG, "onConnectionFailed" + connectionResult);
        }
    };

}
