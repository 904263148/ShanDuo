package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/5/14.
 */

public interface PublishTrendLoadModel {

    void load(OnLoadListener<String> listener, String content,String picture,String lat,String lon , String location);


}
