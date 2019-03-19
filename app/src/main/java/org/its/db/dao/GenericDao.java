package org.its.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.its.db.DbHelper;

public abstract class GenericDao {
    protected static final String TABLE_NAME = "profilo";
    protected Context context;
    protected DbHelper dbHelper;
    protected SQLiteDatabase database;

    protected GenericDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public abstract void openConn();

    public abstract void closeConn();
}
