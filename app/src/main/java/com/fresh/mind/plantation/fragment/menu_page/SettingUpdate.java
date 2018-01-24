package com.fresh.mind.plantation.fragment.menu_page;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.Config;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomSpinner;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.server.AllRainFall;
import com.fresh.mind.plantation.sqlite.server.AllSoilType;
import com.fresh.mind.plantation.sqlite.server.AllTerrainType;
import com.fresh.mind.plantation.sqlite.sorting.SortOrder;
import com.fresh.mind.plantation.tab_pager.HomeTabView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.ContactUs;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.RainfallType;
import com.fresh.mind.plantation.sqlite.server.SoilType;
import com.fresh.mind.plantation.sqlite.server.TerrainType;
import com.fresh.mind.plantation.sqlite.server.TreeList;
import com.fresh.mind.plantation.sqlite.server.TreeTypeInfo;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;

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
import java.io.ByteArrayOutputStream;
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
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fresh.mind.plantation.Constant.Config.celsiu;
import static com.fresh.mind.plantation.Constant.Config.fileLocationOnServer;
import static com.fresh.mind.plantation.Constant.Config.updateCounting;


/**
 * Created by AND I5 on 18-02-2017.
 */
public class SettingUpdate extends Fragment {
    private CustomTextView mUpdate, textView12, languageChageTxt;
    private LinearLayout languageChage;
    private ImageView back;
    private View rootView;

    private Asyn asyn;
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
    private AsynCheck asynCheck;
    private LanguageChange languageChange;
    private CustomSpinner mSortBySpinner;
    private int downloadedSize = 0;
    private int totalSize = 0;
    public static ProgressDialog progressDialog;
    private CustomTextView mCheck;
    private String filePath;/*= "http://plantation.kambaa.com/admin2017/images/izysxI916lcNefETrRtGQqtGFIFtf9.mp4";*/
    private String[] SortOrderList = {"A-Z ", "Z-A"};
    private SortOrder sortOrder;

