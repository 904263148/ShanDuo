package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/5/7 0007 18:30
 */
public interface HomeActLoadModel {

    void load(OnMultiLoadListener<List<ActivityInfo.Act>> listener , String type , String lon , String lat , String page , String pageSize , String userId);
}
