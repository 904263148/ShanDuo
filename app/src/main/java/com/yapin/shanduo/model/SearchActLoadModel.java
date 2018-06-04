package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/6/4 0004 11:58
 */
public interface SearchActLoadModel {

    void load(OnMultiLoadListener<List<ActivityInfo.Act>> listener , String query, String page , String pageSize);
}
