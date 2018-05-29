package com.yapin.shanduo.model;


import com.yapin.shanduo.model.entity.JoinActUser;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/5/26 0026 09:44
 */
public interface JoinActUserLoadModel {

    void load(OnMultiLoadListener<List<JoinActUser.ActUser>> listener , String activityId  , String page , String pageSize);
}
