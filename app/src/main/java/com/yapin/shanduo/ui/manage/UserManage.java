package com.yapin.shanduo.ui.manage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.yapin.shanduo.model.entity.ShanduoUser;

/**
 * Created by dell on 2018/4/23.
 */

public class UserManage {

    /**
     * 保存用户信息的管理类
     * Created by libin
     */

        private static UserManage instance;

        private UserManage() {
        }

        public static UserManage getInstance() {
            if (instance == null) {
                instance = new UserManage();
            }
            return instance;
        }


        /**
         * 保存自动登录的用户信息
         */
        public void saveUserInfo(Context context, String username, String password) {
            SharedPreferences sp = context.getSharedPreferences("shanduouser", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.commit();
        }


        /**
         * 获取用户信息model
         *
         * @param context
         * @param
         * @param
         */
        public ShanduoUser getUserInfo(Context context) {
            SharedPreferences sp = context.getSharedPreferences("shanduouser", Context.MODE_PRIVATE);
            ShanduoUser shanduoUser = new ShanduoUser();
            shanduoUser.setUserName(sp.getString("username", ""));
            shanduoUser.setPassWord(sp.getString("password", ""));
            return shanduoUser;
        }


        /**
         * userInfo中是否有数据
         */
        public boolean hasUserInfo(Context context) {
            ShanduoUser shanduoUser = getUserInfo(context);
            if (shanduoUser != null) {
                if ((!TextUtils.isEmpty(shanduoUser.getUserName())) && (!TextUtils.isEmpty(shanduoUser.getPassWord()))) {//有数据
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
}
