package com.yapin.shanduo.app;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yapin.shanduo.R;
import com.yapin.shanduo.db.DBHelper;
import com.yapin.shanduo.im.utils.Foreground;
import com.yapin.shanduo.model.entity.DaoMaster;
import com.yapin.shanduo.model.entity.DaoSession;

public class ShanDuoPartyApplication extends MultiDexApplication{

    private static Context context;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public AppBarLayout appBarLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Foreground.init(this);
        // 只能在主进程进行离线推送监听器注册
        if(MsfSdkUtils.isMainProcess(this)) {
            Log.d("MyApplication", "main process");

            // 设置离线推送监听器
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    Log.d("MyApplication", "recv offline push");

                    // 这里的 doNotify 是 ImSDK 内置的通知栏提醒，应用也可以选择自己利用回调参数 notification 来构造自己的通知栏提醒
                    notification.doNotify(context, R.mipmap.ic_launcher);
                }
            });
        }

        //扫码初始化
        ZXingLibrary.initDisplayOpinion(this);
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
