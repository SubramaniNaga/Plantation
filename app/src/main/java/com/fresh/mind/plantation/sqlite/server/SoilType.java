package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.fresh.mind.plantation.R.id.districtName;

/**
 * Created by AND I5 on 20-02-2017.
 */

public class SoilType extends SQLiteOpenHelper {


    public SoilType(Context context) {
        super(context, "SoilType", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SoilType = "create table SoilType(id INTEGER PRIMARY KEY AUTOINCREMENT,soilType text,districtName text,Treetype text,terrainType text,rainFallType text,lastUpdate text," +
                "soilTypeTamil text,districtNameTamil text,TreetypeTamil text,terrainTypeTamil text,rainFallTypeTamil text)";
        db.execSQL(SoilType);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SoilType");
        onCreate(db);
    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("SoilType", null, contentValues);
        sqLiteDatabase.close();

    }

    public void delete() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from SoilType");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
        sqLiteDatabase.close();
    }

    public void onUpdate(String district, String soil, String treetype) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SoilType", null);
        if (cursor.moveToFirst()) {
            do {

                String dName = cursor.getString(cursor.getColumnIndex("districtName"));
                Log.d("dName", "" + dName + "  " + treetype);
                if (dName.equals(district)) {
                    int id = cursor.getInt(cursor.getColumnIndex("districtName"));
                    ContentValues cv = new ContentValues();
                    cv.put("Treetype", treetype);
                    cv.put("soilType", soil);
                    sqLiteDatabase.update("SoilType", cv, "id=" + id, null);

                }
            } while (cursor.moveToNext());

        }
    }


    public void onUpdateRainFall(String district, String soil, String treetype) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SoilType", null);
        if (cursor.moveToFirst()) {
            do {
                String dName = cursor.getString(cursor.getColumnIndex("districtName"));
                Log.d("dName", "" + dName);
                if (dName.equals(district)) {
                    int id = cursor.getInt(cursor.getColumnIndex("districtName"));
                    ContentValues cv = new ContentValues();
                    cv.put("Treetype", treetype);
                    cv.put("soilType", soil);
                    sqLiteDatabase.update("SoilType", cv, "id=" + id, null);

                }
            } while (cursor.moveToNext());

        }
    }

    public ArrayList<HashMap<String, String>> getSoilType(String districtName, String type, String language) {
        ArrayList<HashMap<String, String>> mSoilList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if (language.equals("1")) {
            cursor = sqLiteDatabase.rawQuery("select * from SoilType where districtNameTamil like '%" + districtName + "%'", null);
        } else {
            cursor = sqLiteDatabase.rawQuery("select * from SoilType where districtName like '%" + districtName + "%'", null);
        }
        Log.d("mSoiltypeListcursor", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                String soilType = "";
                if (language.equals("1")) {
                  /*  if (type.equals("rainFall")) {
                        soilType = cursor.getString(cursor.getColumnIndex("rainFallTypeTamil"));
                    } else if (type.equals("Terrain")) {
                        soilType = cursor.getString(cursor.getColumnIndex("terrainTypeTamil"));
                    } else if (type.equals("Soil")) {*/
                    soilType = cursor.getString(cursor.getColumnIndex("soilTypeTamil"));

                    //}
                } else {
                   /* if (type.equals("rainFall")) {
                        soilType = cursor.getString(cursor.getColumnIndex("rainFallType"));
                    } else if (type.equals("Terrain")) {
                        soilType = cursor.getString(cursor.getColumnIndex("terrainType"));
                    } else if (type.equals("Soil")) {*/
                    soilType = cursor.getString(cursor.getColumnIndex("soilType"));
                    //}
                }

                if (soilType.equals("null") || soilType.isEmpty()) {
                } else {
                    HashMap<String, String> mHash = new HashMap<>();
                    mHash.put("soilType", soilType);
                    mSoilList.add(mHash);
                }


            } while (cursor.moveToNext());
        }
        HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();
        hashSet.addAll(mSoilList);
        mSoilList.clear();
        mSoilList.addAll(hashSet);
        cursor.close();
        sqLiteDatabase.close();
        return mSoilList;
    }

    public ArrayList<HashMap<String, String>> getRainfall(String districtName, String soillType) {

        ArrayList<HashMap<String, String>> mSoilList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String qury = "select * from SoilType where districtName like '%" + districtName + "%'and soilType like '%" + soillType + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(qury, null);
        if (cursor.moveToFirst()) {
            do {
                String soilType = cursor.getString(cursor.getColumnIndex("soilType"));
                HashMap<String, String> mHash = new HashMap<>();
                mHash.put("rainFall", soilType);
               // Log.d("soilType Ghashag", "" + soilType);
                mSoilList.add(mHash);

            } while (cursor.moveToNext());
        }
        return mSoilList;

    }

    public ArrayList<HashMap<String, String>> getTreeType(String districtName, String soillType) {

        ArrayList<HashMap<String, String>> mSoilList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String qury = "select * from SoilType where districtName like '%" + districtName + "%'and soilType like '%" + soillType + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(qury, null);
      //  Log.d("vasss545564", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                String soilType = cursor.getString(cursor.getColumnIndex("Treetype"));
                HashMap<String, String> mHash = new HashMap<>();
                mHash.put("Treetype", soilType);

                mSoilList.add(mHash);

            } while (cursor.moveToNext());
        }
        return mSoilList;
    }


    public String getLastDate(String last_update) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM SoilType WHERE lastUpdate='" + last_update + "'";
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

    public String getTreeTypeName(String treetype) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM SoilType WHERE Treetype='" + treetype + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String mLastDate = null;

        if (cursor.moveToFirst()) {
            do {
                mLastDate = cursor.getString(cursor.getColumnIndex("Treetype"));

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mLastDate;
    }

    public int getId(String treetypeDb) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM SoilType WHERE Treetype='" + treetypeDb + "'";
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
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update("SoilType", contentValues, "id" + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

    }

    public int getCount() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SoilType", null);
        int cou = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return cou;
    }

    public ArrayList<KeyPairBoolData> getSetter(String language) {
        ArrayList<KeyPairBoolData> mSoilList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;

        cursor = sqLiteDatabase.rawQuery("select * from SoilType", null);

        if (cursor.moveToFirst()) {
            do {
                KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                String soilType = "";
                if (language.equals("1")) {

                    soilType = cursor.getString(cursor.getColumnIndex("soilTypeTamil"));
                } else {
                    soilType = cursor.getString(cursor.getColumnIndex("soilType"));
                }

                if (soilType.equals("null") || soilType.isEmpty()) {
                } else {
                    HashMap<String, String> mHash = new HashMap<>();
                    keyPairBoolData.setName(soilType);
                    mSoilList.add(keyPairBoolData);
                }


            } while (cursor.moveToNext());
        }
        HashSet<KeyPairBoolData> hashSet = new HashSet<>();
        hashSet.addAll(mSoilList);
        mSoilList.clear();
        mSoilList.addAll(hashSet);
        cursor.close();
        sqLiteDatabase.close();
        return mSoilList;
    }
}
