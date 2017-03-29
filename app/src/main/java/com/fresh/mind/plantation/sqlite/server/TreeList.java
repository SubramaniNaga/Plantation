package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;

import static android.R.attr.icon;
import static android.R.attr.version;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.fresh.mind.plantation.Constant.Config.mImages;
import static com.fresh.mind.plantation.R.id.districtName;
import static com.fresh.mind.plantation.R.id.square;
import static com.fresh.mind.plantation.R.string.district;
import static com.fresh.mind.plantation.R.string.treeType;

/**
 * Created by AND I5 on 20-02-2017.
 */

public class TreeList extends SQLiteOpenHelper {


    public TreeList(Context context) {
        super(context, "Treename", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableName = "create table TreeName(Id INTEGER PRIMARY KEY AUTOINCREMENT, districtName text, treeType text, treeNameEng text, treeNameTamil text, treeSpeciesIcon blob, imageName text, lastUpdate text, districtNameTamil text, treeTypeTamil text, treeNameEngTamil text, Scientific_Tamil text, imageNameTamil text,storagePath text)";
        db.execSQL(tableName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TreeName");
        onCreate(db);
    }

    public void onInsertTreeType(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("TreeName", null, contentValues);
        //   Log.d("contentValuesTamil", "" + contentValues);
    }

    public void delete() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from TreeName");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public int getCount() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeName", null);
        int cou = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return cou;
    }

