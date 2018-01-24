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
 * Created by AND I5 on 21-02-2017.
 */

public class TreeTypeInfo extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public TreeTypeInfo(Context context) {
        super(context, "TreeTypeInfo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TreeTypeInfo = "create table TreeTypeTable(id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lastUpdate text,treeType text,soilType text," +
                "districtNameTamil text,treeTypeTamil text,soilTypeTamil text)";
        db.execSQL(TreeTypeInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TreeTypeTable");
        onCreate(db);
    }

    public void onInsert(ContentValues contentValues) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("TreeTypeTable", null, contentValues);
    }


    public void delete() {

        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from TreeTypeTable");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }


    public ArrayList<HashMap<String, String>> getTreeType(String districtName, String soillType, String language) {
        Log.d("dfgdfg", "" + districtName + "  " + soillType);
        ArrayList<HashMap<String, String>> mSoilList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        String qury = null;
        if (language.equals("1")) {
            qury = "select * from TreeTypeTable where districtNameTamil like '%" + districtName + "%'and soilTypeTamil like '%" + soillType + "%'";

        } else {
            qury = "select * from TreeTypeTable where districtName like '%" + districtName + "%'and soilType like '%" + soillType + "%'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(qury, null);

        Log.d("vasss545564", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                if (language.equals("1")) {
                    String soilType = cursor.getString(cursor.getColumnIndex("treeTypeTamil"));
                    Log.d("sosoilTypeilType", "" + soilType);
                    if (soilType.isEmpty() || soillType.equals("null")) {
                    } else {
                        HashMap<String, String> mHash = new HashMap<>();
                        mHash.put("Treetype", soilType);
                        mSoilList.add(mHash);
                    }
                } else {
                    String soilType = cursor.getString(cursor.getColumnIndex("treeType"));
                    Log.d("sosoilTypeilType", "" + soilType);
                    if (soillType.isEmpty() || soillType.equals("null")) {
                    } else {
                        HashMap<String, String> mHash = new HashMap<>();
                        mHash.put("Treetype", soilType);
                        mSoilList.add(mHash);
                    }
                }
            } while (cursor.moveToNext());
        }
        HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();
        hashSet.addAll(mSoilList);
        mSoilList.clear();
        mSoilList.addAll(hashSet);
        cursor.close();
        sqLiteDatabase.close();
        Log.d("mSoilList", "" + mSoilList.size());
        return mSoilList;
    }

    public ArrayList<HashMap<String, String>> getTreTypeeNamesFromDistrict(String selectedDistrictName, String language) {

        ArrayList<HashMap<String, String>> mSoilList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        String qury = null;
        if (language.equals("1")) {
            qury = "select * from TreeTypeTable where districtNameTamil like '%" + selectedDistrictName + "%'";
        } else {
            qury = "select * from TreeTypeTable where districtName like '%" + selectedDistrictName.trim() + "%'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(qury, null);
        Log.d("cursor location", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                Log.d("sddss", "" + cursor.getString(cursor.getColumnIndex("treeTypeTamil")));
                if (language.equals("1")) {
                    String treeType = cursor.getString(cursor.getColumnIndex("treeTypeTamil"));
                    if (treeType.equals("null") || treeType.isEmpty()) {
                    } else {
                        String soilType = cursor.getString(cursor.getColumnIndex("treeTypeTamil"));
                        HashMap<String, String> mHash = new HashMap<>();
                        mHash.put("Treetype", soilType);
                        mSoilList.add(mHash);
                    }
                } else {
                    Log.d("sddssEng", "" + cursor.getString(cursor.getColumnIndex("treeType")));

                    String soilType = cursor.getString(cursor.getColumnIndex("treeType"));
                    if (soilType.equals("null") || soilType.isEmpty()) {
                    } else {
                        HashMap<String, String> mHash = new HashMap<>();
                        mHash.put("Treetype", soilType);
                        mSoilList.add(mHash);
                    }
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


    public String getLastDate(String last_update) {

        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM TreeTypeTable WHERE lastUpdate like '%" + last_update + "%'";
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
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeTypeTable", null);
        int cou = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return cou;
    }

    public ArrayList<KeyPairBoolData> getSetter(String language) {
        ArrayList<KeyPairBoolData> mSoilList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        String qury = "select * from TreeTypeTable";
        Cursor cursor = sqLiteDatabase.rawQuery(qury, null);
        //Log.d("cursor location", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
              //  Log.d("sddss", "" + cursor.getString(cursor.getColumnIndex("treeTypeTamil")));
                if (language.equals("1")) {
                    String treeType = cursor.getString(cursor.getColumnIndex("treeTypeTamil"));
                    if (treeType.equals("null") || treeType.isEmpty()) {
                    } else {
                        String soilType = cursor.getString(cursor.getColumnIndex("treeTypeTamil"));
                        /*HashMap<String, String> mHash = new HashMap<>();
                        mHash.put("Treetype", soilType);
                        mSoilList.add(mHash);*/
                        keyPairBoolData.setName(soilType);
                        mSoilList.add(keyPairBoolData);

                    }
                } else {
                  //  Log.d("sddssEng", "" + cursor.getString(cursor.getColumnIndex("treeType")));

                    String soilType = cursor.getString(cursor.getColumnIndex("treeType"));
                    if (soilType.equals("null") || soilType.isEmpty()) {
                    } else {
                        /*HashMap<String, String> mHash = new HashMap<>();
                        mHash.put("Treetype", soilType);
                        mSoilList.add(mHash);*/
                        keyPairBoolData.setName(soilType);
                        mSoilList.add(keyPairBoolData);
                    }
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
