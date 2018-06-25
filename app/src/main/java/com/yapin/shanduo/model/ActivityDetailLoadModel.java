package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/22 0022 16:40
 */
public interface ActivityDetailLoadModel {

    void load(OnLoadListener<ActivityInfo.Act> listener , String activityId);
}
