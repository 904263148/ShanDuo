package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.GetVipLevelInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/25.
 */

public interface GetVipLevelModel {
    void load(OnLoadListener<GetVipLevelInfo> listener);
}
