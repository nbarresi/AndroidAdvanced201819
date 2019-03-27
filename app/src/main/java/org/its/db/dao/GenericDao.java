package org.its.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.its.db.DbHelper;

public abstract class GenericDao {
     Context context;
     DbHelper dbHelper;
     SQLiteDatabase database;

     GenericDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public abstract void openConn();

    public abstract void closeConn();
}
