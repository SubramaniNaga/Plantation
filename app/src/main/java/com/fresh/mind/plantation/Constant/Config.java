package com.fresh.mind.plantation.Constant;

import android.net.ConnectivityManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.fresh.mind.plantation.sqlite.LanguageChange;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.fresh.mind.plantation.R.string.Feedback;

/**
 * Created by AND I5 on 30-01-2017.
 */

public class Config {

    public static String url = "http://plantation.kambaa.com/webservices/plantation.php";
    public static String urlVideo = "http://plantation.kambaa.com/webservices/plantation1.php";
    public static String urlFeedback="http://plantation.kambaa.com/webservices/plantation.php";
    public static int SPLASH_LAGUAGE = -1;
    public static int LANGUAGE_UPDATE_STATUS = 1;
    public static int SERVICE = 0;
    public static String TREE_NAME = "";
    public static String SELECTED_TREE_TYPE;
    public static String SELECTED_DISTRICT_NAME;
    public static int SELECTED_ID;
    public static String SEARCH_STR_VALUES = "";
    public static int POSITION = -1;
    public static String PHOTO_ALBUM = "androidhive", main = "";
    public static int celsiu = -1;
    public static int updateCounting = 0;

    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg", "png");

    public static ArrayList<HashMap<String, byte[]>> mImages = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> mImagePaths = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> mLocation = new ArrayList<>();

    public static ArrayList<HashMap<String, String>> mSELECTED_TREE_NAME = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> mTreeImagePath = new ArrayList<>();


    public static ArrayList<HashMap<String, byte[]>> mImagesFromDescription = new ArrayList<>();

    public static ArrayList<HashMap<String, byte[]>> mSELECTED_TREETYPE_IMAGES = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> mSELECTED_TREETYPE_NAMES = new ArrayList<>();

    public static ArrayList<HashMap<String, String>> mTREE_TYPES = new ArrayList<>();
    public static ArrayList<HashMap<String, byte[]>> mTREE_TYPES_IMAGES = new ArrayList<>();

    public static ArrayList<HashMap<String, byte[]>> mVIDEO_LIST = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> mVIDEO_PATH = new ArrayList<>();
    public static String SOIL_TYPE, TREE_TYPE, DISTRICT_NAME, RAIN_FALL, TERRAIN_TYPE;
    public static int SELECTED_DIS = -1;
    public static int SELECTED_SOIL = -1;
    public static int SELECTED_RAIN_FALL = -1;
    public static int SELECTED_TERRAIN = -1;
    public static int SELECTE_TREE_TYPE = -1;
    public static int SELECTED_TAB_VIEW_POSITION = 0;
    public static String pass = "splash";


    public static boolean checkInternetConenction(FragmentActivity getActivity) {
        ConnectivityManager connec = (ConnectivityManager) getActivity.getSystemService(getActivity.CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            // Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            /*Toast.makeText(getActivity(), " Not Connected ", Toast.LENGTH_LONG).show();*/
            return false;
        }
        return false;
    }


}
