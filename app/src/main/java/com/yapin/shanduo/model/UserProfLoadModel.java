package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.ShanDuoUserProf;
import com.yapin.shanduo.model.entity.ShanduoUser;
import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/5/25 0025 16:39
 */
public interface UserProfLoadModel {

    void load(OnLoadListener<ShanDuoUserProf> listener , String userId);
}
