package com.yapin.shanduo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 作者：L on 2018/4/23 0023 10:42
 */
public class InputMethodUtil {

    /**
     * 获取当前activity的焦点，隐藏输入法
     *
     * @param activity activity
     */
    public static void hideInputMethod(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null || view.getWindowToken() == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取当前view的焦点，隐藏输入法
     *
     * @param context context
     * @param view     view
     */
    public static void hideInputMethod(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null
                || view == null
                || view.getWindowToken() == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 获取当前view的焦点，隐藏输入法
     *
     * @param context context
     * @param view     view
     */
    public static void showInputMethod(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null || view == null) {
            return;
        }
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

}
