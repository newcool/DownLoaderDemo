package com.niu.mdownloader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sean on 2017/2/14
 */
public class DBOpenHelper extends SQLiteOpenHelper{
    private String TAG = "DBOpenHelper";

    private static String NAME = "download.db";
    private static int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Param.DL_TASK+"("+ Param.ID +" integer primary key autoincrement,"+Param.TASK_URL+" verchar unique,"+Param.TASK_SIZE+" bigint,"+Param.TASK_PROGRESS+" bigint,"+Param.FILEPATH+" verchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
