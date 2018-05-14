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
}
