package com.fresh.mind.plantation.fragment.menu_page;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by AND I5 on 10-03-2017.
 */

public class Others extends Fragment implements LocationListener {
    private View rootView;
    private CustomTextView mSample;
    private CustomTextView dateTxt, mainTxt, descriptionTxt, tempTxt, pressureTxt, humidityTxt, temp_minTxt, temp_maxTxt, speedTxt, degTxt, allTxt, nameTxt;
    LocationManager lm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();

        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setOtherPage(getActivity().getResources().getString(R.string.Schemes));
        rootView = inflater.inflate(R.layout.others, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        mSample = (CustomTextView) rootView.findViewById(R.id.mSample);
        dateTxt = (CustomTextView) rootView.findViewById(R.id.date);
        mainTxt = (CustomTextView) rootView.findViewById(R.id.main);
        descriptionTxt = (CustomTextView) rootView.findViewById(R.id.description);
        tempTxt = (CustomTextView) rootView.findViewById(R.id.temp);
        pressureTxt = (CustomTextView) rootView.findViewById(R.id.pressure);
        humidityTxt = (CustomTextView) rootView.findViewById(R.id.humidity);
        temp_minTxt = (CustomTextView) rootView.findViewById(R.id.temp_min);
        temp_maxTxt = (CustomTextView) rootView.findViewById(R.id.temp_max);
        speedTxt = (CustomTextView) rootView.findViewById(R.id.speed);
        degTxt = (CustomTextView) rootView.findViewById(R.id.deg);
        allTxt = (CustomTextView) rootView.findViewById(R.id.all);
        nameTxt = (CustomTextView) rootView.findViewById(R.id.name);


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
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
           /* Location location = lm.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            }
            lm.requestLocationUpdates(provider, 2000, 100, this);*/
        }


        return rootView;
    }


    public static String getWeatherJSON(String address) throws IOException {
//test  ea574594b9d36ab688642d5fbeab847e;
        URL url = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + address + "&APPID=ea574594b9d36ab688642d5fbeab847e");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("asasaqw", "" + url);
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


    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("dsjfhl", "" + location.getLatitude() + "  " + location.getLongitude());
        String address = getAddress(getActivity(), location.getLatitude(), location.getLongitude());
        Log.d("addre123324ss ", "" + address);

        try {
            String response = getWeatherJSON(address);
            JSONObject jsonObject = new JSONObject(response);
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
            int celsiu = (int) (temp - 273.15);


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
            Log.d("millis", "" + millis + "  " + System.currentTimeMillis());

            Date date = new Date(millis);
            DateFormat formatter = new SimpleDateFormat("hh:mm:ss dd:MM:yyyy");
            String dateFormatted = formatter.format(date);

            String name = jsonObject.getString("name");
            /*JSONObject sys = jsonObject.getJSONObject("sys");
            Log.d("sys",""+sys);
            String type = sys.getString("type");
            String id = sys.getString("id");
            String message = sys.getString("message");
            String country = sys.getString("country");
            String sunrise = sys.getString("sunrise");
            String sunset = sys.getString("sunset");*/

//            Log.d("allViewsFormServer ", "" + " dateFormatted " + dateFormatted + " all " + all + " deg " + deg + " speed " + speed + " pressure " + pressure + " humidity " + humidity + " temp_min " + temp_min + " temp_max " + temp_max + " description " + description + " main " + main + "  " + base + " lon " + lon + "  lat " + lat);


            dateTxt.setText("Date " + dateFormatted);
            mainTxt.setText("Main Situation " + main);
            descriptionTxt.setText("Description  " + description);
            tempTxt.setText("Temperacture " + celsiu + " ℃ ");
            pressureTxt.setText("Pressure " + pressure);
            humidityTxt.setText("Humidity " + humidity);
            temp_minTxt.setText("Temp_Min " + temp_min);
            temp_maxTxt.setText("Temp_Max " + temp_max);
            speedTxt.setText("Speed " + speed);
            degTxt.setText("Deg " + deg);
            allTxt.setText("All " + all);
            nameTxt.setText("City " + name);


            //mSample.setText("Temperacture " + celsiu + " ℃ ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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

    private String getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                /*for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append(",");
                }*/
                sb.append(address.getLocality()).append(",");
                //sb.append(address.getPostalCode()).append(",");
                //sb.append(address.getCountryName());
                result = sb.toString();
            }

        } catch (IOException e) {
            Log.e("sdjhjfkh", "Unable connect to Geocoder", e);
        }
        return result;

    }
}
