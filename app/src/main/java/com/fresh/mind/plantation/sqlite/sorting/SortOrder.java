package com.fresh.mind.plantation.sqlite.sorting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.countDown;
import static android.R.attr.version;

/**
 * Created by AND I5 on 05-04-2017.
 */

public class SortOrder extends SQLiteOpenHelper {
    public SortOrder(Context context) {
        super(context, "sort", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sortTable = "create table SortByOrder(id INTEGER PRIMARY KEY AUTOINCREMENT,byType text)";
        db.execSQL(sortTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("SortByOrder", null, contentValues);
        sqLiteDatabase.close();
        Log.d("insertSuccess", "Super");
    }

    public String getSort() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SortByOrder", null);
        String sortBy = null;
        if (cursor.moveToFirst()) {
            do {
                sortBy = cursor.getString(cursor.getColumnIndex("byType"));
                Log.d("dafdaf", "" + cursor.getString(cursor.getColumnIndex("id")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return sortBy;
    }

    public int getCount() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SortByOrder", null);
        int countDown = cursor.getCount();
        return countDown;
    }

    public void update(String sta, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("byType", sta);
        db.update("SortByOrder", contentValues, "id" + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
