package com.fresh.mind.plantation.fragment.menu_page;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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


import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.JSONParser;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.api.RetrofitCall;
import com.fresh.mind.plantation.api.RetrofitService;
import com.fresh.mind.plantation.customized.CustomTextView;


import com.fresh.mind.plantation.customized.RippleView;
import com.fresh.mind.plantation.model.PrimaryModel;
import com.fresh.mind.plantation.model.json1.TreeSpecielModel;
import com.fresh.mind.plantation.service.ImagesDownloadService;
import com.fresh.mind.plantation.service.UpdateData;
import com.fresh.mind.plantation.service.VideoDownloadService;

import com.fresh.mind.plantation.sqlite.Intercrops;
import com.fresh.mind.plantation.sqlite.LanguageChange;

import com.fresh.mind.plantation.sqlite.StatusForDownload;
import com.fresh.mind.plantation.sqlite.server.AllRainFall;
import com.fresh.mind.plantation.sqlite.server.AllSoilType;
import com.fresh.mind.plantation.sqlite.server.AllTerrainType;
import com.fresh.mind.plantation.sqlite.server.ContactUs;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.server.GlossaryTable;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.Modelinfo;
import com.fresh.mind.plantation.sqlite.server.SchecmesDb;
import com.fresh.mind.plantation.sqlite.server.SoilType;

import com.fresh.mind.plantation.sqlite.server.TreeList;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;
import com.fresh.mind.plantation.tab_pager.HomeTabView;
import com.squareup.picasso.Picasso;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.io.FileNotFoundException;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.thumbnail;
import static android.content.Context.ACTIVITY_SERVICE;
import static com.fresh.mind.plantation.Constant.Config.SPLASH_LANGUAGE;
import static com.fresh.mind.plantation.Constant.Config.fileLocationOnServer;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocation;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocationIntercrops;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocationModel;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocationTreeImages;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocationTreeType;
import static com.fresh.mind.plantation.Constant.Config.url;

/**
 * Created by AND I5 on 30-01-2017.
 */

