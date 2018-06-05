package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.TransactionrecordInfo;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * Created by dell on 2018/6/4.
 */

public interface TransactionrecordModel {
    void load (OnMultiLoadListener<List<TransactionrecordInfo.Trend>> listener ,String page , String pageSize);
}
