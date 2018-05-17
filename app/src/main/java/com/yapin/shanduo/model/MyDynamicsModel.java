package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * Created by dell on 2018/5/15.
 */

public interface MyDynamicsModel {
//    void load(OnLoadListener<TrendInfo.Trend> listener);
    void load (OnMultiLoadListener<List<TrendInfo.Trend>> listener ,String lon , String lat, String page , String pageSize);

}
