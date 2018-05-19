package com.yapin.shanduo.utils;

public class ApiUtil {

    //获取验证码
    public final static String GET_CODE =  ConfigUtil.configUrl() + "/sms/envoyer";

    //注册
    public final static String SIGN_IN = ConfigUtil.configUrl() + "/juser/saveuser";

    //图片上传
    public final static String UPLOAD_IMG = ConfigUtil.configUrl() + "/file/upload";

    //发布动态
    public final static String PUBLISH_ACTIVITY = ConfigUtil.configUrl() + "/jdynamic/savedynamic";

    //登录
    public final static String LOGIN_IN = ConfigUtil.configUrl() + "/juser/loginuser";

    //修改
    public final static String MODIFY_IN = ConfigUtil.configUrl() + "/juser/updateuser";

    //首页活动
    public final static String HOME_ACT = ConfigUtil.configUrl() + "/activity/showHotActivity";

    //添加活动
    public final static String ADD_ACTIVITY = ConfigUtil.configUrl() +  "/activity/saveactivity";

    //首页动态
    public final static String HOME_TREND = ConfigUtil.configUrl() + "/jdynamic/homeList";

    //参加活动
    public final static String JOIN_ACT = ConfigUtil.configUrl() +"/activity/participateActivities";

    //首页轮播
    public final static String HOME_CAROUSEL = ConfigUtil.configUrl() +"/jcarousel/carouselList";

    //图片url
    public final static String IMG_URL = ConfigUtil.configUrl() +"/picture/";

    //点赞
    public final static String PRESS_LIKE = ConfigUtil.configUrl() +"/jdynamic/ispraise";

    //发布动态
    public final static String ADD_Publishingdynamics = ConfigUtil.configUrl() +"/jdynamic/savedynamic";

    //我的动态
    public final static String MY_DYNAMICS = ConfigUtil.configUrl() +"/jdynamic/dynamicList";

    //动态回复
    public final static String TREND_FIRST_REPLAY = ConfigUtil.configUrl() +"/jdynamic/commentList";

    //回复动态
    public final static String TREND_REPLAY = ConfigUtil.configUrl() +"/jdynamic/savecomment";

    //查询好友或黑名单
    public final static String ALL_FRIEND = ConfigUtil.configUrl() + "/jattention/attentionList";

}
