package com.example.jason.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Jason on 27/11/2017.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    // database name
    public static final String DATABASE_NAME = "Lab5.db";
    // database version
    public static final int VERSION_NUM = 1;
    // table name
    public static final String TABLE_NAME = "Table_Messages";
    // table columns
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";

    /*
    SQL Instructions to create table
     */
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_MESSAGE + " TEXT"
            + ")";

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    // Creating Table
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        Log.i("ChatDatabaseHelper", " table Created--------------------") ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i(TAG, " Upgrading database from version " + oldVersion
                + " to " + newVersion +  " which will destroy all old data");
        // drop old table if it exists
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        // create new table
        onCreate(db);
    }

}
