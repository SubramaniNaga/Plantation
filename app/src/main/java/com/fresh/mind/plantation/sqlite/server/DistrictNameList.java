package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.R.attr.name;
import static android.R.attr.version;
import static com.fresh.mind.plantation.R.id.count;

/**
 * Created by AND I5 on 20-02-2017.
 */

public class DistrictNameList extends SQLiteOpenHelper {


    public DistrictNameList(Context context) {
        super(context, "District", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "create table DistrictListName (Id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lastUpdate text,districtNameTamil text )";
        db.execSQL(tableName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DistrictListName");
        onCreate(db);
    }


    public void onCreate(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("DistrictListName", null, contentValues);
        sqLiteDatabase.close();
        //Log.d("Databessee ", "Insert Sucess");
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
                    if (name != null) {
                        status.put("District", name);
                    }
                } else {
                    String name = cursor.getString(cursor.getColumnIndex("districtNameTamil"));
                    //Log.d("name", "" + name);
                    if (name != null) {
                        status.put("District", name);
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

}
