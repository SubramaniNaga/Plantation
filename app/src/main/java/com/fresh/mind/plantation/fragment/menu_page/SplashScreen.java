package com.fresh.mind.plantation.fragment.menu_page;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Weathers;
import com.fresh.mind.plantation.service.UpdateData;
import com.fresh.mind.plantation.service.VideoDownloadService;
import com.fresh.mind.plantation.sqlite.not_need.DistrictList;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.not_need.TreeNames;
import com.fresh.mind.plantation.sqlite.not_need.TreeTypeDatabase;
import com.fresh.mind.plantation.sqlite.not_need.VerifiedDatabase;
import com.fresh.mind.plantation.sqlite.server.ContactUs;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.server.GlossaryTable;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.RainfallType;
import com.fresh.mind.plantation.sqlite.server.SoilType;
import com.fresh.mind.plantation.sqlite.server.TerrainType;

import com.fresh.mind.plantation.sqlite.server.TreeList;
import com.fresh.mind.plantation.sqlite.server.TreeTypeInfo;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;
import com.fresh.mind.plantation.tab_pager.HomeTabView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import java.util.Locale;

import static com.fresh.mind.plantation.Constant.Config.SPLASH_LAGUAGE;
import static com.fresh.mind.plantation.Constant.Config.url;
import static com.fresh.mind.plantation.R.string.treeType;

/**
 * Created by AND I5 on 30-01-2017.
 */

