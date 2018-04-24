package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

public interface PublishLoadModel {

    void load(OnLoadListener<String> listener , String content , String data);

}
