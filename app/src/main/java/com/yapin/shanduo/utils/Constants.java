package com.yapin.shanduo.utils;

import android.Manifest;

import com.amap.api.maps.model.LatLng;

public class Constants {

    public static final String PERMISSIONS_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSIONS_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSIONS_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERMISSIONS_PHONE = Manifest.permission.READ_PHONE_STATE;

    public static final int COUNT_MAX_SHOW_PICTURE = 10;

    public final static int TYPE_SHOW = 0;
    public final static int TYPE_FOOTER_LOAD = 1;
    public final static int TYPE_FOOTER_FULL = 2;

    //忘记密码log
    public final static int FORGET = 1;
    public final static int SETFOGGET = 2;
    public final static int CODE_LOGPWD = 5;
    //忘记支付密码
    public final static int PAY_FORGET = 3;
    public final static int PAY_SETFOGGET = 4;


    //我的活动
    public final static int MY_ACTIVITY = 5;
    /**
     * 缓存文件保存路径
     */
    public static final String CACHE_FILE_PATH = "/shanduo/cache/";

    /**
     * 照片保存路径
     */
    public static final String PICTURE_PATH = "/shanduo/picture/";

    public final static String ALL_PHOTO = "所有图片";

    public static final int REQUEST_CODE_FOR_SELECT_PHOTO = 5;
    public static final int REQUEST_CODE_FOR_TAKE_PHOTO_SHOW = 15;
    public static final int REQUEST_CODE_FOR_SELECT_PHOTO_SHOW = 16;
    public static final int REQUEST_CODE_FOR_SELECT_PHOTO_SHOW_THEBACKGROUND = 10;

    //获取验证码
    public static final String GET_CODE_REG = "1";


    //判断注册页是否为获取验证码
    public static final int IS_CODE = 1;

    //右滑关闭
    public static final int IS_EVENT = 1;

    public static String DEFAULT_CITY = "长沙";
    public static final String EXTRA_TIP = "ExtraTip";
    public static final String KEY_WORDS_NAME = "KeyWord";

    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度

    //popup判断是活动还是动态
    public static final int HOME_ACT = 0;
    public static final int HOME_TREND = 1;

    //打开loginActivity
    public static final int OPEN_LOGIN_ACTIVITY = 1;

    //活动item点击
    public static final int ACT_JOIN = 1;
    //活动地图定位点击
    public static final int ACT_LOCATION = 2;
    //活动信誉轨迹点击
    public static final int ACT_CREDIT = 3;

    //发布动态获取定位的点击
    public static final int RELEASEDYNAMICPOSITIONING= 19;

    //获取好友列表时的typeId
    public static final String ALL_FRIEND = "1";
    public static final String BLACK_FRIEND = "2";

    //微信支付appid
    public static final String WECHAT_APPID ="wx3dd985759741b34e";

    //判断是否参加活动
    public static final int JOIN_ACT = 1;
    public static final int UNJOIN_ACT = 0;

    //errorCode
    public static final int ERROR_CADE_ONE = 10001;  //10001:token为空或失效需要重新登录
    public static final int ERROR_CADE_TWO = 10002;  //10002:需要看参数是否未传或符合格式
    public static final int ERROR_CADE_THREE = 10003;//10003:接口调用失败，需要后台自行查看原因

    //是否点赞
    public static final String IS_LIKE = "1";

    public static final int REQUEST_CODE_FOR_DELETE_PHOTO_SHOW = 25;

    //信用中心
    public static final int TYPE_TOP = 105;
    public static final int TYPE_BOTTOM = 205;

    //群  创建，删除
    public static final String TYPE_CREATE = "1";
    public static final String TYPE_DELETE = "2";

    //好友添加方式 0 直接添加 1 申请 2 拒绝
    public static final String ADD_DIRECTLY = "0";
    public static final String ADD_APPLY = "1";
    public static final String ADD_REFUSE = "2";

    //用户首次下载打开APP
    public static final int ISFIRSTUSE = 0;
    public static final int NOTFIRSTUSE = 1;

    //打开登录页面
    public static final int OPEN_LOGIN = 0;

    //支付参数
    public static final String PAY_BALANCE = "1";
    public static final String PAY_ALIPAY = "2";
    public static final String PAY_WECHAT = "3";
    public static final String PAY_PEABEAN = "4";
    public static final String PEA_FREQUENCY = "5";
    //支付订单类型
    public static final String TYPE_CHARGE = "1";
    public static final String TYPE_VIP = "2";
    public static final String TYPE_SVIP = "3";
    public static final String TYPE_ACT_REFRESH = "4";
    public static final String TYPE_ACT_TOP = "5";

    //从哪个页面打开支付页
    public static final int OPEN_BY_VIP = 0;

    //刷新
    public static final int REFRESH = 1;

    //置顶
    public static final int SET_TOP = 2;

    //充值
    public static final int RECHARGE = 3;

    //删除好友类型
    public static final String DELETE_FRIEND = "1";
    public static final String DELETE_BLACK = "2";

    //分享地址
    public static final String ACT_SHARE_URL = "https://yapinkeji.com/yapingzh/global/activity.html?activityId=";
    public static final String TREND_SHARE_URL = "https://yapinkeji.com/yapingzh/global/dynamic.html?dynamicId=";

    //举报
    public static final String REPORT_ACT = "1";
    public static final String REPORT_TREND = "2";

}