public class SplashScreen extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View rootView;
    private DistrictList districtList;
    private TreeTypeDatabase treeTypeDatabase;
    private TreeNames treeNames;
    private VerifiedDatabase verifiedDatabase;
    private String[] list;
    private Animation slide_up;
    private TreeList treeList;
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
    int totalSize = 0;
    private CustomTextView mTamil, mEnglish, mLoaderTxt;
    private String sdCardLocation = "sdcard/Treepedia/TreeNameByImage";
    private String sdCardLocationTreeType = "sdcard/Treepedia/TreeType";
    private String sdCardLocationTreeImages = "sdcard/Treepedia/TreeImages";
    private int celsiu = -1;
    private Asyn loaderTask;


    @Override
    public void onDestroy() {
        super.onDestroy();
        treeList.close();
        districtNameList.close();
        treeTypeNameList.close();
        verifyDetails.close();
        soilType.close();
        rainfallType.close();
        terrainType.close();
        treeTypeInfo.close();
        imageDb.close();
        languageChange.close();
        contactUs.close();
        glossaryTable.close();
        Log.d("CloseSplashScreen", "Objects");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d("voeewasdas", "onCreateView");
        ((MainActivity) getActivity()).setSplashScreen();

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


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
/*

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
*/

        int count = languageChange.getCount();
        if (count == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("languages", "0");
            languageChange.onInsert(contentValues);
            setLocalLanguage("ta");
        }
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            setLocalLanguage("ta");
        } else {
            setLocalLanguage("en");
        }
        rootView = inflater.inflate(R.layout.splash, null);
        imageView8 = (ImageView) rootView.findViewById(R.id.imageView8);
        mTamil = (CustomTextView) rootView.findViewById(R.id.tamilBtn);
        mEnglish = (CustomTextView) rootView.findViewById(R.id.button);
        mLoaderTxt = (CustomTextView) rootView.findViewById(R.id.mLoaderTxt);
        mLoaderTxt.setVisibility(View.GONE);
        mTamil.setVisibility(View.GONE);
        mEnglish.setVisibility(View.GONE);
        File file = new File("sdcard/Treepedia/.nomedia");
        if (file.exists()) {
            Log.d("fileCreated", "Exist");
        } else {
            Log.d("fileCreated", "NOt Exist");
            File wallpaperDirectory = new File("sdcard/Treepedia/");
            wallpaperDirectory.mkdirs();
            File files = new File(wallpaperDirectory, ".nomedia");
            try {
                files.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(files, true));
                writer.write("This is a test trace file.");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{files.toString()},
                    null,
                    null);
        }

        if (languages.equals("1")) {
            imageView8.setImageResource(R.drawable.splash_tamnil_eng);
        } else {
            imageView8.setImageResource(R.drawable.splash_tamnil_eng);
        }
        mEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Wait", Toast.LENGTH_SHORT).show();
                selectLanguage("en");
            }

        });
        mTamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Wait", Toast.LENGTH_SHORT).show();
                selectLanguage("ta");
            }
        });
        mLoaderTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Asyn().execute();
            }
        });
        return rootView;
    }

    private void selectLanguage(String language) {
        int count = languageChange.getCount();
        if (count == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("languages", "1");
            languageChange.onInsert(contentValues);
            HashMap<String, String> languageStatus = languageChange.getLanguageStatus();
            String status = languageStatus.get("languages");
            String id = languageStatus.get("id");
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            HashMap<String, String> languageStatus = languageChange.getLanguageStatus();
            String status = languageStatus.get("languages");
            String id = languageStatus.get("id");
            if (language.equals("en")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("languages", "2");
                languageChange.update("2", Integer.parseInt(id));
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("languages", "1");
                languageChange.update("1", Integer.parseInt(id));
            }
        }
        //   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        SPLASH_LAGUAGE = 0;
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    private void setLocalLanguage(String language) {
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    private void loading() throws IOException {
        Log.d("dsaljhk", "1");

        downloadVideos();

        if (Config.SPLASH_LAGUAGE == 0) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else {
            if (Config.pass.equals("splash")) {
                districtList = new DistrictList(getActivity());
                treeTypeDatabase = new TreeTypeDatabase(getActivity());
                treeNames = new TreeNames(getActivity());
                verifiedDatabase = new VerifiedDatabase(getActivity());
                list = AppData.DISTRICTS_NAME;
                //Dialog dialog = null;
                if (!Config.checkInternetConenction(getActivity())) {
                    Log.d("asaasasas", "NOeee");
                    final Dialog dialog = new Dialog(getActivity());
                    //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    // dialog.getWindow().setWindowAnimations(R.anim.slide_up_animation);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.network);
                    slide_up = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                    final RelativeLayout dialogAnimation = (RelativeLayout) dialog.findViewById(R.id.dialogAnimation);
                    dialogAnimation.startAnimation(slide_up);
                    CustomTextView done, cancel, enable;
                    done = (CustomTextView) dialog.findViewById(R.id.textView16);
                    cancel = (CustomTextView) dialog.findViewById(R.id.textView15);
                    enable = (CustomTextView) dialog.findViewById(R.id.enable);
                    final Dialog finalDialog = dialog;
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            slide_up = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
                            dialogAnimation.startAnimation(slide_up);
                            finalDialog.dismiss();
                            if (terrainType.getCount() >= 1) {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                            } else {
                                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
                            }
                        }
                    });
                    enable.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalDialog.dismiss();
                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            slide_up = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
                            dialogAnimation.startAnimation(slide_up);
                            dialog.dismiss();
                            getActivity().finish();
                        }
                    });
                    dialog.show();
                } else {
                    /*if (dialog != null) {
                        dialog.dismiss();
                    }*/
                    loaderTask = new Asyn();
                    loaderTask.execute();

                }
            } else {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("voeewasdas", "onResume");
        Log.d("loaderTask", "" + loaderTask);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                Log.d("sajkdhdk", "permission");
            } else {
                Log.d("sajkdhdk", "no permission");
                if (Config.LANGUAGE_UPDATE_STATUS == 0) {
                    //SettingUpdate.progressDialog.dismiss();
                    pass();
                } else {
                    try {
                        if (loaderTask == null) {
                            Log.d("dskj", "start task");
                            mEnglish.setVisibility(View.GONE);
                            mTamil.setVisibility(View.GONE);

                            loading();
                        } else {
                            Log.d("dskj", "Whats");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (Config.LANGUAGE_UPDATE_STATUS == 0) {
                //SettingUpdate.progressDialog.dismiss();
                pass();
            } else {
                try {

                    if (loaderTask == null) {
                        Log.d("dskj", "start task222");
                        mEnglish.setVisibility(View.GONE);
                        mTamil.setVisibility(View.GONE);

                        loading();
                    } else {
                        Log.d("dskj", "Whats222");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void downloadVideos() {
        getActivity().startService(new Intent(getActivity(), VideoDownloadService.class));
    }

    private void pass() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SettingUpdate()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            switch (requestCode) {
                case PERMISSION_REQUEST_CODE: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("sakdjhk", "workig");
                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                        alertDialog.setTitle(getActivity().getString(R.string.permission_required));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(getActivity().getString(R.string.settings_message));
                        alertDialog.setPositiveButton(getString(R.string.app_info), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getActivity().getPackageName()));
                                    myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                                    myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivityForResult(myAppSettings, PERMISSION_REQUEST_CODE);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        Log.d("sajkdhdk", "notworking");
                   /* alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new Home()).commit();
                        }
                    });*/
                        alertDialog.show();
                    }
                    return;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private String downloadFile(String filePath, String sdCardPath) throws IOException {
        Log.d("filePath", "" + filePath);
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
            Log.d("nameforDownlading_Purpose", "" + fileName + " " + filesPath + "  " + sdCardPath);
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
            MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, null, null);
            if (file != null) {
                filePathName = file.toString();
            }
        } catch (MalformedURLException e) {
            Tutorials.showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (IOException e) {
            loaderTask.cancel(true);
            loaderTask = null;
            /*loaderTask = new Asyn();
            loaderTask.execute();*/
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SplashScreen()).commit();
            Tutorials.showError("Error : IOException " + e);
            e.printStackTrace();
        } /*catch (Exception e) {
            Tutorials.showError("Error : Please check your internet connection " + e);
        } */ finally {
            urlConnection.disconnect();
        }
        return filePathName;
    }

    public class Asyn extends AsyncTask<String, Void, String> {

        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mLoaderTxt.setVisibility(View.GONE);
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.dialog_progress);
            CustomTextView mLoaderTxt = (CustomTextView) dialog.findViewById(R.id.mLoaderTxt);
            mLoaderTxt.setText(getActivity().getString(R.string.loader));
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            InputStream is = null;
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("task", "getdata"));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    //if (isCancelled()) break;
                }
                result = sb.toString();
                if (result == null) {
                    if (treeList.getCount() >= 1) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Server not connect", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        dialog.dismiss();
                    }
                } else {
                    JSONObject parentObject = null;
                    try {
                        parentObject = new JSONObject(result);
                        JSONObject json1Object = parentObject.getJSONObject("Json1");
                        boolean status = json1Object.getBoolean("status");
                        String message = json1Object.getString("message");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            Log.d("result1Array 1", +treeList.getCount() + " " + result1Array.length());
                       /* if (result1Array.length() == treeList.getCount()) {

                        } else {*/
                            treeList.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                if (Config.checkInternetConenction(getActivity())) {
                                    Log.d("sadsadddsadsdsad", "true  " + Config.checkInternetConenction(getActivity()));
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String TreeName = jsonObject.getString("TreeName");
                                    String TreeType = jsonObject.getString("TreeType");
                                    String Scientific_Name = jsonObject.getString("ScientificName");
                                    String District = jsonObject.getString("District");
                                    String common_key = jsonObject.getString("common_key");
                                    String Image = jsonObject.getString("Images");
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    Log.d("Last_U2342334ast_Update", "" + Last_Update);
                                    String DistrictTamil = jsonObject.getString("DistrictTamil");
                                    String TreeNameTamil = jsonObject.getString("TreeNameTamil");
                                    String TreeTypeTamil = jsonObject.getString("TreeTypeTamil");
                                    String Scientific_Tamil = jsonObject.getString("ScientificTamil");
                                    String extension = null;
                                    String storagePath = null;
                                    ContentValues contentValues = new ContentValues();

                                    //byte[] imgByte = new byte[0];
                                    Log.d("ImageFromSplash", "" + Image.length() + TreeName);
                                    if (TreeName.equals("null") || TreeName.isEmpty()) {
                                    } else {
                                        try {
                                            if (Image.equals(null) || Image.isEmpty()) {
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
                                                treeList.onInsertTreeType(contentValues);
                                            } else {
                                                extension = Image.substring(Image.lastIndexOf("."));
                                                //Log.d("extension", "" + extension);
                                                if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                                    File file = new File(sdCardLocation + "/" + Image);
                                                    if (file.exists()) {
                                                        Log.d("deded", i + " exist");
                                                        storagePath = sdCardLocation + "/" + Image;

                                                        File file1 = new File(storagePath);
                                                        int internalFileLength = (int) file1.length();

                                                        if (internalFileLength == 0) {
                                                            String ImageDpwnLoad = "http://plantation.kambaa.com/admin2017/images/" + Image;
                                                            Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                            storagePath = downloadFile(ImageDpwnLoad, sdCardLocation);
                                                        }


                                                    } else {
                                                        Log.d("deded", i + " Not exist");
                                                        //String ImageSpilt = Image.replaceAll(" ", "%20");
                                                        String ImageDpwnLoad = "http://plantation.kambaa.com/admin2017/images/" + Image;
                                                        Log.d("ImageImage TreeType", " " + ImageDpwnLoad);
                                                        storagePath = downloadFile(ImageDpwnLoad, sdCardLocation);
                                                    }
                                                    Log.d("storagePath", "" + storagePath);
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

                                                    treeList.onInsertTreeType(contentValues);
                                                }
                                            }

                                        } catch (Exception ex) {
                                            Log.d("whtaComes", "Here");
                                            ex.printStackTrace();
                                        }
                                        //   }
                                    }
                                } else {
                                    Log.d("sadsadddsadsdsad", "false " + Config.checkInternetConenction(getActivity()));
                                }
                            }
                        }

                        JSONObject json2Object = parentObject.getJSONObject("Json2");
                        boolean statusMsg = json2Object.getBoolean("status");
                        String messageStus = json2Object.getString("message");
                        if (statusMsg) {
                            JSONArray result1Array = json2Object.getJSONArray("result");
                      /*  if (result1Array.length() == treeTypeNameList.getCout()) {
                        } else {*/
                            Log.d("result1Array  2", +treeList.getCount() + "  " + result1Array);
                            treeTypeNameList.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                if (Config.checkInternetConenction(getActivity())) {
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String treeType = jsonObject.getString("TreeType");
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    String TreeTypeTamil = jsonObject.getString("TreeTypeTamil");
                                    String Image = jsonObject.getString("Image");
                                    String extension = Image.substring(Image.lastIndexOf("."));
                                    ContentValues contentValues = new ContentValues();
                                    byte[] imgByte = new byte[0];
                                    String storagePath = null;
                                    if (Image.equals(null) || Image.isEmpty()) {
                                        //storagePath = sdCardLocationTreeType + "/" + Image;
                                        contentValues.put("storagePath", "" + storagePath);
                                        contentValues.put("treeType", treeType);
                                        contentValues.put("lastUpdate", Last_Update);
                                        contentValues.put("treeTypeTamil", TreeTypeTamil);
                                        treeTypeNameList.onInsert(contentValues);
                                    } else {
                                        extension = Image.substring(Image.lastIndexOf("."));

                                        if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {

                                            File file = new File(sdCardLocationTreeType + "/" + Image);
                                            if (file.exists()) {
                                                Log.d("deded 2", i + " exist");
                                                storagePath = sdCardLocationTreeType + "/" + Image;
                                                File file1 = new File(storagePath);
                                                int internalFileLength = (int) file1.length();
                                                Log.d("fileLengthsFromAndroid", "" + internalFileLength + "  ");
                                                if (internalFileLength == 0) {
                                                    String ImageDpwnLoad = "http://plantation.kambaa.com/admin2017/images/" + Image;
                                                    Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                    storagePath = downloadFile(ImageDpwnLoad, sdCardLocationTreeType);
                                                }
                                            } else {
                                                Log.d("deded 2", i + " Not exist");
                                                String ImageSpilt = Image.replaceAll(" ", "%20");
                                                String ImageDownload = "http://plantation.kambaa.com/admin2017/images/" + ImageSpilt;
                                                Log.d("ImageImage TreeType", " " + Image + "  " + ImageDownload);
                                                storagePath = downloadFile(ImageDownload, sdCardLocationTreeType);
                                                Log.d("storagePathSeond", "" + storagePath);

                                            }
                                            Log.d("storagePath", "" + storagePath);
                                            //contentValues.put("treeIcon", imgByte);
                                            contentValues.put("storagePath", "" + storagePath);
                                            contentValues.put("treeType", treeType);
                                            contentValues.put("lastUpdate", Last_Update);
                                            contentValues.put("treeTypeTamil", TreeTypeTamil);
                                            treeTypeNameList.onInsert(contentValues);

                                        }
                                    }
                                }

                            }
                            // }
                        }


                        JSONObject json3Object = parentObject.getJSONObject("Json3");
                        boolean statusMsg3 = json3Object.getBoolean("status");
                        String messageStus3 = json3Object.getString("message");
                        if (statusMsg3) {
                            JSONArray result1Array = json3Object.getJSONArray("result");
                            Log.d("result1Array  3", "" + result1Array);
                            if (result1Array.length() == districtNameList.getCount()) {
                            } else {
                                districtNameList.delete();
                                for (int i = 0; i < result1Array.length(); i++) {
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String District = jsonObject.getString("District");
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    String DistrictTamil = jsonObject.getString("DistrictTamil");
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("lastUpdate", Last_Update);
                                    contentValues.put("districtNameTamil", DistrictTamil);
                                    districtNameList.onCreate(contentValues);
                                }
                            }
                        }

                        JSONObject json4Object = parentObject.getJSONObject("Json4");
                        boolean statusMsg4 = json4Object.getBoolean("status");
                        String messageStus4 = json4Object.getString("message");
                        if (statusMsg4) {
                            JSONArray result1Array = json4Object.getJSONArray("result");
                            Log.d("result1Array  4", "" + result1Array);
                            if (result1Array.length() == soilType.getCount()) {
                            } else {
                                soilType.delete();
                                for (int i = 0; i < result1Array.length(); i++) {
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String District = jsonObject.getString("District");
                                    String Soil = jsonObject.getString("Soil");
                                    String Last_Update = jsonObject.getString("LastUpdate");

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("soilType", Soil);
                                    contentValues.put("lastUpdate", Last_Update);
                                    contentValues.put("districtNameTamil", jsonObject.getString("DistrictTamil"));
                                    contentValues.put("soilTypeTamil", jsonObject.getString("SoilTamil"));
                                    soilType.onInsert(contentValues);
                                }
                            }
                        }

                        JSONObject json5Object = parentObject.getJSONObject("Json5");
                        boolean statusMsg5 = json5Object.getBoolean("status");
                        String messageStus5 = json5Object.getString("message");
                        if (statusMsg5) {
                            JSONArray result1Array = json5Object.getJSONArray("result");
                            Log.d("result1Array  5", "" + result1Array);
                            if (result1Array.length() == treeTypeInfo.getCount()) {

                            } else {
                                treeTypeInfo.delete();
                                for (int i = 0; i < result1Array.length(); i++) {
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String District = jsonObject.getString("District");
                                    String Soil = jsonObject.getString("Soil");
                                    String Treetype = jsonObject.getString("Treetype");
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    if (Treetype.equals("null") || Treetype.isEmpty()) {
                                    } else {
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("districtName", District);
                                        contentValues.put("lastUpdate", Last_Update);
                                        contentValues.put("treeType", Treetype);
                                        contentValues.put("soilType", Soil);
                                        contentValues.put("districtNameTamil", jsonObject.getString("DistrictTamil"));
                                        contentValues.put("treeTypeTamil", jsonObject.getString("TreetypeTamil"));
                                        contentValues.put("soilTypeTamil", jsonObject.getString("SoilTamil"));
                                        treeTypeInfo.onInsert(contentValues);
                                        //soilType.onUpdate(District, Soil, Treetype);
                                    }
                                }
                            }
                        }
                        JSONObject json6Object = parentObject.getJSONObject("Json6");
                        boolean statusMsg6 = json6Object.getBoolean("status");
                        String messageStus6 = json6Object.getString("message");
                        if (statusMsg6) {
                            JSONArray result1Array = json6Object.getJSONArray("result");
                            Log.d("result1Array  6", "" + result1Array.length() + "  " + verifyDetails.getCount());
                          /*  if (result1Array.length() == verifyDetails.getCount()) {
                            } else {*/
                            verifyDetails.delete();
                            imageDb.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                if (Config.checkInternetConenction(getActivity())) {
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String District = jsonObject.getString("District");
                                    String SoilType = jsonObject.getString("SoilType");
                                    String RainFall = jsonObject.getString("RainFall");
                                    String TerrainType = jsonObject.getString("TerrainType");
                                    String TreeName = jsonObject.getString("TreeName");
                                    String TreeType = jsonObject.getString("TreeType");
                                    String ScientificName = jsonObject.getString("ScientificName");
                                    String General_Info = jsonObject.getString("General_Info");
                                    String General_Info_Tamil = jsonObject.getString("General_Info_Tamil");
                                    String Habitat = jsonObject.getString("Habitat");
                                    String Habitat_Tamil = jsonObject.getString("Habitat_Tamil");
                                    String Soil = jsonObject.getString("Soil");
                                    String Soil_Tamil = jsonObject.getString("Soil_Tamil");
                                    String Soil_PH = jsonObject.getString("Soil_PH");
                                    String Soil_PH_Tamil = jsonObject.getString("Soil_PH_Tamil");
                                    String Altitude = jsonObject.getString("Altitude");
                                    String Altitude_Tamil = jsonObject.getString("Altitude_Tamil");
                                    String Rainfall = jsonObject.getString("Rainfall");
                                    String Rainfall_Tamil = jsonObject.getString("Rainfall_Tamil");
                                    String Min_Temperature = jsonObject.getString("Min_Temperature");
                                    String Max_Temperature = jsonObject.getString("Max_Temperature");
                                    String Min_Temperature_Tamil = jsonObject.getString("Min_Temperature_Tamil");
                                    String Max_Temperature_Tamil = jsonObject.getString("Max_Temperature_Tamil");
                                    String Terrain = jsonObject.getString("Terrain");
                                    String TerrainType_Tamil = jsonObject.getString("TerrainType_Tamil");
                                    String Tree_Char = jsonObject.getString("Tree_Char");
                                    String Tree_Char_Tamil = jsonObject.getString("Tree_Char_Tamil");
                                    String Conservation = jsonObject.getString("Conservation");
                                    String Conservation_Tamil = jsonObject.getString("Conservation_Tamil");
                                    String Edibility = jsonObject.getString("Edibility");
                                    String Edibility_Tamil = jsonObject.getString("Edibility_Tamil");
                                    String Medicinal = jsonObject.getString("Medicinal");
                                    String Medicinal_Tamil = jsonObject.getString("Medicinal_Tamil");
                                    String Other_Rating = jsonObject.getString("Other_Rating");
                                    String Other_Rating_Tamil = jsonObject.getString("Other_Rating_Tamil");
                                    String Habit = jsonObject.getString("Habit");
                                    String Habit_Tamil = jsonObject.getString("Habit_Tamil");
                                    String Growth = jsonObject.getString("Growth");
                                    String Growth_Tamil = jsonObject.getString("Growth_Tamil");
                                    String Height = jsonObject.getString("Height");
                                    String Height_Tamil = jsonObject.getString("Height_Tamil");
                                    String Cultivation = jsonObject.getString("Cultivation");
                                    String Cultivation_Tamil = jsonObject.getString("Cultivation_Tamil");
                                    String Other_Details = jsonObject.getString("Other_Details");
                                    String Other_Details_Tamil = jsonObject.getString("Other_Details_Tamil");
                                    String Propagation = jsonObject.getString("Propagation");
                                    String Propagation_Tamil = jsonObject.getString("Propagation_Tamil");
                                    String Plantation_Technique = jsonObject.getString("Plantation_Technique");
                                    String Plantation_Technique_Tamil = jsonObject.getString("Plantation_Technique_Tamil");
                                    String Care_Disease = jsonObject.getString("Care_Disease");
                                    String Care_Disease_Tamil = jsonObject.getString("Care_Disease_Tamil");
                                    String Irrigation = jsonObject.getString("Irrigation");
                                    String Irrigation_Tamil = jsonObject.getString("Irrigation_Tamil");
                                    String Yield = jsonObject.getString("Yield");
                                    String Yield_Tamil = jsonObject.getString("Yield_Tamil");
                                    String Recommended_Harvest = jsonObject.getString("Recommended_Harvest");
                                    String Recommended_Harvest_Tamil = jsonObject.getString("Recommended_Harvest_Tamil");
                                    String Market_Details = jsonObject.getString("Market_Details");
                                    String Market_Details_Tamil = jsonObject.getString("Market_Details_Tamil");
                                    String Intercrops = jsonObject.getString("Intercrops");
                                    String Intercrops_Tamil = jsonObject.getString("Intercrops_Tamil");
                                    String Majar_Uses = jsonObject.getString("Majar_Uses");
                                    String Majar_Uses_Tamil = jsonObject.getString("Majar_Uses_Tamil");
                                    String Other_Uses = jsonObject.getString("Other_Uses");
                                    String Other_Uses_Tamil = jsonObject.getString("Other_Uses_Tamil");
                                    String Carbon_Stock = jsonObject.getString("Carbon_Stock");
                                    String Carbon_Stock_Tamil = jsonObject.getString("Carbon_Stock_Tamil");
                                    String References = jsonObject.getString("References");
                                    String References_Tamil = jsonObject.getString("References_Tamil");
                                    String DistrictTamil = jsonObject.getString("DistrictTamil");
                                    String SoilTamil = jsonObject.getString("SoilTamil");
                                    String RainFallTamil = jsonObject.getString("RainFallTamil");
                                    String TerrainTypeTamil = jsonObject.getString("TerrainTypeTamil");
                                    String TreeNameTamil = jsonObject.getString("TreeNameTamil");
                                    String TreeTypeTamil = jsonObject.getString("TreeTypeTamil");
                                    String ScientificTamil = jsonObject.getString("ScientificTamil");
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    String common_key = jsonObject.getString("common_key");
                                    //Log.d("Irrigaasasa1245tion", "" + Irrigation);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("SoilType", SoilType);
                                    contentValues.put("RainFallFirst", RainFall);
                                    contentValues.put("TerrainType", TerrainType);
                                    contentValues.put("TreeName", TreeName);
                                    contentValues.put("TreeType", TreeType);
                                    contentValues.put("ScientificName", ScientificName);
                                    contentValues.put("General_Info", General_Info);
                                    contentValues.put("General_Info_Tamil", General_Info_Tamil);
                                    contentValues.put("Habitat", Habitat);
                                    contentValues.put("Habitat_Tamil", Habitat_Tamil);
                                    contentValues.put("Soil", Soil);
                                    contentValues.put("common_key", common_key);
                                    contentValues.put("lastUpdate", Last_Update);
                                    contentValues.put("Soil_Tamil", Soil_Tamil);
                                    contentValues.put("Soil_PH", Soil_PH);
                                    contentValues.put("Soil_PH_Tamil", Soil_PH_Tamil);
                                    contentValues.put("Altitude", Altitude);
                                    contentValues.put("Altitude_Tamil", Altitude_Tamil);
                                    contentValues.put("Rainfall", Rainfall);
                                    contentValues.put("Rainfall_Tamil", Rainfall_Tamil);
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
                                    contentValues.put("References_Tamil", References_Tamil);
                                    contentValues.put("districtNameTamil", DistrictTamil);
                                    contentValues.put("SoilTamil", SoilTamil);
                                    contentValues.put("RainFallTamil", RainFallTamil);
                                    contentValues.put("TerrainTypeTamil", TerrainTypeTamil);
                                    contentValues.put("TreeNameTamil", TreeNameTamil);
                                    contentValues.put("TreeTypeTamil", TreeTypeTamil);
                                    contentValues.put("ScientificTamil", ScientificTamil);
                                    contentValues.put("Propagation_Tamil", "" + Propagation_Tamil);

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
                                            Log.d("ImageFromSplash", "" + Image + TreeName);
                                            if (Image.equals(null) || Image.isEmpty()) {
                                                imageDb.onInsert(TreeName, common_key, TreeNameTamil, storagePath);
                                            } else {
                                                extension = Image.substring(Image.lastIndexOf("."));
                                                Log.d("extension", i + "  " + extension);
                                                if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                                    File file = new File(sdCardLocationTreeImages + "/" + Image);
                                                    if (file.exists()) {
                                                        Log.d("deded 2", i + " 6 file  exist");
                                                        storagePath = sdCardLocationTreeImages + "/" + Image;

                                                        File file1 = new File(storagePath);
                                                        int internalFileLength = (int) file1.length();
                                                        Log.d("fileLengthsFromAndroid", "" + internalFileLength + "  ");
                                                        if (internalFileLength == 0) {
                                                            String ImageDpwnLoad = "http://plantation.kambaa.com/admin2017/images/" + Image;
                                                            Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                            storagePath = downloadFile(ImageDpwnLoad, sdCardLocationTreeImages);
                                                        }
                                                    } else {
                                                        Log.d("deded 2", i + " 6 Not exist");
                                                        String ImageSplit = Image.replaceAll(" ", "%20");
                                                        String ImageDownload = "http://plantation.kambaa.com/admin2017/images/" + ImageSplit;
                                                        Log.d("ImageImage", arr + "  " + ImageDownload);
                                                        storagePath = downloadFile(ImageDownload, sdCardLocationTreeImages);
                                                        Log.d("storagePathSeond", "" + storagePath);
                                                    }
                                                    imageDb.onInsert(TreeName, common_key, TreeNameTamil, storagePath);

                                                }
                                            }
                                        }

                                    }
                                    Log.d("extension two", "" + i);
                                    contentValues.put("storagePath", "" + storagePath);
                                    verifyDetails.onInsert(contentValues);
                                }
                            }

                        }

                        JSONObject json7Object = parentObject.getJSONObject("Json7");
                        boolean statusMsg7 = json7Object.getBoolean("status");
                        String messageStus7 = json7Object.getString("message");
                        if (statusMsg7) {
                            JSONArray result1Array = json7Object.getJSONArray("result");
                            Log.d("result1Array  7", "" + result1Array);
                            if (result1Array.length() == rainfallType.getCount()) {

                            } else {
                                rainfallType.delete();
                                for (int i = 0; i < result1Array.length(); i++) {

                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String District = jsonObject.getString("District");
                                    String Rainfall = jsonObject.getString("Rainfall");
                                    String Last_Update = jsonObject.getString("LastUpdate");

                                    String DistrictTamil = jsonObject.getString("DistrictTamil");
                                    String RainfallTamil = jsonObject.getString("RainfallTamil");
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("Rainfall", Rainfall);
                                    contentValues.put("lastUpdate", Last_Update);
                                    contentValues.put("districtNameTamil", DistrictTamil);
                                    contentValues.put("RainfallTamil", RainfallTamil);
                                    rainfallType.onInsert(contentValues);
                                }
                            }
                        }

                        JSONObject json8Object = parentObject.getJSONObject("Json8");
                        boolean statusMsg8 = json8Object.getBoolean("status");
                        String messageStus8 = json8Object.getString("message");
                        if (statusMsg8) {

                            JSONArray result1Array = json8Object.getJSONArray("result");
                            Log.d("result1Array  8", "" + result1Array);
                            if (result1Array.length() == terrainType.getCount()) {

                            } else {
                                terrainType.delete();
                                for (int i = 0; i < result1Array.length(); i++) {
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String District = jsonObject.getString("District");
                                    String Terrain = jsonObject.getString("Terrain");
                                    String DistrictTamil = jsonObject.getString("DistrictTamil");
                                    String TerrainTamil = jsonObject.getString("TerrainTamil");
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("Terrain", Terrain);
                                    contentValues.put("lastUpdate", Last_Update);

                                    contentValues.put("districtNameTamil", DistrictTamil);
                                    contentValues.put("TerrainTamil", TerrainTamil);
                                    terrainType.onInsert(contentValues);
                                    //  districtNameList.onCreate(contentValues);
                                }
                            }
                        }


                        JSONObject json9Object = parentObject.getJSONObject("Json9");
                        boolean statusMsg9 = json9Object.getBoolean("status");
                        String messageStus9 = json9Object.getString("message");
                        if (statusMsg9) {
                            JSONArray result1Array = json9Object.getJSONArray("result");
                            Log.d("result1Array  9", "" + result1Array);
                            if (result1Array.length() == contactUs.getCount()) {

                            } else {
                                contactUs.delete();
                                for (int i = 0; i < result1Array.length(); i++) {

                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String District = jsonObject.getString("District");
                                    String Address = jsonObject.getString("Address");
                                    String PhoneNo = jsonObject.getString("PhoneNo");
                                    String DistrictTamil = jsonObject.getString("DistrictTamil");
                                    String AddressTamil = jsonObject.getString("AddressTamil");
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    String directions = jsonObject.getString("Direction");
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("address", Address);
                                    contentValues.put("lastUpdate", Last_Update);
                                    contentValues.put("addressTamil", AddressTamil);
                                    contentValues.put("districtNameTamil", DistrictTamil);
                                    contentValues.put("phoneNo", PhoneNo);
                                    contentValues.put("direction", "" + directions);
                                    contactUs.onInsert(contentValues);
                                }
                            }
                        }

                        JSONObject json10Object = parentObject.getJSONObject("Json10");
                        boolean statusMsg10 = json10Object.getBoolean("status");
                        String messageStus10 = json10Object.getString("message");
                        if (statusMsg10) {
                            JSONArray result1Array = json10Object.getJSONArray("result");
                            Log.d("result1Array  10", "" + result1Array);
                            if (result1Array.length() == glossaryTable.getCount()) {
                                result = "success";
                            } else {
                                glossaryTable.delete();
                                for (int i = 0; i < result1Array.length(); i++) {
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String Word = jsonObject.getString("Word");
                                    String WordTamil = jsonObject.getString("WordTamil");
                                    String Meaning = jsonObject.getString("Meaning");
                                    String MeaningamTil = jsonObject.getString("MeaningTamil");
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("word", Word);
                                    contentValues.put("wordTamil", WordTamil);
                                    contentValues.put("meaning", Meaning);
                                    contentValues.put("meaningTamil", MeaningamTil);
                                    contentValues.put("lastUpdate", Last_Update);
                                    glossaryTable.onInsert(contentValues);
                                }
                                result = "success";
                            }
                        }
                        // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //urlConnection.disconnect();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("Response", "Size  " + s.length() + "  letters " + s);
            Log.d("sampledds", "" + s);
            if (s.equals("success")) {
                dialog.dismiss();

                mTamil.setVisibility(View.VISIBLE);
                mEnglish.setVisibility(View.VISIBLE);
                dialog = null;

            }
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    public static boolean isNetworkAvailable(UpdateData act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
