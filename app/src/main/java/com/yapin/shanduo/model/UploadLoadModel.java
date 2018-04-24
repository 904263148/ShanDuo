package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

import java.util.List;

public interface UploadLoadModel {

    void load(OnLoadListener<String> listener , List<String> paths);
}
