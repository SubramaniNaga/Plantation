package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.ArrayList;

import static android.R.attr.track;
import static android.R.attr.version;

/**
 * Created by AND I5 on 17-04-2017.
 */

public class AllSoilType extends SQLiteOpenHelper {
    public AllSoilType(Context context) {
        super(context, "AllSoilTypeTable", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "create table DistrictListName(Id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lastUpdate text,districtNameTamil text)";
        db.execSQL("create table AllRainFallTab(id INTEGER PRIMARY KEY AUTOINCREMENT,soilTypeAll text,soilTypeAllTamil text,LastUpdate text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("AllRainFallTab", null, contentValues);
    }

    public void delete() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from AllRainFallTab");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public ArrayList<KeyPairBoolData> getSetter(String language) {
        ArrayList<KeyPairBoolData> mDistrinListName = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AllRainFallTab", null);
        Log.d("soilTypeAllTamil", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                if (language.equals("1")) {
                    keyPairBoolData.setName("" + cursor.getString(cursor.getColumnIndex("soilTypeAllTamil")));

                } else {

                    keyPairBoolData.setName("" + cursor.getString(cursor.getColumnIndex("soilTypeAll")));
                }
                mDistrinListName.add(keyPairBoolData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public int getCount() {


        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AllRainFallTab", null);
        return cursor.getCount();
    }

}
