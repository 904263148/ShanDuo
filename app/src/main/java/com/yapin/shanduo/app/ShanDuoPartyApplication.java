package com.yapin.shanduo.app;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.multidex.MultiDexApplication;

import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
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
        initSDK();
    }

    public void  initSDK(){
        Foreground.init(this);
        context = getApplicationContext();
        if(MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify){
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
                    }
                }
            });
        }

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
