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

import com.fresh.mind.plantation.fragment.menu_page.Tutorials;
import com.fresh.mind.plantation.sqlite.server.ImageDb;
import com.fresh.mind.plantation.sqlite.server.TreeList;
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

import static com.fresh.mind.plantation.Constant.Config.LOAD_MORE;
import static com.fresh.mind.plantation.Constant.Config.fileLocationOnServer;
import static com.fresh.mind.plantation.Constant.Config.sdCardLocationTreeImages;
import static com.fresh.mind.plantation.Constant.Config.url;

/**
 * Created by AND I5 on 03-04-2017.
 */

public class DescriptionImages extends IntentService {
    private TreeList treeList;
    private VerifyDetails verifyDetails;
    private ImageDb imageDb;
    private TreeTypeNameList treeTypeNameList;
    int totalSize = 0;

    public DescriptionImages() {
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        treeList = new TreeList(getApplicationContext());
        treeTypeNameList = new TreeTypeNameList(getApplicationContext());
        verifyDetails = new VerifyDetails(getApplicationContext());
        imageDb = new ImageDb(getApplicationContext());
        new ViewDescription().execute();

        return START_NOT_STICKY;

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
                Log.d("result", "" + result);
                if (result == null || result.isEmpty()) {
                    LOAD_MORE = false;
                    return "server";
                } else {
                    JSONObject parentObject = null;
                    try {
                        LOAD_MORE = true;
                        parentObject = new JSONObject(result);
                        JSONObject json6Object = parentObject.getJSONObject("Json6");
                        boolean statusMsg6 = json6Object.getBoolean("status");
                        String messageStus6 = json6Object.getString("message");
                        if (statusMsg6) {
                            JSONArray result1Array = json6Object.getJSONArray("result");
                            Log.d("result1Array  6", "" + result1Array.length() + "  " + verifyDetails.getCount() + "  " + verifyDetails.getCount());
                            if (result1Array.length() == verifyDetails.getCount()) {
                            } else {
                                verifyDetails.delete();
                                imageDb.delete();
                                try {
                                    for (int i = 0; i < result1Array.length(); i++) {
                                        if (ImagesDownloadService.checkInternetConenction(getApplicationContext())) {
                                            JSONObject jsonObject = result1Array.getJSONObject(i);
                                            String thumbnail = jsonObject.getString("thumbnail");
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

                                            String artificial_regeneration = jsonObject.getString("artificial_regeneration").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                            String artificial_regeneration_tamil = jsonObject.getString("artificial_regeneration_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                            String seed_collection = jsonObject.getString("seed_collection").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                            String seed_collection_tamil = jsonObject.getString("artificial_regeneration").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                            String seed_treatment = jsonObject.getString("artificial_regeneration_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                            String seed_treatment_tamil = jsonObject.getString("seed_collection").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                            String nursery_technique = jsonObject.getString("artificial_regeneration_tamil").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");
                                            String nursery_technique_tamil = jsonObject.getString("seed_collection").replace("\\r\\n", "<br/>\t").replace("\\n", "<br/>").replace("\\r", "<br/>".replace("\\t", "<br/>")).replace("\\", "").replace("\"", "").replace("\uF0B7", "\u25CF ").replace("\uF0A7", "\u25CF ");

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


                                            contentValues.put("artificial_regeneration", artificial_regeneration);
                                            contentValues.put("artificial_regeneration_tamil", artificial_regeneration_tamil);
                                            contentValues.put("seed_collection", seed_collection);
                                            contentValues.put("seed_collection_tamil", seed_collection_tamil);
                                            contentValues.put("seed_treatment", seed_treatment);
                                            contentValues.put("seed_treatment_tamil", seed_treatment_tamil);
                                            contentValues.put("nursery_technique", nursery_technique);
                                            contentValues.put("nursery_technique_tamil", nursery_technique_tamil);

                                            contentValues.put("thumbnail", thumbnail);
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
                                                                    Log.d("ImageImage Zeros", TreeName + " " + ImageDpwnLoad);
                                                                    storagePath = downloadFile(ImageDpwnLoad, sdCardLocationTreeImages);
                                                                }
                                                            } else {
                                                                Log.d("deded 2", i + " 6 Not exist");
                                                                String ImageSplit = Image.replaceAll(" ", "%20");
                                                                String ImageDownload = fileLocationOnServer + ImageSplit;
                                                                Log.d("ImageImage", arr + "  " + ImageDownload);
                                                                storagePath = downloadFile(ImageDownload, sdCardLocationTreeImages);
                                                                Log.d("storagePathSeond", TreeName + "  " + storagePath);
                                                            }
                                                            imageDb.onInsert(TreeName, common_key, TreeNameTamil, storagePath, thumbnail);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                            return "return";
                                                        }
                                                        //  }
                                                    }
                                                }
                                            }
                                            Log.d("extension two", "" + i);
                                            contentValues.put("storagePath", "" + storagePath);
                                            verifyDetails.onInsert(contentValues);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return "return";
                                }
                            }
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
            } finally {
                //urlConnection.disconnect();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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
            if (ImagesDownloadService.checkInternetConenction(getApplicationContext())) {
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
        } catch (IOException e) {

            Tutorials.showError("Error : IOException " + e);
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return filePathName;
    }

}
