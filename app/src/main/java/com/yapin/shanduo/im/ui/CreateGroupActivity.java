package com.yapin.shanduo.im.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.CheckGroupPresenter;
import com.yapin.shanduo.presenter.CreateGroupPresenter;
import com.yapin.shanduo.ui.activity.RightSlidingActivity;
import com.yapin.shanduo.ui.contract.CheckGroupContract;
import com.yapin.shanduo.ui.contract.CreateGroupContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 创建群页面
 */
public class CreateGroupActivity extends RightSlidingActivity implements CheckGroupContract.View , CreateGroupContract.View{
    TextView mAddMembers;
    EditText mInputView;
    String type;
    private final int CHOOSE_MEM_CODE = 100;
    TextView tvCount;
    private Activity activity;

    private Context context;
    private CheckGroupPresenter presenter;
    private CreateGroupPresenter createGroupPresenter;

    private String groupType = "1";

    private ArrayList<String> select;

    private long maxNum = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        presenter = new CheckGroupPresenter();
        presenter.init(this);
        createGroupPresenter = new CreateGroupPresenter();
        createGroupPresenter.init(this);
    }

    @Override
    public void initView() {
        activity = this;
        context = ShanDuoPartyApplication.getContext();

        type = getIntent().getStringExtra("type");
        mInputView = (EditText) findViewById(R.id.input_group_name);
        mAddMembers = (TextView) findViewById(R.id.btn_add_group_member);
        mAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInputView.getText().toString().equals("")){
                    Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_need_name), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CreateGroupActivity.this, ChooseFriendActivity.class);
                    startActivityForResult(intent, CHOOSE_MEM_CODE);
                }
            }
        });

        tvCount = (TextView) findViewById(R.id.tv_count);
        findViewById(R.id.rl_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                String [] str = { "200人群" , "500人群" , "1000人群" };
                builder.setItems(str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                tvCount.setText("群人数上限:200");
                                groupType = "1";
                                maxNum = 200;
                                break;
                            case 1:
                                tvCount.setText("群人数上限:500");
                                groupType = "2";
                                maxNum = 500;
                                break;
                            case 2:
                                tvCount.setText("群人数上限:1000");
                                groupType = "3";
                                maxNum = 1000;
                                break;
                        }
                    }
                }).show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (CHOOSE_MEM_CODE == requestCode) {
            if (resultCode == RESULT_OK){
                select = data.getStringArrayListExtra("select");
                presenter.checkGroup(groupType);
            }
        }
    }

    @Override
    public void show(String data) {
        GroupManagerPresenter.createGroup(mInputView.getText().toString(),
                type,
                select,
                maxNum,
                new TIMValueCallBack<String>() {
                    @Override
                    public void onError(int code, String desc) {
                        Log.d("TIM_create_group", "create group failed. code: " + code + " errmsg: " + desc);
                        if (code == 80001){
                            Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_fail_because_wording), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_fail), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onSuccess(String s) {
                        Log.d("TIM_create_group", "create group succ, groupId:" + s);
                        createGroupPresenter.createGroup(Constants.TYPE_CREATE , s , groupType ,mInputView.getText().toString());
                    }
                }
        );
    }

    @Override
    public void success(String data) {
        Toast.makeText(context, getString(R.string.create_group_succeed), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

}
