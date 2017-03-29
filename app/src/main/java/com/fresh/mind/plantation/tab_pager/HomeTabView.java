package com.fresh.mind.plantation.tab_pager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.ListView;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.SessionManager;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.pager_adapter.AdapterMainLay;
import com.fresh.mind.plantation.customized.CustomTabLayout;

import com.fresh.mind.plantation.fragment.Weathers;
import com.fresh.mind.plantation.service.GPSTracker;
import com.fresh.mind.plantation.service.UpdateData;
import com.fresh.mind.plantation.sqlite.LanguageChange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.fresh.mind.plantation.Constant.Config.celsiu;
import static com.fresh.mind.plantation.R.id.address;
import static com.fresh.mind.plantation.fragment.Weathers.getAddress;

/**
 * Created by AND I5 on 13-02-2017.
 */
@SuppressWarnings("ResourceType")
public class HomeTabView extends Fragment {
    private View rootView;
    private ListView listView;
    private SessionManager sessionManager;
    private AdapterMainLay TabAdapter;
    private ViewPager viewPager;
    private LocationManager lm;
    private double lat, lng;
    private Location location;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setHomePage();
        rootView = inflater.inflate(R.layout.plant_home_tab_view, null);
        sessionManager = new SessionManager(getActivity());

        //listView = (ListView) rootView.findViewById(R.id.listView);

        HashMap<String, String> mHas = sessionManager.getUserDetails();
        // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body1, new TabLays()).commit();

        CustomTabLayout tabLayout = (CustomTabLayout) rootView.findViewById(R.id.tab_layout);
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

        tabLayout.getTabAt(0).setIcon(R.drawable.vector_drawable_tree_spec);
        tabLayout.getTabAt(1).setIcon(R.drawable.vector_drawable_tree_type);
        tabLayout.getTabAt(2).setIcon(R.drawable.vector_drawablelocation);
        tabLayout.getTabAt(3).setIcon(R.drawable.vector_drawable_setting_tools);
        Config.pass = "splash";
        viewPager.setCurrentItem(Config.SELECTED_TAB_VIEW_POSITION);
        setTitle(Config.SELECTED_TAB_VIEW_POSITION, getActivity());

        if (Config.SERVICE == 0) {
            getActivity().startService(new Intent(getActivity(), UpdateData.class));
        }

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
                                                   //  Log.d("243-05890", "" + tab.getPosition());
                                                   Config.SELECTED_TAB_VIEW_POSITION = tab.getPosition();
                                                   viewPager.setCurrentItem(Config.SELECTED_TAB_VIEW_POSITION);
                                                   setTitle(Config.SELECTED_TAB_VIEW_POSITION, getActivity());
                                                   //tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

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
        return rootView;
    }

    public static void setTitle(int selectedTabViewPosition, FragmentActivity act) {
        if (selectedTabViewPosition == 0) {
            MainActivity.title.setText(act.getResources().getString(R.string.treeSpecies));
            MainActivity.title.setVisibility(View.VISIBLE);
        }
        if (selectedTabViewPosition == 1) {
            MainActivity.title.setText(act.getResources().getString(R.string.treeType));
        }
        if (selectedTabViewPosition == 2) {
            MainActivity.title.setText(act.getResources().getString(R.string.chooseByLocation));
        }
        if (selectedTabViewPosition == 3) {
            MainActivity.title.setText(act.getResources().getString(R.string.tools));
            //  MainActivity.mSearchViewImg.setVisibility(View.GONE);
        }
    }
/*

    @Override
    public void onLocationChanged(Location location) {

        Log.d("dsjfhl", "" + location.getLatitude() + "  " + location.getLongitude());
        if (Config.celsiu == -1) {
            Log.d("StopLocation", "Running");
            lat = location.getLatitude();
            lng = location.getLongitude();
            String address = getAddress(HomeTabView.this, location.getLatitude(), location.getLongitude());
            Log.d("addre123324ss ", "" + address);
            if (address != null) {
                setAddress(address);
            } else {
                Log.d("addre123324ss ", "" + address);
            }
        } else {
            Log.d("StopLocation", "Stop");

            lm.removeUpdates(this);
            lm = null;
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
*/

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
            Config.celsiu = (int) (temp - 273.15);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
