package com.xloger.kanzhihu.app.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * Created by xloger on 11月15日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class ReadDB {
    private Context context;
    private DBHelper dbHelper;

    public ReadDB(Context context){
        this.context=context;
        dbHelper = new DBHelper(context);
    }

    public void setRead(@NonNull String date,@NonNull String name){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        boolean isRead=isRead(date,name);
        if (!isRead){
            database.execSQL("insert into Reader(date,name,isRead) values(?,?,1)",new String[]{date,name});
            database.close();
        }

    }

    public boolean isRead(@NonNull String date,@NonNull String name){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from Reader where date = ? and name = ?", new String[]{date, name});
        if (cursor.getCount()==0){
            cursor.close();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }
}
