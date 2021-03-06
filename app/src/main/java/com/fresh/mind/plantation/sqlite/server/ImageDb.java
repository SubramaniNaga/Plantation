package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fresh.mind.plantation.Constant.Config.mImagePaths;


public class ImageDb extends SQLiteOpenHelper {


    public ImageDb(Context context) {
        super(context, "Imge", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Imge = "create table tableImage(Id INTEGER PRIMARY KEY AUTOINCREMENT,treeIcon blob,common_key text,treeName text,treeNameTamil text,storagePath text,thumbnail text)";
        db.execSQL(Imge);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "tableImage");
        onCreate(db);
    }

    public void onInsert(String TreeName, String common_key, String treeNameTamil, String storagePath, String thumbnail) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("treeIcon", imgByte);
        contentValues.put("common_key", common_key);
        contentValues.put("treeName", TreeName);
        contentValues.put("storagePath", storagePath);
        contentValues.put("treeNameTamil", treeNameTamil);
        contentValues.put("thumbnail", thumbnail);
        sqLiteDatabase.insertWithOnConflict("tableImage", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        // Log.d("Create", "Successs Inseert" + contentValues);

    }


    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("tableImage", null, contentValues);
    }


    public void delete() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from tableImage");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public ArrayList<HashMap<String, byte[]>> getImages(String treeName, String lang) {
        ArrayList<HashMap<String, byte[]>> mDistrinListName = new ArrayList<HashMap<String, byte[]>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        //Log.d("mDistrinListName come", "" + treeName + "  " + lang);
        if (lang.equals("1")) {
            cursor = sqLiteDatabase.rawQuery("select * from tableImage", null);
            //  Log.d("mDistrinListNamedsdscursor", "" + cursor.getCount());
            if (cursor.getCount() == 0) {
                cursor = sqLiteDatabase.rawQuery("select * from tableImage", null);
            }
        } else {
            cursor = sqLiteDatabase.rawQuery("select * from tableImage", null);
        }

        //Log.d("mDistrinListName CUrsor", "" + cursor.getCount());
        cursor.moveToNext();
        while (cursor.moveToNext()) {

            HashMap<String, byte[]> status = new HashMap<>();
               /* String treeNameDb = cursor.getString(cursor.getColumnIndex("treeName"));
                Log.d("mDistrinListNametreeNameDb", "" + treeNameDb);
                if (treeName.equals(treeNameDb)) {*/
            byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("treeIcon"));
            status.put("treeIcon", treeIcon);
            //}
            mDistrinListName.add(status);
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, byte[]>> getTreeByImages(String treeType, String selectedDistrictName) {
        ArrayList<HashMap<String, byte[]>> mDistrinListName = new ArrayList<HashMap<String, byte[]>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM tableImage WHERE tableImage='" + selectedDistrictName + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        //Log.d("cursorcursor", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, byte[]> status = new HashMap<>();
                byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("treeIcon"));
                status.put("treeIcon", treeIcon);
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public String getTreeName(String treeName) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from tableImage where treeName='" + treeName + "'", null);
        String treeNameDb = null;
        if (cursor.moveToFirst()) {
            do {
                treeNameDb = cursor.getString(cursor.getColumnIndex("treeName"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return treeNameDb;
    }

    public int getId(String treeNameDb) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from tableImage where treeName='" + treeNameDb + "'", null);
        int treeId = 0;
        if (cursor.moveToFirst()) {
            do {
                treeNameDb = cursor.getString(cursor.getColumnIndex("Id"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return treeId;
    }

    public void delteTreeNameImage(String treeNameDb) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from tableImage where='" + treeNameDb + "'");
    }

    public ArrayList<HashMap<String, String>> getImagePaths(String languages, String commonKey) {
        ArrayList<HashMap<String, String>> mImagePaths = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // Log.d("tre12457872eName", "" + commonKey);

        Cursor cursor = sqLiteDatabase.rawQuery("select * from tableImage where common_key='" + commonKey + "'", null);
        //  Log.d("cursorkjioucommonKey", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                //Log.d("as22342as", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                String treeNameEng = cursor.getString(cursor.getColumnIndex("treeName"));
                String treeNameTamil = cursor.getString(cursor.getColumnIndex("treeNameTamil"));
                //  Log.d("treeNatreeNameEngme", commonKey.length() + " firss " + commonKey + "  Second  " + treeNameEng.length() + "   " + treeNameEng + " " + treeNameTamil);
                /*if (treeNameEng.trim().toLowerCase().equals(treeName.trim().toLowerCase())) {*/
                // Log.d("sd23423412", "1");
                HashMap<String, String> mImages = new HashMap<>();
                mImages.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                mImagePaths.add(mImages);
               /* } else if (treeNameTamil.equals(treeName)) {
                    HashMap<String, String> mImages = new HashMap<>();
                    // Log.d("sd23423412", "2");
                    mImages.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                    mImagePaths.add(mImages);
                }*/

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mImagePaths;
    }
}
