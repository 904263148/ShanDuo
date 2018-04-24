package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

public interface GetCodeModel {

    void load(OnLoadListener<String> listener , String phone , String typeId);

}
