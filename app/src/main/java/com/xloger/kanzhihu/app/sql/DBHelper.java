package com.xloger.kanzhihu.app.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xloger on 11月15日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int version=1;

    public DBHelper(Context context) {
        super(context, "app.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Reader(_id integer primary key,date text not null,name text not null,isRead integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
