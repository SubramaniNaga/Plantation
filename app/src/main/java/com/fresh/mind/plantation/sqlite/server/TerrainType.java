package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static android.R.attr.version;
import static com.fresh.mind.plantation.R.id.districtName;

/**
 * Created by AND I5 on 21-02-2017.
 */

public class TerrainType extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public TerrainType(Context context) {
        super(context, "TerrainType", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TerrainType = "create table Terrain(id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lastUpdate text,Terrain text,districtNameTamil text,TerrainTamil text)";
        db.execSQL(TerrainType);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Terrain");
        onCreate(db);
    }

    public void onInsert(ContentValues contentValues) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("Terrain", null, contentValues);

    }


    public void delete() {

        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from Terrain");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);

    }

    public ArrayList<HashMap<String, String>> getTerrainType(String districtName, String language) {
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        Cursor cursor = null;
        sqLiteDatabase = this.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("select * from Terrain where districtNameTamil like '%" + districtName + "%'", null);
        if (language.equals("1")) {
        } else {
            cursor = sqLiteDatabase.rawQuery("select * from Terrain where districtName like '%" + districtName + "%'", null);
        }
        if (cursor.moveToFirst()) {
            do {
                if (language.equals("1")) {
                    String TerrainTamil = cursor.getString(cursor.getColumnIndex("TerrainTamil"));
                    if (TerrainTamil.isEmpty() || TerrainTamil.equals("null")) {
                    } else {
                        HashMap<String, String> status = new HashMap<>();
                        status.put("Terrain", TerrainTamil);
                        hashMaps.add(status);
                    }
                } else {
                    HashMap<String, String> status = new HashMap<>();
                    String Terrain = cursor.getString(cursor.getColumnIndex("Terrain"));
                    if (Terrain.isEmpty() || Terrain.equals("null")) {
                    } else {
                        status.put("Terrain", Terrain);
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


    public String getLastDate(String District, String Terrain) {

        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM Terrain WHERE districtName like '%" + District + "%'and Terrain like '%" + Terrain + "%'";
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
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM Terrain";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int cou = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return cou;
    }

    public ArrayList<KeyPairBoolData> getSetter(String language) {
        ArrayList<KeyPairBoolData> hashMaps = new ArrayList<>();
        Cursor cursor = null;
        sqLiteDatabase = this.getReadableDatabase();

        cursor = sqLiteDatabase.rawQuery("select * from Terrain", null);

        if (cursor.moveToFirst()) {
            do {
                KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                if (language.equals("1")) {
                    String TerrainTamil = cursor.getString(cursor.getColumnIndex("TerrainTamil"));
                    if (TerrainTamil.isEmpty() || TerrainTamil.equals("null")) {
                    } else {
                        /*HashMap<String, String> status = new HashMap<>();
                        status.put("Terrain", TerrainTamil);
                        hashMaps.add(status);*/
                        keyPairBoolData.setName(TerrainTamil);
                        hashMaps.add(keyPairBoolData);
                    }
                } else {
                    HashMap<String, String> status = new HashMap<>();
                    String Terrain = cursor.getString(cursor.getColumnIndex("Terrain"));
                    if (Terrain.isEmpty() || Terrain.equals("null")) {
                    } else {
                        /*status.put("Terrain", Terrain);
                        hashMaps.add(status);*/
                        keyPairBoolData.setName(Terrain);
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
