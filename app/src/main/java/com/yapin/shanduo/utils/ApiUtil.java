package com.yapin.shanduo.utils;

public class ApiUtil {

    //获取验证码
    public final static String GET_CODE =  ConfigUtil.configUrl() + "/shanduoparty/sms/envoyer";

    //注册
    public final static String SIGN_IN = ConfigUtil.configUrl() + "/shanduoparty/juser/saveuser";

    //图片上传
    public final static String UPLOAD_IMG = ConfigUtil.configUrl() + "/shanduoparty/file/upload";

    //发布动态
    public final static String PUBLISH_ACTIVITY = ConfigUtil.configUrl() + "/shanduoparty/jdynamic/savedynamic";

    //登录
    public final static String LOGIN_IN = ConfigUtil.configUrl() + "/shanduoparty/juser/loginuser";

    //修改
    public final static String MODIFY_IN = ConfigUtil.configUrl() + "/shanduoparty/juser/updateuser";
}
