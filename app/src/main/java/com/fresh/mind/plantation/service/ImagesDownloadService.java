package com.fresh.mind.plantation.service;

import android.app.Dialog;
import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.JSONParser;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;

import com.fresh.mind.plantation.api.RetrofitCall;
import com.fresh.mind.plantation.api.RetrofitService;
import com.fresh.mind.plantation.fragment.menu_page.Tutorials;
import com.fresh.mind.plantation.model.PrimaryModel;
import com.fresh.mind.plantation.sqlite.Intercrops;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.StatusForDownload;
import com.fresh.mind.plantation.sqlite.server.AllRainFall;
import com.fresh.mind.plantation.sqlite.server.AllSoilType;
import com.fresh.mind.plantation.sqlite.server.AllTerrainType;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.Modelinfo;
import com.fresh.mind.plantation.sqlite.server.SchecmesDb;
import com.fresh.mind.plantation.sqlite.server.TreeList;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.process;
import static com.fresh.mind.plantation.Constant.Config.LOAD_MORE;
import static com.fresh.mind.plantation.Constant.Config.fileLocationOnServer;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocation;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocationIntercrops;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocationModel;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocationTreeImages;
import static com.fresh.mind.plantation.Constant.Config.url;
import static com.fresh.mind.plantation.Constant.Utils.showNotification;
import static com.fresh.mind.plantation.R.id.mRefresh;
import static com.fresh.mind.plantation.R.id.name;
import static com.fresh.mind.plantation.activity.SplashActivity.mEnglish;
import static com.fresh.mind.plantation.activity.SplashActivity.mTamil;


/**
 * Created by AND I5 on 01-04-2017.
 */

public class ImagesDownloadService extends IntentService {
    private TreeList treeList;
    private VerifyDetails verifyDetails;
    private ImageDb imageDb;
    private TreeTypeNameList treeTypeNameList;
    int totalSize = 0;
    boolean process = false;
    private Intercrops intercrops;
    private Modelinfo modelinfo;
    private AllTerrainType allTerrainType;
    private AllSoilType allSoilType;
    private AllRainFall allRainFall;
    private SchecmesDb schecmesDb;
    private RetrofitService retrofitService;
    private StatusForDownload statusForDownload;
    private String statusDownload, idDownload;

    public ImagesDownloadService() {
        super(null);
    }
    /*    private TerrainType terrainType;
        private RainfallType rainfallType;
        private TreeTypeInfo treeTypeInfo;
    private DistrictNameList districtNameList;
        private SoilType soilType;
        private ContactUs contactUs;
        private GlossaryTable glossaryTable;
        private LanguageChange languageChange;*/


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        retrofitService = RetrofitCall.getClient().create(RetrofitService.class);
        LanguageChange languageChange = new LanguageChange(getApplicationContext());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getApplicationContext());
        } else {
            Utils.setLocalLanguage("en", getApplicationContext());
        }
        treeList = new TreeList(getApplicationContext());
        intercrops = new Intercrops(getApplicationContext());
        modelinfo = new Modelinfo(getApplicationContext());
        statusForDownload = new StatusForDownload(getApplicationContext());
        treeTypeNameList = new TreeTypeNameList(getApplicationContext());
        verifyDetails = new VerifyDetails(getApplicationContext());
        imageDb = new ImageDb(getApplicationContext());
        allRainFall = new AllRainFall(getApplicationContext());
        allSoilType = new AllSoilType(getApplicationContext());
        allTerrainType = new AllTerrainType(getApplicationContext());
        schecmesDb = new SchecmesDb(getApplicationContext());


        HashMap<String, String> stringHashMap = statusForDownload.getStatus();
        statusDownload = stringHashMap.get("status");
        idDownload = stringHashMap.get("id");
