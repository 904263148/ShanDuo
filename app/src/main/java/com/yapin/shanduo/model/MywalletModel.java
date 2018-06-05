package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.TransactionrecordInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

import java.util.List;

/**
 * Created by dell on 2018/6/4.
 */

public interface MywalletModel {
    void load(OnLoadListener <FlickerPurseInfo> listener);
}
