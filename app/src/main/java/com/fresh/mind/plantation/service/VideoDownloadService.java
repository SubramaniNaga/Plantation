package com.fresh.mind.plantation.service;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.sqlite.server.VideoPath;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.fresh.mind.plantation.Constant.Config.fileLocationOnServer;

import static com.fresh.mind.plantation.Constant.Config.sdCardLocationVideo;
import static com.fresh.mind.plantation.fragment.menu_page.Tutorials.showError;

/**
 * Created by AND I5 on 21-03-2017.
 */

public class VideoDownloadService extends IntentService {
    int totalSize = 0;
    private VideoPath videoPath;

    public VideoDownloadService() {
        super(null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //Log.d("ServiceClasas", "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //Log.d("ServiceClasas", "onStartCommand");
        videoPath = new VideoPath(getApplicationContext());
        new FeachingVideos().execute();
        return START_NOT_STICKY;
    }


    class FeachingVideos extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

/*            dialog = new Dialog(VideoDownloadService.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_progress);
            dialog.setCancelable(false);

            dialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {
            String s = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Config.urlVideo);
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

                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        boolean status = jsonObject.getBoolean("status");
                        if (status) {
                            JSONArray result = jsonObject.getJSONArray("result");
                            if (result.length() == videoPath.getCount()) {
                                Log.d("skdjksalj", "VideofilesSame");
                            } else {
                                videoPath.delete();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject loopObj = result.getJSONObject(i);
                                    String video = loopObj.getString("video");
                                    // Log.d("vvideoideo", "" + video);
                                    String Description = loopObj.getString("Description");
                                    String DescriptionTamil = loopObj.getString("DescriptionTamil");
                                    String LastUpdate = loopObj.getString("LastUpdate");
                                    String tutorial_title=loopObj.getString("tutorial_title");
                                    String tutorial_title_tamil=loopObj.getString("tutorial_title_tamil");
                                    if (video.isEmpty() || video.equals("null")) {
                                    } else {
                                        String storagePath = null;
                                        File file = new File(sdCardLocationVideo + video);
                                        //  Log.d("fildsde", "" + file);
                                        if (file.exists()) {
                                            Log.d("deded", i + " exist");
                                            storagePath = sdCardLocationVideo + video;
                                        } else {
                                            Log.d("deded", i + " Not exist");
                                            storagePath = downloadFile(fileLocationOnServer + video);
                                        }
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("video", storagePath);
                                        contentValues.put("Description", "" + Description);
                                        contentValues.put("DescriptionTamil", "" + DescriptionTamil);
                                        contentValues.put("lastUpdate", "" + LastUpdate);
                                        contentValues.put("tutorial_title", "" + tutorial_title);
                                        contentValues.put("tutorial_title_tamil", "" + tutorial_title_tamil);
                                        videoPath.onInsert(contentValues);
                                        // Log.d("storagePathVideo", "" + storagePath);
                                    }
                                }
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }


                } else {
                    //Toast.makeText(getApplicationContext(), "Something missing", Toast.LENGTH_SHORT).show();
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("responseFromVideoDownloasServiceClass", "" + s);

        }
    }


    public String downloadFile(String filePath) {
        //   Log.d("filePathVideo", "" + filePath);
        File dir = new File(sdCardLocationVideo);
        File[] mp3List = dir.listFiles();
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
        //  Log.d("fildsadeName", "" + fileName);
        if (mp3List != null) {
            for (File fileFilter : mp3List) {
                String name = fileFilter.getName();
                //    Log.d("s87hj", "" + name);
                if (name.equals(fileName)) {
                    Log.d("drjh78943yhe", "File Exist  " + name);
                } else {
                    Log.d("drjh78943yhe222", "File Exist  " + name);
                    return getFile(fileName, filePath);


                    //Download(filePath);
                }
            }
        } else {
            Log.d("drjh78943yhe3333", "File Exist  ");
            return getFile(fileName, filePath);
            //Download(filePath);
        }
        return null;
    }

    private String getFile(String fileName, String filesPath) {
        try {
            //  Log.d("nameforDownlading_Purpose", "" + fileName + "   " + filesPath);
            URL url = new URL(filesPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File wallpaperDirectory = new File(sdCardLocationVideo);
            wallpaperDirectory.mkdirs();
            File file = new File(wallpaperDirectory, fileName);
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            totalSize = urlConnection.getContentLength();
            byte[] buffer = new byte[1024];
/*
            FileOutputStream fos = new FileOutputStream(sdCardLocationVideo);
            fos.write(buffer);
            fos.close();*/
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                // downloadedSize += bufferLength;
                // update the progressbar //
                // Log.d("viewingSizeIntoDownloaded", "" + downloadedSize);

            }
            fileOutput.flush();
            fileOutput.close();
          /*  File files = new File(wallpaperDirectory, ".nomedia");
            try {
                files.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(files, true));
                writer.write("This is a test trace file.");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.toString()}, null, null);
        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);
            e.printStackTrace();
        } catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }
        return sdCardLocationVideo + "/" + fileName;
    }


}
