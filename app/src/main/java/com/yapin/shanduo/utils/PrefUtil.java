package com.yapin.shanduo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtil {

    private static final String TOKEN = "token";

    public static void setToken(Context context, String token) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(TOKEN, token).commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(TOKEN, "");
    }

    private static final String LON = "lon";
    private static final String LAT = "lat";

    public static void setLon(Context context, String token) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LON, token).commit();
    }

    public static String getLon(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(LON, "");
    }

    public static void setLat(Context context, String token) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LAT, token).commit();
    }

    public static String getLat(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(LAT, "");
    }

//    private static final String AoiName = "AoiName";
//    public static void setAoiName(Context context, String token) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        sp.edit().putString(AoiName, token).commit();
//    }
//
//    public static String getAoiName(Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        return sp.getString(AoiName, "");
//    }

//    private static final String Accuracy = "Accuracy";
//    public static void setAccuracy(Context context, String token) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        sp.edit().putString(Accuracy, token).commit();
//    }
//
//    public static String getAccuracy(Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        return sp.getString(Accuracy, "");
//    }

    private static final String FIRSTUSE = "isFirstUse";

    public static void setFirstUse(Context context, int type) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(FIRSTUSE, type).commit();
    }

    public static int getFirstUse(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(FIRSTUSE, Constants.ISFIRSTUSE);
    }

    private static final String CITY = "city";
    public static void setCity(Context context, String place) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(CITY, place).commit();
    }

    public static String getCity(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(CITY , "长沙市");
    }

}
