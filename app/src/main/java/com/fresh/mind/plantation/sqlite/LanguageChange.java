package com.fresh.mind.plantation.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import static android.R.attr.id;
import static android.R.attr.password;
import static android.R.attr.version;

/**
 * Created by AND I5 on 25-02-2017.
 */

public class LanguageChange extends SQLiteOpenHelper {


    public LanguageChange(Context context) {
        super(context, "Language", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String mLanguage = "create table Language(id INTEGER primary key autoincrement,languages text)";
        db.execSQL(mLanguage);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("Language", null, contentValues);
        sqLiteDatabase.close();
    }

    public void update(String sta, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("languages", sta);
        db.update("Language", contentValues, "id" + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public String getStatus() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Language", null);
        String languages = null;
        if (cursor.moveToFirst()) {
            do {
                languages = cursor.getString(cursor.getColumnIndex("languages"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return languages;
    }

    public int getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + "Language";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public HashMap<String, String> getLanguageStatus() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Language", null);
        if (cursor.moveToFirst()) {
            do {
                String languages = cursor.getString(cursor.getColumnIndex("languages"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                stringStringHashMap.put("languages", languages);
                stringStringHashMap.put("id", "" + id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return stringStringHashMap;
    }
}
