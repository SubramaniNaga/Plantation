package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.version;
import static com.fresh.mind.plantation.R.id.count;

/**
 * Created by AND I5 on 17-04-2017.
 */

public class SchecmesDb extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;

    public SchecmesDb(Context context) {
        super(context, "Schecmes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table SchecmesTab(id INTEGER PRIMARY KEY AUTOINCREMENT,NoOfScheme text,NoOfSchemeTamil text,sch1 text,sch1Tamil text,sch2 text,sch2Tamil text,sch3 text,sch3Tamil text,sch4 text,sch4Tamil text" +
                ",LastUpdate text,common_key text,SchemeTitleTamil text,SchemeTitle text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsert(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("SchecmesTab", null, contentValues);
        Log.d("Success", "InsertSucceessSchecmes " + contentValues);
    }

    public void delete() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from SchecmesTab");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public int getCou() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SchecmesTab", null);
        Log.d("soilTypeAllTamilwtasss", "" + cursor.getCount());
        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                count = cursor.getCount();
            } while (cursor.moveToNext());
        }
        return count;
    }

    public ArrayList<HashMap<String, String>> getSchemes() {
        ArrayList<HashMap<String, String>> schems = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SchecmesTab", null);
        Log.d("soilTypeAllTamilwtasss", "" + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("SchemeTitleTamil", "" + cursor.getString(cursor.getColumnIndex("SchemeTitleTamil")));
                stringStringHashMap.put("SchemeTitle", "" + cursor.getString(cursor.getColumnIndex("SchemeTitle")));

                stringStringHashMap.put("sch1", "" + cursor.getString(cursor.getColumnIndex("sch1")));
                stringStringHashMap.put("sch2", "" + cursor.getString(cursor.getColumnIndex("sch2")));
                stringStringHashMap.put("sch3", "" + cursor.getString(cursor.getColumnIndex("sch3")));
                stringStringHashMap.put("sch4", "" + cursor.getString(cursor.getColumnIndex("sch4")));
                stringStringHashMap.put("sch1Tamil", "" + cursor.getString(cursor.getColumnIndex("sch1Tamil")));
                stringStringHashMap.put("sch2Tamil", "" + cursor.getString(cursor.getColumnIndex("sch2Tamil")));
                stringStringHashMap.put("sch3Tamil", "" + cursor.getString(cursor.getColumnIndex("sch3Tamil")));
                stringStringHashMap.put("sch4Tamil", "" + cursor.getString(cursor.getColumnIndex("sch4Tamil")));
                schems.add(stringStringHashMap);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return schems;
    }

    public HashMap<String, List<String>> getExpandableValues(String language) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SchecmesTab", null);
        Log.d("soilTypeAllTamilwtasss", "" + cursor.getCount());
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                if (language.equals("1")) {

                    List<String> childSchems = new ArrayList<String>();
                    //childSchems.add(cursor.getString(cursor.getColumnIndex("SchemeTitleTamil")));
                    childSchems.add(cursor.getString(cursor.getColumnIndex("sch1Tamil")));
                    childSchems.add(cursor.getString(cursor.getColumnIndex("sch2Tamil")));
                    childSchems.add(cursor.getString(cursor.getColumnIndex("sch3Tamil")));
                    childSchems.add(cursor.getString(cursor.getColumnIndex("sch4Tamil")));
                    //NoOfScheme
                    //Log.d("childSchems", "lis  " + childSchems.size());
                    expandableListDetail.put(cursor.getString(cursor.getColumnIndex("NoOfSchemeTamil")), childSchems);

                } else {
                    List<String> childSchems = new ArrayList<String>();
                    //childSchems.add(cursor.getString(cursor.getColumnIndex("SchemeTitle")));
                    childSchems.add(cursor.getString(cursor.getColumnIndex("sch1")));
                    childSchems.add(cursor.getString(cursor.getColumnIndex("sch2")));
                    childSchems.add(cursor.getString(cursor.getColumnIndex("sch3")));
                    childSchems.add(cursor.getString(cursor.getColumnIndex("sch4")));
                    //NoOfScheme
                    //   Log.d("childSchems", "english  " + childSchems.size() + "  \n " + childSchems);
                    Log.d("sdskldskd", "" + cursor.getString(cursor.getColumnIndex("NoOfScheme")) + "   " + childSchems);
                    expandableListDetail.put(cursor.getString(cursor.getColumnIndex("NoOfScheme")), childSchems);

                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return expandableListDetail;
    }


//        public ArrayList<HashMap<String, String>> getSchemes(String common_key, String language) {
//            ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
//            ArrayList<HashMap<String, String>> mDistrinListName12 = new ArrayList<HashMap<String, String>>();
//            Cursor cursor = null, cursor1;
//            sqLiteDatabase = this.getReadableDatabase();
//
//            if (language.equals("1")) {
//                cursor = sqLiteDatabase.rawQuery("select * from SchecmesTab where common_key='" + common_key + "'", null);
//                if (cursor.getCount() == 0) {
//                    cursor = sqLiteDatabase.rawQuery("select * from SchecmesTab where common_key='" + common_key + "'", null);
//                    if (cursor.moveToFirst()) {
//                        do {
//                            HashMap<String, String> status = new HashMap<>();
//                            status.put("sch1", "" + cursor.getString(cursor.getColumnIndex("Scheme1")));
//                            status.put("sch2", "" + cursor.getString(cursor.getColumnIndex("Scheme2")));
//                            status.put("sch3", "" + cursor.getString(cursor.getColumnIndex("Scheme3")));
//                            status.put("sch4", "" + cursor.getString(cursor.getColumnIndex("Scheme4")));
//                            mDistrinListName12.add(status);
//                        } while (cursor.moveToNext());
//                    }
//
//                }
//
//            }
//
//            return mDistrinListName;
//        }
}