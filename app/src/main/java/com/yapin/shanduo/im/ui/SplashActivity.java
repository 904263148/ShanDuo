package com.yapin.shanduo.im.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.huawei.android.pushagent.PushManager;
import com.tencent.TIMCallBack;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushSettings;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.tencent.qcloud.ui.NotifyDialog;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.UserInfo;
import com.yapin.shanduo.im.utils.PushUtil;
import com.yapin.shanduo.ui.activity.FirstUseActivity;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.MainActivity;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends FragmentActivity implements SplashView,TIMCallBack {

    private Context context;

    SplashPresenter presenter;
    private int LOGIN_RESULT_CODE = 100;
    private int GOOGLE_PLAY_RESULT_CODE = 200;
    private final int REQUEST_PHONE_PERMISSIONS = 0;
    private static final String TAG = SplashActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ShanDuoPartyApplication.getContext();
        clearNotification();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if ((checkSelfPermission(Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permissionsList.size() == 0){
                init();
            }else{
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_PHONE_PERMISSIONS);
            }
        }else{
            init();
        }
    }

    /**
     * 跳转到主界面
     */
    @Override
    public void navToHome() {
        if(Constants.ISFIRSTUSE == PrefUtil.getFirstUse(context)){
            Log.d("FirstUse" , "第一次启动");
            PrefUtil.setFirstUse(context ,Constants.NOTFIRSTUSE);
            StartActivityUtil.start(this , FirstUseActivity.class);
            finish();
        }else {
            if (TextUtils.isEmpty(PrefUtil.getToken(context))) {
                StartActivityUtil.start(this, MainActivity.class);
                finish();
            } else {
                //登录之前要初始化群和好友关系链缓存
                FriendshipEvent.getInstance().init();
                GroupEvent.getInstance().init();
                LoginBusiness.loginIm(PrefJsonUtil.getProfile(context).getUserId(), PrefJsonUtil.getProfile(context).getUserSig(), this);
            }
        }
    }

    /**
     * 跳转到登录界面
     */
    @Override
    public void navToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, LOGIN_RESULT_CODE);
    }

    /**
     * 是否已有用户登录
     */
    @Override
    public boolean isUserLogin() {
//        return UserInfo.getInstance().getId()!= null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()));
        return PrefUtil.getToken(context)!= null;
    }

    /**
     * imsdk登录失败后回调
     */
    @Override
    public void onError(int i, String s) {
        Log.e(TAG, "login error : code " + i + " " + s);
        switch (i) {
            case 6208:
                //离线状态下被其他终端踢下线
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navToHome();
                    }
                });
                break;
            case 6200:
                Toast.makeText(this,getString(R.string.login_error_timeout),Toast.LENGTH_SHORT).show();
                navToHome();
                break;
            default:
                Toast.makeText(this,getString(R.string.login_error),Toast.LENGTH_SHORT).show();
                StartActivityUtil.start(this , FirstUseActivity.class);
                finish();
                break;
        }

    }

    /**
     * imsdk登录成功后回调
     */
    @Override
    public void onSuccess() {

        TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
        //开启离线推送
        settings.setEnabled(true);
        //设置收到 C2C 离线消息时的提示声音，这里把声音文件放到了 res/raw 文件夹下
        settings.setC2cMsgRemindSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dudulu));
        //设置收到群离线消息时的提示声音，这里把声音文件放到了 res/raw 文件夹下
        settings.setGroupMsgRemindSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dudulu));
        TIMManager.getInstance().configOfflinePushSettings(settings);

        //初始化程序后台后消息推送
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
        String deviceMan = Build.MANUFACTURER;
        //注册小米和华为推送
        if (deviceMan.equals("Xiaomi") && shouldMiInit()){
            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
        }else if (deviceMan.equals("HUAWEI")){
            PushManager.requestToken(this);
        }

