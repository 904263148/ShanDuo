package com.yapin.shanduo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.yapin.shanduo.model.entity.TokenInfo;

/**
 * 作者：L on 2018/4/21 0021 10:36
 */
public class PrefJsonUtil {

    private static final String PREFERENCES_NAME_FOR_PROFILE = "profile";

    public static void setProfile(Context context, String bean) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME_FOR_PROFILE, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCES_NAME_FOR_PROFILE, bean).commit();
    }

    public static TokenInfo getProfile(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME_FOR_PROFILE, Context.MODE_PRIVATE);
        String profile = sp.getString(PREFERENCES_NAME_FOR_PROFILE, "");
        return new Gson().fromJson(profile, TokenInfo.class);
    }

}
