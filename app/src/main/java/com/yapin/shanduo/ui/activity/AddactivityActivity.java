package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.Addactivitypresenter;
import com.yapin.shanduo.ui.contract.AddactivityContract;
import com.yapin.shanduo.utils.DateTimePickDialogUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/4.
 */

public class AddactivityActivity extends BaseActivity implements AddactivityContract.View,View.OnClickListener{

    @BindView(R.id.et_add_title)
    EditText et_add_title;
    @BindView(R.id.et_add_numberofgirls)
    EditText et_add_numberofgirls;
    @BindView(R.id.et_add_Numberofboys)
    EditText et_add_Numberofboys;
    @BindView(R.id.tv_add_place)
    TextView tv_add_place;
    @BindView(R.id.tv_add_starttime)
    TextView tv_add_starttime;
    @BindView(R.id.tv_add_Deadline)
    TextView tv_add_Deadline;
    @BindView(R.id.et_add_Modeofconsumption)
    TextView et_add_Modeofconsumption;
    @BindView(R.id.et_add_Remarks)
    EditText et_add_Remarks;
    @BindView(R.id.but_add_Releaseactivities)
    Button but_add_Releaseactivities;

    String activityCutoffTime;
    String activityStartTime;
    String mode;
    String lat;
    String lon;
    String remarks;
    String activityAddress;
    String manNumber;
    String womanNumber;
    String activityType;
    String textlonlat;

    private Addactivitypresenter presenter;
    private Context context;
    private Activity activity;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new Addactivitypresenter();
        presenter.init(context,this);
        tv_add_starttime.setOnClickListener(this);

        et_add_Modeofconsumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                final String[] emotion = {"我请客", "AA制","男的AA，美女随意"};
                //    设置一个单项选择下拉框
                builder1.setSingleChoiceItems(emotion, 4, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
//                        Toast.makeText(activity, "你选择的是：" + emotion[which], Toast.LENGTH_SHORT).show();
                        et_add_Modeofconsumption.setText(emotion[which]);
                    }
                });
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mode = et_add_Modeofconsumption.getText().toString().trim();
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });
                builder1.show();
            }
        });

        tv_add_Deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(AddactivityActivity.this, null);
                dateTimePicKDialog.dateTimePicKDialog(tv_add_Deadline, 0, true);
            }
        });

        tv_add_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(AddactivityActivity.this, null);
                dateTimePicKDialog.dateTimePicKDialog(tv_add_starttime, 0, true);
            }
        });

        tv_add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(activity ,MapGaodeActivity.class);
                startActivityForResult(intent , 1);
            }
        });

        initView();
    }
    //获取定位选择的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

//            data.getStringExtra("textTitle");
            setText(data.getStringExtra("textTitle"));
            textlonlat = data.getStringExtra("textlonlat");
            Log.i("test","/n地址是："+tv_add_place+"/n经纬度是："+textlonlat);
        }
    }
    //绑定定位选择的数据
    public void setText(String data){
        tv_add_place.setText(data);
    }


    @Override
    public void initView() {

    }

    @OnClick({R.id.but_add_Releaseactivities })
    public void onClick(View view){
//        activityType ,activityStartTime, activityAddress ,mode, manNumber ,womanNumber, remarks ,activityCutoffTime, lon, lat
        switch (view.getId()) {
            case R.id.but_add_Releaseactivities:

                String []ary = textlonlat.split("\\,");
                lat=ary[0];
                lon=ary[1];
                Log.i("test","/n经度："+lon+"/n纬度："+lat);
                activityType = et_add_title.getText().toString().trim();
                womanNumber = et_add_numberofgirls.getText().toString().trim();
                manNumber = et_add_Numberofboys.getText().toString().trim();

                activityAddress = tv_add_place.getText().toString().trim();
                remarks = et_add_Remarks.getText().toString().trim();

                activityCutoffTime =tv_add_Deadline.getText().toString().trim();
                activityStartTime=tv_add_starttime.getText().toString().trim();

                presenter.addactivity(activityType,activityStartTime,activityAddress,mode,manNumber,womanNumber,remarks,activityCutoffTime,lon,lat);
                break;
        }

    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,"发布成功");
        finish();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {

    }



}
