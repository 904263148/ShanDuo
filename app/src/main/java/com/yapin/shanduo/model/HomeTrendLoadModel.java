package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/5/9 0009 19:38
 */
public interface HomeTrendLoadModel {

    void load(OnMultiLoadListener<List<TrendInfo.Trend>> listener , String typeId, String lat , String  lon , String page , String pageSize);
}
