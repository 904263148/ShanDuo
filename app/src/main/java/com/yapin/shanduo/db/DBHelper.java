package com.yapin.shanduo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yapin.shanduo.model.entity.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class DBHelper extends DaoMaster.OpenHelper {

    public static final String DATABASE_NAME = "shanduo.db";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        DaoMaster.createAllTables(db, true);
    }

}
