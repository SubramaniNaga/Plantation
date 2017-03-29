package com.fresh.mind.plantation.sqlite.not_need;

import android.content.ContentValues;
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

public class TreeTypeDatabase extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;

    public TreeTypeDatabase(FragmentActivity context) {
        super(context, "TreeTypeDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "create table treeTypeDatabase(id INTEGER PRIMARY KEY AUTOINCREMENT, districtName text ,treeType  text, treeNameEng text,treeNameTamil text)";
        db.execSQL(tableName);
       // Log.d("Databessee ", "Create Sucess");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsertTreeType(ContentValues contentValues) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("treeTypeDatabase", null, contentValues);
     //   Log.d("Databessee ", "Insert Sucess " + contentValues);
    }


    public int getCount() {
        int count = 0;
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from treeTypeDatabase", null);
        count = cursor.getCount();
        return count;
    }

    public ArrayList<HashMap<String, String>> getTreTypeeNames() {
        ArrayList<HashMap<String, String>> mDistrinListName = new ArrayList<HashMap<String, String>>();

        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from treeTypeDatabase", null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> status = new HashMap<>();
                status.put("treeName", cursor.getString(cursor.getColumnIndex("treeType")));
                status.put("treeNameEng", cursor.getString(cursor.getColumnIndex("treeNameEng")));
                status.put("treeNameTamil", cursor.getString(cursor.getColumnIndex("treeNameTamil")));
                mDistrinListName.add(status);
            } while (cursor.moveToNext());
        }
       // Log.d("mTreeType", "" + mDistrinListName.size());
        return mDistrinListName;
    }

    public void delete() {

        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from treeTypeDatabase");
        //Cursor cursor = sqLiteDatabase.rawQuery("delete * from treeTypeDatabase", null);
    }
}