package com.fresh.mind.plantation.fragment.menu_page;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.adapter.base_adapter.VideoTutorialAdapter;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
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
import java.io.ByteArrayOutputStream;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


import static com.fresh.mind.plantation.Constant.Config.mVIDEO_LIST;
import static com.fresh.mind.plantation.Constant.Config.mVIDEO_PATH;


/**
 * Created by AND I5 on 25-02-2017.
 */

public class Tutorials extends Fragment {
    private View rootView;
    int downloadedSize = 0;
    int totalSize = 0;

    private String fileLocationOnServer = "http://plantation.kambaa.com/admin2017/images/";
    private String sdCardLocation = "sdcard/Treepedia/Videos";
    private VideoPath videoPath;
    private CustomTextView mTutorial;
    private ListView mListView;


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

        ((MainActivity) getActivity()).setTutorials(getActivity().getResources().getString(R.string.tutorials));
        videoPath = new VideoPath(getActivity());
        rootView = inflater.inflate(R.layout.tutorials, null);
        mTutorial = (CustomTextView) rootView.findViewById(R.id.mTutorial);
        mListView = (ListView) rootView.findViewById(R.id.mListView);

        if (mVIDEO_LIST.size() == 0) {
            new LoaderVideos().execute();
        } else {
            mListView.setAdapter(new VideoTutorialAdapter(getActivity(), mVIDEO_PATH, mVIDEO_LIST));
            mTutorial.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
        //  new FeachingVideos().execute();

        return rootView;
    }

