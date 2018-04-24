package com.yapin.shanduo.utils;

import android.app.Activity;

import com.yapin.shanduo.R;


/**
 * 启动和关闭activity动画类
 *
 */
public class ActivityTransitionUtil {
    /**
     * 从右侧滑入
     *
     * @param activity activity
     */
    public static void startActivityTransition(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_left);

    }

    /**
     * 从左侧滑出
     *
     * @param activity activity
     */
    public static void finishActivityTransition(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_left,
                R.anim.out_to_right);
    }

}