/*        districtNameList = new DistrictNameList(getApplicationContext());
        soilType = new SoilType(getApplicationContext());
        rainfallType = new RainfallType(getApplicationContext());
        terrainType = new TerrainType(getApplicationContext());
        treeTypeInfo = new TreeTypeInfo(getApplicationContext());
        languageChange = new LanguageChange(getApplicationContext());
        contactUs = new ContactUs(getApplicationContext());
        glossaryTable = new GlossaryTable(getApplicationContext());*/
        if (idDownload != null) {
            if (idDownload.equals("-1")) {
            } else {
                runBackground();
            }
        } else {
            runBackground();
        }


        return START_NOT_STICKY;

    }

    private void runBackground() {
        if (checkInternetConenction(getApplicationContext())) {
            Asyn asyn = new Asyn();
            //  loadInialData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                asyn.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                asyn.execute();
            }
            ViewDescription viewDescription = new ViewDescription();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                viewDescription.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                viewDescription.execute();
            }

            //  new ViewDescription().execute();
        }
    }


    private void loadInialData() {


        callInit().enqueue(new Callback<PrimaryModel>() {
            @Override
            public void onResponse(Call<PrimaryModel> call, Response<PrimaryModel> response) {

                Log.d("responsemodel", "" + response.body());
                PrimaryModel primaryModel = response.body();
                if (primaryModel != null) {
                    Log.d("responsemodel", "111" + primaryModel.json1TreePecieas.treeSpecielModels.size());
                    Toast.makeText(getApplicationContext(), "" + primaryModel.json1TreePecieas.treeSpecielModels.size(), Toast.LENGTH_LONG).show();
                    //  mRefresh.setVisibility(View.GONE);

                    if (primaryModel.json1TreePecieas.treeSpecielModels.size() > 10) {
                        Log.d("dsdlskdls", "" + primaryModel.json1TreePecieas.treeSpecielModels.size());
                        //onInsertWithConditions(primaryModel.json1TreePecieas.treeSpecielModels, 10);
                    } else {
                        //  onInsertWithConditions(primaryModel.json1TreePecieas.treeSpecielModels, primaryModel.json1TreePecieas.treeSpecielModels.size());
                    }
                   /* mTamil.setVisibility(View.VISIBLE);
                    mEnglish.setVisibility(View.VISIBLE);*/

                } else {
                    Log.d("responsemodel", "000 ");
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_500), Toast.LENGTH_LONG).show();
                    //mRefresh.setVisibility(View.VISIBLE);
                }

                Log.d("treeList", "" + treeList.getCount());
                //dialog.dismiss();
            }

            @Override
            public void onFailure(Call<PrimaryModel> call, Throwable t) {
                Log.d("responsemodel", "Valuess ");
                // dialog.dismiss();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_500), Toast.LENGTH_LONG).show();
                //mRefresh.setVisibility(View.VISIBLE);
            }
        });
    }

    private Call<PrimaryModel> callInit() {
        return retrofitService.callCoreData("getdata");
    }

    public class ViewDescription extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("doingbackground", "viewwww");
            String result = null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            if (statusDownload == null) {
                nameValuePairs.add(new BasicNameValuePair("task", "getdata"));
            } else {
                nameValuePairs.add(new BasicNameValuePair("task", "getdata"));
                nameValuePairs.add(new BasicNameValuePair("param", statusDownload));
            }
            try {

                try {
                    result = JSONParser.getJsonResponse(url, nameValuePairs);
                    Log.d("sdsdsdsd", "re  " + result);
                } catch (OutOfMemoryError outOfMemoryError) {
                    outOfMemoryError.printStackTrace();
                }
                Log.d("resultJson6", "" + result);
                if (result == null || result.isEmpty()) {
                    LOAD_MORE = false;
                    return "server";
                } else {
                    JSONObject parentObject = null;
                    try {
                        if (Config.checkInternetConenction(ImagesDownloadService.this)) {
                            LOAD_MORE = true;
                            parentObject = new JSONObject(result);
                            Log.d("parentObject", "" + parentObject);
                            boolean isStstus = parentObject.getBoolean("status");
                            Log.d("isStstus123654789", "" + isStstus);
                            if (isStstus) {
                                JSONObject json6Object = parentObject.getJSONObject("Json6");
                                boolean statusMsg6 = json6Object.getBoolean("status");
                                String messageStus6 = json6Object.getString("message");

                                /*JSONObject json11Object = parentObject.getJSONObject("Json11");
                                boolean statusMsg11 = json11Object.getBoolean("status");
                                String messageStus11 = json11Object.getString("message");
                                if (statusMsg11) {
                                    JSONArray result1Array = json11Object.getJSONArray("result");
                                    if (schecmesDb.getCou() == result1Array.length()) {
                                    } else {
                                        for (int i = 0; i < result1Array.length(); i++) {
                                            JSONObject jsonObject = result1Array.getJSONObject(i);
                                            Log.d("jsonObject", "" + jsonObject);
                                            String sch1 = jsonObject.getString("Scheme1");
                                            String sch1Tamil = jsonObject.getString("Scheme1_Tamil");
                                            String sch2 = jsonObject.getString("Scheme2");
                                            String sch2Tamil = jsonObject.getString("Scheme2_Tamil");
                                            String sch3 = jsonObject.getString("Scheme3");
                                            String sch3Tamil = jsonObject.getString("Scheme3_Tamil");
                                            String sch4 = jsonObject.getString("Scheme4");
                                            String sch4Tamil = jsonObject.getString("Scheme4_Tamil");
                                            Log.d("32rr5445", " " + sch1 + "  " + sch1Tamil + " " + sch2 + "  " + sch2Tamil + " " + sch3 + "  " + sch3Tamil + " " + sch4 + "  " + sch4Tamil);
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("sch1", "" + sch1);
                                            contentValues.put("sch1Tamil", sch1Tamil);
                                            contentValues.put("sch2", "" + sch2);
                                            contentValues.put("sch2Tamil", "" + sch2Tamil);
                                            contentValues.put("sch3", "" + jsonObject.getString("Scheme3"));
                                            contentValues.put("sch3Tamil", "" + jsonObject.getString("Scheme3_Tamil"));
                                            contentValues.put("sch4", "" + jsonObject.getString("Scheme4"));
                                            contentValues.put("sch4Tamil", "" + jsonObject.getString("Scheme4_Tamil"));
                                            contentValues.put("LastUpdate", "" + jsonObject.getString("LastUpdate"));
                                            schecmesDb.onInsert(contentValues);
                                        }
                                    }
                                }*/

                                if (statusMsg6) {
                                    JSONArray result1Array = json6Object.getJSONArray("result");
                                    Log.d("result1Array  6", "" + result1Array.length() + "  " + verifyDetails.getCount() + "  " + verifyDetails.getCount());
                              /*  if (result1Array.length() == verifyDetails.getCount()) {
                                    Log.d("sampleee", "sampeeee");
                                } else {*/
                                    Log.d("sampleee", "Not Same");
                                    /*verifyDetails.delete();
                                    imageDb.delete();*/
                                    try {
                                        for (int i = 0; i < result1Array.length(); i++) {
                                            if (checkInternetConenction(getApplicationContext())) {
                                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                                String thumbnail = jsonObject.getString("thumbnail");

                                                if (thumbnail != null && !thumbnail.isEmpty()) {
                                                    File file = new File(sdCardLocationTreeImages + "/" + thumbnail);
                                                    if (file.exists()) {
                                                        Log.d("deded thumbnail", i + " 6 file  exist");
                                                        thumbnail = sdCardLocationTreeImages + "/" + thumbnail;

                                                        File file1 = new File(thumbnail);
                                                        int internalFileLength = (int) file1.length();
                                                        Log.d("fileLengthsFromAndroid", "" + internalFileLength + "  ");
                                                        if (internalFileLength == 0) {
                                                            String ImageDpwnLoad = fileLocationOnServer + thumbnail;
                                                            Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                            thumbnail = downloadFile(ImageDpwnLoad, sdCardLocationTreeImages);
                                                        }

                                                    } else {
                                                        Log.d("deded thumbnail", i + " 6 Not exist");
                                                        String ImageSplit = thumbnail.replaceAll(" ", "%20");
                                                        String ImageDownload = fileLocationOnServer + ImageSplit;
                                                        Log.d("ImageImage", "  " + ImageDownload);
                                                        thumbnail = downloadFile(ImageDownload, sdCardLocationTreeImages);
                                                        Log.d("storagePathSeondthumbnail", "" + thumbnail);

                                                    }
                                                }

                                                String District = jsonObject.getString("District").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                Log.d("thumbnaildsjkjdks", District + " " + thumbnail);
                                                String SoilType = jsonObject.getString("SoilType").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String RainFall = jsonObject.getString("RainFall").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String TerrainType = jsonObject.getString("TerrainType").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String TreeName = jsonObject.getString("TreeName").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String TreeType = jsonObject.getString("TreeType").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String ScientificName = jsonObject.getString("ScientificName").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String General_Info = jsonObject.getString("General_Info")/*.replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ")*/;
                                                String General_Info_Tamil = jsonObject.getString("General_Info_Tamil")/*.replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ")*/;
                                                String Habitat = jsonObject.getString("Habitat").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Habitat_Tamil = jsonObject.getString("Habitat_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Soil = jsonObject.getString("Soil").replace("\\r\\n", "<br/>").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Soil_Tamil = jsonObject.getString("Soil_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Soil_PH = jsonObject.getString("Soil_PH").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Soil_PH_Tamil = jsonObject.getString("Soil_PH_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Altitude = jsonObject.getString("Altitude").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Altitude_Tamil = jsonObject.getString("Altitude_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Rainfall = jsonObject.getString("Rainfall").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Rainfall_Tamil = jsonObject.getString("Rainfall_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Min_Temperature = jsonObject.getString("Min_Temperature").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Max_Temperature = jsonObject.getString("Max_Temperature").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Min_Temperature_Tamil = jsonObject.getString("Min_Temperature_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Max_Temperature_Tamil = jsonObject.getString("Max_Temperature_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Terrain = jsonObject.getString("Terrain").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String TerrainType_Tamil = jsonObject.getString("TerrainType_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Tree_Char = jsonObject.getString("Tree_Char")/*.replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ")*/;
                                                String Tree_Char_Tamil = jsonObject.getString("Tree_Char_Tamil")/*.replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ")*/;
                                                String Conservation = jsonObject.getString("Conservation").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Conservation_Tamil = jsonObject.getString("Conservation_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Edibility = jsonObject.getString("Edibility").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Edibility_Tamil = jsonObject.getString("Edibility_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Medicinal = jsonObject.getString("Medicinal").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Medicinal_Tamil = jsonObject.getString("Medicinal_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Other_Rating = jsonObject.getString("Other_Rating".replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF "));
                                                String Other_Rating_Tamil = jsonObject.getString("Other_Rating_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Habit = jsonObject.getString("Habit").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Habit_Tamil = jsonObject.getString("Habit_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Growth = jsonObject.getString("Growth").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Growth_Tamil = jsonObject.getString("Growth_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Height = jsonObject.getString("Height").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Height_Tamil = jsonObject.getString("Height_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Cultivation = jsonObject.getString("Cultivation").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Cultivation_Tamil = jsonObject.getString("Cultivation_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Other_Details = jsonObject.getString("Other_Details").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\t<br/>\u25CF ").replace("\uF0A7", "\t<br/>\u25CF ");
                                                String Other_Details_Tamil = jsonObject.getString("Other_Details_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Propagation = jsonObject.getString("Propagation").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Propagation_Tamil = jsonObject.getString("Propagation_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");

                                                String artificial_regeneration = jsonObject.getString("artificial_regeneration").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String artificial_regeneration_tamil = jsonObject.getString("artificial_regeneration_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String seed_collection = jsonObject.getString("seed_collection").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String seed_collection_tamil = jsonObject.getString("artificial_regeneration").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String seed_treatment = jsonObject.getString("artificial_regeneration_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String seed_treatment_tamil = jsonObject.getString("seed_collection").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String nursery_technique = jsonObject.getString("artificial_regeneration_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String nursery_technique_tamil = jsonObject.getString("seed_collection").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");

                                                String Plantation_Technique = jsonObject.getString("Plantation_Technique").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Plantation_Technique_Tamil = jsonObject.getString("Plantation_Technique_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Care_Disease = jsonObject.getString("Care_Disease").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Care_Disease_Tamil = jsonObject.getString("Care_Disease_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Irrigation = jsonObject.getString("Irrigation").replace("\\r\\n", "<br/>").replace("\\n", "<br/>\t").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Irrigation_Tamil = jsonObject.getString("Irrigation_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Yield = jsonObject.getString("Yield").replace("\\r\\n", "<br/>").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Yield_Tamil = jsonObject.getString("Yield_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Recommended_Harvest = jsonObject.getString("Recommended_Harvest").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7 ", "\u25CF ").replace("\uF0A7 ", "\u25CF ");
                                                String Recommended_Harvest_Tamil = jsonObject.getString("Recommended_Harvest_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Market_Details = jsonObject.getString("Market_Details").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Market_Details_Tamil = jsonObject.getString("Market_Details_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Intercrops = jsonObject.getString("Intercrops").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Intercrops_Tamil = jsonObject.getString("Intercrops_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Majar_Uses = jsonObject.getString("Majar_Uses").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "\r".replace("\\t", "\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Majar_Uses_Tamil = jsonObject.getString("Majar_Uses_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Other_Uses = jsonObject.getString("Other_Uses").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Other_Uses_Tamil = jsonObject.getString("Other_Uses_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Carbon_Stock = jsonObject.getString("Carbon_Stock").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Carbon_Stock_Tamil = jsonObject.getString("Carbon_Stock_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String References = jsonObject.getString("References").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String References_Tamil = jsonObject.getString("References_Tamil").replace("\\r\\n", "<br/>").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String DistrictTamil = jsonObject.getString("DistrictTamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String SoilTamil = jsonObject.getString("SoilTamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String RainFallTamil = jsonObject.getString("RainFallTamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String TerrainTypeTamil = jsonObject.getString("TerrainTypeTamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String TreeNameTamil = jsonObject.getString("TreeNameTamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String TreeTypeTamil = jsonObject.getString("TreeTypeTamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String ScientificTamil = jsonObject.getString("ScientificTamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                                String Last_Update = jsonObject.getString("LastUpdate");
                                                String common_key = jsonObject.getString("common_key");
                                                //Log.d("Irrigaasasa1245tion", "" + common_key);
                                                ContentValues contentValues = new ContentValues();
                                                contentValues.put("thumbnail", thumbnail);
                                                contentValues.put("districtName", District.trim());
                                                contentValues.put("SoilType", SoilType.trim());
                                                contentValues.put("RainFallFirst", RainFall.trim());
                                                contentValues.put("TerrainType", TerrainType.trim());
                                                contentValues.put("TreeName", TreeName.trim());
                                                contentValues.put("TreeType", TreeType.trim());
                                                contentValues.put("ScientificName", ScientificName.trim());
                                                contentValues.put("General_Info", General_Info.trim());
                                                contentValues.put("General_Info_Tamil", General_Info_Tamil.trim());
                                                contentValues.put("Habitat", Habitat.trim());
                                                contentValues.put("Habitat_Tamil", Habitat_Tamil.trim());
                                                contentValues.put("Soil", Soil);
                                                contentValues.put("common_key", common_key);
                                                contentValues.put("lastUpdate", Last_Update);
                                                contentValues.put("Soil_Tamil", Soil_Tamil.trim());
                                                contentValues.put("Soil_PH", Soil_PH.trim());
                                                contentValues.put("Soil_PH_Tamil", Soil_PH_Tamil.trim());
                                                contentValues.put("Altitude", Altitude.trim());
                                                contentValues.put("Altitude_Tamil", Altitude_Tamil.trim());
                                                contentValues.put("Rainfall", Rainfall.trim());
                                                contentValues.put("Rainfall_Tamil", Rainfall_Tamil.trim());
                                                contentValues.put("Min_Temperature", Min_Temperature);
                                                contentValues.put("Max_Temperature", Max_Temperature);
                                                contentValues.put("Min_Temperature_Tamil", Min_Temperature_Tamil);
                                                contentValues.put("Max_Temperature_Tamil", Max_Temperature_Tamil);
                                                contentValues.put("Terrain", Terrain);
                                                contentValues.put("TerrainType_Tamil", TerrainType_Tamil);
                                                contentValues.put("Tree_Char", Tree_Char);
                                                contentValues.put("Tree_Char_Tamil", Tree_Char_Tamil);
                                                contentValues.put("Conservation", Conservation);
                                                contentValues.put("Conservation_Tamil", Conservation_Tamil);
                                                contentValues.put("Edibility", Edibility);
                                                contentValues.put("Edibility_Tamil", Edibility_Tamil);
                                                contentValues.put("Medicinal", Medicinal);
                                                contentValues.put("Medicinal_Tamil", Medicinal_Tamil);
                                                contentValues.put("Other_Rating", Other_Rating);
                                                contentValues.put("Other_Rating_Tamil", Other_Rating_Tamil);
                                                contentValues.put("Habit", Habit);
                                                contentValues.put("Habit_Tamil", Habit_Tamil);
                                                contentValues.put("Growth", Growth);
                                                contentValues.put("Growth_Tamil", Growth_Tamil);
                                                contentValues.put("Height", Height);
                                                contentValues.put("Height_Tamil", Height_Tamil);
                                                contentValues.put("Cultivation", Cultivation);
                                                contentValues.put("Cultivation_Tamil", Cultivation_Tamil);
                                                contentValues.put("Other_Details", Other_Details);
                                                contentValues.put("Other_Details_Tamil", Other_Details_Tamil);
                                                contentValues.put("Propagation", Propagation);
                                                contentValues.put("Plantation_Technique", Plantation_Technique);
                                                contentValues.put("Plantation_Technique_Tamil", Plantation_Technique_Tamil);
                                                contentValues.put("Care_Disease", Care_Disease);
                                                contentValues.put("Care_Disease_Tamil", Care_Disease_Tamil);
                                                contentValues.put("Irrigation", Irrigation);
                                                contentValues.put("Irrigation_Tamil", Irrigation_Tamil);
                                                contentValues.put("Yield", Yield);
                                                contentValues.put("Yield_Tamil", Yield_Tamil);
                                                contentValues.put("Recommended_Harvest", Recommended_Harvest);
                                                contentValues.put("Recommended_Harvest_Tamil", Recommended_Harvest_Tamil);
                                                contentValues.put("Market_Details", Market_Details);
                                                contentValues.put("Market_Details_Tamil", Market_Details_Tamil);
                                                contentValues.put("Intercrops", Intercrops);
                                                contentValues.put("Intercrops_Tamil", Intercrops_Tamil);
                                                contentValues.put("Majar_Uses", Majar_Uses);
                                                contentValues.put("Majar_Uses_Tamil", Majar_Uses_Tamil);
                                                contentValues.put("Other_Uses", Other_Uses);
                                                contentValues.put("Other_Uses_Tamil", Other_Uses_Tamil);
                                                contentValues.put("Carbon_Stock", Carbon_Stock);
                                                contentValues.put("Carbon_Stock_Tamil", Carbon_Stock_Tamil);
                                                contentValues.put("Reference", References);
                                                contentValues.put("References_Tamil", References_Tamil.trim());
                                                contentValues.put("districtNameTamil", DistrictTamil.trim());
                                                contentValues.put("SoilTamil", SoilTamil.trim());
                                                contentValues.put("RainFallTamil", RainFallTamil.trim());
                                                contentValues.put("TerrainTypeTamil", TerrainTypeTamil.trim());
                                                contentValues.put("TreeNameTamil", TreeNameTamil.trim());
                                                contentValues.put("TreeTypeTamil", TreeTypeTamil.trim());
                                                contentValues.put("ScientificTamil", ScientificTamil.trim());
                                                contentValues.put("Propagation_Tamil", "" + Propagation_Tamil.trim());

                                                contentValues.put("artificial_regeneration", artificial_regeneration);
                                                contentValues.put("artificial_regeneration_tamil", artificial_regeneration_tamil);
                                                contentValues.put("seed_collection", seed_collection);
                                                contentValues.put("seed_collection_tamil", seed_collection_tamil);
                                                contentValues.put("seed_treatment", seed_treatment);
                                                contentValues.put("seed_treatment_tamil", seed_treatment_tamil);
                                                contentValues.put("nursery_technique", nursery_technique);
                                                contentValues.put("nursery_technique_tamil", nursery_technique_tamil);

                                                //   Log.d("contentValues how", "" + contentValues.size());
                                                JSONObject ImagesObj = jsonObject.getJSONObject("Images");
                                                boolean statusImg = ImagesObj.getBoolean("status");
                                                String storagePath = null;
                                                if (statusImg) {
                                                    JSONArray Images = ImagesObj.getJSONArray("result");
                                                    for (int arr = 0; arr < Images.length(); arr++) {
                                                        JSONObject jsonObject1 = Images.getJSONObject(arr);
                                                        String Image = jsonObject1.getString("Image");
                                                        String extension = Image.substring(Image.lastIndexOf("."));
                                                        // Log.d("ImageFromImageDownLoad", i + "   " + Image + "  " + TreeName);
                                                        if (Image.equals("null") || Image.isEmpty()) {
                                                            imageDb.onInsert(TreeName, common_key, TreeNameTamil, storagePath, thumbnail);
                                                        } else {
                                                            extension = Image.substring(Image.lastIndexOf("."));
                                                            // Log.d("extensionFormImage", i + "  " + extension);
                                                            //if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                                            try {
                                                                File file = new File(sdCardLocationTreeImages + "/" + Image);
                                                                if (file.exists()) {
                                                                    //  Log.d("deded 2 ImageService", i + " 6 file  exist");
                                                                    storagePath = sdCardLocationTreeImages + "/" + Image;
                                                                    File file1 = new File(storagePath);
                                                                    int internalFileLength = (int) file1.length();
                                                                    if (internalFileLength == 0) {
                                                                        String ImageDpwnLoad = fileLocationOnServer + Image;
                                                                        //Log.d("ImageImageImageServiceIfExist", TreeName + " " + ImageDpwnLoad);
                                                                        storagePath = downloadFile(ImageDpwnLoad, sdCardLocationTreeImages);
                                                                    }

                                                                } else {
                                                                    // Log.d("deded 2 ImageService", i + " 6 Not exist");
                                                                    String ImageSplit = Image.replaceAll(" ", "%20");
                                                                    String ImageDownload = fileLocationOnServer + ImageSplit;
                                                                    // Log.d("ImageImageImageService", arr + "  " + ImageDownload);
                                                                    storagePath = downloadFile(ImageDownload, sdCardLocationTreeImages);
                                                                    //   Log.d("storagePathSeondImageService", TreeName + "  " + storagePath);

                                                                }
                                                                imageDb.onInsert(TreeName, common_key, TreeNameTamil, storagePath, thumbnail);
                                                            } catch (Exception | OutOfMemoryError e) {
                                                                e.printStackTrace();
                                                                return "return";
                                                            }
                                                            //  }
                                                        }
                                                    }


                                                }
                                                // Log.d("extensionIMageServiceSubbu", TreeName + "  " + i + "   " + storagePath);
                                                contentValues.put("storagePath", "" + storagePath);
                                                verifyDetails.onInsert(contentValues);

                                            }
                                        }


                                    } catch (Exception | OutOfMemoryError e) {
                                        e.printStackTrace();
                                        return "return";
                                    }
                                    // }
                                }


                                JSONObject json15Object = parentObject.getJSONObject("Json15");
                                boolean statusMsg15 = json15Object.getBoolean("status");
                                String messageStus15 = json15Object.getString("message");
                                if (statusMsg15) {
                                    JSONArray result1Array = json15Object.getJSONArray("result");
                      /*  if (result1Array.length() == treeTypeNameList.getCout()) {
                        } else {*/
//                            Log.d("result1Arraytresss", +treeList.getCount() + "  " + result1Array);
                                    for (int i = 0; i < result1Array.length(); i++) {
                                        if (Config.checkInternetConenction(getApplicationContext())) {
                                            JSONObject jsonObject = result1Array.getJSONObject(i);
                                            String treeType = jsonObject.getString("Description").trim();
                                            String Last_Update = jsonObject.getString("LastUpdate");
                                            String TreeTypeTamil = jsonObject.getString("Description_Tamil").trim();
                                            String Image = jsonObject.getString("Image").trim();

                                            String extension = Image.substring(Image.lastIndexOf("."));
                                            ContentValues contentValues = new ContentValues();
                                            byte[] imgByte = new byte[0];
                                            String storagePath = null;
                                            if (Image.equals("null") || Image.isEmpty()) {
                                                //storagePath = sdCardLocationTreeType + "/" + Image;
                                                contentValues.put("storagePath", "" + storagePath);
                                                contentValues.put("Description", treeType);
                                                contentValues.put("lastUpdate", Last_Update);
                                                contentValues.put("Description_Tamil", TreeTypeTamil);
                                                modelinfo.onInsert(contentValues);
                                            } else {
                                                extension = Image.substring(Image.lastIndexOf("."));
                                                //if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                                try {
                                                    File file = new File(sdCardLocationModel + "/" + Image);
                                                    if (file.exists()) {
                                                        Log.d("deded 2", i + " exist");
                                                        storagePath = sdCardLocationModel + "/" + Image;
                                                        File file1 = new File(storagePath);
                                                        int internalFileLength = (int) file1.length();
                                                        Log.d("fileLengthsFromAndroid", "" + internalFileLength + "  ");
                                                        if (internalFileLength == 0) {
                                                            String ImageDpwnLoad = fileLocationOnServer + Image;
                                                            Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                            storagePath = downloadFile(ImageDpwnLoad, sdCardLocationModel);
                                                        }
                                                    } else {
                                                        Log.d("deded 2", i + " Not exist");
                                                        String ImageSpilt = Image.replaceAll(" ", "%20");
                                                        String ImageDownload = fileLocationOnServer + ImageSpilt;
                                                        Log.d("ImageImage TreeType", " " + Image + "  " + ImageDownload);
                                                        storagePath = downloadFile(ImageDownload, sdCardLocationModel);
                                                        Log.d("storagePathSeond", "" + storagePath);

                                                    }
                                                    Log.d("storagePath", "" + storagePath);
                                                    //contentValues.put("treeIcon", imgByte);
                                                    contentValues.put("storagePath", "" + storagePath);
                                                    contentValues.put("Description", treeType);
                                                    contentValues.put("lastUpdate", Last_Update);
                                                    contentValues.put("Description_Tamil", TreeTypeTamil);

                                                    modelinfo.onInsert(contentValues);
                                                } catch (Resources.NotFoundException | OutOfMemoryError e) {
                                                    Image = jsonObject.getString("Image");
                                                    Log.d("dassssds", "" + Image);
                                                    // return "return";
                                                }

                                            }
                                        }
                                    }
                                }

                                JSONObject json16Object = parentObject.getJSONObject("Json16");
                                boolean statusMsg16 = json16Object.getBoolean("status");
                                String messageStus16 = json16Object.getString("message");
                                if (statusMsg16) {
                                    JSONArray result1Array = json16Object.getJSONArray("result");
                      /*  if (result1Array.length() == treeTypeNameList.getCout()) {
                        } else {*/
//                            Log.d("result1Arraytresss", +treeList.getCount() + "  " + result1Array);
                                    for (int i = 0; i < result1Array.length(); i++) {
                                        if (Config.checkInternetConenction(getApplication())) {
                                            JSONObject jsonObject = result1Array.getJSONObject(i);
                                            String treeType = jsonObject.getString("Description").trim();
                                            String Last_Update = jsonObject.getString("LastUpdate");
                                            String TreeTypeTamil = jsonObject.getString("Description_Tamil").trim();
                                            String Image = jsonObject.getString("Image").trim();

                                            String extension = Image.substring(Image.lastIndexOf("."));
                                            ContentValues contentValues = new ContentValues();
                                            byte[] imgByte = new byte[0];
                                            String storagePath = null;
                                            if (Image.equals("null") || Image.isEmpty()) {
                                                //storagePath = sdCardLocationTreeType + "/" + Image;
                                                contentValues.put("storagePath", "" + storagePath);
                                                contentValues.put("Description", treeType);
                                                contentValues.put("lastUpdate", Last_Update);
                                                contentValues.put("Description_Tamil", TreeTypeTamil);
                                                intercrops.onInsert(contentValues);
                                            } else {
                                                extension = Image.substring(Image.lastIndexOf("."));
                                                //if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                                try {
                                                    File file = new File(sdCardLocationIntercrops + "/" + Image);
                                                    if (file.exists()) {
                                                        Log.d("deded 2", i + " exist");
                                                        storagePath = sdCardLocationIntercrops + "/" + Image;
                                                        File file1 = new File(storagePath);
                                                        int internalFileLength = (int) file1.length();
                                                        Log.d("fileLengthsFromAndroid", "" + internalFileLength + "  ");
                                                        if (internalFileLength == 0) {
                                                            String ImageDpwnLoad = fileLocationOnServer + Image;
                                                            Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                            storagePath = downloadFile(ImageDpwnLoad, sdCardLocationIntercrops);
                                                        }
                                                    } else {
                                                        Log.d("deded 2", i + " Not exist");
                                                        String ImageSpilt = Image.replaceAll(" ", "%20");
                                                        String ImageDownload = fileLocationOnServer + ImageSpilt;
                                                        Log.d("ImageImage TreeType", " " + Image + "  " + ImageDownload);
                                                        storagePath = downloadFile(ImageDownload, sdCardLocationIntercrops);
                                                        Log.d("storagePathSeond", "" + storagePath);

                                                    }
                                                    Log.d("storagePath", "" + storagePath);
                                                    //contentValues.put("treeIcon", imgByte);
                                                    contentValues.put("storagePath", "" + storagePath);
                                                    contentValues.put("Description", treeType);
                                                    contentValues.put("lastUpdate", Last_Update);
                                                    contentValues.put("Description_Tamil", TreeTypeTamil);

                                                    intercrops.onInsert(contentValues);
                                                } catch (Resources.NotFoundException | OutOfMemoryError e) {
                                                    Image = jsonObject.getString("Image");
                                                    Log.d("dassssdsinter", "" + Image);
                                                    // return "return";
                                                } catch (IOException e) {


                                                }

                                            }
                                        }
                                    }
                                    return "success";
                                }
                            } else {
                                return "false";
                            }
                        }
                    } catch (IllegalStateException | OutOfMemoryError e) {
                        return "server";
                    } catch (JSONException | RuntimeException e) {
                        e.printStackTrace();
                        return "server";
                    }
                }
            } catch (ClientProtocolException | OutOfMemoryError e) {
                e.printStackTrace();
                return "hostname";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "hostname";
            } catch (IOException e) {
                e.printStackTrace();
                return "hostname";
            } finally {
                //urlConnection.disconnect();
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ldkjsksld", "" + s);
            Log.d("statusDownloadService", " " + statusDownload + "  " + idDownload);
            if (s.equals("success")) {
                Log.d("sljdsdlk", "UpdateDb");
                if (idDownload == null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("status", "1");
                    statusForDownload.onInsert(contentValues);
                } else {
                    int status = Integer.parseInt(statusDownload) + 1;
                    statusForDownload.onUpdate(idDownload, status);
                }
            } else {
                statusForDownload.onUpdate(idDownload, -1);
            }

            HashMap<String, String> stringHashMap = statusForDownload.getStatus();
            statusDownload = stringHashMap.get("status");
            idDownload = stringHashMap.get("id");
            Log.d("soadjslkdjs", "" + idDownload + "  " + statusDownload);
            if (statusDownload.equals("-1")) {

            } else {
                runBackground();
            }
        }
    }

    public class Asyn extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("doingbackground", "Synctask");
            String result = null;


            try {

                Log.d("statusDownloadService", "ioio" + statusDownload + "  " + idDownload);
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                if (statusDownload == null) {
                    nameValuePairs.add(new BasicNameValuePair("task", "getdata"));
                } else {
                    nameValuePairs.add(new BasicNameValuePair("task", "getdata"));
                    nameValuePairs.add(new BasicNameValuePair("param", statusDownload));
                }
                /*HttpPost httpPost = new HttpPost(url);
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
                HttpConnectionParams.setSoTimeout(httpParameters, 10000 + 12000);
                DefaultHttpClient client = new DefaultHttpClient();


                httpPost.setParams(httpParameters);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        //if (isCancelled()) break;
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError outOfMemoryError) {
                    outOfMemoryError.printStackTrace();
                }
                result = sb.toString();*/
                result = JSONParser.getJsonResponse(url, nameValuePairs);

                Log.d("resultJson1", "" + result);
                if (result == null || result.isEmpty() || result.contains("SHTML Wrapper - 500 Server Error")) {
                    LOAD_MORE = false;
                    return "server";
                } else {
                    JSONObject parentObject = null;
                    try {

                        LOAD_MORE = true;
                        try {
                            parentObject = new JSONObject(result);
                        } catch (OutOfMemoryError e) {
                            e.printStackTrace();
                        }
                        JSONObject json1Object = parentObject.getJSONObject("Json1");
                        boolean status = json1Object.getBoolean("status");
                        String message = json1Object.getString("message");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            Log.d("result1ArrayService 1", +treeList.getCount() + " " + result1Array.length());
                            /*if (result1Array.length() == treeList.getCount()) {
                                Log.d("sampleee  111", " Same");
                            } else {*/
                            Log.d("sampleeeService1111", "Not Same");
                            //treeList.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                if (checkInternetConenction(getApplicationContext())) {
                                    // Log.d("sadsadddsadsdsad Forasyn", "true  " + checkInternetConenction(getApplicationContext()));
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String TreeName = jsonObject.getString("TreeName").trim();
                                    String TreeType = jsonObject.getString("TreeType").trim();
                                    String Scientific_Name = jsonObject.getString("ScientificName").trim();
                                    String District = jsonObject.getString("District").trim();
                                    String common_key = jsonObject.getString("common_key");

                                    String Image = jsonObject.getString("thumbnail");
                                    //String Image = jsonObject.getString("Images");

                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    //   Log.d("Last_U2342334ast_Update From Async", "" + Last_Update);
                                    String DistrictTamil = jsonObject.getString("DistrictTamil").trim();
                                    String TreeNameTamil = jsonObject.getString("TreeNameTamil").trim();
                                    String TreeTypeTamil = jsonObject.getString("TreeTypeTamil").trim();
                                    String Scientific_Tamil = jsonObject.getString("ScientificTamil").trim();

                                    String extension = null;
                                    String storagePath = null;
                                    ContentValues contentValues = new ContentValues();
                                    //byte[] imgByte = new byte[0];
                                    Log.d("ImageFromServieImagesService", i + "  " + Image + " " + TreeName);
                                    if (TreeName.equals("null") || TreeName.isEmpty()) {
                                    } else {
                                        try {
                                            if (Image.equals("null") || Image.isEmpty()) {
                                                //contentValues.put("storagePath", "" + storagePath);
                                                contentValues.put("treeType", TreeType);
                                                contentValues.put("districtName", "" + District);
                                                contentValues.put("treeNameTamil", Scientific_Name);
                                                contentValues.put("treeNameEng", TreeName);
                                                contentValues.put("lastUpdate", Last_Update);
                                                contentValues.put("Scientific_Tamil", Scientific_Tamil);
                                                contentValues.put("treeTypeTamil", TreeTypeTamil);
                                                contentValues.put("treeNameEngTamil", TreeNameTamil);
                                                contentValues.put("districtNameTamil", "" + DistrictTamil);
                                                contentValues.put("districtNameTamil", "" + DistrictTamil);
                                                contentValues.put("common_key", "" + common_key);
                                                treeList.onInsertTreeType(contentValues);
                                                 /*   Intent broadcastIntent = new Intent();
                                                    broadcastIntent.setAction("refresh");
                                                    //Log.d("mListHash", "" + treeList.getTreTypeeNames(AppData.checkLanguage(getApplicationContext())));
                                                    broadcastIntent.putExtra("Data", treeList.getTreTypeeNames(AppData.checkLanguage(getApplicationContext())));
                                                    sendBroadcast(broadcastIntent);*/
                                            } else {
                                                extension = Image.substring(Image.lastIndexOf("."));
                                                //Log.d("extension", "" + extension);
                                                //  if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                                File file = new File(sdCardLocation + "/" + Image);
                                                if (file.exists()) {
                                                    Log.d("dededFewService", i + " exist");
                                                    storagePath = sdCardLocation + "/" + Image;
                                                    File file1 = new File(storagePath);
                                                    int internalFileLength = (int) file1.length();
                                                    if (internalFileLength == 0) {
                                                        String ImageDpwnLoad = fileLocationOnServer + Image;
                                                        Log.d("ImageImage Zeros", TreeName + " " + ImageDpwnLoad);
                                                        storagePath = downloadFile(ImageDpwnLoad, sdCardLocation);
                                                    }
                                                } else {
                                                    Log.d("dededFewService", i + " Not exist");
                                                    //String ImageSpilt = Image.replaceAll(" ", "%20");
                                                    String ImageDpwnLoad = fileLocationOnServer + Image;
                                                    Log.d("ImageImageServiceTreeType", " " + ImageDpwnLoad);
                                                    storagePath = downloadFile(ImageDpwnLoad, sdCardLocation);
                                                }
                                                Log.d("storagePathService", TreeName + " " + storagePath);
                                                contentValues.put("storagePath", "" + storagePath);
                                                contentValues.put("treeType", TreeType);
                                                contentValues.put("districtName", "" + District);
                                                contentValues.put("treeNameTamil", Scientific_Name);
                                                contentValues.put("treeNameEng", TreeName);
                                                contentValues.put("lastUpdate", Last_Update);
                                                contentValues.put("Scientific_Tamil", Scientific_Tamil);
                                                contentValues.put("treeTypeTamil", TreeTypeTamil);
                                                contentValues.put("treeNameEngTamil", TreeNameTamil);
                                                contentValues.put("districtNameTamil", "" + DistrictTamil);
                                                contentValues.put("common_key", "" + common_key);
                                                treeList.onInsertTreeType(contentValues);

/*
                                                    Intent broadcastIntent = new Intent();
                                                    broadcastIntent.setAction("refresh");
                                                    //   Log.d("mListHash", "" + treeList.getTreTypeeNames(AppData.checkLanguage(getApplicationContext())));
                                                    broadcastIntent.putExtra("Data", treeList.getTreTypeeNames(AppData.checkLanguage(getApplicationContext())));
                                                    sendBroadcast(broadcastIntent);*/
                                            }
                                            //  }

                                        } catch (Exception ex) {
                                            Log.d("whtaComes", "Here");
                                            ex.printStackTrace();
                                            return "return";
                                        }
                                    }

                                } else {
                                    Log.d("sadsadddsadsdsadService", "false " + checkInternetConenction(getApplicationContext()));
                                }
                            }
                            //}
                        }

                    } catch (JSONException | RuntimeException e) {
                        e.printStackTrace();
                        return "parsing";
                    }
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return "hostname";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "hostname";
            } catch (IOException e) {
                e.printStackTrace();
                return "hostname";
            } finally {
                //urlConnection.disconnect();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("sampledds", "" + s);
            if (s.equals("success")) {
                Config.LOAD_MORE = false;
                showNotification(getString(R.string.bgm_success), getString(R.string.bgm_success), getApplicationContext());
            } else if (s.equals("server")) {
                showNotification(getString(R.string.bgm_failed), getString(R.string.server_500), getApplicationContext());
               /* if (treeList.getCount() >= 1 && verifyDetails.getCount() >= 1) {

                } else {

                }*/
            } else if (s.equals("parsing")) {
                showNotification(getString(R.string.bgm_failed), getString(R.string.download_error), getApplicationContext());
            } else if (s.equals("hostname")) {
                showNotification(getString(R.string.bgm_failed), getString(R.string.server_not_found), getApplicationContext());
            } else if (s.equals("return")) {
                //  showNotification(getString(R.string.bgm_failed), getString(R.string.server_not_found), getApplicationContext());
            }
        }
    }

    private String downloadFile(String filePath, String sdCardPath) throws IOException {
        File dir = new File(sdCardPath);
        File[] mp3List = dir.listFiles();
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
        if (mp3List != null) {
            for (File fileFilter : mp3List) {
                String name = fileFilter.getName();
                //  Log.d("s87hj", "" + name);
                if (name.equals(fileName)) {
                    Log.d("AlreadyFileInThere", "File Exist  " + name);
                } else {
                    Log.d("AlreadyFileInThere", "No File Exist  " + name);
                    String fileReturn = getFile(fileName, filePath, sdCardPath);
                    Log.d("fileReturn1", "" + fileReturn);
                    return fileReturn;
                }
            }
        } else {
            Log.d("AlreadyFileInThere", "No File Exist  else");
            String fileReturn = getFile(fileName, filePath, sdCardPath);
            Log.d("fileReturn", "" + fileReturn);
            return fileReturn;
        }
        return null;
    }


    private String getFile(String fileName, String filesPath, String sdCardPath) throws IOException {
        File file = null;
        String filePathName = null;
        HttpURLConnection urlConnection = null;
        FileOutputStream fileOutput = null;
        try {
            if (checkInternetConenction(getApplicationContext())) {
                Log.d("nameforDownladinPurpose", "" + fileName + " " + filesPath + "  " + sdCardPath);
                URL url = new URL(filesPath);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                File wallpaperDirectory = new File(sdCardPath);
                wallpaperDirectory.mkdirs();
                file = new File(wallpaperDirectory, fileName);
                Log.d("sadsadd", "file  " + file);
                fileOutput = new FileOutputStream(file);
                InputStream inputStream = urlConnection.getInputStream();
                totalSize = urlConnection.getContentLength();
                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.flush();
                fileOutput.close();
                inputStream.close();
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.toString()}, null, null);
                if (file != null) {
                    filePathName = file.toString();
                }
            } else {
                File file1 = new File(sdCardPath + "/" + fileName);
                file1.delete();
            }
        } catch (MalformedURLException e) {
            Tutorials.showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {

            Tutorials.showError("Error : IOException " + e);
            e.printStackTrace();
        } catch (RuntimeException e) {
            Log.d("Exception", "" + e);
        } catch (EOFException e) {

        } finally {
            urlConnection.disconnect();
        }
        return filePathName;
    }

    public static boolean checkInternetConenction(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

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
