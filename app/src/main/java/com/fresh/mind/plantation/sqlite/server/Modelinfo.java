package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class Modelinfo extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public Modelinfo(Context context) {
        super(context, "Modelinfo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table = "create table Modelinfo(id INTEGER PRIMARY KEY AUTOINCREMENT,Description text,treeIcon blob,lastUpdate text,Description_Tamil text,storagePath text)";
        db.execSQL(table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Modelinfo");
        onCreate(db);
    }

    public void delete() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from Modelinfo");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("Modelinfo", null, contentValues);
    }

    public ArrayList<HashMap<String, String>> getDescription(String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Modelinfo", null);
        Log.d("sdasdFormCursor", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                if (language.equals("1")) {
                    HashMap<String, String> status = new HashMap<>();
                    String tamil = cursor.getString(cursor.getColumnIndex("Description_Tamil"));
                    Log.d("dsklfkldjkda", "" + tamil);
                    if (tamil != null && !tamil.isEmpty()) {
                        status.put("Description_Tamil", tamil);
                    } else {
                        status.put("Description_Tamil", cursor.getString(cursor.getColumnIndex("Description")));
                    }
                    status.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);
                } else {
                    HashMap<String, String> status = new HashMap<>();
                    status.put("Description", cursor.getString(cursor.getColumnIndex("Description")));
                    status.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public String getModelLastDate(String last_update) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM Modelinfo WHERE lastUpdate='" + last_update + "'";
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
