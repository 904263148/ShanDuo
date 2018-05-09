package com.yapin.shanduo.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 作者：L on 2018/5/4 0004 09:14
 */
public class MyGallyPageTransformer implements ViewPager.PageTransformer {
    private static final float min_scale = 0.85f;

    @Override
    public void transformPage(View page, float position) {
        float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
        if (position < -1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position < 0) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position >= 0 && position < 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position >= 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }
    }

//    private static final float MAX_SCALE = 1.0f;
//    private static final float MIN_SCALE = 0.7f;
//
//    @Override
//    public void transformPage(View view, float position) {
//        if (position < -1) {
//            view.setScaleX(MIN_SCALE);
//            view.setScaleY(MIN_SCALE);
//        } else if (position <= 1) {
//            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
//            view.setScaleX(scaleFactor);
//            if (position > 0) {
//                view.setTranslationX(-scaleFactor * 2);
//            } else if (position < 0) {
//                view.setTranslationX(scaleFactor * 2);
//            }
//            view.setScaleY(scaleFactor);
//        } else {
//            view.setScaleX(MIN_SCALE);
//            view.setScaleY(MIN_SCALE);
//        }
//
//    }
}
