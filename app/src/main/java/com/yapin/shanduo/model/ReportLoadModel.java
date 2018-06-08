package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/7 0007 18:09
 */
public interface ReportLoadModel {

    void load(OnLoadListener<String> listener , String userId , String activityId , String dynamicId , String typeId , String remarks);
}
