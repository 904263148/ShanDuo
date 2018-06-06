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

    //修改个人信息
    public final static String MODIFY_IN = ConfigUtil.configUrl() + "/juser/updateuser";

    //首页活动
    public final static String HOME_ACT = ConfigUtil.configUrl() + "/activity/showHotActivity";

    //添加活动
    public final static String ADD_ACTIVITY =  ConfigUtil.configUrl() +"/activity/saveactivity";

    //首页动态
    public final static String HOME_TREND = ConfigUtil.configUrl() + "/jdynamic/dynamicList";

    //参加活动
    public final static String JOIN_ACT = ConfigUtil.configUrl() +"/activity/joinActivities";

    //首页轮播
    public final static String HOME_CAROUSEL = ConfigUtil.configUrl() +"/jcarousel/carouselList";

    //图片url
    public final static String IMG_URL = ConfigUtil.configUrl() +"/picture/";

    //点赞
    public final static String PRESS_LIKE = ConfigUtil.configUrl() +"/jdynamic/ispraise";

    //发布动态
    public final static String ADD_Publishingdynamics = ConfigUtil.configUrl() +"/jdynamic/savedynamic";

    //我的动态
    public final static String MY_DYNAMICS = ConfigUtil.configUrl() +  "/jdynamic/dynamicList";

    //我的活动
    public final static String MY_ACTIVITY = ConfigUtil.configUrl() +  "/activity/showHotActivity";

    //动态回复
    public final static String TREND_FIRST_REPLAY = ConfigUtil.configUrl() +"/jdynamic/commentList";

    //回复动态
    public final static String TREND_REPLAY = ConfigUtil.configUrl() +"/jdynamic/savecomment";

    //查询好友或黑名单
    public final static String ALL_FRIEND = ConfigUtil.configUrl() + "/jattention/attentionList";

    //按条件查找用户
    public final static String SEARCH_HUMAN = ConfigUtil.configUrl() + "/jattention/seekuser";

    //查找用户资料
    public final static String USER_PROF = ConfigUtil.configUrl() +"/jattention/userdetails";

    //查找活动中报名的用户数据
    public final static String ACT_JOIN_USER = ConfigUtil.configUrl() +"/activity/participant";

    //查询token是否有效
    public final static String JUDGE_TOKEN = ConfigUtil.configUrl() + "juser/checktoken";

    //参与者对活动评价
    public final static String INITIATOREVALU = ConfigUtil.configUrl() + "/score/updateScore";

    //发起者对参与者评价
    public final static String PARTICIPANTEVALUATION = ConfigUtil.configUrl() + "/score/updateOthersScore";

    //修改登录密码
    public final static String LOGINPASSWORD = ConfigUtil.configUrl() + "/juser/updatepassword";

    //验证支付密码
    public final static String VERIFYINGPAYMENT = ConfigUtil.configUrl() + "/jmoney/checkpassword";

    //修改支付密码
    public final static String REVISEPAYMENT = ConfigUtil.configUrl() + "/jmoney/updatepassword";

    //申请添加好友
    public final static String ADD_FRIEND = ConfigUtil.configUrl() + "/jattention/saveapply";

    //信誉轨迹
    public final static String CREDIT_DETALILS = ConfigUtil.configUrl() +"reputation/creditDetails";

    //检查是否可以创建群组
    public final static String CHECK_GROUP = ConfigUtil.configUrl() + "jgroup/checkgroup" ;

    //创建或删除群组
    public final static String CREATE_GROUP = ConfigUtil.configUrl() + "jgroup/isgroup" ;

    //根据关键字查询相应活动
    public final static String QUERY_ACT = ConfigUtil.configUrl() + "activity/selectQuery";

    //闪多钱包
    public final static String FLICKER_PURSE = ConfigUtil.configUrl() +"/jmoney/getmoney";

    //交易记录
    public final static String TRANSACTION_RECORD = ConfigUtil.configUrl() +"/jmoney/moneyList";

    //提交订单
    public final static String GET_ORDER = ConfigUtil.configUrl() + "jorder/payorder";

    //查询好友动态活动数量
    public final static String USERCOUNT = ConfigUtil.configUrl() +"/juser/usercount";
}
