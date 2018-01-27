package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.fresh.mind.plantation.setter.SetterDistrict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.R.attr.key;
import static android.R.attr.name;
import static android.R.attr.version;
import static com.fresh.mind.plantation.R.id.count;

/**
 * Created by AND I5 on 20-02-2017.
 */

public class DistrictNameList extends SQLiteOpenHelper {

    public DistrictNameList(Context context) {
        super(context, "DistrictListName", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "create table DistrictListName(Id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lastUpdate text,districtNameTamil text,common_key text,treetypes text,treetypesTamil text,storagePath text)";
        db.execSQL(tableName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DistrictListName");

        // Log.d("dsadfasdf", "" + oldVersion + "  " + newVersion);
        onCreate(db);
    }


    public void onCreate(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("DistrictListName", null, contentValues);
        sqLiteDatabase.close();
        // Log.d("Databessee ", "Insert Sucess");
    }


    public int getCount() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from DistrictListName", null);
        int count = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return count;
    }

    public void delete() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from DistrictListName");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
        sqLiteDatabase.close();
    }

    public ArrayList<HashMap<String, String>> getDistrictNames(String languages) {
        //Log.d("languages", "" + languages);
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from DistrictListName order by districtName ASC", null);
        //Log.d("cur12343sor", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                if (languages.equals("2")) {
                    String name = cursor.getString(cursor.getColumnIndex("districtName"));
                    String storagePath = cursor.getString(cursor.getColumnIndex("storagePath"));
                    if (name != null) {
                        status.put("District", name);
                        status.put("storagePath", storagePath);
                    }
                } else {
                    String storagePath = cursor.getString(cursor.getColumnIndex("storagePath"));
                    String name = cursor.getString(cursor.getColumnIndex("districtNameTamil"));
                    //Log.d("name", "" + name);
                    if (name != null) {
                        status.put("District", name);
                        status.put("storagePath", storagePath);

                    }
                }
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public String getLastDate(String last_update) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM DistrictListName WHERE lastUpdate='" + last_update + "'";
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

    public ArrayList<KeyPairBoolData> getSetter(String languages) {
        ArrayList<KeyPairBoolData> mDistrinListName = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from DistrictListName order by districtName ASC", null);
        //Log.d("cur12343sor", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                KeyPairBoolData setterDistrict = new KeyPairBoolData();
                if (languages.equals("2")) {
                    String name = cursor.getString(cursor.getColumnIndex("districtName"));
                    if (name != null) {
                        setterDistrict.setName(name);
                        setterDistrict.setSelected(false);
                    }
                } else {
                    String name = cursor.getString(cursor.getColumnIndex("districtNameTamil"));
                    //Log.d("name", "" + name);
                    if (name != null) {
                        setterDistrict.setName(name);
                        setterDistrict.setSelected(false);

                    }
                }
                mDistrinListName.add(setterDistrict);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public ArrayList<String> getDistrictBaseTreeType(String languages, String selectedDistrictName) {
        ArrayList<String> stringArrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query;
        if (languages.equals("2")) {
            query = "SELECT * FROM DistrictListName WHERE districtName like '%" + selectedDistrictName + "%'";
        } else {
            query = "SELECT * FROM DistrictListName WHERE districtNameTamil like '%" + selectedDistrictName + "%'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String values;
        if (cursor.moveToFirst()) {
            do {
                if (languages.equals("2")) {
                    values = cursor.getString(cursor.getColumnIndex("treetypes"));

                } else {
                    values = cursor.getString(cursor.getColumnIndex("treetypesTamil"));
                }
                if (values != null && !values.isEmpty()) {
                    //stringArrayList.add("Select Tree Types");
                    String[] valuesSplit = values.split(",");
                    for (int i = 0; i < valuesSplit.length; i++) {
                        stringArrayList.add(capitalize(valuesSplit[i]));
                    }
                } else {
                    stringArrayList.add("No Tree Types");
                }


            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return stringArrayList;
    }

    public ArrayList<KeyPairBoolData> getDistrictBaseTreeTypeKeyPair(String languages, String selectedDistrictName) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<KeyPairBoolData> mDistrinListName = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query;
        if (languages.equals("2")) {
            query = "SELECT * FROM DistrictListName WHERE districtName like '%" + selectedDistrictName + "%'";
        } else {
            query = "SELECT * FROM DistrictListName WHERE districtNameTamil like '%" + selectedDistrictName + "%'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String values;
        if (cursor.moveToFirst()) {
            do {

                if (languages.equals("2")) {
                    values = cursor.getString(cursor.getColumnIndex("treetypes"));

                } else {
                    values = cursor.getString(cursor.getColumnIndex("treetypesTamil"));
                }
                if (values != null && !values.isEmpty()) {
                    //stringArrayList.add("Select Tree Types");
                    String[] valuesSplit = values.split(",");
                    for (int i = 0; i < valuesSplit.length; i++) {
                        // stringArrayList.add(capitalize(valuesSplit[i]));
                        KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                        keyPairBoolData.setName("" + capitalize(valuesSplit[i]));
                        mDistrinListName.add(keyPairBoolData);

                    }

                } else {
                    stringArrayList.add("No Tree Types");
                }


            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return mDistrinListName;
    }


    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
