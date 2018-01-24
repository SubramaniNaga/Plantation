package com.fresh.mind.plantation.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.NotifyActivity;
import com.fresh.mind.plantation.sqlite.Intercrops;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.Modelinfo;
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

/**
 * Created by AND I5 on 18-02-2017.
 */

public class UpdateData extends Service {
    Asyn asyn;
    private TreeList treeList;
    private DistrictNameList districtNameList;
    private TreeTypeNameList treeTypeNameList;
    private SoilType soilType;
    private VerifyDetails verifyDetails;
    private TreeTypeInfo treeTypeInfo;
    private TerrainType terrainType;
    private RainfallType rainfallType;
    private Modelinfo modelinfo;
    private Intercrops intercrops;

    private ImageDb imageDb;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // Code to execute when the service is first created
        Log.d("dsadsd", "StartService");
        treeList = new TreeList(getApplicationContext());
        districtNameList = new DistrictNameList(getApplicationContext());
        treeTypeNameList = new TreeTypeNameList(getApplicationContext());
        verifyDetails = new VerifyDetails(getApplicationContext());
        soilType = new SoilType(getApplicationContext());
        rainfallType = new RainfallType(getApplicationContext());
        terrainType = new TerrainType(getApplicationContext());
        treeTypeInfo = new TreeTypeInfo(getApplicationContext());
        imageDb = new ImageDb(getApplicationContext());
        Config.SERVICE = 1;
    }
/*
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

        Log.d("CloseUpdateData", "Objects");
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {

        if (isNetworkAvailable()) {
            asyn = new Asyn();
            asyn.execute();
        } else {
            /*final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Check your network Connections");
            builder.setCancelable(false);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();*/
        }
        return START_STICKY;
    }

    public class Asyn extends AsyncTask<Integer, Void, Integer> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        /*    progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.show();*/
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            String result = null;
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
                result = sb.toString();
                if (result == null) {
                    Log.d("Response", "" + result);
                } else {
                    JSONObject parentObject = null;
                    try {
                        parentObject = new JSONObject(result);
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
                                    //Log.d("mLastDLast_Updateate_one  ", i + "  " + mLastDate_fromDb + "  " + Last_Update_fromServer);
                                    if (mLastDate_fromDb != null) {
                                        if (Last_Update_fromServer.equals(mLastDate_fromDb)) {
                                            Log.d("whatCome", "Fine 12");
                                        } else {
                                            //   Log.d("whatCome", "Fine 12 not es");
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
                               // Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    //checkUpdates(mLastDate_fromDb, Last_Update_fromServer)
                                    // Log.d("whatCome", "Fine");
                                } else {
                                    //  Log.d("whatCome", "Update ");
                                    updateCounting = updateCounting + 1;
                                }
                            }
                        }

                        json1Object = parentObject.getJSONObject("Json15");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");

                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                String mLastDate_fromDb = modelinfo.getModelLastDate(Last_Update_fromServer);
                                // Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    //checkUpdates(mLastDate_fromDb, Last_Update_fromServer)
                                    // Log.d("whatCome", "Fine");
                                } else {
                                    //  Log.d("whatCome", "Update ");
                                    updateCounting = updateCounting + 1;
                                }
                            }
                        }

                        json1Object = parentObject.getJSONObject("Json16");
                        status = json1Object.getBoolean("status");
                        if (status) {
                            JSONArray result1Array = json1Object.getJSONArray("result");

                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                String mLastDate_fromDb = intercrops.getModelLastDate(Last_Update_fromServer);
                                // Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    //checkUpdates(mLastDate_fromDb, Last_Update_fromServer)
                                    // Log.d("whatCome", "Fine");
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
                                //   Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                    //   Log.d("whatCome", "Fine");
                                } else {
                                    //  Log.d("whatCome", "Update ");
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
                                // Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                    //  Log.d("whatCome", "Fine");
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
                            for (int i = 0; i < result1Array.length(); i++) {
                                JSONObject jsonObject = result1Array.getJSONObject(i);
                                String Last_Update_fromServer = jsonObject.getString("LastUpdate");
                                String mLastDate_fromDb = treeTypeInfo.getLastDate(Last_Update_fromServer);
                                // Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                    // Log.d("whatCome", "Fine");
                                } else {
                                    // Log.d("whatCome", "Update ");
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
                                // Log.d("mLastDLast_Updateate", " mLastDate_fromDb " + mLastDate_fromDb + "  Last_Update_fromServer " + Last_Update_fromServer);
                                if (mLastDate_fromDb != null) {
                                    //checkUpdates(mLastDate_fromDb, Last_Update_fromServer);
                                    //  Log.d("whatCome", "Fine 6");
                                } else {
                                    // Log.d("whatCome", "Update 66");
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
                            //Log.d("result1Array  9", "" + result1Array.length());
                        }
                        JSONObject json10Object = parentObject.getJSONObject("Json10");
                        boolean statusMsg10 = json10Object.getBoolean("status");
                        String messageStus10 = json10Object.getString("message");
                        if (statusMsg10) {
                            JSONArray result1Array = json10Object.getJSONArray("result");
                            //  Log.d("result1Array  10", "" + result1Array.length());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        stopService(new Intent(getApplicationContext(), UpdateData.class));
                        //startService(new Intent(getApplicationContext(), UpdateData.class));
                    }
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                stopService(new Intent(getApplicationContext(), UpdateData.class));
               // startService(new Intent(getApplicationContext(), UpdateData.class));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                stopService(new Intent(getApplicationContext(), UpdateData.class));
               // startService(new Intent(getApplicationContext(), UpdateData.class));
            } catch (IOException e) {
                e.printStackTrace();
                stopService(new Intent(getApplicationContext(), UpdateData.class));
                //startService(new Intent(getApplicationContext(), UpdateData.class));
            }
            return updateCounting;
        }

        @Override
        protected void onPostExecute(Integer updateCounting) {
            super.onPostExecute(updateCounting);
            //progressDialog.dismiss();
            //  Log.d("updateCounting", "" + updateCounting);
            if (updateCounting >= 1) {
                showNotification(updateCounting);
            } else {
                Log.d("updateCounting", "" + updateCounting + " Not found updates Now");
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


    public void showNotification(int itemsUpdate) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_logo_plantation)
                .setContentTitle("Update Available")
                .setContentText("New Tree Plantation is available now.");

        Intent notificationIntent = new Intent(getApplicationContext(), NotifyActivity.class);
        notificationIntent.putExtra("task", "update");
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
