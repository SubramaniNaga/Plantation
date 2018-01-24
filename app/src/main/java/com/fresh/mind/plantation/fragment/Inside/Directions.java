package com.fresh.mind.plantation.fragment.Inside;import android.Manifest;import android.app.ProgressDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.content.pm.PackageManager;import android.graphics.Color;import android.location.Address;import android.location.Criteria;import android.location.Geocoder;import android.location.Location;import android.location.LocationListener;import android.location.LocationManager;import android.os.AsyncTask;import android.os.Bundle;import android.os.Handler;import android.provider.Settings;import android.support.annotation.Nullable;import android.support.v4.app.ActivityCompat;import android.support.v4.app.Fragment;import android.text.Html;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Toast;import com.fresh.mind.plantation.Constant.JSONParser;import com.fresh.mind.plantation.Constant.Utils;import com.fresh.mind.plantation.R;import com.fresh.mind.plantation.activity.MainActivity;import com.fresh.mind.plantation.customized.CustomTextView;import com.fresh.mind.plantation.service.GPSTracker;import com.fresh.mind.plantation.sqlite.LanguageChange;import com.google.android.gms.maps.CameraUpdateFactory;import com.google.android.gms.maps.GoogleMap;import com.google.android.gms.maps.MapView;import com.google.android.gms.maps.MapsInitializer;import com.google.android.gms.maps.OnMapReadyCallback;import com.google.android.gms.maps.model.BitmapDescriptorFactory;import com.google.android.gms.maps.model.CameraPosition;import com.google.android.gms.maps.model.LatLng;import com.google.android.gms.maps.model.Marker;import com.google.android.gms.maps.model.MarkerOptions;import com.google.android.gms.maps.model.Polyline;import com.google.android.gms.maps.model.PolylineOptions;import org.json.JSONArray;import org.json.JSONException;import org.json.JSONObject;import java.io.IOException;import java.util.ArrayList;import java.util.List;import java.util.Locale;import static android.R.attr.width;import static com.fresh.mind.plantation.R.string.location;/** * Created by AND I5 on 25-02-2017. */public class Directions extends Fragment implements LocationListener {    private View rootView;    private MapView mMapView;    private GoogleMap googleMap;    private LocationManager lm;    private double lat, lng;    private Polyline line;    private String address;    private CustomTextView textView;    private Location location;    private String[] place = {"Coimbatore", "Avinashi", "Perundurai", "Bhavani", "Erode", "Chennai", "Madurai", "Coimbatore Airport", "Psg Tech", "Psg Public School"};    private Double[] lats = {11.0168, 11.1914, 11.2681, 11.4501, 11.3410, 13.0827, 9.9252, 11.0307, 11.024809, 11.023727};    private Double[] lngs = {76.9558, 77.2689, 77.5906, 77.6822, 77.7172, 80.2707, 78.1198, 77.0406, 77.002826, 77.004823};    /*   private final LocationListener mLocationListener = new LocationListener() {           @Override           public void onLocationChanged(final Location location) {               //your code here           }       };*/    @Nullable    @Override    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        super.onCreateView(inflater, container, savedInstanceState);        LanguageChange languageChange = new LanguageChange(getActivity());        String languages = languageChange.getStatus();        if (languages.equals("1")) {            Utils.setLocalLanguage("ta", getActivity());        } else {            Utils.setLocalLanguage("en", getActivity());        }        ((MainActivity) getActivity()).setDirection(getActivity().getResources().getString(R.string.locator));        rootView = inflater.inflate(R.layout.directions, null);        MainActivity.menuItem.setVisible(false);        MainActivity.menuItem1.setVisible(false);        textView = (CustomTextView) rootView.findViewById(R.id.textView);        GPSTracker gps = new GPSTracker(getActivity());        // check if GPS enabled        if (gps.canGetLocation()) {            lat = gps.getLatitude();            lng = gps.getLongitude();            Log.d("CureenLocationDirection", "" + lat + "  " + lng);            // \n is for new line           /* Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();            LatLng currentLocation = new LatLng(latitude, longitude);            // For zooming automatically to the location of the marker            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(10).build();            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/        } else {            // can't get location            // GPS or Network is not enabled            // Ask user to enable GPS/network in settings            gps.showSettingsAlert();        }        mMapView = (MapView) rootView.findViewById(R.id.mapView);        //Log.d("mMapView", "" + mMapView + "  " + savedInstanceState);        //    if (savedInstanceState != null) {        try {            mMapView.onCreate(savedInstanceState);            mMapView.onResume(); // needed to get the map to display immediately            MapsInitializer.initialize(getActivity().getApplicationContext());        } catch (NullPointerException e) {            e.printStackTrace();        }        //}        address = getAddress(lat, lng);        mMapView.getMapAsync(new OnMapReadyCallback() {            @Override            public void onMapReady(GoogleMap mMap) {                //Log.d("smaplePOstion", "maploadind");                googleMap = mMap;                googleMap.setMyLocationEnabled(true);                LatLng currentLocation = new LatLng(lat, lng);                googleMap.addMarker(new MarkerOptions().position(currentLocation).title(address));                // For zooming automatically to the location of the marker                CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(10).build();                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));                new Handler().postDelayed(new Runnable() {                    @Override                    public void run() {                        for (int i = 0; i < lngs.length; i++) {                            //Log.d("CurrentLocation", "" + lat + "  " + lng + "  ApiLatLng " + lats[i] + "  " + lngs[i]);                            double lat1 = lat;                            double lon1 = lng;                            double lat2 = lats[i];                            double lon2 = lngs[i];                            double theta = lon1 - lon2;                            double dist = Math.sin(deg2rad(lat1))                                    * Math.sin(deg2rad(lat2))                                    + Math.cos(deg2rad(lat1))                                    * Math.cos(deg2rad(lat2))                                    * Math.cos(deg2rad(theta));                            dist = Math.acos(dist);                            dist = rad2deg(dist);                            dist = dist * 60 * 1.1515;                            //Log.d("CurrentLocationdistdlkjk", "" + dist);                            drawMarker(new LatLng(lat2, lon2), dist, getAddress(lat2, lon2));                        }                    }                }, 100);            }        });        /*if (direction != null && direction != "") {            Address location = getLocationFromAddress(direction.toUpperCase());            double destLat = location.getLatitude();            double destLng = location.getLongitude();            //Log.d("dsfsdf", "" + destLat + "  " + destLng);            String urlTopass = makeURL(lat, lng, destLat, destLng);            new connectAsyncTask(urlTopass).execute();        } else {            textView.setVisibility(View.VISIBLE);            textView.setText("Not found contact address");        }*/        return rootView;    }    private double deg2rad(double deg) {        return (deg * Math.PI / 180.0);    }    private double rad2deg(double rad) {        return (rad * 180.0 / Math.PI);    }    private void drawMarker(LatLng point, double dist, String place) {        /*MarkerOptions markerOptions = new MarkerOptions();        markerOptions.position(point);        googleMap.addMarker(markerOptions);*/        MarkerOptions marker = new MarkerOptions().position(point).title(place);        if (dist < 30) {            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));        } else {           /* BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.vector_drawable_pin);            Bitmap b = bitmapdraw.getBitmap();            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 50, 50, false);*/            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));        }        googleMap.addMarker(marker);    }    private String getAddress(double latitude, double longitude) {        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());        String result = null;        try {            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);            if (addressList != null && addressList.size() > 0) {                Address address = addressList.get(0);                StringBuilder sb = new StringBuilder();                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {                    sb.append(address.getAddressLine(i)).append(",");                }                sb.append(address.getLocality()).append("\n");                sb.append(address.getPostalCode()).append(",");                sb.append(address.getCountryName());                result = sb.toString();            }        } catch (IOException e) {            Log.e("sdjhjfkh", "Unable connect to Geocoder", e);        }        return result;    }    public Address getLocationFromAddress(String strAddress) {        //Log.d("strAddressstrAddress", "" + strAddress);        Geocoder coder = new Geocoder(getActivity());        List<Address> address;        LatLng p1 = null;        Address location = null;        try {            address = coder.getFromLocationName(strAddress, 5);            if (address == null) {                return null;            }            location = address.get(0);            location.getLatitude();            location.getLongitude();            p1 = new LatLng(location.getLatitude(), location.getLongitude());        } catch (IOException ex) {            ex.printStackTrace();        }        return location;    }    @Override    public void onResume() {        super.onResume();        mMapView.onResume();        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);        boolean gps_enabled = false;        boolean network_enabled = false;        try {            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);        } catch (Exception ex) {        }        try {            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);        } catch (Exception ex) {        }        if (!gps_enabled && !network_enabled) {            Log.d("sadd", "eeeeee");            // notify user            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getActivity());            dialog.setMessage(getActivity().getResources().getString(R.string.not_available_location));            dialog.setCancelable(false);            dialog.setPositiveButton(getActivity().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {                @Override                public void onClick(DialogInterface paramDialogInterface, int paramInt) {                    // TODO Auto-generated method stub                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);                    startActivity(myIntent);                    //get gps                }            });            dialog.show();        } else {            lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);            Criteria criteria = new Criteria();            String provider = lm.getBestProvider(criteria, true);            Log.d("provider", "" + provider);            if (provider != null) {                Log.d("provideproviderr", "" + provider);                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)                    location = lm.getLastKnownLocation(provider);                if (location != null) {                    Log.d("asdsadsadsa", "Locations");                    onLocationChanged(location);                }                GPSTracker gpsTracker = new GPSTracker(getActivity());                if (gpsTracker.canGetLocation()) {                    lat = gpsTracker.getLatitude();                    lng = gpsTracker.getLongitude();                    Log.d("sddfsd", "" + lat + "  " + lng);                    if (lat == 0.0 && lng == 0.0) {                        lm.requestLocationUpdates(provider, 0, 0, this);                    } else {                        String address = getAddress(lat, lng);                        Log.d("lksldsjdaddress", "" + address);                    }                }            }        }    }    @Override    public void onPause() {        super.onPause();        mMapView.onPause();    }    @Override    public void onDestroy() {        super.onDestroy();        mMapView.onDestroy();        googleMap.clear();    }    @Override    public void onLowMemory() {        super.onLowMemory();        mMapView.onLowMemory();    }    @Override    public void onLocationChanged(Location location) {        lat = location.getLatitude();        lng = location.getLongitude();        Log.d("vlkjsdkjd", "" + lat + "  " + lng);        // For showing a move to my location button        googleMap.setMyLocationEnabled(true);        LatLng sydney = new LatLng(lat, lng);        googleMap.addMarker(new MarkerOptions().position(sydney).title(address));        // For zooming automatically to the location of the marker        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));    }    @Override    public void onStatusChanged(String provider, int status, Bundle extras) {    }    @Override    public void onProviderEnabled(String provider) {    }    @Override    public void onProviderDisabled(String provider) {    }    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {        StringBuilder urlString = new StringBuilder();        urlString.append("http://maps.googleapis.com/maps/api/directions/json");        urlString.append("?origin=");// from        urlString.append(Double.toString(sourcelat));        urlString.append(",");        urlString.append(Double.toString(sourcelog));        urlString.append("&destination=");// to        urlString.append(Double.toString(destlat));        urlString.append(",");        urlString.append(Double.toString(destlog));        urlString.append("&sensor=false&mode=driving&alternatives=true");        return urlString.toString();    }    private class connectAsyncTask extends AsyncTask<Void, Void, String> {        private ProgressDialog progressDialog;        String url;        connectAsyncTask(String urlPass) {            url = urlPass;        }        @Override        protected void onPreExecute() {            // TODO Auto-generated method stub            super.onPreExecute();            progressDialog = new ProgressDialog(getActivity());            progressDialog.setMessage("Fetching route, Please wait...");            progressDialog.setIndeterminate(true);            progressDialog.setCancelable(false);            progressDialog.show();        }        @Override        protected String doInBackground(Void... params) {            JSONParser jParser = new JSONParser();            String json = jParser.getJSONFromUrl(url);            return json;        }        @Override        protected void onPostExecute(String result) {            super.onPostExecute(result);            if (result != null) {                ////Log.d("resulret43435435t", "" + result);                drawPath(result);                try {                    JSONObject jsonObject = new JSONObject(result);                    JSONArray routes = jsonObject.getJSONArray("routes");                    String text = "", durationtxt = "", end_address = "", start_address = "";                    for (int ro = 0; ro < routes.length(); ro++) {                        JSONObject jsonObject1 = routes.getJSONObject(ro);                        JSONArray legs = jsonObject1.getJSONArray("legs");                        for (int leg = 0; leg < legs.length(); leg++) {                            JSONObject jsonObject2 = legs.getJSONObject(leg);                            JSONObject distance = jsonObject2.getJSONObject("distance");                            text = distance.getString("text");                            ////Log.d("durationtxt", "" + text);                            JSONObject duration = jsonObject2.getJSONObject("duration");                            durationtxt = duration.getString("text");                            start_address = jsonObject2.getString("start_address");                            end_address = jsonObject2.getString("end_address");                            // //Log.d("durationtxt", "" + durationtxt);                            textView.setVisibility(View.VISIBLE);                            textView.setText(Html.fromHtml("<h5>Form : </h5>" + start_address + "<h5>To : </h5>" + end_address + "<h5>Distance / Duration : </h5>" + text + " / " + durationtxt));                        }                    }//                    //Log.d("routes123", "" + routes);                } catch (JSONException e) {                    e.printStackTrace();                }                progressDialog.dismiss();            }            progressDialog.dismiss();        }    }    public void drawPath(String result) {        if (line != null) {            googleMap.clear();        }        try {            // Tranform the string into a json object            final JSONObject json = new JSONObject(result);            JSONArray routeArray = json.getJSONArray("routes");            JSONObject routes = routeArray.getJSONObject(0);            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");            String encodedString = overviewPolylines.getString("points");            List<LatLng> list = decodePoly(encodedString);            LatLng src1 = null, destN = null;            for (int z = 0; z < list.size() - 1; z++) {                if (z == 0) {                    src1 = list.get(z);                }                LatLng src = list.get(z);                LatLng dest = list.get(z + 1);                destN = list.get(z + 1);                line = googleMap.addPolyline(new PolylineOptions()                        .add(new LatLng(src.latitude, src.longitude),                                new LatLng(dest.latitude, dest.longitude))                        .width(10).color(Color.GREEN).geodesic(true));            }            //Log.d("src1", "" + src1);            googleMap.addMarker(new MarkerOptions().position(new LatLng(src1.latitude, src1.longitude)).icon(                    BitmapDescriptorFactory.fromResource(R.drawable.pin)));            googleMap.addMarker(new MarkerOptions().position(new LatLng(destN.latitude, destN.longitude)).icon(                    BitmapDescriptorFactory.fromResource(R.drawable.pin_start)));            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(src1.latitude, src1.longitude)));            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));        } catch (Exception e) {            e.printStackTrace();        }    }    private List<LatLng> decodePoly(String encoded) {        List<LatLng> poly = new ArrayList<LatLng>();        int index = 0, len = encoded.length();        int lat = 0, lng = 0;        while (index < len) {            int b, shift = 0, result = 0;            do {                b = encoded.charAt(index++) - 63;                result |= (b & 0x1f) << shift;                shift += 5;            } while (b >= 0x20);            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));            lat += dlat;            shift = 0;            result = 0;            do {                b = encoded.charAt(index++) - 63;                result |= (b & 0x1f) << shift;                shift += 5;            } while (b >= 0x20);            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));            lng += dlng;            LatLng p = new LatLng((((double) lat / 1E5)),                    (((double) lng / 1E5)));            poly.add(p);        }        return poly;    }}