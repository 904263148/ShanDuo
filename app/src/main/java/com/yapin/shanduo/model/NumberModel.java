package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.NumberInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/5.
 */

public interface NumberModel {
    void load(OnLoadListener<NumberInfo> listener);
}
