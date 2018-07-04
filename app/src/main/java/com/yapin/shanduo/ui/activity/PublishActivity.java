package com.yapin.shanduo.ui.activity;

import android.os.Bundle;

import com.yapin.shanduo.R;
import butterknife.ButterKnife;

public class PublishActivity extends BaseActivity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
    }

}
