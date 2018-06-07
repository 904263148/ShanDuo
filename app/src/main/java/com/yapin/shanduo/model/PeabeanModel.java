package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/6.
 */

public interface PeabeanModel {
    void load(OnLoadListener<String> listener, String activityId ,String typeId );
}
