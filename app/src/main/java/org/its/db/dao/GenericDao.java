package org.its.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.its.db.DbHelper;

public class GenericDao {
    protected DbHelper dbHelper;
    protected SQLiteDatabase database;

    GenericDao() {

    }

    public void openConn(Context context) {
        dbHelper = new DbHelper(context);
        database= dbHelper.getWritableDatabase();
    }

    public void closeConn() {
        if (dbHelper != null)
            dbHelper.close();
    }
}