public class SplashScreen extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View rootView;
    private Animation slide_up;
    private TreeList treeList;
    private DistrictNameList districtNameList;
    private TreeTypeNameList treeTypeNameList;
    private Modelinfo modelinfo;
    private SoilType soilType;
    private VerifyDetails verifyDetails;
    /*private TerrainType terrainType;
    private RainfallType rainfallType;
    private TreeTypeInfo treeTypeInfo;*/
    private ImageDb imageDb;
    private ContactUs contactUs;
    private GlossaryTable glossaryTable;
    private ImageView imageView8;
    private LanguageChange languageChange;
    private SchecmesDb schecmesDb;
    int totalSize = 0;
    private CustomTextView mRefresh;
    private RippleView mTamil, mEnglish;
    private Dialog dialog;
    private AllTerrainType allTerrainType;
    private AllSoilType allSoilType;
    private AllRainFall allRainFall;
    private Intercrops intercrops;
    private Asyn loaderTask;
    private String sch1;
    private RetrofitService retrofitService;
    private StatusForDownload statusForDownload;

    @Override
    public void onDestroy() {
        super.onDestroy();
        treeList.close();
        districtNameList.close();
        treeTypeNameList.close();
        verifyDetails.close();
        soilType.close();
        imageDb.close();
        languageChange.close();
        contactUs.close();
        glossaryTable.close();
        modelinfo.close();
        intercrops.close();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ((MainActivity) getActivity()).setSplashScreen();
        retrofitService = RetrofitCall.getClient().create(RetrofitService.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        languageChange = new LanguageChange(getActivity());
        statusForDownload = new StatusForDownload(getActivity());
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

        treeList = new TreeList(getActivity());
        districtNameList = new DistrictNameList(getActivity());
        treeTypeNameList = new TreeTypeNameList(getActivity());
        verifyDetails = new VerifyDetails(getActivity());
        soilType = new SoilType(getActivity());
        /*rainfallType = new RainfallType(getActivity());
        terrainType = new TerrainType(getActivity());
        treeTypeInfo = new TreeTypeInfo(getActivity());*/
        imageDb = new ImageDb(getActivity());
        modelinfo = new Modelinfo(getActivity());
        intercrops = new Intercrops(getActivity());

        contactUs = new ContactUs(getActivity());
        glossaryTable = new GlossaryTable(getActivity());
        schecmesDb = new SchecmesDb(getActivity());
        allRainFall = new AllRainFall(getActivity());
        allSoilType = new AllSoilType(getActivity());
        allTerrainType = new AllTerrainType(getActivity());

        imageView8 = (ImageView) rootView.findViewById(R.id.imageView8);
        mTamil = (RippleView) rootView.findViewById(R.id.tamilBtn);
        mEnglish = (RippleView) rootView.findViewById(R.id.button);
        mRefresh = (CustomTextView) rootView.findViewById(R.id.mRefresh);
        mTamil.setVisibility(View.GONE);
        mEnglish.setVisibility(View.GONE);


        imageView8.setImageResource(R.drawable.splash_tamnil_eng);


        mEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguage("en");
            }

        });
        mTamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguage("ta");

            }
        });
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefresh.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SplashScreen()).commit();
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
        Log.d("isServiceRunning", "" + isServiceRunning());
       /* if (isServiceRunning()) {
        } else {
            getActivity().startService(new Intent(getActivity(), ImagesDownloadService.class));
        }*/
        Intent intent = new Intent(getActivity(), MainActivity.class);
        SPLASH_LANGUAGE = 0;
        getActivity().startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            //   Log.d("dfsfsdf", "" + service.service.getClassName());
            if ("com.fresh.mind.plantation.service".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
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
        //downloadVideos();
        Log.d("kloopikl", "" + Config.pass + "  " + SPLASH_LANGUAGE);
        if (SPLASH_LANGUAGE == 0) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else {
            if (Config.pass.equals("splash")) {
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
                            if (verifyDetails.getCount() >= 1) {
                                mTamil.setVisibility(View.VISIBLE);
                                mEnglish.setVisibility(View.VISIBLE);
                                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
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
                    Log.d("comnnnnn", "loader");

                    Log.d("dsdsad", "" + treeList.getCount() + "  " + verifyDetails.getCount());
                    if (treeList.getCount() >= 1 && verifyDetails.getCount() >= 1) {
                        Log.d("Processs", "Whatss");
                        getActivity().startService(new Intent(getActivity(), VideoDownloadService.class));
                        getActivity().startService(new Intent(getActivity(), ImagesDownloadService.class));
                        // getActivity().startService(new Intent(getActivity(), DescriptionImages.class));
                        mTamil.setVisibility(View.VISIBLE);
                        mEnglish.setVisibility(View.VISIBLE);
                        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                    } else {
                        getActivity().startService(new Intent(getActivity(), VideoDownloadService.class));
                        mTamil.setVisibility(View.GONE);
                        mEnglish.setVisibility(View.GONE);
                        Log.d("Processs", "Comonn");

                        dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_progress);
                        CustomTextView mLoaderTxt = (CustomTextView) dialog.findViewById(R.id.mLoaderTxt);
                        mLoaderTxt.setText(getActivity().getString(R.string.loader));
                        dialog.setCancelable(false);
                        mLoaderTxt.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            JumpingBeans.with(mLoaderTxt).appendJumpingDots().build();
                        }
                    /*    Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.BOTTOM;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        window.setAttributes(wlp);
                        dialog.show();

                        loadInialData();*/

                        loaderTask = new Asyn();
                        loaderTask.execute();


                    }
                }
            } else {
                getActivity().startService(new Intent(getActivity(), ImagesDownloadService.class));
                //getActivity().startService(new Intent(getActivity(), DescriptionImages.class));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
            }
        }
    }

    private void loadInialData() {

        callInit().enqueue(new Callback<PrimaryModel>() {
            @Override
            public void onResponse(Call<PrimaryModel> call, Response<PrimaryModel> response) {

                Log.d("responsemodel", "" + response.body());
                PrimaryModel primaryModel = response.body();
                if (primaryModel != null && primaryModel.status) {
                    Log.d("responsemodel", "111" + primaryModel.json1TreePecieas.treeSpecielModels.size());
                    Toast.makeText(getActivity(), "" + primaryModel.json1TreePecieas.treeSpecielModels.size(), Toast.LENGTH_LONG).show();
                    mRefresh.setVisibility(View.GONE);

                    if (primaryModel.json1TreePecieas.treeSpecielModels.size() > 10) {
                        onInsertWithConditions(primaryModel.json1TreePecieas.treeSpecielModels, 10);
                    } else {
                        onInsertWithConditions(primaryModel.json1TreePecieas.treeSpecielModels, primaryModel.json1TreePecieas.treeSpecielModels.size());
                    }
                    mTamil.setVisibility(View.VISIBLE);
                    mEnglish.setVisibility(View.VISIBLE);

                } else {
                    Log.d("responsemodel", "000 ");
                    Toast.makeText(getActivity(), getResources().getString(R.string.server_500), Toast.LENGTH_LONG).show();
                    mRefresh.setVisibility(View.VISIBLE);
                }

                Log.d("treeList", "" + treeList.getCount());
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<PrimaryModel> call, Throwable t) {
                Log.d("responsemodel", "no Valuess ");
                dialog.dismiss();
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                mRefresh.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onInsertWithConditions(ArrayList<TreeSpecielModel> treeSpecielModels, int length) {
        String extension = null;
        String storagePath = null;
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < length; i++) {
            String Image = treeSpecielModels.get(i).Images;
            try {
                Log.d("Imagesldksldks", "" + Image);
                if (Image == null || Image.isEmpty()) {
                    //contentValues.put("storagePath", "" + storagePath);
                    contentValues.put("treeType", treeSpecielModels.get(i).TreeType);
                    contentValues.put("districtName", "" + treeSpecielModels.get(i).District);
                    contentValues.put("treeNameTamil", treeSpecielModels.get(i).ScientificName);
                    contentValues.put("treeNameEng", treeSpecielModels.get(i).TreeName);
                    contentValues.put("lastUpdate", treeSpecielModels.get(i).LastUpdate);
                    contentValues.put("Scientific_Tamil", treeSpecielModels.get(i).ScientificTamil);
                    contentValues.put("treeTypeTamil", treeSpecielModels.get(i).TreeTypeTamil);
                    contentValues.put("treeNameEngTamil", treeSpecielModels.get(i).TreeNameTamil);
                    contentValues.put("districtNameTamil", "" + treeSpecielModels.get(i).DistrictTamil);
                    contentValues.put("districtNameTamil", "" + treeSpecielModels.get(i).DistrictTamil);
                    contentValues.put("common_key", "" + treeSpecielModels.get(i).common_key);
                    Log.d("deded else", i + " exist " + treeSpecielModels.get(i).common_key);
                    treeList.onInsertTreeType(contentValues);

                } else {
                    extension = Image.substring(Image.lastIndexOf("."));
                    //Log.d("extension", "" + extension);
                    //  if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                    File file = new File(sdCardLocation + "/" + Image);
                    if (file.exists()) {
                        Log.d("deded", i + " exist");
                        storagePath = sdCardLocation + "/" + Image;
                        File file1 = new File(storagePath);
                        int internalFileLength = (int) file1.length();
                        if (internalFileLength == 0) {
                            String ImageDpwnLoad = fileLocationOnServer + Image;
                            Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                            storagePath = downloadFile(ImageDpwnLoad, sdCardLocation);
                        }

                    } else {
                        Log.d("deded", i + " Not exist");
                        //String ImageSpilt = Image.replaceAll(" ", "%20");
                        String ImageDpwnLoad = fileLocationOnServer + Image;
                        Log.d("ImageImage TreeType", " " + ImageDpwnLoad);
                        storagePath = downloadFile(ImageDpwnLoad, sdCardLocation);
                    }

                    Log.d("storagePath_Common", "" + storagePath + "  " + treeSpecielModels.get(i).common_key);
                    contentValues.put("storagePath", "" + storagePath);
                    contentValues.put("treeType", treeSpecielModels.get(i).TreeType);
                    contentValues.put("districtName", "" + treeSpecielModels.get(i).District);
                    contentValues.put("treeNameTamil", treeSpecielModels.get(i).ScientificName);
                    contentValues.put("treeNameEng", treeSpecielModels.get(i).TreeName);
                    contentValues.put("lastUpdate", treeSpecielModels.get(i).LastUpdate);
                    contentValues.put("Scientific_Tamil", treeSpecielModels.get(i).ScientificTamil);
                    contentValues.put("treeTypeTamil", treeSpecielModels.get(i).TreeTypeTamil);
                    contentValues.put("treeNameEngTamil", treeSpecielModels.get(i).TreeNameTamil);
                    contentValues.put("districtNameTamil", "" + treeSpecielModels.get(i).DistrictTamil);
                    contentValues.put("districtNameTamil", "" + treeSpecielModels.get(i).DistrictTamil);
                    contentValues.put("common_key", "" + treeSpecielModels.get(i).common_key);
                    Log.d("contentValuesssss", "" + contentValues);
                    treeList.onInsertTreeType(contentValues);

                }

            } catch (Exception ex) {
                Log.d("whtaComes", "Here");
                ex.printStackTrace();
                //return "return";
            }
        }
        Toast.makeText(getActivity(), "" + treeList.getCount(), Toast.LENGTH_LONG).show();
        Log.d("treeLis121548t", "" + treeList.getCount());
    }

    private Call<PrimaryModel> callInit() {
        return retrofitService.callCoreData("getdata");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("voeewasdas", "onResume");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // if (checkPermission()) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                File wallpaperDirectory = new File("/sdcard/Treepedia/");
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
                if (Config.LANGUAGE_UPDATE_STATUS == 0) {
                    //SettingUpdate.progressDialog.dismiss();
                    pass();
                } else {
                    try {
                        if (loaderTask == null) {
                            mEnglish.setVisibility(View.GONE);
                            mTamil.setVisibility(View.GONE);
                            mRefresh.setVisibility(View.GONE);
                            loading();
                            //  changeTextStatus(Config.checkInternetConenction(getActivity()));

                        } else {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //  }
            }
        } else {
            File wallpaperDirectory = new File("/sdcard/Treepedia/");
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
            if (Config.LANGUAGE_UPDATE_STATUS == 0) {
                //SettingUpdate.progressDialog.dismiss();
                pass();
            } else {
                try {
                    if (loaderTask == null) {
                        mEnglish.setVisibility(View.GONE);
                        mTamil.setVisibility(View.GONE);
                        mRefresh.setVisibility(View.GONE);
                        loading();
                        //changeTextStatus(Config.checkInternetConenction(getActivity()));
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(getActivity())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, 547);
            return false;
        } else {
            return true;
        }
    }

    public void changeTextStatus(boolean status) throws IOException {
        if (status) {
            loading();
        }
    }

    private void pass() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 547) {
            if (Settings.canDrawOverlays(getActivity())) {
                // startService(new Intent(this, PowerButtonService.class));
                pass();
            }
        }
        /*if (requestCode == 547) {
            if (!Settings.canDrawOverlays(getActivity())) {
                // You don't have permission
                Log.d("sdsds", "come not");
                checkPermission();

            } else {
                Log.d("sdsds", "come");
                //do as per your logic
            }

        }*/

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
            if (Config.checkInternetConenction(getActivity())) {
                //Log.d("nameforDownlading_Purpose", "" + fileName + " " + filesPath + "  " + sdCardPath);
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
            } else {
                Log.d("comnnnnn", "getfileMethd");
                File file1 = new File(sdCardPath + "/" + fileName);
                file1.delete();
            }
        } catch (MalformedURLException e) {
            Tutorials.showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            loaderTask.cancel(true);
            loaderTask = null;
            dialog.dismiss();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SplashScreen()).commit();
            Tutorials.showError("Error : IOException " + e);
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return filePathName;
    }

    public class Asyn extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("loaderss", "000");
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_progress);
            CustomTextView mLoaderTxt = (CustomTextView) dialog.findViewById(R.id.mLoaderTxt);
            mLoaderTxt.setText(getActivity().getString(R.string.loader));
            dialog.setCancelable(false);
            mLoaderTxt.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                JumpingBeans.with(mLoaderTxt).appendJumpingDots().build();
            }
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("loaderss", "1111");
            String result = null;
           /* HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            InputStream is = null;*/
            HashMap<String, String> stringHashMap = statusForDownload.getStatus();
            String statusDownload = stringHashMap.get("status");
            String idDownload = stringHashMap.get("id");
            Log.d("statusDownload", "ioio" + statusDownload + "  " + idDownload);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("task", "getdata"));

            Log.d("nameValuePairs", "" + nameValuePairs);
            try {
               /* httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    //if (isCancelled()) break;
                }
                result = sb.toString();*/

                result = JSONParser.getJsonResponse(url, nameValuePairs);
                //"http://plantation.kambaaincorporation.in/webservices/testapi.php

                Log.d("result", "" + result);
                if (result == null || result.isEmpty() || result.contains("500 Server Error")) {
                    return "server";
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

                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String TreeName = jsonObject.getString("TreeName").trim();
                                    String TreeType = jsonObject.getString("TreeType").trim();
                                    String Scientific_Name = jsonObject.getString("ScientificName").trim();
                                    String District = jsonObject.getString("District").trim();
                                    String common_key = jsonObject.getString("common_key");


                                    String Image = jsonObject.getString("thumbnail");
                                    //String Image = jsonObject.getString("Images");

                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    //Log.d("Last_U2342334ast_Update", "" + Last_Update);
                                    String DistrictTamil = jsonObject.getString("DistrictTamil").trim();
                                    String TreeNameTamil = jsonObject.getString("TreeNameTamil").trim();
                                    String TreeTypeTamil = jsonObject.getString("TreeTypeTamil").trim();
                                    String Scientific_Tamil = jsonObject.getString("ScientificTamil").trim();
                                    String extension = null;
                                    String storagePath = null;
                                    ContentValues contentValues = new ContentValues();
                                    //byte[] imgByte = new byte[0];
                                    Log.d("ImageFromSplash", "" + Image + "  " + TreeName);
                                    if (TreeName.equals("null") || TreeName.isEmpty()) {
                                    } else {
                                        try {
                                            if (Image.equals("null") || Image.isEmpty()) {
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
                                                contentValues.put("districtNameTamil", "" + DistrictTamil);
                                                contentValues.put("common_key", "" + common_key);
                                                Log.d("deded else", i + " exist " + common_key);
                                                treeList.onInsertTreeType(contentValues);

                                            } else {
                                                extension = Image.substring(Image.lastIndexOf("."));
                                                //Log.d("extension", "" + extension);
                                                //  if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                                File file = new File(sdCardLocation + "/" + Image);
                                                if (file.exists()) {
                                                    Log.d("deded", i + " exist");
                                                    storagePath = sdCardLocation + "/" + Image;
                                                    File file1 = new File(storagePath);
                                                    int internalFileLength = (int) file1.length();
                                                    if (internalFileLength == 0) {
                                                        String ImageDpwnLoad = fileLocationOnServer + Image;
                                                        Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                        storagePath = downloadFile(ImageDpwnLoad, sdCardLocation);
                                                    }

                                                } else {
                                                    Log.d("deded", i + " Not exist");
                                                    //String ImageSpilt = Image.replaceAll(" ", "%20");
                                                    String ImageDpwnLoad = fileLocationOnServer + Image;
                                                    Log.d("ImageImage TreeType", " " + ImageDpwnLoad);
                                                    storagePath = downloadFile(ImageDpwnLoad, sdCardLocation);
                                                }

                                                Log.d("storagePath OCmmon", "" + storagePath + common_key);
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

                                            }

                                        } catch (Exception ex) {
                                            Log.d("whtaComes", "Here");
                                            ex.printStackTrace();
                                            //return "return";
                                        }
                                        //   }
                                    }
                                } else {
                                    Log.d("comnnnnn", "json1");
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
                            treeTypeNameList.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                if (Config.checkInternetConenction(getActivity())) {
                                    JSONObject jsonObject = result1Array.getJSONObject(i);
                                    String treeType = jsonObject.getString("TreeType").trim();
                                    String Last_Update = jsonObject.getString("LastUpdate");
                                    String TreeTypeTamil = jsonObject.getString("TreeTypeTamil").trim();
                                    String Image = jsonObject.getString("Image").trim();

                                    String extension = Image.substring(Image.lastIndexOf("."));
                                    ContentValues contentValues = new ContentValues();
                                    byte[] imgByte = new byte[0];
                                    String storagePath = null;
                                    if (Image.equals("null") || Image.isEmpty()) {
                                        //storagePath = sdCardLocationTreeType + "/" + Image;
                                        contentValues.put("storagePath", "" + storagePath);
                                        contentValues.put("treeType", treeType);
                                        contentValues.put("lastUpdate", Last_Update);
                                        contentValues.put("treeTypeTamil", TreeTypeTamil);
                                        treeTypeNameList.onInsert(contentValues);
                                    } else {
                                        extension = Image.substring(Image.lastIndexOf("."));
                                        //if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                        try {
                                            File file = new File(sdCardLocationTreeType + "/" + Image);
                                            if (file.exists()) {
                                                Log.d("deded 2", i + " exist");
                                                storagePath = sdCardLocationTreeType + "/" + Image;
                                                File file1 = new File(storagePath);
                                                int internalFileLength = (int) file1.length();
                                                Log.d("fileLengthsFromAndroid", "" + internalFileLength + "  ");
                                                if (internalFileLength == 0) {
                                                    String ImageDpwnLoad = fileLocationOnServer + Image;
                                                    Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                    storagePath = downloadFile(ImageDpwnLoad, sdCardLocationTreeType);
                                                }
                                            } else {
                                                Log.d("deded 2", i + " Not exist");
                                                String ImageSpilt = Image.replaceAll(" ", "%20");
                                                String ImageDownload = fileLocationOnServer + ImageSpilt;
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
                                        } catch (Resources.NotFoundException e) {
                                            Image = jsonObject.getString("Image");
                                            Log.d("dassssds", "" + Image);
                                            // return "return";
                                        }
                                        /*} else {
                                            contentValues.put("storagePath", "" + storagePath);
                                            contentValues.put("treeType", treeType);
                                            contentValues.put("lastUpdate", Last_Update);
                                            contentValues.put("treeTypeTamil", TreeTypeTamil);
                                            treeTypeNameList.onInsert(contentValues);
                                        }*/
                                    }
                                } else {
                                    Log.d("comnnnnn", "json2");
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
                         /*   if (result1Array.length() == districtNameList.getCount()) {
                            } else {*/
                            districtNameList.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String District = jsonObject.getString("District").trim();
                                String Last_Update = jsonObject.getString("LastUpdate").trim();
                                String DistrictTamil = jsonObject.getString("DistrictTamil").trim();
                                String treetypes = jsonObject.get("treetypes").toString().toLowerCase();
                                String treetypesTamil = jsonObject.getString("treetypesTamil").toLowerCase();
                                Log.d("DistrictTamiljson", "" + DistrictTamil + "  " + District);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("districtName", District);
                                contentValues.put("lastUpdate", Last_Update);
                                contentValues.put("districtNameTamil", DistrictTamil);
                                contentValues.put("treetypes", treetypes);
                                contentValues.put("treetypesTamil", treetypesTamil);

                                districtNameList.onCreate(contentValues);
                            }
                            //}
                        }

                       /* JSONObject json4Object = parentObject.getJSONObject("Json4");
                        boolean statusMsg4 = json4Object.getBoolean("status");
                        String messageStus4 = json4Object.getString("message");
                        if (statusMsg4) {
                            JSONArray result1Array = json4Object.getJSONArray("result");
                            Log.d("result1Array  4", "" + result1Array);
                            *//*if (result1Array.length() == soilType.getCount()) {
                            } else {*//*
                            soilType.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String District = jsonObject.getString("District").trim();
                                String Soil = jsonObject.getString("Soil").trim();
                                String Last_Update = jsonObject.getString("LastUpdate");

                                ContentValues contentValues = new ContentValues();
                                contentValues.put("districtName", District);
                                contentValues.put("soilType", Soil);
                                contentValues.put("lastUpdate", Last_Update);
                                contentValues.put("districtNameTamil", jsonObject.getString("DistrictTamil").trim());
                                contentValues.put("soilTypeTamil", jsonObject.getString("SoilTamil").trim());
                                soilType.onInsert(contentValues);
                            }
                        }*/
                        // }

                     /*   JSONObject json5Object = parentObject.getJSONObject("Json5");
                        boolean statusMsg5 = json5Object.getBoolean("status");
                        String messageStus5 = json5Object.getString("message");
                        if (statusMsg5) {
                            JSONArray result1Array = json5Object.getJSONArray("result");
                            Log.d("result1Array  5", "" + result1Array);
                            *//*if (result1Array.length() == treeTypeInfo.getCount()) {

                            } else {*//*
                            treeTypeInfo.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String District = jsonObject.getString("District").trim();
                                String Soil = jsonObject.getString("Soil").trim();
                                String Treetype = jsonObject.getString("Treetype").trim();
                                String Last_Update = jsonObject.getString("LastUpdate");
                                if (Treetype.equals("null") || Treetype.isEmpty()) {
                                } else {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("lastUpdate", Last_Update);
                                    contentValues.put("treeType", Treetype);
                                    contentValues.put("soilType", Soil);
                                    contentValues.put("districtNameTamil", jsonObject.getString("DistrictTamil").trim());
                                    contentValues.put("treeTypeTamil", jsonObject.getString("TreetypeTamil").trim());
                                    contentValues.put("soilTypeTamil", jsonObject.getString("SoilTamil").trim());
                                    treeTypeInfo.onInsert(contentValues);

                                }
                            }

                        }*/
                        JSONObject json6Object = parentObject.getJSONObject("Json6");
                        boolean statusMsg6 = json6Object.getBoolean("status");
                        String messageStus6 = json6Object.getString("message");
                        Log.d("Status", "" + statusMsg6);
                        if (statusMsg6) {
                            JSONArray result1Array = json6Object.getJSONArray("result");
                            Log.d("result1Array  6", "" + result1Array.length() + "  " + verifyDetails.getCount());
                          /*  if (result1Array.length() == verifyDetails.getCount()) {
                            } else {*/
                            verifyDetails.delete();
                            imageDb.delete();
                            try {
                                for (int i = 0; i < result1Array.length(); i++) {
                                    if (Config.checkInternetConenction(getActivity())) {
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

                                        Log.d("thumdfdskjfkd", "" + thumbnail);
                                        String District = jsonObject.getString("District").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String SoilType = jsonObject.getString("SoilType").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String RainFall = jsonObject.getString("RainFall").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String TerrainType = jsonObject.getString("TerrainType").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String TreeName = jsonObject.getString("TreeName").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String TreeType = jsonObject.getString("TreeType").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String ScientificName = jsonObject.getString("ScientificName").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
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
                                        String Other_Details = jsonObject.getString("Other_Details").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Other_Details_Tamil = jsonObject.getString("Other_Details_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("" +
                                                "\uF0A7", "\u25CF ");
                                        String Propagation = jsonObject.getString("Propagation").replace("\uF0B7", "\u25CF ").replace("<br/>\t\uF0B7", "\u25CF ").replace("\\r\\n", "<br/>\t").replace("\\n", "").replace("\\r", "".replace("\\t", "")).replace("\\", "").replace("\"", "");
                                        String Propagation_Tamil = jsonObject.getString("Propagation_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");


                                        String artificial_regeneration = jsonObject.getString("artificial_regeneration").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String artificial_regeneration_tamil = jsonObject.getString("artificial_regeneration_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");

                                        String seed_collection = jsonObject.getString("seed_collection").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String seed_collection_tamil = jsonObject.getString("seed_collection_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");

                                        String seed_treatment = jsonObject.getString("seed_treatment").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String seed_treatment_tamil = jsonObject.getString("seed_treatment_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");

                                        String nursery_technique = jsonObject.getString("nursery_technique").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String nursery_technique_tamil = jsonObject.getString("nursery_technique_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");

                                        String Plantation_Technique = jsonObject.getString("Plantation_Technique").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Plantation_Technique_Tamil = jsonObject.getString("Plantation_Technique_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Care_Disease = jsonObject.getString("Care_Disease").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>\t").replace("\\r", "<br/>\t".replace("\\t", "<br/>\t")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Care_Disease_Tamil = jsonObject.getString("Care_Disease_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Irrigation = jsonObject.getString("Irrigation").replace("\\r\\n", "<br/>").replace("\\n", "<br/>\t").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Irrigation_Tamil = jsonObject.getString("Irrigation_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Yield = jsonObject.getString("Yield").replace("\\r\\n", "<br/>").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Yield_Tamil = jsonObject.getString("Yield_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Recommended_Harvest = jsonObject.getString("Recommended_Harvest").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Recommended_Harvest_Tamil = jsonObject.getString("Recommended_Harvest_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Market_Details = jsonObject.getString("Market_Details").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Market_Details_Tamil = jsonObject.getString("Market_Details_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Intercrops = jsonObject.getString("Intercrops").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Intercrops_Tamil = jsonObject.getString("Intercrops_Tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                        String Majar_Uses = jsonObject.getString("Majar_Uses").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
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
                                        //Log.d("Irrigaasasa1245tion", "" + Irrigation).replace("\r\n", "").replace("\n", "").replace("\r", "".replace("\t", "")).replace("\\", "");
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("thumbnail", thumbnail);
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
/*v"artificial_regeneration": "",
                "": "",
                "": "",
                "": "",
                "": "",
                "": "",
                "": "",
                "": "",*/

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
                                                Log.d("ImageFromSplashJson6", i + "  " + thumbnail + "  " + Image + "   " + TreeName);
                                                if (Image.equals("null") || Image.isEmpty()) {
                                                    imageDb.onInsert(TreeName, common_key, TreeNameTamil, storagePath, thumbnail);
                                                } else {
                                                    extension = Image.substring(Image.lastIndexOf("."));
                                                    Log.d("extension", i + "  " + extension);
                                                    //if (extension.toLowerCase().equals(".jpg".toLowerCase()) || extension.toLowerCase().equals(".png".toLowerCase())) {
                                                    try {
                                                        File file = new File(sdCardLocationTreeImages + "/" + Image);
                                                        if (file.exists()) {
                                                            Log.d("deded 2", i + " 6 file  exist");
                                                            storagePath = sdCardLocationTreeImages + "/" + Image;

                                                            File file1 = new File(storagePath);
                                                            int internalFileLength = (int) file1.length();
                                                            Log.d("fileLengthsFromAndroid", "" + internalFileLength + "  ");
                                                            if (internalFileLength == 0) {
                                                                String ImageDpwnLoad = fileLocationOnServer + Image;
                                                                Log.d("ImageImage Zeros", " " + ImageDpwnLoad);
                                                                storagePath = downloadFile(ImageDpwnLoad, sdCardLocationTreeImages);
                                                            }

                                                        } else {
                                                            Log.d("deded 2", i + " 6 Not exist");
                                                            String ImageSplit = Image.replaceAll(" ", "%20");
                                                            String ImageDownload = fileLocationOnServer + ImageSplit;
                                                            Log.d("ImageImage", arr + "  " + ImageDownload);
                                                            storagePath = downloadFile(ImageDownload, sdCardLocationTreeImages);
                                                            Log.d("storagePathSeond", "" + storagePath);

                                                        }
                                                        Log.d("TreeNamesss", "" + TreeName);
                                                        imageDb.onInsert(TreeName, common_key, TreeNameTamil, storagePath, thumbnail);

                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        // return "return";
                                                    }
                                                    /*}
                                                    else{
                                                        imageDb.onInsert(TreeName, common_key, TreeNameTamil, storagePath);
                                                    }*/
                                                }
                                            }
                                        }
                                        Log.d("extension two", "" + i);
                                        contentValues.put("storagePath", "" + storagePath);
                                        verifyDetails.onInsert(contentValues);
                                    } else {
                                        Log.d("comnnnnn", "json6");
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                //  return "return";
                            }
                        }
/*
                        JSONObject json7Object = parentObject.getJSONObject("Json7");
                        boolean statusMsg7 = json7Object.getBoolean("status");
                        String messageStus7 = json7Object.getString("message");
                        if (statusMsg7) {
                            JSONArray result1Array = json7Object.getJSONArray("result");
                            Log.d("result1Array  7", "" + result1Array);
                           *//* if (result1Array.length() == rainfallType.getCount()) {

                            } else {*//*
                            rainfallType.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String District = jsonObject.getString("District").trim();
                                String Rainfall = jsonObject.getString("Rainfall").trim();
                                String Last_Update = jsonObject.getString("LastUpdate").trim();
                                String DistrictTamil = jsonObject.getString("DistrictTamil").trim();
                                String RainfallTamil = jsonObject.getString("RainfallTamil").trim();
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("districtName", District);
                                contentValues.put("Rainfall", Rainfall);
                                contentValues.put("lastUpdate", Last_Update);
                                contentValues.put("districtNameTamil", DistrictTamil);
                                contentValues.put("RainfallTamil", RainfallTamil);
                                rainfallType.onInsert(contentValues);
                            }
                        }*/
                        // }

                        /*JSONObject json8Object = parentObject.getJSONObject("Json8");
                        boolean statusMsg8 = json8Object.getBoolean("status");
                        String messageStus8 = json8Object.getString("message");
                        if (statusMsg8) {

                            JSONArray result1Array = json8Object.getJSONArray("result");
                            Log.d("result1Array  8", "" + result1Array);
                        *//*    if (result1Array.length() == terrainType.getCount()) {

                            } else {*//*
                            terrainType.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String District = jsonObject.getString("District").trim();
                                String Terrain = jsonObject.getString("Terrain").trim();
                                String DistrictTamil = jsonObject.getString("DistrictTamil").trim();
                                String TerrainTamil = jsonObject.getString("TerrainTamil").trim();
                                String Last_Update = jsonObject.getString("LastUpdate");
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("districtName", District);
                                contentValues.put("Terrain", Terrain);
                                contentValues.put("lastUpdate", Last_Update);

                                contentValues.put("districtNameTamil", DistrictTamil);
                                contentValues.put("TerrainTamil", TerrainTamil);
                                terrainType.onInsert(contentValues);

                            }
                        }*/
                        // }

                        JSONObject json9Object = parentObject.getJSONObject("Json9");
                        boolean statusMsg9 = json9Object.getBoolean("status");
                        String messageStus9 = json9Object.getString("message");
                        if (statusMsg9) {
                            JSONArray result1Array = json9Object.getJSONArray("result");
                            Log.d("result1Array  9", "" + result1Array);
                          /*  if (result1Array.length() == contactUs.getCount()) {

                            } else {*/
                            contactUs.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String District = jsonObject.getString("District").trim();
                                String Address = jsonObject.getString("Address").trim();
                                String PhoneNo = jsonObject.getString("PhoneNo").trim();
                                String DistrictTamil = jsonObject.getString("DistrictTamil").trim();
                                String AddressTamil = jsonObject.getString("AddressTamil").trim();
                                String Last_Update = jsonObject.getString("LastUpdate");
                                String directions = jsonObject.getString("Direction").trim();
                                String Email = jsonObject.getString("Email").toString().trim();
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("districtName", District);
                                contentValues.put("address", Address);
                                contentValues.put("lastUpdate", Last_Update);
                                contentValues.put("addressTamil", AddressTamil);
                                contentValues.put("districtNameTamil", DistrictTamil);
                                contentValues.put("phoneNo", PhoneNo);
                                contentValues.put("direction", "" + directions);
                                contentValues.put("Email", Email);
                                contactUs.onInsert(contentValues);
                            }
                        }
                        // }

                        JSONObject json10Object = parentObject.getJSONObject("Json10");
                        boolean statusMsg10 = json10Object.getBoolean("status");
                        String messageStus10 = json10Object.getString("message");
                        if (statusMsg10) {
                            JSONArray result1Array = json10Object.getJSONArray("result");
                            Log.d("result1Array  10", "" + result1Array);
                           /* if (result1Array.length() == glossaryTable.getCount()) {
                                result = "success";
                            } else {*/
                            glossaryTable.delete();
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Word = jsonObject.getString("Word").trim();
                                String WordTamil = jsonObject.getString("WordTamil").trim();
                                String Meaning = jsonObject.getString("Meaning").trim();
                                String MeaningamTil = jsonObject.getString("MeaningTamil").trim();
                                String Last_Update = jsonObject.getString("LastUpdate");
                                Log.d("Word", "" + Word);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("word", Word);
                                contentValues.put("wordTamil", WordTamil);
                                contentValues.put("meaning", Meaning);
                                contentValues.put("meaningTamil", MeaningamTil);
                                contentValues.put("lastUpdate", Last_Update);
                                glossaryTable.onInsert(contentValues);
                            }
                            //result = "success";
                        }

                        JSONObject json11Object = parentObject.getJSONObject("Json11");
                        boolean statusMsg11 = json11Object.getBoolean("status");
                        String messageStus11 = json11Object.getString("message");
                        if (statusMsg11) {
                            JSONArray result1Array = json11Object.getJSONArray("result");
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                Log.d("jsonObject", "" + jsonObject);

                                sch1 = jsonObject.getString("Scheme1");
                                String sch1Tamil = jsonObject.getString("Scheme1_Tamil");
                                String sch2 = jsonObject.getString("Scheme2");
                                String sch2Tamil = jsonObject.getString("Scheme2_Tamil");
                                String sch3 = jsonObject.getString("Scheme3");
                                String sch3Tamil = jsonObject.getString("Scheme3_Tamil");
                                String sch4 = jsonObject.getString("Scheme4");
                                String sch4Tamil = jsonObject.getString("Scheme4_Tamil");

                                //Log.d("32rr5445", " " + sch1 + "  " + sch1Tamil + " " + sch2 + "  " + sch2Tamil + " " + sch3 + "  " + sch3Tamil + " " + sch4 + "  " + sch4Tamil);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("NoOfScheme", "Scheme " + (i + 1));
                                contentValues.put("NoOfSchemeTamil", "திட்டங்கள் " + (i + 1));
                                contentValues.put("sch1", "" + sch1);
                                contentValues.put("sch1Tamil", sch1Tamil);
                                contentValues.put("sch2", "" + sch2);
                                contentValues.put("sch2Tamil", "" + sch2Tamil);
                                contentValues.put("sch3", "" + jsonObject.getString("Scheme3"));
                                contentValues.put("sch3Tamil", "" + jsonObject.getString("Scheme3_Tamil"));
                                contentValues.put("sch4", "" + jsonObject.getString("Scheme4"));
                                contentValues.put("sch4Tamil", "" + jsonObject.getString("Scheme4_Tamil"));

                                Log.d("sdsldksldk", "" + jsonObject.getString("SchemeTitle") + " ksdlskldTamil " + jsonObject.getString("SchemeTitleTamil"));

                                contentValues.put("SchemeTitle", "" + jsonObject.getString("SchemeTitle"));
                                contentValues.put("SchemeTitleTamil", "" + jsonObject.getString("SchemeTitleTamil"));

                                contentValues.put("LastUpdate", "" + jsonObject.getString("LastUpdate"));
                                schecmesDb.onInsert(contentValues);

                            }
                        }
                        JSONObject json12Object = parentObject.getJSONObject("Json12");
                        Log.d("json12Object", "" + json12Object);
                        boolean statusMsg12 = json12Object.getBoolean("status");
                        String messageStus12 = json12Object.getString("message");
                        if (statusMsg12) {
                            JSONArray result1Array = json12Object.getJSONArray("result");
                            Log.d("result1ArrayallSoilType", "" + result1Array);

                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("soilTypeAll", "" + jsonObject.getString("Soil").trim());
                                contentValues.put("soilTypeAllTamil", "" + jsonObject.getString("SoilTamil").trim());
                                contentValues.put("LastUpdate", "" + jsonObject.getString("LastUpdate"));
                                allSoilType.onInsert(contentValues);
                            }
                        }

                        JSONObject json13Object = parentObject.getJSONObject("Json13");
                        boolean statusMsg13 = json13Object.getBoolean("status");
                        String messageStus13 = json13Object.getString("message");
                        if (statusMsg13) {
                            JSONArray result1Array = json13Object.getJSONArray("result");

                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                Log.d("result1ArrayallallTerrainType", "" + jsonObject);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("terrainTypeAll", "" + jsonObject.getString("Terrain").trim());
                                contentValues.put("terrainTypeAllTamil", "" + jsonObject.getString("TerrainTamil").trim());
                                contentValues.put("LastUpdate", "" + jsonObject.getString("LastUpdate"));
                                allTerrainType.onInsert(contentValues);
                            }
                        }
                        JSONObject json14Object = parentObject.getJSONObject("Json14");
                        boolean statusMsg14 = json14Object.getBoolean("status");
                        String messageStus14 = json14Object.getString("message");
                        if (statusMsg14) {
                            JSONArray result1Array = json14Object.getJSONArray("result");
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                Log.d("result1ArrayallallRainFall", "" + jsonObject);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("rainFallAll", "" + jsonObject.getString("Rainfall").trim());
                                contentValues.put("rainFallAllTamil", "" + jsonObject.getString("RainfallTamil").trim());
                                contentValues.put("LastUpdate", "" + jsonObject.getString("LastUpdate"));
                                allRainFall.onInsert(contentValues);
                            }
                        }

                        JSONObject json15Object = parentObject.getJSONObject("Json15");
                        boolean statusMsg15 = json15Object.getBoolean("status");
                        String messageStus15 = json15Object.getString("message");
                        if (statusMsg15) {
                            JSONArray result1Array = json15Object.getJSONArray("result");
                      /*  if (result1Array.length() == treeTypeNameList.getCout()) {
                        } else {*/
                            Log.d("result1Arraytresss15", +treeList.getCount() + "  " + result1Array);
                          /*  if (result1Array.length() >= 10) {
                                insertJson15(result1Array, 1);
                            } else {*/
                            insertJson15(result1Array, 0);
                            //}
                        }

                        JSONObject json16Object = parentObject.getJSONObject("Json16");
                        boolean statusMsg16 = json16Object.getBoolean("status");
                        String messageStus16 = json16Object.getString("message");
                        if (statusMsg16) {
                            JSONArray result1Array = json16Object.getJSONArray("result");
                      /*  if (result1Array.length() == treeTypeNameList.getCout()) {
                        } else {*/
                            Log.d("result1Arraytresss16", +treeList.getCount() + "  " + result1Array);
                          /*  if (result1Array.length() >= 10) {
                                onInsertJson16(result1Array, 1);
                            } else {*/
                            onInsertJson16(result1Array, 0);
                            //}
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return "server";
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
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("reuslfdkjfdfjo", "" + s);
            dialog.dismiss();
            dialog = null;
            try {
                loaderTask.cancel(true);
                if (s != null && !s.isEmpty())
                    if (s.equals("success")) {
                        getActivity().startService(new Intent(getActivity(), ImagesDownloadService.class));
                        HashMap<String, String> stringHashMap = statusForDownload.getStatus();
                        String statusDownload = stringHashMap.get("status");
                        String idDownload = stringHashMap.get("id");
                        if (idDownload == null) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("status", "1");
                            statusForDownload.onInsert(contentValues);
                        } else {
                            int status = Integer.parseInt(statusDownload) + 1;
                            statusForDownload.onUpdate(idDownload, status);
                        }
                        mTamil.setVisibility(View.VISIBLE);
                        mEnglish.setVisibility(View.VISIBLE);
                    } else if (s.contains("server")) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.server_500), Toast.LENGTH_LONG).show();
                        mRefresh.setVisibility(View.VISIBLE);
                    } else if (s.equals("hostname")) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.server_not_found), Toast.LENGTH_LONG).show();
                        mRefresh.setVisibility(View.VISIBLE);
                    } else if (s.equals("return")) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.netConnection), Toast.LENGTH_LONG).show();
                        mRefresh.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.netConnection), Toast.LENGTH_LONG).show();
                        mRefresh.setVisibility(View.VISIBLE);
                    }
            } catch (NullPointerException e) {
                Log.d("NullFromSplashScree", String.valueOf(e));

            }
        }

    }

    private void insertJson15(JSONArray result1Array, int point) throws JSONException {
        int length = 0;
        if (0 == point) {
            length = result1Array.length();
        } else if (1 == point) {
            length = 10;
        }
        Log.d("result1Arraytresss151515", "" + point);
        for (int i = 0; i < length; i++) {
            if (Config.checkInternetConenction(getActivity())) {
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
                    } catch (Resources.NotFoundException e) {
                        Image = jsonObject.getString("Image");
                        Log.d("dassssds", "" + Image);
                        // return "return";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private void onInsertJson16(JSONArray result1Array, int point) throws JSONException {
        int length = 0;
        if (0 == point) {
            Log.d("json16values", "" + point);
            length = result1Array.length();
        } else if (1 == point) {
            Log.d("json16values", " 0 0 " + point);
            length = 10;
        }

        for (int i = 0; i < length; i++) {
            if (Config.checkInternetConenction(getActivity())) {
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
                    } catch (Resources.NotFoundException e) {
                        Image = jsonObject.getString("Image");
                        Log.d("dassssdsinter", "" + Image);
                        // return "return";
                    } catch (IOException e) {


                    }

                }
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
