package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/14.
 */

public interface DeletedynamicModel {
    void load(OnLoadListener<String> listener , String dynamicIds);
}
