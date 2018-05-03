package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/4/27.
 */

public class MapTextingActivity extends BaseActivity {

    private Context context;
    private Activity activity;

    @BindView(R.id.tv_main_result)
    TextView textView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maptesting);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);




    }


}
