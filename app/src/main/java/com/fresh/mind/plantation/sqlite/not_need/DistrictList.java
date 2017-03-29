package com.fresh.mind.plantation.sqlite.not_need;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 30-01-2017.
 */

public class DistrictList extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public DistrictList(FragmentActivity context) {
        super(context, "District", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "create table districtBook(id INTEGER PRIMARY KEY AUTOINCREMENT, districtName text )";
        db.execSQL(tableName);
        // Log.d("Databessee ", "Create Sucess");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onCreate(ContentValues contentValues) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("districtBook", null, contentValues);
        //Log.d("Databessee ", "Insert Sucess");
    }


    public int getCount() {
        int count = 0;
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from districtBook", null);
        count = cursor.getCount();
        return count;
    }

    public void delete() {

        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from districtBook");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public ArrayList<HashMap<String, String>> getDistrictNames() {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();

        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from districtBook order by districtName ASC", null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                status.put("District", cursor.getString(cursor.getColumnIndex("districtName")));
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }

        return mDistrinListName;
    }
}
