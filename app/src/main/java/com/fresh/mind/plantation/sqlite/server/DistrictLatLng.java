package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 24-01-2018.
 */

public class DistrictLatLng extends SQLiteOpenHelper {
    public DistrictLatLng(Context context) {
        super(context, "DistrictLatLng", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "create table DistrictLatLng(Id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lat text,lng text,office text)";
        db.execSQL(tableName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DistrictLatLng");
        onCreate(db);
    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("DistrictLatLng", null, contentValues);
        sqLiteDatabase.close();


    }

    public ArrayList<HashMap<String, String>> getLanLng() {
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from DistrictLatLng", null);
        //Log.d("cur12343sor", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                status.put("districtName", cursor.getString(cursor.getColumnIndex("districtName")));
                status.put("lat", cursor.getString(cursor.getColumnIndex("lat")));
                status.put("lng", cursor.getString(cursor.getColumnIndex("lng")));
                status.put("office", cursor.getString(cursor.getColumnIndex("office")));
                status.put("id", cursor.getString(cursor.getColumnIndex("Id")));

                hashMaps.add(status);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return hashMaps;
    }
}
