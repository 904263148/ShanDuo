package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/5/10 0010 17:41
 */
public interface LikeLoadModel {

    void load(OnLoadListener<String> listener , String dynamicId );
}
