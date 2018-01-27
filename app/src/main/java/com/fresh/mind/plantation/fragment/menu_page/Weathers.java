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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.List;
import java.util.Locale;

import static android.media.CamcorderProfile.get;

/**
 * Created by AND I5 on 13-03-2017.
 */

public class Weathers extends Fragment implements LocationListener {
    private View rootView;
    private LocationManager lm;
    private double lat, lng;
    private CustomTextView mAddressTxt, mTemptxt, mCloudTxt, updateTimeTxt;
    private String address;
    MapView mMapView;
    private GoogleMap googleMap;


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

        ((MainActivity) getActivity()).setHelp(getActivity().getResources().getString(R.string.weathr));
        rootView = inflater.inflate(R.layout.plant_weather, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        mAddressTxt = (CustomTextView) rootView.findViewById(R.id.address);
        mTemptxt = (CustomTextView) rootView.findViewById(R.id.mTempStatus);
        mCloudTxt = (CustomTextView) rootView.findViewById(R.id.mCloudStatus);
        updateTimeTxt = (CustomTextView) rootView.findViewById(R.id.updateTime);

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
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            Location location = lm.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            }
            lm.requestLocationUpdates(provider, 2000, 100, this);
        }

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //address = getAddress(lat, lng);


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;
                googleMap.setMyLocationEnabled(true);
                LatLng sydney = new LatLng(lat, lng);
                //  googleMap.addMarker(new MarkerOptions().position(sydney).title(address));
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        googleMap.clear();
                        double lat = latLng.latitude;
                        double lng = latLng.longitude;
                        address = getAddress(getActivity(), lat, lng);
                        Log.d("asdasds", "" + address);
                        setAddress(address);
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(address));
                    }
                });
                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        googleMap.clear();
                        LatLng sydney = new LatLng(lat, lng);
                        //  googleMap.addMarker(new MarkerOptions().position(sydney).title(address));
                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        address = getAddress(getActivity(), lat, lng);
                        Log.d("addre123324ssLongPress ", "" + address);
                        setAddress(address);
                    }
                });
            }
        });

        return rootView;
    }


    public static String getWeatherJSON(String address) throws IOException {
//test  ea574594b9d36ab688642d5fbeab847e;

        URL url = null;
        try {
            //http://api.openweathermap.org/data/2.5/weather?q=erode&units=metric&APPID=ea574594b9d36ab688642d5fbeab847e
            // url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + address + "&APPID=ea574594b9d36ab688642d5fbeab847e");
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + address + "&units=metric&APPID=ea574594b9d36ab688642d5fbeab847e");
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
       // Log.d("asdsad", "" + response);
        return response;

    }


    public static String convertStreamToString(InputStream is) {
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

        lat = location.getLatitude();
        lng = location.getLongitude();
        address = getAddress(getActivity(), location.getLatitude(), location.getLongitude());
        Log.d("addre123324ss ", "" + address);
        setAddress(address);
    }

    private void setAddress(String address) {

        try {
            String response = getWeatherJSON(address);
            JSONObject jsonObject = new JSONObject(response);
            Log.d("jsonObject", "" + jsonObject);
            //JSONObject coord = jsonObject.getJSONObject("coord");

            JSONArray weather = jsonObject.getJSONArray("weather");

            JSONObject jsonObject1 = weather.getJSONObject(0);

            String main = jsonObject1.getString("main");

            JSONObject weatherMain = jsonObject.getJSONObject("main");
            double temp = weatherMain.getDouble("temp");
            int celsiu = (int) (temp - 273.15);
            Log.d("containssssMostlySunny", "" + main + "  " + main.length());
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


            if (address == null) {
                mAddressTxt.setText("Unknown Area Name");
            } else {
                mAddressTxt.setText("" + address);
            }
            mTemptxt.setText("" + celsiu + " ℃ ");
            MainActivity.tempTxt.setText("" + celsiu + " ℃ ");
            MainActivity.tempTxt.setVisibility(View.VISIBLE);
            mCloudTxt.setText("" + main);
            //updateTimeTxt.setText("" + dateFormatted);

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


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public static String getAddress(Context context, double latitude, double longitude) {
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

            //  Log.d("result", "" + result);
        } catch (IOException e) {
            Log.e("UnableGeoCoder", "Unable connect to Geocoder", e);
        }
        return result;

    }
}
