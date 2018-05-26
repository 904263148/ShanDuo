package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/4/19.
 */

public interface ModifyLoadModel {
    void load(OnLoadListener<String> listener, String name, String gender, String birthday, String emotion, String signature, String hometown, String occupation, String school, String picture, String background);
}
