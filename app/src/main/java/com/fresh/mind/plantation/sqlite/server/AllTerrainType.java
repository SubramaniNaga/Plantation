package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import java.util.ArrayList;

/**
 * Created by AND I5 on 17-04-2017.
 */

public class AllTerrainType extends SQLiteOpenHelper {
    public AllTerrainType(Context context) {
        super(context, "AllTerrainTypeTable", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table AllTerrainTypeTab(id INTEGER PRIMARY KEY AUTOINCREMENT,terrainTypeAll text,terrainTypeAllTamil text,LastUpdate text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("AllTerrainTypeTab", null, contentValues);
    }

    public void delete() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from AllTerrainTypeTab");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public ArrayList<KeyPairBoolData> getSetter(String language) {
        ArrayList<KeyPairBoolData> mDistrinListName = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AllTerrainTypeTab", null);
        Log.d("soilTypeAllTamil", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                if (language.equals("1")) {
                    keyPairBoolData.setName("" + cursor.getString(cursor.getColumnIndex("terrainTypeAllTamil")));

                } else {

                    keyPairBoolData.setName("" + cursor.getString(cursor.getColumnIndex("terrainTypeAll")));
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
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AllTerrainTypeTab", null);
        Log.d("soilTypeAllTamil", "" + cursor.getCount());
        return cursor.getCount();
    }
}