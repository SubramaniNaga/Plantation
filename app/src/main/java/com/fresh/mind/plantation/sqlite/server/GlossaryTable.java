package com.fresh.mind.plantation.sqlite.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.version;

/**
 * Created by AND I5 on 01-03-2017.
 */

public class GlossaryTable extends SQLiteOpenHelper {
    SQLiteDatabase sql;

    public GlossaryTable(Context context) {
        super(context, "glossary", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String contact = "create table glossaryDetails(id INTEGER PRIMARY KEY AUTOINCREMENT,word text,lastUpdate text,wordTamil text ,meaning text,meaningTamil text,common_key text)";
        db.execSQL(contact);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS glossaryDetails");
        onCreate(db);
    }


    public void onInsert(ContentValues contentValues) {
        sql = this.getWritableDatabase();
        sql.insert("glossaryDetails", null, contentValues);
        Log.d("contentValues", "" + contentValues);
        sql.close();

    }


    public ArrayList<HashMap<String, String>> getGlossaryDetails(String languages) {
        sql = this.getReadableDatabase();
        Cursor cursors = sql.rawQuery("select * from glossaryDetails", null);
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        if (cursors.moveToFirst()) {
            do {
                HashMap<String, String> mHash = new HashMap<>();
                if (languages.equals("1")) {
                    String word = cursors.getString(cursors.getColumnIndex("wordTamil"));
                    String meaning = cursors.getString(cursors.getColumnIndex("meaningTamil"));

                    mHash.put("meaning", meaning);
                    mHash.put("word", word);

                    hashMaps.add(mHash);
                } else {
                    String word = cursors.getString(cursors.getColumnIndex("word"));
                    String meaning = cursors.getString(cursors.getColumnIndex("meaning"));
                    mHash.put("meaning", meaning);
                    mHash.put("word", word);

                    hashMaps.add(mHash);
                }
            } while (cursors.moveToNext());
        }
        cursors.close();
        sql.close();
        return hashMaps;
    }

    public int getCount() {
        SQLiteDatabase sql = this.getReadableDatabase();
        String query = "SELECT * FROM glossaryDetails";
        Cursor cursor = sql.rawQuery(query, null);
        int cou = cursor.getCount();
        cursor.close();
        sql.close();
        return cou;

    }

    public void delete() {
        sql = this.getReadableDatabase();
        sql.execSQL("delete from glossaryDetails");
    }
}
