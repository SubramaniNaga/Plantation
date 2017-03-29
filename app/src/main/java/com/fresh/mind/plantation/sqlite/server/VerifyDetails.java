package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.fresh.mind.plantation.R.id.districtName;
import static com.fresh.mind.plantation.R.string.rainFall;
import static com.fresh.mind.plantation.R.string.treeType;

/**
 * Created by AND I5 on 20-02-2017.
 */

public class VerifyDetails extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;

    public VerifyDetails(Context context) {
        super(context, "VerifyDetails", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String VerifyDetails = "create table VerifyDetails(id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lastUpdate text,SoilType text,RainFallFirst text,TerrainType text,TreeName text," +
                "TreeType text,ScientificName text,General_Info text,General_Info_Tamil text,Habitat text,Habitat_Tamil text,Soil text,Soil_Tamil text,Soil_PH text,Soil_PH_Tamil text,Altitude text," +
                "Altitude_Tamil text,Rainfall text,Rainfall_Tamil text,Min_Temperature text,Max_Temperature text,Min_Temperature_Tamil text,Max_Temperature_Tamil text, Terrain text, TerrainType_Tamil text, " +
                "Tree_Char text, Tree_Char_Tamil text, Conservation text, Conservation_Tamil text, Edibility text, Edibility_Tamil text,  Medicinal text,Medicinal_Tamil text,Other_Rating text," +
                "Other_Rating_Tamil text,Habit text,Habit_Tamil text,Growth text,Growth_Tamil text,Height text,Height_Tamil text,Cultivation text,Cultivation_Tamil text, Other_Details text," +
                "Other_Details_Tamil text, Propagation text, Propagation_Tamil text, Plantation_Technique text, Plantation_Technique_Tamil text, Care_Disease text,Care_Disease_Tamil text,Irrigation text," +
                "Irrigation_Tamil text,Yield text,Yield_Tamil text,Recommended_Harvest text,Recommended_Harvest_Tamil text,Market_Details text,Market_Details_Tamil text,Intercrops text," +
                "Intercrops_Tamil text,Majar_Uses text,Majar_Uses_Tamil text,Other_Uses text,Other_Uses_Tamil text,Carbon_Stock text,Carbon_Stock_Tamil text,Reference text,References_Tamil text," +
                "districtNameTamil text,SoilTamil text,RainFallTamil text,TerrainTypeTamil text,TreeNameTamil text,TreeTypeTamil text,ScientificTamil text,common_key text,storagePath text)";

        db.execSQL(VerifyDetails);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS VerifyDetails");
        onCreate(db);
    }

    public void onInsert(ContentValues contentValues) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("VerifyDetails", null, contentValues);
        //   Log.d("contentValues", "" + contentValues);
    }

    public void delete() {
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from VerifyDetails");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public ArrayList<HashMap<String, byte[]>> getImages(String treeName) {
        ArrayList<HashMap<String, byte[]>> mDistrinListName = new ArrayList<HashMap<String, byte[]>>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from VerifyDetails where TreeName='" + treeName + "'", null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, byte[]> status = new HashMap<>();
                byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("treeIcon"));
                status.put("treeIcon", treeIcon);
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, String>> getDescription(String treeName, String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String, String>> mDistrinListName12 = new ArrayList<HashMap<String, String>>();
        Cursor cursor = null, cursor1;
        sqLiteDatabase = this.getReadableDatabase();

        if (language.equals("1")) {
            cursor = sqLiteDatabase.rawQuery("select * from VerifyDetails where TreeNameTamil='" + treeName + "'", null);
            if (cursor.getCount() == 0) {
                cursor = sqLiteDatabase.rawQuery("select * from VerifyDetails where TreeName='" + treeName + "'", null);
                if (cursor.moveToFirst()) {
                    do {
                        HashMap<String, String> status = new HashMap<>();
                        status.put("General_Info", cursor.getString(cursor.getColumnIndex("General_Info")));
                        status.put("Habitat", cursor.getString(cursor.getColumnIndex("Habitat")));
                        status.put("Soil", cursor.getString(cursor.getColumnIndex("Soil")));
                        status.put("Soil_PH", cursor.getString(cursor.getColumnIndex("Soil_PH")));
                        status.put("Altitude", cursor.getString(cursor.getColumnIndex("Altitude")));
                        status.put("Min_Temperature", cursor.getString(cursor.getColumnIndex("Min_Temperature")));
                        status.put("Rainfall", cursor.getString(cursor.getColumnIndex("Rainfall")));
                        status.put("Max_Temperature", cursor.getString(cursor.getColumnIndex("Max_Temperature")));
                        status.put("Medicinal", cursor.getString(cursor.getColumnIndex("Medicinal")));
                        status.put("Terrain", cursor.getString(cursor.getColumnIndex("Terrain")));
                        status.put("Tree_Char", cursor.getString(cursor.getColumnIndex("Tree_Char")));
                        status.put("Conservation", cursor.getString(cursor.getColumnIndex("Conservation")));
                        status.put("Edibility", cursor.getString(cursor.getColumnIndex("Edibility")));
                        status.put("Other_Rating", cursor.getString(cursor.getColumnIndex("Other_Rating")));
                        status.put("Habit", cursor.getString(cursor.getColumnIndex("Habit")));
                        status.put("Growth", cursor.getString(cursor.getColumnIndex("Growth")));
                        status.put("Height", cursor.getString(cursor.getColumnIndex("Height")));
                        status.put("Cultivation", cursor.getString(cursor.getColumnIndex("Cultivation")));
                        status.put("Other_Details", cursor.getString(cursor.getColumnIndex("Other_Details")));
                        status.put("Propagation", cursor.getString(cursor.getColumnIndex("Propagation")));
                        status.put("Plantation_Technique", cursor.getString(cursor.getColumnIndex("Plantation_Technique")));
                        status.put("Care_Disease", cursor.getString(cursor.getColumnIndex("Care_Disease")));
                        status.put("Irrigation", cursor.getString(cursor.getColumnIndex("Irrigation")));
                        status.put("Yield", cursor.getString(cursor.getColumnIndex("Yield")));
                        status.put("Recommended_Harvest", cursor.getString(cursor.getColumnIndex("Recommended_Harvest")));
                        status.put("Market_Details", cursor.getString(cursor.getColumnIndex("Market_Details")));
                        status.put("Intercrops", cursor.getString(cursor.getColumnIndex("Intercrops")));
                        status.put("Majar_Uses", cursor.getString(cursor.getColumnIndex("Majar_Uses")));
                        status.put("Other_Uses", cursor.getString(cursor.getColumnIndex("Other_Uses")));
                        status.put("Carbon_Stock", cursor.getString(cursor.getColumnIndex("Carbon_Stock")));
                        status.put("Reference", cursor.getString(cursor.getColumnIndex("Reference")));
                        status.put("Intercrops", cursor.getString(cursor.getColumnIndex("Intercrops")));
                        //status.put("lastUpdate", cursor.getString(cursor.getColumnIndex("lastUpdate")));
                        mDistrinListName12.add(status);
                    } while (cursor.moveToNext());

                }
                return mDistrinListName12;

            }

        } else {
            cursor = sqLiteDatabase.rawQuery("select * from VerifyDetails where TreeName='" + treeName + "'", null);
        }
//        Log.d("cursor", "super  " + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                if (language.equals("1")) {
                    Log.d("dhjgl", "" + cursor.getString(cursor.getColumnIndex("SoilTamil")));
                    HashMap<String, String> status = new HashMap<>();
                    status.put("General_Info", cursor.getString(cursor.getColumnIndex("General_Info_Tamil")));
                    status.put("Habitat", cursor.getString(cursor.getColumnIndex("Habitat_Tamil")));
                    status.put("Soil", cursor.getString(cursor.getColumnIndex("Soil_Tamil")));
                    status.put("Soil_PH", cursor.getString(cursor.getColumnIndex("Soil_PH_Tamil")));
                    status.put("Altitude", cursor.getString(cursor.getColumnIndex("Altitude_Tamil")));
                    status.put("Min_Temperature", cursor.getString(cursor.getColumnIndex("Min_Temperature_Tamil")));
                    status.put("Rainfall", cursor.getString(cursor.getColumnIndex("RainFallTamil")));
                    status.put("Max_Temperature", cursor.getString(cursor.getColumnIndex("Max_Temperature_Tamil")));
                    status.put("Medicinal", cursor.getString(cursor.getColumnIndex("Medicinal_Tamil")));
                    status.put("Terrain", cursor.getString(cursor.getColumnIndex("TerrainTypeTamil")));
                    status.put("Tree_Char", cursor.getString(cursor.getColumnIndex("Tree_Char_Tamil")));
                    status.put("Conservation", cursor.getString(cursor.getColumnIndex("Conservation_Tamil")));
                    status.put("Edibility", cursor.getString(cursor.getColumnIndex("Edibility_Tamil")));
                    status.put("Other_Rating", cursor.getString(cursor.getColumnIndex("Other_Rating_Tamil")));
                    status.put("Habit", cursor.getString(cursor.getColumnIndex("Habit_Tamil")));
                    status.put("Growth", cursor.getString(cursor.getColumnIndex("Growth_Tamil")));
                    status.put("Height", cursor.getString(cursor.getColumnIndex("Height_Tamil")));
                    status.put("Cultivation", cursor.getString(cursor.getColumnIndex("Cultivation_Tamil")));
                    status.put("Other_Details", cursor.getString(cursor.getColumnIndex("Other_Details_Tamil")));
                    status.put("Propagation", cursor.getString(cursor.getColumnIndex("Propagation")));
                    status.put("Plantation_Technique", cursor.getString(cursor.getColumnIndex("Plantation_Technique_Tamil")));
                    status.put("Care_Disease", cursor.getString(cursor.getColumnIndex("Care_Disease_Tamil")));
                    status.put("Irrigation", cursor.getString(cursor.getColumnIndex("Irrigation_Tamil")));
                    status.put("Yield", cursor.getString(cursor.getColumnIndex("Yield_Tamil")));
                    status.put("Recommended_Harvest", cursor.getString(cursor.getColumnIndex("Recommended_Harvest_Tamil")));
                    status.put("Market_Details", cursor.getString(cursor.getColumnIndex("Market_Details_Tamil")));
                    status.put("Intercrops", cursor.getString(cursor.getColumnIndex("Intercrops_Tamil")));
                    status.put("Majar_Uses", cursor.getString(cursor.getColumnIndex("Majar_Uses_Tamil")));
                    status.put("Other_Uses", cursor.getString(cursor.getColumnIndex("Other_Uses_Tamil")));
                    status.put("Carbon_Stock", cursor.getString(cursor.getColumnIndex("Carbon_Stock_Tamil")));
                    status.put("Reference", cursor.getString(cursor.getColumnIndex("References_Tamil")));
                    status.put("Intercrops", cursor.getString(cursor.getColumnIndex("Intercrops_Tamil")));
                    //status.put("lastUpdate", cursor.getString(cursor.getColumnIndex("lastUpdate")));

                    mDistrinListName.add(status);
                } else {
                    HashMap<String, String> status = new HashMap<>();

                    status.put("General_Info", cursor.getString(cursor.getColumnIndex("General_Info")));
                    status.put("Habitat", cursor.getString(cursor.getColumnIndex("Habitat")));
                    status.put("Soil", cursor.getString(cursor.getColumnIndex("Soil")));
                    status.put("Soil_PH", cursor.getString(cursor.getColumnIndex("Soil_PH")));
                    status.put("Altitude", cursor.getString(cursor.getColumnIndex("Altitude")));
                    status.put("Min_Temperature", cursor.getString(cursor.getColumnIndex("Min_Temperature")));
                    status.put("Rainfall", cursor.getString(cursor.getColumnIndex("Rainfall")));
                    status.put("Max_Temperature", cursor.getString(cursor.getColumnIndex("Max_Temperature")));
                    status.put("Medicinal", cursor.getString(cursor.getColumnIndex("Medicinal")));
                    status.put("Terrain", cursor.getString(cursor.getColumnIndex("Terrain")));
                    status.put("Tree_Char", cursor.getString(cursor.getColumnIndex("Tree_Char")));
                    status.put("Conservation", cursor.getString(cursor.getColumnIndex("Conservation")));
                    status.put("Edibility", cursor.getString(cursor.getColumnIndex("Edibility")));
                    status.put("Other_Rating", cursor.getString(cursor.getColumnIndex("Other_Rating")));
                    status.put("Habit", cursor.getString(cursor.getColumnIndex("Habit")));
                    status.put("Growth", cursor.getString(cursor.getColumnIndex("Growth")));
                    status.put("Height", cursor.getString(cursor.getColumnIndex("Height")));
                    status.put("Cultivation", cursor.getString(cursor.getColumnIndex("Cultivation")));
                    status.put("Other_Details", cursor.getString(cursor.getColumnIndex("Other_Details")));
                    status.put("Propagation", cursor.getString(cursor.getColumnIndex("Propagation")));
                    status.put("Plantation_Technique", cursor.getString(cursor.getColumnIndex("Plantation_Technique")));
                    status.put("Care_Disease", cursor.getString(cursor.getColumnIndex("Care_Disease")));
                    status.put("Irrigation", cursor.getString(cursor.getColumnIndex("Irrigation")));
                    status.put("Yield", cursor.getString(cursor.getColumnIndex("Yield")));
                    status.put("Recommended_Harvest", cursor.getString(cursor.getColumnIndex("Recommended_Harvest")));
                    status.put("Market_Details", cursor.getString(cursor.getColumnIndex("Market_Details")));
                    status.put("Intercrops", cursor.getString(cursor.getColumnIndex("Intercrops")));
                    status.put("Majar_Uses", cursor.getString(cursor.getColumnIndex("Majar_Uses")));
                    status.put("Other_Uses", cursor.getString(cursor.getColumnIndex("Other_Uses")));
                    status.put("Carbon_Stock", cursor.getString(cursor.getColumnIndex("Carbon_Stock")));
                    status.put("Reference", cursor.getString(cursor.getColumnIndex("Reference")));
                    status.put("Intercrops", cursor.getString(cursor.getColumnIndex("Intercrops")));
                    //status.put("lastUpdate", cursor.getString(cursor.getColumnIndex("lastUpdate")));

                    mDistrinListName.add(status);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }


    public ArrayList<HashMap<String, String>> getQureyBaseTreeName(String treeType, String
            districtName, String rainFall, String terrainType, String soilType, String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
      //  Log.d("position  ", "" + treeType + "  " + districtName + "  " + rainFall + "  " + terrainType + "  " + soilType);
        sqLiteDatabase = this.getReadableDatabase();
        SQLiteDatabase Database = this.getReadableDatabase();
//        String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'and soilType='" + soilType + "'and rainFall='" + rainFall + "'and terrainType='" + terrainType + "'and treeType='" + treeType + "'";
        //String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'";
        //String query = "SELECT * FROM VerifyDetails";
        String query = null;
        Cursor cursor;
        if (language.equals("1")) {
            query = "SELECT * FROM VerifyDetails WHERE districtNameTamil like '%" + districtName.trim() + "%'and SoilTamil like '%" + soilType.trim() + "%'and RainFallTamil like '%" + rainFall.trim() + "%'and TerrainTypeTamil like '%" + terrainType.trim() + "%'and TreeTypeTamil like '%" + treeType.trim() + "%'";
            cursor = Database.rawQuery(query, null);
            if (cursor.getCount() == 0) {
                query = "SELECT * FROM VerifyDetails WHERE districtName like'%" + districtName + "%'and SoilType like '%" + soilType + "%'and RainFallFirst like'%" + rainFall + "%'and TerrainType like '%" + terrainType + "%'and TreeType like '%" + treeType + "%'";
                cursor = Database.rawQuery(query, null);
            }
        } else {
            query = "SELECT * FROM VerifyDetails WHERE districtName like'%" + districtName + "%'and SoilType like '%" + soilType + "%'and RainFallFirst like'%" + rainFall + "%'and TerrainType like '%" + terrainType + "%'and TreeType like '%" + treeType + "%'";
            cursor = Database.rawQuery(query, null);
        }

      //  Log.d("sdafhjk", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                //Log.d("sdsdsds", "" + cursor.getString(cursor.getColumnIndex("TreeTypeTamil")) + "  " + cursor.getString(cursor.getColumnIndex("RainFallFirst")));
                if (language.equals("1")) {

                    String TreeNameTamil = cursor.getString(cursor.getColumnIndex("TreeNameTamil"));
                    Log.d("treeTypeTamil", "" + TreeNameTamil);
                    if (TreeNameTamil.equals("null") || TreeNameTamil.isEmpty()) {
                        //Log.d("fdfsdsa", "This 1");
                        HashMap<String, String> status = new HashMap<>();
                        status.put("treeType", cursor.getString(cursor.getColumnIndex("TreeType")));
                        status.put("treeName", cursor.getString(cursor.getColumnIndex("TreeName")));
                        status.put("subTreeName", cursor.getString(cursor.getColumnIndex("ScientificName")));
                        status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));

                        mDistrinListName.add(status);
                    } else {
                      //  Log.d("fdfsdsa", "This 2");
                        HashMap<String, String> status = new HashMap<>();
                        status.put("treeType", cursor.getString(cursor.getColumnIndex("TreeTypeTamil")));
                        status.put("treeName", cursor.getString(cursor.getColumnIndex("TreeNameTamil")));
                        status.put("subTreeName", cursor.getString(cursor.getColumnIndex("ScientificTamil")));
                        status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                        mDistrinListName.add(status);
                    }

                } else {
                    HashMap<String, String> status = new HashMap<>();
                    status.put("treeType", cursor.getString(cursor.getColumnIndex("TreeType")));
                    status.put("treeName", cursor.getString(cursor.getColumnIndex("TreeName")));
                    status.put("subTreeName", cursor.getString(cursor.getColumnIndex("ScientificName")));
                    status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);
                }
            } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
        }
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, byte[]>> getQureyBaseTreeImages(String treeType, String
            districtName, String rainFall, String terrainType, String soilType) {
        ArrayList<HashMap<String, byte[]>> mDistrinListName = new ArrayList<HashMap<String, byte[]>>();
      //  Log.d("position  ", "" + treeType + "  " + districtName + "  " + rainFall + "  " + terrainType + "  " + soilType);
        sqLiteDatabase = this.getReadableDatabase();
        SQLiteDatabase Database = this.getReadableDatabase();
        String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'and Soil='" + soilType + "'and Rain_Fall='" + rainFall + "'and Terrain_Type='" + terrainType + "'and TreeType='" + treeType + "'";
        //String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'";
        //String query = "SELECT * FROM VerifyDetails";
        Cursor cursor = Database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, byte[]> status = new HashMap<>();
                byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("treeIcon"));
                status.put("treeIcon", treeIcon);
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }
        return mDistrinListName;
    }


    public String getLastDate(String last_update) {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM VerifyDetails WHERE lastUpdate='" + last_update + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String mLastDate = null;

        if (cursor.moveToFirst()) {
            do {
                mLastDate = cursor.getString(cursor.getColumnIndex("lastUpdate"));

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mLastDate;
    }


    public int getId(String last_update_fromServer) {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM VerifyDetails WHERE TreeName='" + last_update_fromServer + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return id;
    }

    public void update(ContentValues contentValues, int id) {
        sqLiteDatabase = this.getWritableDatabase();
        String where = "id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        sqLiteDatabase.update("VerifyDetails", contentValues, "id" + "=?", new String[]{String.valueOf(id)});
    }

    public String getTreeName(String treeName) {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM VerifyDetails WHERE TreeName='" + treeName + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String mLastDate = null;

        if (cursor.moveToFirst()) {
            do {
                mLastDate = cursor.getString(cursor.getColumnIndex("TreeName"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mLastDate;
    }

    public void log(String msg) {
        Log.d("SmapleTexs ", "" + msg);
    }

    public int getCount() {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from VerifyDetails", null);
        int cou = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return cou;
    }

    public ArrayList<HashMap<String, String>> getTreeByImagePath(String treeType, String
            districtName, String rainFall, String terrainType, String soilType, String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        Log.d("position  ", "" + treeType + "  " + districtName + "  " + rainFall + "  " + terrainType + "  " + soilType);
        sqLiteDatabase = this.getReadableDatabase();
        SQLiteDatabase Database = this.getReadableDatabase();
//        String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'and soilType='" + soilType + "'and rainFall='" + rainFall + "'and terrainType='" + terrainType + "'and treeType='" + treeType + "'";
        //String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'";
        //String query = "SELECT * FROM VerifyDetails";
        String query = null;
        Cursor cursor;
        if (language.equals("1")) {
            query = "SELECT * FROM VerifyDetails WHERE districtNameTamil like '%" + districtName.trim() + "%'and SoilTamil like '%" + soilType.trim() + "%'and RainFallTamil like '%" + rainFall.trim() + "%'and TerrainTypeTamil like '%" + terrainType.trim() + "%'and TreeTypeTamil like '%" + treeType.trim() + "%'";
            cursor = Database.rawQuery(query, null);
            if (cursor.getCount() == 0) {
                query = "SELECT * FROM VerifyDetails WHERE districtName like'%" + districtName + "%'and SoilType like '%" + soilType + "%'and RainFallFirst like'%" + rainFall + "%'and TerrainType like '%" + terrainType + "%'and TreeType like '%" + treeType + "%'";
                cursor = Database.rawQuery(query, null);
            }
        } else {
            query = "SELECT * FROM VerifyDetails WHERE districtName like'%" + districtName + "%'and SoilType like '%" + soilType + "%'and RainFallFirst like'%" + rainFall + "%'and TerrainType like '%" + terrainType + "%'and TreeType like '%" + treeType + "%'";
            cursor = Database.rawQuery(query, null);
        }

        Log.d("sdafhjk222", "" + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                mDistrinListName.add(status);

            } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
        }
        return mDistrinListName;
    }
}
