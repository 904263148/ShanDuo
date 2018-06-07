package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/6.
 */

public interface ConfirmModel {
    void load(OnLoadListener<String> listener, String activityId );
}