    private AllRainFall allRainFall;
    private AllSoilType allSoilType;
    private AllTerrainType allTerrainType;
/*
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        treeList = new TreeList(getActivity());
        districtNameList = new DistrictNameList(getActivity());
        treeTypeNameList = new TreeTypeNameList(getActivity());
        verifyDetails = new VerifyDetails(getActivity());
        soilType = new SoilType(getActivity());
        rainfallType = new RainfallType(getActivity());
        terrainType = new TerrainType(getActivity());
        treeTypeInfo = new TreeTypeInfo(getActivity());
        imageDb = new ImageDb(getActivity());
        contactUs = new ContactUs(getActivity());
        sortOrder = new SortOrder(getActivity());
        allRainFall = new AllRainFall(getActivity());
        allSoilType = new AllSoilType(getActivity());
        allTerrainType = new AllTerrainType(getActivity());
        updateCounting = 0;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        Config.SELECTED_TAB_VIEW_POSITION = 0;
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }

        ((MainActivity) getActivity()).setUpdatePage(getActivity().getString(R.string.UpdateData));
        //setContentView(R.layout.update_view);
        rootView = inflater.inflate(R.layout.update_view, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        mUpdate = (CustomTextView) rootView.findViewById(R.id.mUpdate);
        back = (ImageView) rootView.findViewById(R.id.back);
        textView12 = (CustomTextView) rootView.findViewById(R.id.textView12);
        languageChage = (LinearLayout) rootView.findViewById(R.id.languageChage);
        languageChageTxt = (CustomTextView) rootView.findViewById(R.id.languageChageTxt);
        mCheck = (CustomTextView) rootView.findViewById(R.id.mCheck);
        mSortBySpinner = (CustomSpinner) rootView.findViewById(R.id.mSortBySpinner);
        mSortBySpinner.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.adapter_spinner, R.id.mSPinnerText, SortOrderList));
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            languageChageTxt.setBackground(getActivity().getDrawable(R.drawable.riiple_oval));
            mCheck.setBackground(getActivity().getDrawable(R.drawable.riiple_oval));

        }
        treeList = new TreeList(getActivity());
        Log.d("das32143", "" + Config.celsiu);

        if (languages.equals("1")) {
            languageChageTxt.setText("English");
        } else {
            languageChageTxt.setText("தமிழ்");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mUpdate.setBackground(getActivity().getDrawable(R.drawable.riiple_oval));
            back.setBackground(getActivity().getDrawable(R.drawable.ripple_back));
        }

        Log.d("s2342354", "" + Config.main);
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

        mUpdate.setVisibility(View.VISIBLE);
        Log.d("updateCountingForUpdatePage", "" + updateCounting);
        if (updateCounting == 0) {
            mUpdate.setVisibility(View.GONE);
            textView12.setVisibility(View.GONE);
        } else {
            mUpdate.setVisibility(View.VISIBLE);
            textView12.setVisibility(View.VISIBLE);
        }
        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.checkInternetConenction(getActivity())) {

                    textView12.setVisibility(View.GONE);
                    mUpdate.setVisibility(View.GONE);

                    asynCheck = new AsynCheck();
                    asynCheck.execute();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.netConnection), Toast.LENGTH_LONG).show();
                }
            }
        });
        mSortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String byType = SortOrderList[position];
                Log.d("ddsds", "" + byType);
                int count = sortOrder.getCount();
                if (count >= 1) {
                    sortOrder.update(byType, 1);
                } else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("byType", byType);
                    sortOrder.onInsert(contentValues);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        Bundle bundle = getArguments();
        String task = bundle.getString("task");*/
/*
        if (task.equals("update")) {
            mUpdate.setVisibility(View.VISIBLE);
        } else {
            mUpdate.setVisibility(View.GONE);
            asynCheck = new AsynCheck();
            asynCheck.execute();
        }*/


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new UpdateAsynTask().execute();
            }
        });

        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.pass = "not";
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                getActivity().overridePendingTransition(0, 0);

            }
        });
        languageChage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update();

                //new UpdateLanguage().execute();

            }
        });
        return rootView;
    }


    void downloadFile() {
        File dir = new File("/sdcard/Plantation/");
        File[] mp3List = dir.listFiles();
        if (mp3List != null) {
            for (File fileFilter : mp3List) {
                String name = fileFilter.getName();
                Log.d("s87hj", "" + name);
                String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
                if (name.equals(fileName)) {
                    Log.d("drjh78943yhe", "File Exist");
                } else {
                    getFile(filePath.substring(filePath.lastIndexOf('/') + 1));
                }
            }
        } else {
            getFile(filePath.substring(filePath.lastIndexOf('/') + 1));
        }
    }

    private void getFile(String fileName) {
        try {
            URL url = new URL(filePath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File wallpaperDirectory = new File("/sdcard/Plantation/");
            wallpaperDirectory.mkdirs();
            File file = new File(wallpaperDirectory, fileName);
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            totalSize = urlConnection.getContentLength();
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
            }
            fileOutput.close();
            File files = new File(wallpaperDirectory, ".nomedia");
            try {
                files.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(files, true));
                writer.write("This is a test trace file.");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getActivity(), new String[]{files.toString()}, null, null);
        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);
            e.printStackTrace();
        } catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }
    }

    private void getFiles(File dir) {

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    Log.d("fi12487le", "" + file);
                } else {
                    if (file.getName().endsWith(".mp4")) {
                        Log.d("fi12487le123214234", "" + file);
                    }
                }
            }
        }
    }

    private void showError(String s) {
        Log.d("adsfsadf", "" + s);
    }

    private void deleteAll() {
        treeList.delete();
        districtNameList.delete();
        treeTypeNameList.delete();
        verifyDetails.delete();
        soilType.delete();
        rainfallType.delete();
        terrainType.delete();
        treeTypeInfo.delete();
        allTerrainType.delete();
        allSoilType.delete();
        allRainFall.delete();
        contactUs.delete();
    }

    public class UpdateLanguage extends AsyncTask<HashMap<String, String>, Void, HashMap<String, String>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.show();
        }

        @Override
        protected HashMap<String, String> doInBackground(HashMap<String, String>... params) {
            int count = languageChange.getCount();
            HashMap<String, String> languageStatus;
            if (count == 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("languages", "1");
                languageChange.onInsert(contentValues);
                languageStatus = languageChange.getLanguageStatus();
                Utils.setLocalLanguage("ta", getActivity());
            } else {
                languageStatus = languageChange.getLanguageStatus();
            }
            return languageStatus;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> languageStatus) {
            super.onPostExecute(languageStatus);
            //Log.d("languageStatus", "" + languageStatus);
            if (languageStatus != null) {
                String status = languageStatus.get("languages");
                String id = languageStatus.get("id");
                if (status.equals("1")) {
                    languageChageTxt.setText(getResources().getString(R.string.English));
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("languages", "2");
                    languageChange.update("2", Integer.parseInt(id));
                } else {
                    languageChageTxt.setText(getResources().getString(R.string.Tamil));
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("languages", "1");
                    languageChange.update("1", Integer.parseInt(id));
                }

                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new MainActivity()).commit();
                Config.LANGUAGE_UPDATE_STATUS = 0;
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        }
    }

    private void update() {
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
            if (status.equals("1")) {
                //  languageChageTxt.setText(getResources().getString(R.string.English));
                ContentValues contentValues = new ContentValues();
                contentValues.put("languages", "2");
                languageChange.update("2", Integer.parseInt(id));
            } else {
                //languageChageTxt.setText(getResources().getString(R.string.Tamil));
                ContentValues contentValues = new ContentValues();
                contentValues.put("languages", "1");
                languageChange.update("1", Integer.parseInt(id));
            }
         /*   progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.show();*/
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new Sesss()).commit();
            //mLocation.clear();
            Config.mImages.clear();
            Config.mImagePaths.clear();
            Config.mTREE_TYPES_IMAGES.clear();
            Config.mTREE_TYPES.clear();

            Config.LANGUAGE_UPDATE_STATUS = 0;
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
            getActivity().overridePendingTransition(0, 0);
        }
    }

    public class UpdateAsynTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Config.url);
            InputStream is = null;
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("task", "getdata"));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                result = sb.toString();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            deleteAll();
            if (s != null) {
                Log.d("Response", "" + s);
            }

            progressDialog.dismiss();
          /*  Intent intent = new Intent(UpdateActiity.this, MainActivity.class);
            startActivity(intent);
            finish();*/
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SplashScreen()).commit();

        }


    }

    public class Asyn extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Config.url);
            InputStream is = null;
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("task", "getdata"));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                result = sb.toString();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Log.d("Response", "" + s);

                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(s);
                    JSONObject json1Object = parentObject.getJSONObject("Json1");
                    boolean status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");

                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String mLastDate_fromDb = treeList.getLastDate(Last_Update_fromServer);

                            Log.d("mLastDLast_Updateate 1", "" + mLastDate_fromDb + "  " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                Log.d("whatCome", "Fine 1");
                            } else {
                                Log.d("whatCome", "Update 1");
                                String TreeName = jsonObject.getString("TreeName");
                                String TreeType = jsonObject.getString("TreeType");
                                String Scientific_Name = jsonObject.getString("Scientific Name");
                                String District = jsonObject.getString("District");
                                String common_key = jsonObject.getString("common_key");
                                String Image = jsonObject.getString("Images");
                                String Last_Update = jsonObject.getString("LastUpdate");
                                Image = Image.replaceAll(" ", "%20");
                                Image = fileLocationOnServer + Image;
                                Log.d("ImageImage", " " + Image);
                                URL imageUrl = new URL(Image);
                                URLConnection ucon = imageUrl.openConnection();
                                InputStream is = ucon.getInputStream();
                                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                                int bufferSize = 1024;
                                byte[] buffer = new byte[bufferSize];

                                int len = 0;
                                while ((len = is.read(buffer)) != -1) {
                                    byteBuffer.write(buffer, 0, len);
                                }
                                byte[] imgByte = byteBuffer.toByteArray();

                                String TreeNameDb = treeList.getTreeName(TreeName);
                                Log.d("TreeName", "" + TreeName + "  " + TreeNameDb);
                                if (TreeNameDb != null) {
                                    if (TreeNameDb.equals(TreeName)) {
                                        int id = treeList.getId(TreeNameDb);
                                        treeList.update(id, imgByte, TreeType, District, Scientific_Name, TreeName, Last_Update);
                                    }
                                } else {

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("treeIcon", imgByte);
                                    contentValues.put("treeType", TreeType);
                                    contentValues.put("districtName", "" + District);
                                    contentValues.put("treeNameTamil", Scientific_Name);
                                    contentValues.put("treeNameEng", TreeName);
                                    contentValues.put("lastUpdate", Last_Update);
                                    // treeList.onInsertTreeType(contentValues);
                                }


                            }
                        }
                    }

                    json1Object = parentObject.getJSONObject("Json2");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        Log.d("Adsad", "" + result1Array.length() + "  " + treeTypeNameList.getCout());
                        if (result1Array.length() >= treeTypeNameList.getCout()) ;
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);

                            String Image = jsonObject.getString("Image");
                            Image = Image.replaceAll(" ", "%20");
                            Image = fileLocationOnServer + Image;
                            Log.d("ImageImage TreeType", " " + Image);
                            URL imageUrl = new URL(Image);
                            URLConnection ucon = imageUrl.openConnection();
                            InputStream is = ucon.getInputStream();
                            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                            int bufferSize = 1024;
                            byte[] buffer = new byte[bufferSize];
                            int len = 0;
                            while ((len = is.read(buffer)) != -1) {
                                byteBuffer.write(buffer, 0, len);
                            }
                            byte[] imgByte = byteBuffer.toByteArray();

                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String mLastDate_fromDb = treeTypeNameList.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate 2", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine 2");
                            } else {
                                Log.d("whatCome", "Update 2");
                                String treeType = jsonObject.getString("TreeType");
                                String treeTypeDb = treeTypeNameList.getTreeType(treeType);
                                if (treeTypeDb != null) {
                                    if (treeType.equals(treeTypeDb)) {
                                        int id = treeTypeNameList.getId(treeTypeDb);

                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("treeType", treeType);
                                        contentValues.put("lastUpdate", Last_Update_fromServer);
                                        contentValues.put("treeIcon", imgByte);
                                        treeTypeNameList.update(contentValues, id);
                                    } else {
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("treeType", treeType);
                                        contentValues.put("lastUpdate", Last_Update_fromServer);
                                        contentValues.put("treeIcon", imgByte);
                                        treeTypeNameList.onInsert(contentValues);
                                    }
                                } else {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("treeType", treeType);
                                    contentValues.put("lastUpdate", Last_Update_fromServer);
                                    contentValues.put("treeIcon", imgByte);
                                    treeTypeNameList.onInsert(contentValues);
                                }
                            }
                        }
                    }

                    json1Object = parentObject.getJSONObject("Json3");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");

                      /*  int distCount = districtNameList.getCount();
                        if (distCount == result1Array.length()) {
                        } else {*/
                        districtNameList.delete();
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String District = jsonObject.getString("District");
                            String Last_Update = jsonObject.getString("LastUpdate");
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("districtName", District);
                            contentValues.put("lastUpdate", Last_Update);

                            districtNameList.onCreate(contentValues);
                            Log.d("Insertrt", "Successs");
                            //}
                        }
                    }
