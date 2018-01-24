package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

import org.json.JSONArray;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;


public class TreeTypeNameList extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public TreeTypeNameList(Context context) {
        super(context, "TreeTypeNameList", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table = "create table TreeTypeNameList(id INTEGER PRIMARY KEY AUTOINCREMENT,treeType text,treeIcon blob,lastUpdate text,treeTypeTamil text,storagePath text,common_key text)";
        db.execSQL(table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TreeTypeNameList");
        onCreate(db);
    }

    public void delete() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from TreeTypeNameList");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("TreeTypeNameList", null, contentValues);
    }


    public ArrayList<HashMap<String, String>> getTreTypeeNames(String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeTypeNameList", null);
        Log.d("sdasdFormCursor", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                if (language.equals("1")) {
                    HashMap<String, String> status = new HashMap<>();
                    status.put("treeType", cursor.getString(cursor.getColumnIndex("treeTypeTamil")));
                    status.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);
                } else {
                    HashMap<String, String> status = new HashMap<>();
                    status.put("treeType", cursor.getString(cursor.getColumnIndex("treeType")));
                    status.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public ArrayList<KeyPairBoolData> getTreTypeeNamesKayPair(String language) {
        ArrayList<KeyPairBoolData> mDistrinListName = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeTypeNameList", null);
        Log.d("sdasdFormCursor", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                if (language.equals("1")) {
                    keyPairBoolData.setName(""+cursor.getString(cursor.getColumnIndex("treeTypeTamil")));
                    /*status.put("treeType", cursor.getString(cursor.getColumnIndex("treeTypeTamil")));
                    status.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);*/
                } else {
                   /* HashMap<String, String> status = new HashMap<>();
                    status.put("treeType", cursor.getString(cursor.getColumnIndex("treeType")));
                    status.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);*/
                    keyPairBoolData.setName(""+cursor.getString(cursor.getColumnIndex("treeType")));
                }
                mDistrinListName.add(keyPairBoolData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, byte[]>> getImages(String langauage) {
        ArrayList<HashMap<String, byte[]>> mDistrinListName = new ArrayList<HashMap<String, byte[]>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeTypeNameList", null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, byte[]> status = new HashMap<>();
                if (langauage.equals("1")) {
                    byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("treeIcon"));
                    status.put("treeIcon", treeIcon);
                    mDistrinListName.add(status);
                } else {
                    byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("treeIcon"));
                    status.put("treeIcon", treeIcon);
                    mDistrinListName.add(status);
                }
            } while (cursor.moveToNext());
        }
        return mDistrinListName;
    }


    public String getLastDate(String last_update) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM TreeTypeNameList WHERE lastUpdate='" + last_update + "'";
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

    public String getTreeType(String treeType) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM TreeTypeNameList WHERE treeType like '%" + treeType + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String mLastDate = null;
        if (cursor.moveToFirst()) {
            do {
                mLastDate = cursor.getString(cursor.getColumnIndex("treeType"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mLastDate;
    }

    public int getId(String treeTypeDb) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM TreeTypeNameList WHERE treeType like '%" + treeTypeDb + "%'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return id;
    }

    public void update(ContentValues contentValues, int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update("TreeTypeNameList", contentValues, "id" + "=?", new String[]{String.valueOf(id)});
    }

    public int getCout() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeTypeNameList", null);
        int count = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return count;

    }


    public ArrayList<HashMap<String, String>> getImagePaths(String languages) {
        ArrayList<HashMap<String, String>> mImagePaths = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeTypeNameList", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> mImages = new HashMap<>();
                mImages.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                mImagePaths.add(mImages);
            } while (cursor.moveToNext());
        }
        return mImagePaths;
    }
}
