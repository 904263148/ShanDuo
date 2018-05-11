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

}