//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "refreshed token: " + refreshedToken);
//
//        if(!TextUtils.isEmpty(refreshedToken)) {
//            TIMOfflinePushToken param = new TIMOfflinePushToken();
//            param.setToken(refreshedToken);
//            param.setBussid(169);
//            TIMManager.getInstance().setOfflinePushToken(param);
//        }

        Log.d(TAG, "imsdk env " + TIMManager.getInstance().getEnv());
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
        StartActivityUtil.start(context , MainActivity.class);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult code:" + requestCode);
        if (LOGIN_RESULT_CODE == requestCode) {
            Log.d(TAG, "login error no " + TLSService.getInstance().getLastErrno());
            if (0 == TLSService.getInstance().getLastErrno()){
                String id = TLSService.getInstance().getLastUserIdentifier();
                UserInfo.getInstance().setId(id);
                UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
                navToHome();
            } else if (resultCode == RESULT_CANCELED){
                finish();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(this, getString(R.string.need_permission),Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void init(){

        Utils.setLocation(context);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(),loglvl);
        //初始化TLS
        TlsBusiness.init(getApplicationContext());
        //设置刷新监听
        RefreshEvent.getInstance();
//        String id =  TLSService.getInstance().getLastUserIdentifier();
//        UserInfo.getInstance().setId(id);
//        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));

//        UserInfo.getInstance().setId("123456789");
//        UserInfo.getInstance().setUserSig("eJxlj0tvgkAYRff8CjLrpg4Mw2ATFzw09mWDUijdTIgz2i8iUBir1Pjfa2mb0vRuz8m9uUdN13UU3S0us*Wy3BWKq7aSSL-SEUYXv7CqQPBMcVKLf1AeKqglz1ZK1h00KKUmxn0HhCwUrODHMIlFbeYMe0ojNrzb*TKsc4HjmOSPAusO3o9D-9p3VT4d7J4TzwOax-X8-WY2jVQ4mexvw7kf78WYKSYKyyMuuI9tw6KBl2ThNhGQ50FgP7w*zWJ-kyZkEZQvqk2ztJWpvR6NepMKtvL7lGkNMbOY0aNvsm6gLDrBxAY9-8KfQdpJ*wCTJF1E");

//        UserInfo.getInstance().setId("456789");
//        UserInfo.getInstance().setUserSig("eJxlj8FOg0AQhu88BeFs7LLslq03hJKgSMXSqlwIlqU7ksIKW0s1vruKTcQ41*-7559513RdN5JweZ5vNs2*Vpk6Sm7oF7qBjLNfKCUUWa4yqy3*Qd5LaHmWl4q3AzQppRihsQMFrxWUcDIIndpsNuJdUWVDyc8C8pVmDFt-FNgO8Gb*6AaxlxISikni2usD8Mjzcejg6G3lxELUIihnLjRVM*dXah8HwomuX1gakEs8Sez7-nkRP3npqpU9bP3lLfOr7m63fljIAxJkVKlgx08fYYoxQ9PxQa*87aCpBwEjk5rYQt9jaB-aJ2noXEM_");

//        UserInfo.getInstance().setId("123456");
//        UserInfo.getInstance().setUserSig("eJxFkNtqg0AURf-F59LOJSNa6IMESUwUclHb*CKjM5FDvGWciGnpv9daQ1-X4rD3Pl9G6B*feduCSLlOqRLGq4GMpwnLoQUlU37WUo0YM8YIQg-bS9VBU4*CIMwwoQj9SxCy1nCGv0NCF8ycTQfFiAI3Wnor2AR5dbrsbR1En*XueHfD22rbD0XY05eYefzQ1vGyMP3EAdch2kmSjON15AabMtvhpMIfQ9fY*1g4XqBolfnXa3l4d09vjzBxSadxvy0WYz3LItSepYZKTrMIo9SkFps5z-PmVutU31s5feP7B3rVV3Y_");

//        PrefJsonUtil.setProfile(context , "");

        if( !(TextUtils.isEmpty(PrefUtil.getToken(context))) ){
            UserInfo.getInstance().setUserSig(PrefJsonUtil.getProfile(context).getUserSig());
            UserInfo.getInstance().setId(PrefJsonUtil.getProfile(context).getUserId());
        }

        presenter = new SplashPresenter(this);
        presenter.start();
    }

    /**
     * 判断小米推送是否已经初始化
     */
    private boolean shouldMiInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清楚所有通知栏通知
     */
    private void clearNotification(){
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }
}
