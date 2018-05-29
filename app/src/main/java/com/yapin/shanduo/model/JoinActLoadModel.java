package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/5/10 0010 19:20
 */
public interface JoinActLoadModel {

    void load(OnLoadListener<String> listener , String activityId , String type);

}
