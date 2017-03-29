package com.fresh.mind.plantation.sqlite.not_need;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by AND I5 on 06-02-2017.
 */

public class VerifiedDatabase extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public VerifiedDatabase(Context context) {
        super(context, "VerifedDetails", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String verify = "create table VerifyDetails (id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text ,soilType text,treeType  text,subTreeName text,treeName text,treeIcon text,rainFall text,terrainType text)";
        db.execSQL(verify);
        Log.d("verifiedDatabase", "Create Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsert(ContentValues contentValues) {

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("VerifyDetails", null, contentValues);

        //   Log.d("VerifyDetails", "Success " + contentValues);

    }

    public int getCount() {
        int count = 0;
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from VerifyDetails", null);
        count = cursor.getCount();
        return count;
    }


    public ArrayList<HashMap<String, String>> getSelectedDestrictname(String treeType, String districtName) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();

        sqLiteDatabase = this.getReadableDatabase();
        SQLiteDatabase Database = this.getReadableDatabase();
        String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'and treeType='" + treeType + "' order by treeName ASC";
        Cursor cursor = Database.rawQuery(query, null);
        Log.d("treeTypeCount", districtName + "  " + treeType + "  " + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                status.put("treeType", cursor.getString(cursor.getColumnIndex("treeType")));
                status.put("treeName", cursor.getString(cursor.getColumnIndex("treeName")));
                status.put("subTreeName", cursor.getString(cursor.getColumnIndex("subTreeName")));
                status.put("treeIcon", cursor.getString(cursor.getColumnIndex("treeIcon")));
                mDistrinListName.add(status);


            } while (cursor.moveToNext());
        }
        HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();

        hashSet.addAll(mDistrinListName);
        mDistrinListName.clear();
        mDistrinListName.addAll(hashSet);

        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public void delete() {

        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from VerifyDetails");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public ArrayList<HashMap<String, String>> getQureyBaseTreeName(String treeType, String districtName, String rainFall, String terrainType, String soilType) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        Log.d("position  ", "" + treeType + "  " + districtName + "  " + rainFall + "  " + terrainType + "  " + soilType);
        sqLiteDatabase = this.getReadableDatabase();
        SQLiteDatabase Database = this.getReadableDatabase();
//        String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'and soilType='" + soilType + "'and rainFall='" + rainFall + "'and terrainType='" + terrainType + "'and treeType='" + treeType + "'";
        String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'";
        Cursor cursor = Database.rawQuery(query, null);
        //Log.d("sdafhjk", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                String treeTypes = cursor.getString(cursor.getColumnIndex("treeType"));
                if (treeTypes.equals(treeType)) {
                    String soilTypes = cursor.getString(cursor.getColumnIndex("soilType"));
                    if (soilTypes.equals(soilType)) {
                        String rainFalls = cursor.getString(cursor.getColumnIndex("rainFall"));
                        if (rainFalls.equals(rainFall)) {
                            String terrainTypes = cursor.getString(cursor.getColumnIndex("terrainType"));
                            if (terrainTypes.equals(terrainType)) {
                                String subTreeName = cursor.getString(cursor.getColumnIndex("subTreeName"));
                                String treeName = cursor.getString(cursor.getColumnIndex("treeName"));
                                //  Log.d("subTreeName", treeName + "  " + subTreeName + "  " + treeTypes + " " + cursor.getString(cursor.getColumnIndex("id")));
                                HashMap<String, String> status = new HashMap<>();
                                status.put("treeType", cursor.getString(cursor.getColumnIndex("treeType")));
                                status.put("treeName", cursor.getString(cursor.getColumnIndex("treeName")));
                                status.put("subTreeName", cursor.getString(cursor.getColumnIndex("subTreeName")));
                                status.put("treeIcon", cursor.getString(cursor.getColumnIndex("treeIcon")));
                                mDistrinListName.add(status);
                            }
                        }
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
            sqLiteDatabase.close();
        }
        return mDistrinListName;
    }

    public boolean onAlreadyExist(String treeType, String districtName, String rainFall, String terrainType, String soilType) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();

        sqLiteDatabase = this.getReadableDatabase();
        SQLiteDatabase Database = this.getReadableDatabase();
        String query = "SELECT * FROM VerifyDetails WHERE districtName='" + districtName + "'";
        Cursor cursor = Database.rawQuery(query, null);
        //Log.d("sdafhjk", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                String treeTypes = cursor.getString(cursor.getColumnIndex("treeType"));
                if (treeTypes.equals(treeType)) {
                    String soilTypes = cursor.getString(cursor.getColumnIndex("soilType"));
                    if (soilTypes.equals(soilType)) {
                        String rainFalls = cursor.getString(cursor.getColumnIndex("rainFall"));
                        if (rainFalls.equals(rainFall)) {
                            String terrainTypes = cursor.getString(cursor.getColumnIndex("terrainType"));
                            if (terrainTypes.equals(terrainType)) {
                                String subTreeName = cursor.getString(cursor.getColumnIndex("subTreeName"));
                                String treeName = cursor.getString(cursor.getColumnIndex("treeName"));
                                // Log.d("subTreeName", treeName + "  " + subTreeName + "  " + treeTypes + " " + cursor.getString(cursor.getColumnIndex("id")));
                                //  Log.d("Treeee", treeName + " " + subTreeName + "  " + cursor.getString(cursor.getColumnIndex("treeType")));
                                return true;
                            }
                        }
                    }
                }
            } while (cursor.moveToNext());
        }
        return false;
    }
}
