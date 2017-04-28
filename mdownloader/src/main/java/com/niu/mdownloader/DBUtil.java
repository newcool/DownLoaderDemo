package com.niu.mdownloader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sean on 2017/2/14
 * 数据库操作类
 */
public class DBUtil {
    private String TAG = "DBUtil";

    private static  DBUtil dbUtil;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private DBUtil(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public static DBUtil getInstance(Context context){

        if(dbUtil == null){
            synchronized (DBUtil.class){
                if(dbUtil==null){
                    dbUtil = new DBUtil(context);
                }
            }
        }
        return dbUtil;
    }

    public synchronized void insert(TaskInfo info){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into "+Param.DL_TASK+"("+Param.TASK_URL+","+Param.TASK_SIZE+","+Param.TASK_PROGRESS+","+Param.FILEPATH+") values (?,?,?,?)"
                ,new Object[]{info.getUrl(),info.getSize(),info.getProgress(),info.getFilepath()});

        db.close();
    }
    public synchronized void update(TaskInfo info){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update "+Param.DL_TASK+" set "+Param.TASK_PROGRESS+"=? where "+Param.TASK_URL + "=?",new String[]{String.valueOf(info.getProgress()),info.getUrl()});
        db.close();
    }

    public synchronized void delete(String url){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from "+Param.DL_TASK+" where "+Param.TASK_URL+"=?",new String[]{url} );
        db.close();
    }

    public synchronized TaskInfo query(String url){
        db = dbOpenHelper.getWritableDatabase();
        TaskInfo taskInfo = null;
        Cursor cursor = db.rawQuery("select "+Param.TASK_URL+","+Param.TASK_SIZE+","+Param.TASK_PROGRESS+","+Param.FILEPATH+" from "+Param.DL_TASK+" where "+Param.TASK_URL+"=?",new String[]{url});
        if(cursor.moveToFirst()){
            taskInfo = new TaskInfo(cursor.getString(0),cursor.getLong(1),cursor.getLong(2),cursor.getString(3));
        }

        cursor.close();
        db.close();
        return taskInfo;
    }

}
