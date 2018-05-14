package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/5/5.
 */

public interface AddactivityModel {

    void load(OnLoadListener<String> listener, String activityType, String activityStartTime, String activityAddress, String mode, String manNumber, String womanNumber, String remarks, String activityCutoffTime, String lon, String lat,String detailedAddress);

}