    class LoaderVideos extends AsyncTask<ArrayList<HashMap<String, byte[]>>, Void, ArrayList<HashMap<String, byte[]>>> {
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(null);
            progressDialog.setMessage(getActivity().getString(R.string.fetchingData));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, byte[]>> doInBackground(ArrayList<HashMap<String, byte[]>>... params) {

            mVIDEO_PATH = videoPath.getViewPath(AppData.checkLanguage(getActivity()));
            for (int l = 0; l < mVIDEO_PATH.size(); l++) {
                String storagePath = mVIDEO_PATH.get(l).get("path");
                //Log.d("storaVideoiogePath", "" + storagePath);
                if (storagePath != null) {
                    byte[] byteArray = new byte[0];
                    HashMap<String, byte[]> mHashBitMap = new HashMap<>();
                    Bitmap ThumbImage = ThumbnailUtils.createVideoThumbnail(new File(storagePath).getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                    //Log.d("ThumbImage", "" + ThumbImage);
                    if (ThumbImage != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        ThumbImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        mHashBitMap.put("ThumbImage", byteArray);
                        mVIDEO_LIST.add(mHashBitMap);
                    } else {
                        mHashBitMap.put("ThumbImage", byteArray);
                        mVIDEO_LIST.add(mHashBitMap);
                    }
                }

            }

            return mVIDEO_LIST;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, byte[]>> hashMaps) {
            super.onPostExecute(hashMaps);

            if (mVIDEO_LIST != null) {
                if (mVIDEO_LIST.size() > +1) {
                    mListView.setAdapter(new VideoTutorialAdapter(getActivity(), mVIDEO_PATH, mVIDEO_LIST));
                    mTutorial.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                } else {
                    mTutorial.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
            } else {
                mTutorial.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    class FeachingVideos extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading");
            progressDialog.show();*/
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_progress);
            dialog.setCancelable(false);
          /*  Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);*/
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://plantation.kambaa.com/webservices/webservice1.php");
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
            Log.d("adadsad", "" + s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        JSONArray result = jsonObject.getJSONArray("result");

                        for (int i = 0; i < result.length(); i++) {
                            JSONObject loopObj = result.getJSONObject(i);
                            String video = loopObj.getString("video");

                            Log.d("vvideoideo", "" + video);
                            String Description = loopObj.getString("Description");
                            String DescriptionTamil = loopObj.getString("DescriptionTamil");
                            String LastUpdate = loopObj.getString("LastUpdate");
                            Log.d("1267loop", "loop" + video + "  " + i);
                            String storagePath = null;
                            File file = new File(sdCardLocation + "/" + video);
                            if (file.exists()) {
                                Log.d("deded", i + " exist");
                                storagePath = sdCardLocation + "/" + video;
                            } else {
                                storagePath = downloadFile(fileLocationOnServer + video);

                                ContentValues contentValues = new ContentValues();
                                contentValues.put("video", storagePath);
                                contentValues.put("Description", "" + Description);
                                contentValues.put("DescriptionTamil", "" + DescriptionTamil);
                                contentValues.put("lastUpdate", "" + LastUpdate);
                                videoPath.onInsert(contentValues);
                            }
                            Log.d("storagePathVideo", "" + storagePath);
                        }


                        /*if (mp3List != null) {
                            if (result.length() > mp3List.length) {
                                Log.d("asaspl", "1");
                                //videoPath.delete();
                                // Log.d("valuesss Length", "" + result.length() + "  " + mp3List.length);
                                for (int h = 0; h < mp3List.length; h++) {
                                    File file = mp3List[h];
                                    String fileName = file.getName();
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject loopObj = result.getJSONObject(i);
                                        String video = loopObj.getString("video");
                                        String Description = loopObj.getString("Description");
                                        String DescriptionTamil = loopObj.getString("DescriptionTamil");
                                        String LastUpdate = loopObj.getString("LastUpdate");
                                        Log.d("1267loop", "loop" + video + "  " + i);
                                        if (fileName.equals(video)) {
                                            Log.d("1267equals", "equals " + fileName + "  " + video);
                                        } else {
                                            Log.d("1267Notequals", "Notequals " + fileName + "  " + video);
                                            downloadFile(fileLocationOnServer + video);
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("video", video);
                                            contentValues.put("Description", "" + Description);
                                            contentValues.put("DescriptionTamil", "" + DescriptionTamil);
                                            contentValues.put("lastUpdate", "" + LastUpdate);
                                            videoPath.onInsert(contentValues);
                                        }
                                    }
                                }
                            } else if (result.length() == mp3List.length) {
                                Log.d("asaspl", "2");
                                dialog.dismiss();
                                ArrayList<HashMap<String, String>> mVideoPath = videoPath.getViewPath(AppData.checkLanguage(getActivity()));
                                ArrayList<HashMap<String, String>> mDetailsList = new ArrayList<>();
                                // Log.d("valuesss mVideoPath", "" + mVideoPath.size());
                                for (int l = 0; l < mVideoPath.size(); l++) {
                                    String fileName = mVideoPath.get(l).get("path");
                                    File dir = new File("" + sdCardLocation);
                                    File[] mp3List = dir.listFiles();
                                    if (mp3List != null) {
                                        for (int m = 0; m < mp3List.length; m++) {
                                            File file = mp3List[m];
                                            String externalFileName = file.getName();
                                            //  Log.d("adsasd", "" + fileName + "  " + externalFileName);
                                            if (fileName.equals(externalFileName)) {
                                                //   Log.d("asasassa", "equals");
                                                HashMap<String, String> hashMapHashMap = new HashMap();
                                                hashMapHashMap.put("path", "" + file);
                                                hashMapHashMap.put("description", mVideoPath.get(l).get("Description"));
                                                mDetailsList.add(hashMapHashMap);
                                            } else {
                                                // Log.d("asasassa", "equals not");
                                            }
                                        }
                                    }
                                }
                                //  Log.d("mDetailsList", "" + mDetailsList.size());
                                HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();
                                hashSet.addAll(mDetailsList);
                                mDetailsList.clear();
                                mDetailsList.addAll(hashSet);
                                if (mDetailsList.size() >= 1) {
                                    mListView.setAdapter(new VideoTutorialAdapter(getActivity(), mDetailsList));
                                    mTutorial.setVisibility(View.GONE);
                                    mListView.setVisibility(View.VISIBLE);
                                } else {
                                    mTutorial.setVisibility(View.VISIBLE);
                                    mListView.setVisibility(View.GONE);
                                }
                            } else {
                                Log.d("asaspl", "3");
                                // Log.d("valuesss Length", "Low" + result.length() + "  " + mp3List.length);
                                if (mp3List != null) {
                                    for (int h = 0; h < mp3List.length; h++) {
                                        File file = mp3List[h];
                                        //Log.d("asaa", "" + file);

                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject loopObj = result.getJSONObject(i);
                                            String video = loopObj.getString("video");
                                            String Description = loopObj.getString("Description");
                                            String DescriptionTamil = loopObj.getString("DescriptionTamil");
                                            String LastUpdate = loopObj.getString("LastUpdate");
                                            //  Log.d("valuesss jh4546", "" + video + "  " + Description + "  " + DescriptionTamil + "  " + LastUpdate);
                                            downloadFile(fileLocationOnServer + video);
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("video", video);
                                            contentValues.put("Description", "" + Description);
                                            contentValues.put("DescriptionTamil", "" + DescriptionTamil);
                                            contentValues.put("lastUpdate", "" + LastUpdate);
                                            videoPath.onInsert(contentValues);
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject loopObj = result.getJSONObject(i);
                                        String video = loopObj.getString("video");
                                        String Description = loopObj.getString("Description");
                                        String DescriptionTamil = loopObj.getString("DescriptionTamil");
                                        String LastUpdate = loopObj.getString("LastUpdate");
                                        //  Log.d("valuesss jh4546", "" + video + "  " + Description + "  " + DescriptionTamil + "  " + LastUpdate);
                                        downloadFile(fileLocationOnServer + video);
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("video", video);
                                        contentValues.put("Description", "" + Description);
                                        contentValues.put("DescriptionTamil", "" + DescriptionTamil);
                                        contentValues.put("lastUpdate", "" + LastUpdate);
                                        videoPath.onInsert(contentValues);
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject loopObj = result.getJSONObject(i);
                                String video = loopObj.getString("video");
                                String Description = loopObj.getString("Description");
                                String DescriptionTamil = loopObj.getString("DescriptionTamil");
                                String LastUpdate = loopObj.getString("LastUpdate");
                                //   Log.d("valuesss else", "" + video + "  " + Description + "  " + DescriptionTamil + "  " + LastUpdate);
                                downloadFile(fileLocationOnServer + video);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("video", video);
                                contentValues.put("Description", "" + Description);
                                contentValues.put("DescriptionTamil", "" + DescriptionTamil);
                                contentValues.put("lastUpdate", "" + LastUpdate);
                                videoPath.onInsert(contentValues);
                            }
                        }*/
                        ArrayList<HashMap<String, String>> mVideoPath = videoPath.getViewPath(AppData.checkLanguage(getActivity()));
                        ArrayList<HashMap<String, String>> mDetailsList = new ArrayList<>();
                        // Log.d("valuesss mVideoPath", "" + mVideoPath.size());
                        for (int l = 0; l < mVideoPath.size(); l++) {
                            String fileName = mVideoPath.get(l).get("path");
                            File dir = new File("" + sdCardLocation);
                            File[] mp3List = dir.listFiles();
                            if (mp3List != null) {
                                for (int m = 0; m < mp3List.length; m++) {
                                    File file = mp3List[m];
                                    String externalFileName = file.getName();
                                    //  Log.d("adsasd", "" + fileName + "  " + externalFileName);
                                    //if (fileName.equals(externalFileName)) {
                                    //   Log.d("asasassa", "equals");
                                    HashMap<String, String> hashMapHashMap = new HashMap();
                                    hashMapHashMap.put("path", "" + file);
                                    hashMapHashMap.put("description", mVideoPath.get(l).get("Description"));
                                    mDetailsList.add(hashMapHashMap);
                                    //} else {
                                    // Log.d("asasassa", "equals not");
                                }
                            }
                        }

                        //  Log.d("mDetailsList", "" + mDetailsList.size());
                        HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();
                        hashSet.addAll(mDetailsList);
                        mDetailsList.clear();
                        mDetailsList.addAll(hashSet);
                        if (mDetailsList.size() >= 1) {
                            //s mListView.setAdapter(new VideoTutorialAdapter(getActivity(), mDetailsList));
                            mTutorial.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                        } else {
                            mTutorial.setVisibility(View.VISIBLE);
                            mListView.setVisibility(View.GONE);
                        }
                    }
                    // progressDialog.dismiss();
                    dialog.dismiss();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //downloadFile();
            } else {
                Toast.makeText(getActivity(), "Something missing", Toast.LENGTH_SHORT).show();
            }
            //progressDialog.dismiss();
            dialog.dismiss();
        }

    }