/*
                    json1Object = parentObject.getJSONObject("Json4");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String District = jsonObject.getString("District");
                            String Soil = jsonObject.getString("Soil");
                            String Treetype = jsonObject.getString("Treetype");

                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String mLastDate_fromDb = soilType.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate 4", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine 4");
                            } else {
                                Log.d("whatCome", "Update 4");
                                String TreetypeDb = soilType.getTreeTypeName(Treetype);
                                if (TreetypeDb != null) {
                                    int id = soilType.getId(TreetypeDb);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("lastUpdate", Last_Update_fromServer);
                                    contentValues.put("treeType", Treetype);
                                    contentValues.put("soilType", Soil);

                                    soilType.update(contentValues, id);
                                } else {

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("lastUpdate", Last_Update_fromServer);
                                    contentValues.put("treeType", Treetype);
                                    contentValues.put("soilType", Soil);

                                    treeTypeInfo.onInsert(contentValues);
                                }
                            }
                        }
                    }*/

                   /* json1Object = parentObject.getJSONObject("Json5");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String mLastDate_fromDb = treeTypeInfo.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate 5", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine 5");
                            } else {
                                Log.d("whatCome", "Update 5");

                            }
                        }
                    }*/

                    json1Object = parentObject.getJSONObject("Json6");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String District = jsonObject.getString("District");
                            String Soil = jsonObject.getString("Soil Type");
                            String Rain_Fall = jsonObject.getString("Rain Fall");
                            String Terrain_Type = jsonObject.getString("Terrain Type");
                            String TreeType = jsonObject.getString("TreeType");
                            String Scientific_Name = jsonObject.getString("Scientific Name");

                            String Solid = jsonObject.getString("Solid");
                            String Altitude = jsonObject.getString("Altitude");
                            String Climate = jsonObject.getString("Climate");
                            String Rainfall = jsonObject.getString("Rainfall");
                            String Terrain = jsonObject.getString("Terrain");
                            String Plantation_Material = jsonObject.getString("Plantation Material");
                            String Plantation_Technique = jsonObject.getString("Plantation Technique");
                            String Other_Benefits = jsonObject.getString("Other Benefits");
                            String Care = jsonObject.getString("Care");
                            String Irrigation = jsonObject.getString("Irrigation");

                            String Recommended_Harvest = jsonObject.getString("Recommended Harvest");
                            String Recommended_Combinations = jsonObject.getString("Recommended Combinations");
                            String Recommended_Model = jsonObject.getString("Recommended Model");
                            String Disease_control = jsonObject.getString("Disease control");
                            String Intercropping = jsonObject.getString("Intercropping");

                            String Other = jsonObject.getString("Other");
                            String Benefits = jsonObject.getString("Benefits");
                            String Additional = jsonObject.getString("Additional");
                            String Last_Update = jsonObject.getString("LastUpdate");
                            String common_key = jsonObject.getString("common_key");

                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String mLastDate_fromDb = verifyDetails.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate 6", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);

                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine 6");
                            } else {
                                Log.d("whatCome", "Update 6");
                                String TreeName = jsonObject.getString("TreeName");
                                String TreeNameDb = verifyDetails.getTreeName(TreeName);
                                if (TreeNameDb != null) {
                                    if (TreeNameDb.equals(TreeName)) {
                                        int id = verifyDetails.getId(TreeNameDb);

                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("districtName", District);
                                        contentValues.put("Soil", Soil);
                                        contentValues.put("Rain_Fall", Rain_Fall);
                                        contentValues.put("Terrain_Type", Terrain_Type);
                                        contentValues.put("TreeType", TreeType);
                                        contentValues.put("TreeName", TreeName);
                                        contentValues.put("Scientific_Name", Scientific_Name);
                                        contentValues.put("Solid", Solid);
                                        contentValues.put("Altitude", Altitude);
                                        contentValues.put("Climate", Climate);
                                        contentValues.put("Rainfall", Rainfall);
                                        contentValues.put("Terrain", Terrain);
                                        contentValues.put("Plantation_Material", Plantation_Material);
                                        contentValues.put("Plantation_Technique", Plantation_Technique);
                                        contentValues.put("Other_Benefits", Other_Benefits);
                                        contentValues.put("Care", Care);
                                        contentValues.put("Irrigation", Irrigation);
                                        contentValues.put("Recommended_Harvest", Recommended_Harvest);
                                        contentValues.put("Recommended_Combinations", Recommended_Combinations);
                                        contentValues.put("Recommended_Model", Recommended_Model);
                                        contentValues.put("Intercropping", Intercropping);
                                        contentValues.put("Disease_control", Disease_control);
                                        contentValues.put("Other", Other);
                                        contentValues.put("Benefits", Benefits);
                                        contentValues.put("Additional", Additional);
                                        contentValues.put("common_key", common_key);
                                        contentValues.put("lastUpdate", Last_Update);
                                        verifyDetails.update(contentValues, id);
                                        JSONArray Images = jsonObject.getJSONArray("Images");


                                        for (int arr = 0; arr < Images.length(); arr++) {
                                            JSONObject jsonObject1 = Images.getJSONObject(arr);
                                            String Image = jsonObject1.getString("Image");
                                            Image = Image.replaceAll(" ", "%20");
                                            Image = fileLocationOnServer + Image;
                                            Log.d("ImageImage", " " + Image);
                                            URL imageUrl = new URL(Image);
                                            URLConnection ucon = imageUrl.openConnection();
                                            InputStream is = ucon.getInputStream();
                                            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                                            int bufferSize = 1024;
                                            byte[] buffer = new byte[bufferSize];

                                            int len = 0;
                                            while ((len = is.read(buffer)) != -1) {
                                                byteBuffer.write(buffer, 0, len);
                                            }
                                            byte[] imgByte = byteBuffer.toByteArray();
                                            //imageDb.update(imgByte, TreeName, common_key);
                                            String treeNameDb = imageDb.getTreeName(TreeName);
                                            Log.d("treeNameDb", "" + treeNameDb);
                                            imageDb.delteTreeNameImage(treeNameDb);
                                            //int idImageDb=imageDb.getId(treeNameDb);
                                            //  imageDb.onInsert(imgByte, TreeName, common_key);

                                        }
                                    }
                                } else {

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("districtName", District);
                                    contentValues.put("Soil", Soil);
                                    contentValues.put("Rain_Fall", Rain_Fall);
                                    contentValues.put("Terrain_Type", Terrain_Type);
                                    contentValues.put("TreeType", TreeType);
                                    contentValues.put("TreeName", TreeName);
                                    contentValues.put("Scientific_Name", Scientific_Name);
                                    contentValues.put("Solid", Solid);
                                    contentValues.put("Altitude", Altitude);
                                    contentValues.put("Climate", Climate);
                                    contentValues.put("Rainfall", Rainfall);
                                    contentValues.put("Terrain", Terrain);
                                    contentValues.put("Plantation_Material", Plantation_Material);
                                    contentValues.put("Plantation_Technique", Plantation_Technique);
                                    contentValues.put("Other_Benefits", Other_Benefits);
                                    contentValues.put("Care", Care);
                                    contentValues.put("Irrigation", Irrigation);
                                    contentValues.put("Recommended_Harvest", Recommended_Harvest);
                                    contentValues.put("Recommended_Combinations", Recommended_Combinations);
                                    contentValues.put("Recommended_Model", Recommended_Model);
                                    contentValues.put("Intercropping", Intercropping);
                                    contentValues.put("Disease_control", Disease_control);
                                    contentValues.put("Other", Other);
                                    contentValues.put("Benefits", Benefits);
                                    contentValues.put("Additional", Additional);
                                    contentValues.put("common_key", common_key);
                                    contentValues.put("lastUpdate", Last_Update);
                                    verifyDetails.onInsert(contentValues);
                                    JSONArray Images = jsonObject.getJSONArray("Images");

                                    for (int arr = 0; arr < Images.length(); arr++) {
                                        JSONObject jsonObject1 = Images.getJSONObject(arr);
                                        String Image = jsonObject1.getString("Image");
                                        Image = Image.replaceAll(" ", "%20");
                                        Image = fileLocationOnServer + Image;
                                        Log.d("ImageImage", " " + Image);
                                        URL imageUrl = new URL(Image);
                                        URLConnection ucon = imageUrl.openConnection();
                                        InputStream is = ucon.getInputStream();
                                        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                                        int bufferSize = 1024;
                                        byte[] buffer = new byte[bufferSize];

                                        int len = 0;
                                        while ((len = is.read(buffer)) != -1) {
                                            byteBuffer.write(buffer, 0, len);
                                        }
                                        byte[] imgByte = byteBuffer.toByteArray();
                                        //imageDb.onInsert(imgByte, TreeName, common_key);
                                    }
                                }
                            }
                        }
                    }
                   /* json1Object = parentObject.getJSONObject("Json7");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String District = jsonObject.getString("District");
                            String Rainfall = jsonObject.getString("Rainfall");

                            String mLastDate_fromDb = rainfallType.getLastDate(District, Rainfall);
                            Log.d("mLastDLast_Updateat 7e", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                if (mLastDate_fromDb.equals(Last_Update_fromServer)) {
                                    Log.d("whatCome", "Fine 7");
                                } else {
                                    Log.d("whatCome", "Update 777");
                                }
                            } else {
                                Log.d("whatCome", "Update 7");
                            }
                        }
                    }*/
                    /*json1Object = parentObject.getJSONObject("Json8");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String District = jsonObject.getString("District");
                            String Terrain = jsonObject.getString("Terrain");

                            String mLastDate_fromDb = terrainType.getLastDate(District, Terrain);
                            Log.d("mLastDLast_Updateate 8", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                if (mLastDate_fromDb.equals(Last_Update_fromServer)) {
                                    Log.d("whatCome", "Fine 8");
                                } else {
                                    Log.d("whatCome", "Update 88");
                                }
                            } else {
                                Log.d("whatCome", "Update 8");
                            }
                        }
                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            progressDialog.dismiss();
            /*Intent intent = new Intent(UpdateActiity.this, MainActivity.class);
            startActivity(intent);
            finish();*/
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SplashScreen()).commit();

        }

    }


    public class AsynCheck extends AsyncTask<String, Void, Integer> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());

            progressDialog.setTitle(null);
            progressDialog.setMessage(getString(R.string.chekingUpdates));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            String s = null;
            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Config.url);
                InputStream is = null;
                List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("task", "getdata"));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                s = sb.toString();
                Log.d("Responsevvv", "" + s);
                if (s.isEmpty()) {
                    Log.d("Response", "" + s);
                    return -2;
                } else {
                    JSONObject parentObject = null;
                    try {
                        parentObject = new JSONObject(s);
                        JSONObject json1Object = parentObject.getJSONObject("Json1");
                        boolean status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String TreeName = jsonObject.getString("TreeName");
                                if (TreeName.equals("null") || TreeName.isEmpty()) {
                                } else {
                                    String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                    String mLastDate_fromDb = treeList.getLastDate(Last_Update_fromServer);
                                    Log.d("mLastDLast_Updateate_one  ", i + "  " + mLastDate_fromDb + "  " + Last_Update_fromServer);
                                    if (mLastDate_fromDb != null) {
                                        if (Last_Update_fromServer.equals(mLastDate_fromDb)) {
                                            Log.d("whatCome", "Fine 111");
                                        } else {
                                            Log.d("whatCome", "Fine 11 not es");
                                        }
                                    } else {
                                        //  Log.d("whatCome", "Update 12 ");
                                        updateCounting = updateCounting + 1;
                                    }
                                }
                            }
                        }

                        json1Object = parentObject.getJSONObject("Json2");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");

                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                String mLastDate_fromDb = treeTypeNameList.getLastDate(Last_Update_fromServer);
                                Log.d("mLastDLast_Updateate", "22 mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    if (Last_Update_fromServer.equals(mLastDate_fromDb)) {
                                        Log.d("whatCome", "Fine 22");
                                    } else {
                                        //   Log.d("whatCome", "Fine 12 not es");
                                    }
                                } else {
                                    //  Log.d("whatCome", "Update ");
                                    updateCounting = updateCounting + 1;
                                }
                            }
                        }

                        json1Object = parentObject.getJSONObject("Json3");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                String mLastDate_fromDb = districtNameList.getLastDate(Last_Update_fromServer);
                                Log.d("mLastDLast_Updateate", "33 mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    if (Last_Update_fromServer.equals(mLastDate_fromDb)) {
                                        Log.d("whatCome", "Fine 33");
                                    } else {
                                        Log.d("whatCome", "Fine 33 not es");
                                    }
                                } else {
                                    //  Log.d("whatCome", "Update ");
                                    updateCounting = updateCounting + 1;
                                }
                            }
                        }
