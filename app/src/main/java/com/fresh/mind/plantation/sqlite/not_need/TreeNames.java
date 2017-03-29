package com.fresh.mind.plantation.sqlite.not_need;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by AND I5 on 30-01-2017.
 */

public class TreeNames extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public TreeNames(FragmentActivity context) {
        super(context, "TreeNames", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "create table treeNames(id INTEGER PRIMARY KEY AUTOINCREMENT, districtName text ,treeType  text, treeNameEng text,treeNameTamil text,treeIcon text)";
        db.execSQL(tableName);
        // Log.d("Databessee ", "Create Sucess");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsertTreeType(ContentValues contentValues) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("treeNames", null, contentValues);
    }


    public int getCount() {
        int count = 0;
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from treeNames", null);
        count = cursor.getCount();
        return count;
    }

    public ArrayList<HashMap<String, String>> getTreTypeeNames() {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from treeNames order by treeNameEng ASC", null);
        //Cursor cursor = sqLiteDatabase.rawQuery("select * from treeNames", null);
        Log.d("cursor",""+cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                status.put("treeType", cursor.getString(cursor.getColumnIndex("treeType")));
                status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                status.put("subTreeName", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
              //  status.put("treeIcon", cursor.getString(cursor.getColumnIndex("treeIcon")));
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }
        HashSet<HashMap<String, String>> hashSet = new HashSet<HashMap<String, String>>();
        hashSet.addAll(mDistrinListName);
        mDistrinListName.clear();
        mDistrinListName.addAll(hashSet);
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }

    public void delete() {

        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from treeNames");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }

    public ArrayList<HashMap<String, String>> getSelectedTreeName(String selectedTreeType, String districtName) {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();
        sqLiteDatabase = this.getReadableDatabase();
        SQLiteDatabase Database = this.getReadableDatabase();
        String query = "SELECT * FROM treeNames WHERE treeType='" + selectedTreeType + "'";
        Cursor cursor = Database.rawQuery(query, null);
        //  Log.d("treeTypeCount", "" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                /*String districtNames = cursor.getString(cursor.getColumnIndex("districtName"));
                Log.d("districtName", "" + districtName + "  " + districtNames);
                if (districtName.equals(districtNames)) {*/
                HashMap<String, String> status = new HashMap<>();
                //status.put("treeName", cursor.getString(cursor.getColumnIndex("treeType")));
                status.put("treeName", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                status.put("subTreeName", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
                status.put("treeIcon", cursor.getString(cursor.getColumnIndex("treeIcon")));
                mDistrinListName.add(status);
                /*}*/
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return mDistrinListName;
    }
}