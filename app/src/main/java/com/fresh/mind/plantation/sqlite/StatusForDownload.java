package com.fresh.mind.plantation.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

import static android.R.attr.id;
import static android.R.attr.version;

/**
 * Created by AND I5 on 30-12-2017.
 */

public class StatusForDownload extends SQLiteOpenHelper {
    public StatusForDownload(Context context) {
        super(context, "Downloads", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "create table Downloads(id INTEGER PRIMARY KEY AUTOINCREMENT,status text)";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("Downloads", null, contentValues);
    }

    public HashMap<String, String> getStatus() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Downloads", null);
        Log.d("sdasdFormCursor", "" + cursor.getCount());
        HashMap<String, String> status = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                status.put("id", "" + cursor.getString(cursor.getColumnIndex("id")));
                status.put("status", "" + cursor.getString(cursor.getColumnIndex("status")));
            } while (cursor.moveToNext());
        }
        return status;
    }

    public void onUpdate(String idDownload, int status) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", idDownload);
        cv.put("status", "" + status);
        sqLiteDatabase.update("Downloads", cv, "id=" + idDownload, null);

        Log.d("updfatejdkjl", "sucess");
    }
}
