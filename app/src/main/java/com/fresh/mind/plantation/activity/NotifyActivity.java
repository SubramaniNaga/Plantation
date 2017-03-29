package com.fresh.mind.plantation.activity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.menu_page.SplashScreen;
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
import org.apache.http.ParseException;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fresh.mind.plantation.Constant.Config.updateCounting;
import static com.fresh.mind.plantation.Constant.Config.url;


/**
 * Created by AND I5 on 08-03-2017.
 */

public class NotifyActivity extends AppCompatActivity {
    CustomTextView mUpdate, textView12, languageChageTxt;
    ImageView back;

    private ImageView img;
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
    private Asyn asynCheck;
    private LinearLayout languageChage;
    LanguageChange languageChange;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();


        treeList = new TreeList(getApplicationContext());
        districtNameList = new DistrictNameList(getApplicationContext());
        treeTypeNameList = new TreeTypeNameList(getApplicationContext());
        verifyDetails = new VerifyDetails(getApplicationContext());
        soilType = new SoilType(getApplicationContext());
        rainfallType = new RainfallType(getApplicationContext());
        terrainType = new TerrainType(getApplicationContext());
        treeTypeInfo = new TreeTypeInfo(getApplicationContext());
        imageDb = new ImageDb(getApplicationContext());
        contactUs = new ContactUs(getApplicationContext());
        updateCounting = 0;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        languageChange = new LanguageChange(getApplicationContext());
        String languages = languageChange.getStatus();
        Config.SELECTED_TAB_VIEW_POSITION = 0;
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", NotifyActivity.this);
        } else {
            Utils.setLocalLanguage("en", NotifyActivity.this);
        }

        //((MainActivity) getApplicationContext()).setUpdatePage(getApplicationContext().getString(R.string.UpdateData));
        setContentView(R.layout.update_view);

        mUpdate = (CustomTextView) findViewById(R.id.mUpdate);
        back = (ImageView) findViewById(R.id.back);
        textView12 = (CustomTextView) findViewById(R.id.textView12);
        languageChage = (LinearLayout) findViewById(R.id.languageChage);
        languageChageTxt = (CustomTextView) findViewById(R.id.languageChageTxt);
        img = (ImageView) findViewById(R.id.img);
        languageChage.setVisibility(View.GONE);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        appBarLayout.setVisibility(View.VISIBLE);
        treeList = new TreeList(getApplicationContext());

        if (languages.equals("1")) {
            languageChageTxt.setText(getResources().getString(R.string.Tamil));
        } else {
            languageChageTxt.setText(getResources().getString(R.string.English));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mUpdate.setBackground(getApplicationContext().getDrawable(R.drawable.riiple_oval));
            back.setBackground(getApplicationContext().getDrawable(R.drawable.ripple_back));
        }

        mUpdate.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String task = intent.getStringExtra("task");

      /*  if (task.equals("update")) {
            mUpdate.setVisibility(View.VISIBLE);
        } else {
            mUpdate.setVisibility(View.GONE);
            asynCheck = new Asyn();
            asynCheck.execute();
        }*/


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UpdateActiity.this, "Please wait in few sec", Toast.LENGTH_SHORT).show();

                //   downloadFile();
                //asyn = new Asyn();
                //asyn.execute();
                new UpdateAsynTask().execute();
            }
        });


        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.pass = "not";
                onBackPressed();

            }
        });
        languageChage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  update();
                /*AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActiity.this);
                builder.setTitle(getResources().getString(R.string.ChangeLanguage));
                builder.setNegativeButton(getResources().getString(R.string.English), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update();
                    }
                });
                builder.setPositiveButton(getResources().getString(R.string.Tamil), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update();
                    }
                });
                builder.show();*/
            }
        });
    }

    public class UpdateAsynTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NotifyActivity.this);
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
                deleteAll();
                Log.d("Response", "" + s);
            }
            progressDialog.dismiss();
            Intent intent = new Intent(NotifyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            //getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SplashScreen()).commit();
        }
    }

    public class Asyn extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        /*    progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.show();*/
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
            //progressDialog.dismiss();

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
                            Log.d("mLastDLast_Updateate_one", "" + mLastDate_fromDb + "  " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                if (Last_Update_fromServer.equals(mLastDate_fromDb)) {
                                    Log.d("whatCome", "Fine 12");
                                } else {
                                    Log.d("whatCome", "Fine 12 not es");
                                }

                            } else {
                                Log.d("whatCome", "Update 12 ");
                                updateCounting = updateCounting + 1;
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
                            Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine");
                            } else {
                                Log.d("whatCome", "Update ");
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
                            Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine");
                            } else {
                                Log.d("whatCome", "Update ");
                                updateCounting = updateCounting + 1;
                            }
                        }
                    }

                    json1Object = parentObject.getJSONObject("Json4");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String mLastDate_fromDb = soilType.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine");
                            } else {
                                Log.d("whatCome", "Update ");
                                updateCounting = updateCounting + 1;
                            }
                        }
                    }

                    json1Object = parentObject.getJSONObject("Json5");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String mLastDate_fromDb = treeTypeInfo.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine");
                            } else {
                                Log.d("whatCome", "Update ");
                                updateCounting = updateCounting + 1;
                            }
                        }
                    }

                    json1Object = parentObject.getJSONObject("Json6");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            String mLastDate_fromDb = verifyDetails.getLastDate(Last_Update_fromServer);
                            Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                            if (mLastDate_fromDb != null) {
                                //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                Log.d("whatCome", "Fine 6");
                            } else {
                                Log.d("whatCome", "Update 66");
                                updateCounting = updateCounting + 1;
                            }
                        }
                    }
                    json1Object = parentObject.getJSONObject("Json7");
                    status = json1Object.getBoolean("status");
                    if (status) {
                        JSONArray result1Array = json1Object.getJSONArray("result");
                        for (int i = 0; i < result1Array.length(); i++) {
                            JSONObject jsonObject = result1Array.getJSONObject(i);
                            String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                            /*String mLastDate_fromDb = rainfallType.getLastDate(Last_Update_fromServer);
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
                        Log.d("result1Array  9", "" + result1Array.length());
                    }
                    JSONObject json10Object = parentObject.getJSONObject("Json10");
                    boolean statusMsg10 = json10Object.getBoolean("status");
                    String messageStus10 = json10Object.getString("message");
                    if (statusMsg10) {
                        JSONArray result1Array = json10Object.getJSONArray("result");
                        Log.d("result1Array  10", "" + result1Array.length());
                    }
                    Log.d("updateCounting", "" + updateCounting);
                    if (updateCounting >= 1) {
                        //  showNotification(updateCounting);
                        textView12.setText("This update tack few minutes downlading files from Server. Please click Update Now Button.");
                        textView12.setVisibility(View.VISIBLE);
                        mUpdate.setVisibility(View.VISIBLE);
                    } else {
                        textView12.setText("Updates Not Found");
                        mUpdate.setVisibility(View.GONE);
                        textView12.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private void checkUpdates(String mLastDate, String Last_Update, JSONObject jsonObject) {
            SimpleDateFormat sdf = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                Date strDate = null, update_date = null;
                try {
                    strDate = sdf.parse(mLastDate);
                    update_date = sdf.parse(Last_Update);

                    if (update_date.getTime() < strDate.getTime()) {
                        Log.d("Equals", "Fine");
                    } else {
                        updateCounting = updateCounting + 1;
                        Log.d("Equals", "Update");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            }
        }

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
        imageDb.delete();
        contactUs.delete();
    }
}