/*
                        json1Object = parentObject.getJSONObject("Json4");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                String mLastDate_fromDb = soilType.getLastDate(Last_Update_fromServer);
                                Log.d("mLastDLast_Updateate", "44 mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    if (Last_Update_fromServer.equals(mLastDate_fromDb)) {
                                        Log.d("whatCome", "Fine 444");
                                    } else {
                                        Log.d("whatCome", "Fine 44 not es");
                                    }
                                } else {
                                    //  Log.d("whatCome", "Update ");
                                    updateCounting = updateCounting + 1;

                                }
                            }
                        }

                        json1Object = parentObject.getJSONObject("Json5");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            Log.d("sdresult1Array", "" + result1Array.length());
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                String mLastDate_fromDb = treeTypeInfo.getLastDate(Last_Update_fromServer);
                                Log.d("mLastDLast_Updateate", "55 mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    if (Last_Update_fromServer.equals(mLastDate_fromDb)) {
                                        Log.d("whatCome", "Fine 55  " + i);
                                    } else {
                                        Log.d("whatCome", "Fine 55 not es  " + i);
                                    }
                                } else {
                                    // Log.d("whatCome", "Update ");
                                    updateCounting = updateCounting + 1;

                                }
                            }
                        }*/

                        json1Object = parentObject.getJSONObject("Json6");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                String mLastDate_fromDb = verifyDetails.getLastDate(Last_Update_fromServer);
                                Log.d("mLastDLast_Updateate", "66 mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    if (Last_Update_fromServer.equals(mLastDate_fromDb)) {
                                        Log.d("whatCome", "Fine 66");
                                    } else {
                                        Log.d("whatCome", "Fine 66 not es");
                                    }
                                } else {
                                    // Log.d("whatCome", "Update 66");
                                    updateCounting = updateCounting + 1;
                                    return updateCounting;
                                }
                            }
                        }/*
                        json1Object = parentObject.getJSONObject("Json7");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            *//*String mLastDate_fromDb = rainfallType.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine");
                            } else {
                                Log.d("whatCome", "Update ");
                                updateCounting = updateCounting + 1;
                            }*//*
                            }
                        }*/
                        json1Object = parentObject.getJSONObject("Json8");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            /*String mLastDate_fromDb = terrainType.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine");
                            } else {
                                Log.d("whatCome", "Update ");
                                updateCounting = updateCounting + 1;
                            }*/
                            }
                        }

                        JSONObject json9Object = parentObject.getJSONObject("Json9");
                        boolean statusMsg9 = json9Object.getBoolean("status");
                        String messageStus9 = json9Object.getString("message");
                        if (statusMsg9) {
                            JSONArray result1Array = json9Object.getJSONArray("result");
                            //Log.d("result1Array  9", "" + result1Array.length());
                        }
                        JSONObject json10Object = parentObject.getJSONObject("Json10");
                        boolean statusMsg10 = json10Object.getBoolean("status");
                        String messageStus10 = json10Object.getString("message");
                        if (statusMsg10) {
                            JSONArray result1Array = json10Object.getJSONArray("result");
                            //  Log.d("result1Array  10", "" + result1Array.length());
                            return updateCounting;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        return -1;
                        //startService(new Intent(getApplicationContext(), UpdateData.class));
                    }
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return -1;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
            return updateCounting;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            Log.d("updateCounting", "" + s);
            progressDialog.dismiss();
            mCheck.setVisibility(View.GONE);
            if (s >= 1) {
                Log.d("smapeldd55454", "111111  " + s);
                textView12.setVisibility(View.VISIBLE);
                mUpdate.setVisibility(View.VISIBLE);
                textView12.setText(getString(R.string.updateAvailable));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SplashScreen()).commit();
            } else if (s == -2) {
                Log.d("smapeldd55454", "22222  " + s);
                textView12.setText(getString(R.string.upToDateServerIssue));
                textView12.setVisibility(View.GONE);
                mCheck.setVisibility(View.VISIBLE);
                mCheck.setText(R.string.tryAgain);
            } else {
                Log.d("smapeldd55454", "3333  " + s);
                textView12.setText(getString(R.string.tryAgain));
                textView12.setVisibility(View.GONE);
                mCheck.setVisibility(View.VISIBLE);
                mCheck.setText(R.string.tryAgain);
            }
        }
    }
}
