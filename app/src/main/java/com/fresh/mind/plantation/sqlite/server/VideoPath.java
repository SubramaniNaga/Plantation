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

import static android.R.attr.version;

/**
 * Created by AND I5 on 01-03-2017.
 */

public class VideoPath extends SQLiteOpenHelper {


    public VideoPath(Context context) {
        super(context, "VideoPath", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String VideoPath = "create table VideoPathTable(id INTEGER PRIMARY KEY AUTOINCREMENT,video text,Description text,DescriptionTamil text,lastUpdate text)";
        db.execSQL(VideoPath);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS VideoPathTable");
        onCreate(db);
    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sql = this.getWritableDatabase();
        sql.insert("VideoPathTable", null, contentValues);
        sql.close();
    }

    public ArrayList<HashMap<String, String>> getViewPath(String s) {
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from VideoPathTable", null);
        Log.d("count", "asasa" + cursor.getCount() + "  " + s);
        if (cursor.moveToFirst()) {
            do {
                if (s.equals("1")) {
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("path", "" + cursor.getString(cursor.getColumnIndex("video")));
                    stringStringHashMap.put("Description", "" + cursor.getString(cursor.getColumnIndex("DescriptionTamil")));
                    hashMaps.add(stringStringHashMap);
                } else {
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("path", "" + cursor.getString(cursor.getColumnIndex("video")));
                    Log.d("descriptionVideo", "" + cursor.getString(cursor.getColumnIndex("Description")));
                    stringStringHashMap.put("Description", "" + cursor.getString(cursor.getColumnIndex("Description")));

                    hashMaps.add(stringStringHashMap);
                }
            } while (cursor.moveToNext());
        }
        HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();
        hashSet.addAll(hashMaps);
        hashMaps.clear();
        hashMaps.addAll(hashSet);
        cursor.close();
        sql.close();
        return hashMaps;
    }

    public void delete() {
        SQLiteDatabase sql = getReadableDatabase();
        sql.execSQL("delete from VideoPathTable");
        sql.close();

    }
}
