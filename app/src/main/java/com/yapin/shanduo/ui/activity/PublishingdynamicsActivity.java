package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.widget.glide.TakePhotoPopWin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/12.
 */

public class PublishingdynamicsActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    private Activity activity;

    @BindView(R.id.ll_textview_camera)      //相机
    LinearLayout ll_textview_camera;
    @BindView(R.id.tv_pd_cancel)    //取消
    TextView tv_pd_cancel;
    @BindView(R.id.tv_pd_text)  //动态内容
    TextView tv_pd_text;
    @BindView(R.id.ib_pd_Location)  //定位
    ImageButton ib_pd_Location;
    @BindView(R.id.ib_pd_Expression)    //表情
    ImageButton ib_pd_Expression;
    @BindView(R.id.tv_pd_Publish)   //发表
    TextView tv_pd_Publish;



    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishingdynamics);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);


    }


    @OnClick({R.id.ll_textview_camera,R.id.tv_pd_Publish,R.id.tv_pd_cancel,R.id.ib_pd_Location})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_textview_camera:

            case R.id.ib_pd_Location:
                Intent intent =new Intent(activity ,MapGaodeActivity.class);
                startActivityForResult(intent , 1);

            case R.id.tv_pd_cancel:
                finish();
                break;

            case R.id.tv_pd_Publish:
                String content =tv_pd_text.getText().toString().trim();

                break;



        }
    }
}