    public ArrayList<HashMap<String, String>> getTreTypeeNames(String languages) {

        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeName", null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeName", null);
        Log.d("language123", "" + cursor.getCount() + "  " + languages);
        if (cursor.moveToFirst()) {
            Log.d("language123", "tamilEnglish231123");
            do {
                Log.d("language123", "tamilEnglish");
                if (languages.equals("1")) {
                    Log.d("language123", "tamil");
                    HashMap<String, String> status = new HashMap<>();
                    String treeTypeTamil = cursor.getString(cursor.getColumnIndex("treeTypeTamil"));
                    if (treeTypeTamil.equals("null") || treeTypeTamil.isEmpty()) {
                        status.put("treeType", cursor.getString(cursor.getColumnIndex("treeType")));
                    } else {
                        status.put("treeType", treeTypeTamil);
                    }
                    String treeNameEngTamil = cursor.getString(cursor.getColumnIndex("treeNameEngTamil"));
                    if (treeNameEngTamil.equals("null") || treeNameEngTamil.isEmpty()) {
                        status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                    } else {
                        status.put("treeName", treeNameEngTamil);
                    }
                    String Scientific_Tamil = cursor.getString(cursor.getColumnIndex("Scientific_Tamil"));
                    if (Scientific_Tamil.equals("null") || Scientific_Tamil.isEmpty()) {
                        status.put("subTreeName", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
                    } else {

                        status.put("subTreeName", Scientific_Tamil);
                    }
                    status.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);
                } else {
                    Log.d("language123", "english");
                    HashMap<String, String> status = new HashMap<>();

                    Log.d("language123", "" + cursor.getString(cursor.getColumnIndex("treeType")));
                    status.put("treeType", cursor.getString(cursor.getColumnIndex("treeType")));
                    status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                    status.put("subTreeName", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
                    status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                    mDistrinListName.add(status);
                    Log.d("mDist32243rinListName", "" + mDistrinListName.size());
                }
            } while (cursor.moveToNext());
        }
        /*HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();
        hashSet.addAll(mDistrinListName);
        mDistrinListName.clear();
        mDistrinListName.addAll(hashSet);*/
        cursor.close();
        sqLiteDatabase.close();
        Log.d("mDistrinListName", "" + mDistrinListName.size());
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, byte[]>> getTreImages() {
        ArrayList<HashMap<String, byte[]>> mDistrinListName = new ArrayList<HashMap<String, byte[]>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeName order by treeNameEng ASC", null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeName", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, byte[]> status = new HashMap<>();
                byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("treeSpeciesIcon"));
                //Log.d("treeIconTreeSpesi", "" + treeIcon);
                status.put("treeIcon", treeIcon);
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }
      /*  HashSet<HashMap<String, byte[]>> hashSet = new HashSet<HashMap<String, byte[]>>();
        hashSet.addAll(mDistrinListName);
        mDistrinListName.clear();
        mDistrinListName.addAll(hashSet);*/
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, String>> getSelectedTreeName(String treeType, String selectedDistrictName, String language) {
        Log.d("treeselectedDistrictNameName", "" + treeType + treeType.length() + "  " + selectedDistrictName + selectedDistrictName.length());
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase Database = this.getReadableDatabase();
        String query = null;
        Cursor cursor;
        if (language.equals("1")) {
            query = "SELECT * FROM TreeName WHERE treeTypeTamil like '%" + treeType.trim() + "%'and districtNameTamil like '%" + selectedDistrictName.trim() + "%'";
            cursor = Database.rawQuery(query, null);
            if (cursor.getCount() == 0) {
                query = "SELECT * FROM TreeName WHERE treeType like '%" + treeType.trim() + "%'and districtName like '%" + selectedDistrictName.trim() + "%'";
                cursor = Database.rawQuery(query, null);
            }
        } else {
            query = "SELECT * FROM TreeName WHERE treeType like '%" + treeType.trim() + "%'and districtName like '%" + selectedDistrictName.trim() + "%'";
            cursor = Database.rawQuery(query, null);
        }

        Log.d("sdfasdf", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                if (language.equals("1")) {
                    String treeNameEngTamil = cursor.getString(cursor.getColumnIndex("treeNameEngTamil"));
                    if (treeNameEngTamil.equals("null") || treeNameEngTamil.isEmpty()) {
                        status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                        status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                        status.put("subTreeName", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
                        mDistrinListName.add(status);
                    } else {
                        status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEngTamil")));
                        status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                        status.put("subTreeName", cursor.getString(cursor.getColumnIndex("Scientific_Tamil")));
                        mDistrinListName.add(status);
                    }
                } else {
                    status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                    status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                    status.put("subTreeName", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
                    mDistrinListName.add(status);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        Database.close();
        return mDistrinListName;
    }


    public ArrayList<HashMap<String, String>> getSelectedTreeName(String treeName, String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        SQLiteDatabase Database = this.getReadableDatabase();
        Log.d("getSelectedTreeName", "" + treeName + "  " + language);

        Cursor cursor;
        if (language.equals("1")) {
            String query = "SELECT * FROM TreeName WHERE treeTypeTamil='" + treeName.trim() + "'";
            cursor = Database.rawQuery(query, null);
            if (cursor.getCount() == 0) {
                query = "SELECT * FROM TreeName WHERE treeType='" + treeName.trim() + "'";
                cursor = Database.rawQuery(query, null);
            }
        } else {
            String query = "SELECT * FROM TreeName WHERE treeType='" + treeName.trim() + "'";
            cursor = Database.rawQuery(query, null);
        }


        if (cursor.moveToFirst()) {
            do {
                //Log.d("getSelectedTreeName rr", "" + treeName + "  " + language + "  " + cursor.getCount() + cursor.getString(cursor.getColumnIndex("treeNameEngTamil")) + cursor.getString(cursor.getColumnIndex("Scientific_Tamil")));
                HashMap<String, String> status = new HashMap<>();
                if (language.equals("1")) {
                    String treeType = cursor.getString(cursor.getColumnIndex("treeTypeTamil"));

                    if (treeType.equals(treeName)) {
                        String name = cursor.getString(cursor.getColumnIndex("treeNameEngTamil"));

                        if (name.equals("null") || name.isEmpty()) {
                            status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                            status.put("subTreeName", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
                            mDistrinListName.add(status);
                        } else {
                            status.put("treeName", name);
                            status.put("subTreeName", cursor.getString(cursor.getColumnIndex("Scientific_Tamil")));
                            mDistrinListName.add(status);
                        }
                    }
                } else {
                    status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                    status.put("subTreeName", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
                    mDistrinListName.add(status);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, byte[]>> getTreeByImages(String treeType, String selectedDistrictName, String language) {
        ArrayList<HashMap<String, byte[]>> mDistrinListName = new ArrayList<HashMap<String, byte[]>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = null;
        if (language.equals("1")) {
            query = "SELECT * FROM TreeName WHERE treeTypeTamil='" + treeType.trim() + "'and districtNameTamil='" + selectedDistrictName.trim() + "'";
        } else {
            query = "SELECT * FROM TreeName WHERE treeType='" + treeType + "'and districtName='" + selectedDistrictName + "'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, byte[]> status = new HashMap<>();
                byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("treeSpeciesIcon"));
                status.put("treeIcon", treeIcon);
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, String>> getTreeByImages(String treeType, String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = null;
        if (language.equals("1")) {
            query = "SELECT * FROM TreeName WHERE treeTypeTamil='" + treeType.trim() + "'";
        } else {
            query = "SELECT * FROM TreeName WHERE treeType='" + treeType + "'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                byte[] treeIcon = cursor.getBlob(cursor.getColumnIndex("storagePath"));
                Log.d("treeIcon", "" + treeIcon);
                status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public String getLastDate(String last_update) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM TreeName WHERE lastUpdate='" + last_update + "'";
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

    public int getId(String treeNameEng) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM TreeName WHERE treeNameEng='" + treeNameEng + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("Id"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return id;
    }

    public void update(int id, byte[] imgByte, String treeType, String district, String scientific_name, String treeName, String last_update) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("treeIcon", imgByte);
        contentValues.put("treeType", treeType);
        contentValues.put("districtName", "" + district);
        contentValues.put("treeNameTamil", scientific_name);
        contentValues.put("treeNameEng", treeName);
        contentValues.put("lastUpdate", last_update);
        sqLiteDatabase.update("TreeName", contentValues, "Id" + "=?", new String[]{String.valueOf(id)});
    }

    public String getTreeName(String treeName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM TreeName WHERE treeNameEng='" + treeName + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String mLastDate = null;
        if (cursor.moveToFirst()) {
            do {
                mLastDate = cursor.getString(cursor.getColumnIndex("treeNameEng"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mLastDate;
    }
/*
    public Cursor getCursor() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeName", null);
        return cursor;
    }*/

    public ArrayList<HashMap<String, String>> getImagePaths(String languages) {
        ArrayList<HashMap<String, String>> mImagePaths = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeName", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> mImages = new HashMap<>();
                //      Log.d("loadingFromSQL", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                mImages.put("storagePath", "" + cursor.getString(cursor.getColumnIndex("storagePath")));
                mImagePaths.add(mImages);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mImagePaths;
    }

    public ArrayList<HashMap<String, String>> getTreImagePath(String treeType, String selectedDistrictName, String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = null;
        if (language == "1") {

            query = "SELECT * FROM TreeName WHERE treeTypeTamil like '%" + treeType.trim() + "%'and districtNameTamil like '%" + selectedDistrictName.trim() + "%'";
        } else {

            query = "SELECT * FROM TreeName WHERE treeType like '%" + treeType + "%'and districtName like '%" + selectedDistrictName + "%'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> status = new HashMap<>();
                status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public ArrayList<HashMap<String, String>> getTreeByImagePath(String treeType, String selectedDistrictName, String language) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = null;
        if (language == "1") {
            query = "SELECT * FROM TreeName WHERE treeTypeTamil='" + treeType.trim() + "'and districtNameTamil='" + selectedDistrictName.trim() + "'";
        } else {
            query = "SELECT * FROM TreeName WHERE treeType='" + treeType + "'and districtName='" + selectedDistrictName + "'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();

                status.put("storagePath", cursor.getString(cursor.getColumnIndex("storagePath")));
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public boolean checkImages(String image) {
        File file = new File(image);
        String fileName = file.getName();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from TreeName", null);
        if (cursor.moveToFirst()) {
            do {
                String fileNameFormDb = cursor.getString(cursor.getColumnIndex("storagePath"));
                File file1 = new File(fileNameFormDb);
                String fileNameDB = file1.getName();
                Log.d("FileNamesss", "" + fileName + "  " + fileNameDB);
                if (fileName.equals(fileNameDB)) {
                    return true;
                } else {
                    return false;
                }

            } while (cursor.moveToNext());
        }
        return false;
    }
}