    public String downloadFile(String filePath) {
        //Log.d("filePath", "" + filePath);
        File dir = new File(sdCardLocation);
        File[] mp3List = dir.listFiles();
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);
        if (mp3List != null) {
            for (File fileFilter : mp3List) {
                String name = fileFilter.getName();
                //  Log.d("s87hj", "" + name);
                if (name.equals(fileName)) {
                    //    Log.d("drjh78943yhe", "File Exist  " + name);
                } else {
                    return getFile(fileName, filePath);

                    //Download(filePath);
                }
            }
        } else {
            return getFile(fileName, filePath);
            //Download(filePath);
        }
        return null;
    }

    private void getVideoFile(String fileName, String fileURL) {

        try {
            String rootDir = sdCardLocation;
            File rootFile = new File(rootDir);
            rootFile.mkdir();
            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            /*File wallpaperDirectory = new File(sdCardLocation);
            wallpaperDirectory.mkdirs();*/
            FileOutputStream f = new FileOutputStream(new File(rootFile, fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
                //Log.d("dsfd564", "" + len1);
            }
            f.close();
        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }
    }

    private String getFile(String fileName, String filesPath) {
        try {
            // Log.d("nameforDownlading_Purpose", "" + fileName);
            URL url = new URL(fileName);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File wallpaperDirectory = new File(sdCardLocation);
            wallpaperDirectory.mkdirs();
            File file = new File(wallpaperDirectory, fileName);
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            totalSize = urlConnection.getContentLength();
            byte[] buffer = new byte[1024];
/*
            FileOutputStream fos = new FileOutputStream(sdCardLocation);
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
            MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, null, null);
        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);
            e.printStackTrace();
        } catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }
        return sdCardLocation + "/" + fileName;
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

    public String Download(String Url) {
        String filepath = null;
        try {
            //set the download URL, a url that points to a file on the internet
            //this is the file to be downloaded
            URL url = new URL(Url);
            //create the new connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //set up some things on the connection
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            //and connect!
            urlConnection.connect();
            //set the path where we want to save the file
            //in this case, going to save it on the root directory of the
            //sd card.
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, specifying the path, and the filename
            //which we want to save the file as.
            File fileNm = new File(Url);
            String filename = fileNm.getName();   // you can download to any type of file ex:.jpeg (image) ,.txt(text file),.mp3 (audio file)
            Log.i("Local filename:", "" + filename);
            File file = new File(SDCardRoot, filename);
            if (file.createNewFile()) {
                file.createNewFile();
            }

            //this will be used to write the downloaded data into the file we created
            FileOutputStream fileOutput = new FileOutputStream(file);

            //this will be used in reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file
            int totalSize = urlConnection.getContentLength();
            //variable to store total downloaded bytes
            int downloadedSize = 0;

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0; //used to store a temporary size of the buffer

            //now, read through the input buffer and write the contents to the file
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                //add the data in the buffer to the file in the file output stream (the file on the sd card
                fileOutput.write(buffer, 0, bufferLength);
                //add up the size so we know how much is downloaded
                downloadedSize += bufferLength;
                //this is where you would do something to report the prgress, like this maybe
                Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);

            }
            //close the output stream when done
            fileOutput.close();
            if (downloadedSize == totalSize) filepath = file.getPath();

            //catch some possible errors...
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            filepath = null;
            e.printStackTrace();
        }
        Log.i("filepath:", " " + filepath);

        return filepath;

    }

    public static void showError(String s) {
        Log.d("adsfsadf", "" + s);
    }
}
