package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.Constant.Config.districtName;

/**
 * Created by AND I5 on 01-03-2017.
 */

public class ContactUs extends SQLiteOpenHelper {
    SQLiteDatabase sql;

    public ContactUs(Context context) {
        super(context, "contact", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String contact = "create table contactAddress(id INTEGER PRIMARY KEY AUTOINCREMENT,districtName text,lastUpdate text,districtNameTamil text,address text,addressTamil text," +
                "phoneNo text,direction text,common_key text,Email text)";
        db.execSQL(contact);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contactAddress");
        onCreate(db);
    }

    public void onInsert(ContentValues contentValues) {
        sql = this.getWritableDatabase();
        sql.insert("contactAddress", null, contentValues);
        sql.close();
    }

    public void delete() {
        sql = this.getReadableDatabase();
        sql.execSQL("delete from contactAddress");
        sql.close();
    }

    public int getCount() {

        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from contactAddress", null);
        int cou = cursor.getCount();
        cursor.close();
        sql.close();
        return cou;


    }

    public ArrayList<HashMap<String, String>> getContactDetails(String languages) {
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursors = sql.rawQuery("select * from contactAddress", null);
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        if (cursors.moveToFirst()) {
            do {
                HashMap<String, String> mHash = new HashMap<>();
                if (languages.equals("1")) {
                    String districtName = cursors.getString(cursors.getColumnIndex("districtNameTamil"));
                    String address = cursors.getString(cursors.getColumnIndex("addressTamil"));
                    String phoneNo = cursors.getString(cursors.getColumnIndex("phoneNo"));
                    mHash.put("districtName", districtName);
                    mHash.put("direction", cursors.getString(cursors.getColumnIndex("direction")));
                    mHash.put("address", address);
                    mHash.put("phoneNo", phoneNo);
                    hashMaps.add(mHash);
                } else {
                    String districtName = cursors.getString(cursors.getColumnIndex("districtName"));
                    String address = cursors.getString(cursors.getColumnIndex("address"));
                    String phoneNo = cursors.getString(cursors.getColumnIndex("phoneNo"));
                    mHash.put("districtName", districtName);
                    mHash.put("address", address);
                    mHash.put("direction", cursors.getString(cursors.getColumnIndex("direction")));
                    mHash.put("phoneNo", phoneNo);
                    hashMaps.add(mHash);
                }
            } while (cursors.moveToNext());
        }
        cursors.close();
        sql.close();
        return hashMaps;
    }

    public ArrayList<HashMap<String, String>> getDistrictWithEmails(String languages) {
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursors = sql.rawQuery("select * from contactAddress", null);
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        HashMap<String, String> mHash = new HashMap<>();
        if (languages.equals("2")) {
            mHash.put("districtName", "Select District");
            mHash.put("Email", "Select Email");
            hashMaps.add(mHash);
        } else if (languages.equals("1")) {
            mHash.put("districtName", "மாவட்டத்தைத் தேர்ந்தெடுக்கவும்");
            mHash.put("Email", "Select Email");
            hashMaps.add(mHash);
        }
        if (cursors.moveToFirst()) {

            do {
                if (languages.equals("1")) {
                    mHash = new HashMap<>();
                    String districtName = cursors.getString(cursors.getColumnIndex("districtNameTamil"));
                    mHash.put("districtName", districtName);
                    mHash.put("Email", cursors.getString(cursors.getColumnIndex("Email")));
                    hashMaps.add(mHash);
                } else {
                    mHash = new HashMap<>();
                    String districtName = cursors.getString(cursors.getColumnIndex("districtName"));
                    mHash.put("districtName", districtName);
                    mHash.put("Email", cursors.getString(cursors.getColumnIndex("Email")));
                    hashMaps.add(mHash);
                }
            } while (cursors.moveToNext());
        }
        cursors.close();
        sql.close();
        return hashMaps;
    }
}
