package com.yapin.shanduo.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.yapin.shanduo.db.DBHelper;
import com.yapin.shanduo.model.entity.DaoMaster;
import com.yapin.shanduo.model.entity.DaoSession;

public class ShanDuoPartyApplication extends MultiDexApplication{

    private static Context context;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }

    /**
     * 获取DaoMaster对象
     *
     * @return DaoMaster
     */
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DBHelper helper = new DBHelper(context , DBHelper.DATABASE_NAME , null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 获取DaoSession对象
     *
     * @return DaoSession
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


}
