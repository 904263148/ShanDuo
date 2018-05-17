package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;

import butterknife.BindView;

/**
 * Created by dell on 2018/5/3.
 */

public class MywalletActivity extends BaseActivity {

    private Context context;
    private Activity activity;

    private MyDynamicsActivity adapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        context = ShanDuoPartyApplication.getContext();
        activity = this;

    }

}
