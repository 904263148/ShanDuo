package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/7/2 0002 10:42
 */
public interface TrendDetailLoadModel {

    void load(OnLoadListener<TrendInfo.Trend> listener , String dynamicId);
}
