package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * Created by dell on 2018/5/19.
 */

public interface MyactivityModel {

    void load (OnMultiLoadListener<List<ActivityInfo.Act>> listener ,String type , String lon , String lat, String page , String pageSize);

}
