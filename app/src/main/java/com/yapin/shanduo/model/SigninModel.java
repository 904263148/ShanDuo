package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.SigninInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/8.
 */

public interface SigninModel {
    void load(OnLoadListener<SigninInfo> listener);
}
