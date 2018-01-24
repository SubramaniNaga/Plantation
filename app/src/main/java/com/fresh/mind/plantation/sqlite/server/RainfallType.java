package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.fresh.mind.plantation.R.id.districtName;


/**
 * Created by AND I5 on 21-02-2017.
 */

public class RainfallType extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public RainfallType(Context context) {
        super(context, "RainFall", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String RainFallType = "create table RainFall(id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lastUpdate text,Rainfall text,RainfallTamil text,districtNameTamil text)";
        db.execSQL(RainFallType);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS RainFall");
        onCreate(db);
    }

    public void onInsert(ContentValues contentValues) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("RainFall", null, contentValues);
    }


    public void delete() {

        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from RainFall");

        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public ArrayList<HashMap<String, String>> getRainnFallType(String districtName, String language) {
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (language.equals("1")) {
            cursor = sqLiteDatabase.rawQuery("select * from RainFall where districtNameTamil like '%" + districtName + "%'", null);
        } else {
            cursor = sqLiteDatabase.rawQuery("select * from RainFall where districtName like '%" + districtName + "%'", null);
        }
        if (cursor.moveToFirst()) {
            do {

                if (language.equals("1")) {
                    String RainfallTamil = cursor.getString(cursor.getColumnIndex("RainfallTamil"));
                    if (RainfallTamil.isEmpty() || RainfallTamil.equals("null")) {
                    } else {
                        HashMap<String, String> status = new HashMap<>();
                        status.put("Rainfall", RainfallTamil);
                        hashMaps.add(status);
                    }
                } else {
                    String Rainfall = cursor.getString(cursor.getColumnIndex("Rainfall"));
                    if (Rainfall.isEmpty() || Rainfall.equals("null")) {
                    } else {
                        HashMap<String, String> status = new HashMap<>();
                        status.put("Rainfall", Rainfall);
                        hashMaps.add(status);
                    }
                }

            } while (cursor.moveToNext());
        }
        HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();
        hashSet.addAll(hashMaps);
        hashMaps.clear();
        hashMaps.addAll(hashSet);
        cursor.close();
        sqLiteDatabase.close();
        return hashMaps;

    }


    public String getLastDate(String District, String Rainfall) {

        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM RainFall WHERE districtName like %'" + District + "%'and Rainfall like %'" + Rainfall + "%'";
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

    public int getCount() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from RainFall", null);
        int cou = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return cou;
    }

    public ArrayList<KeyPairBoolData> getSetter(String language) {
        ArrayList<KeyPairBoolData> hashMaps = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = sqLiteDatabase.rawQuery("select * from RainFall", null);

        if (cursor.moveToFirst()) {
            do {
                KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                if (language.equals("1")) {
                    String RainfallTamil = cursor.getString(cursor.getColumnIndex("RainfallTamil"));
                    if (RainfallTamil.isEmpty() || RainfallTamil.equals("null")) {
                    } else {
                        /*HashMap<String, String> status = new HashMap<>();
                        status.put("Rainfall", RainfallTamil);
                        hashMaps.add(status);*/
                        keyPairBoolData.setName(RainfallTamil);
                        hashMaps.add(keyPairBoolData);
                    }
                } else {
                    String Rainfall = cursor.getString(cursor.getColumnIndex("Rainfall"));
                    if (Rainfall.isEmpty() || Rainfall.equals("null")) {
                    } else {
                        /*HashMap<String, String> status = new HashMap<>();
                        status.put("Rainfall", Rainfall);
                        hashMaps.add(status);*/
                        keyPairBoolData.setName(Rainfall);
                        hashMaps.add(keyPairBoolData);
                    }
                }

            } while (cursor.moveToNext());
        }
        HashSet<KeyPairBoolData> hashSet = new HashSet<>();
        hashSet.addAll(hashMaps);
        hashMaps.clear();
        hashMaps.addAll(hashSet);
        cursor.close();
        sqLiteDatabase.close();
        return hashMaps;

    }
}
