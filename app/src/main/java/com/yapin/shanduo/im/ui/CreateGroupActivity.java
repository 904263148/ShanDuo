package com.yapin.shanduo.im.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMGroupManager;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.yapin.shanduo.R;

/**
 * 创建群页面
 */
public class CreateGroupActivity extends Activity {
    TextView mAddMembers;
    EditText mInputView;
    String type;
    private final int CHOOSE_MEM_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
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

        findViewById(R.id.bt_create_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIMGroupManager.CreateGroupParam param = TIMGroupManager.getInstance().new CreateGroupParam();
                param.setGroupType("Public");
                param.setGroupName("hello");
                param.setIntroduction("hello world");
                param.setNotification("welcome to hello group");
                //创建群组
                TIMGroupManager.getInstance().createGroup(param, new TIMValueCallBack<String>() {
                    @Override
                    public void onError(int code, String desc) {
                        Log.d("TIM_create_group", "create group failed. code: " + code + " errmsg: " + desc);
                    }
                    @Override
                    public void onSuccess(String s) {
                        Log.d("TIM_create_group", "create group succ, groupId:" + s);
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (CHOOSE_MEM_CODE == requestCode) {
            if (resultCode == RESULT_OK){
                GroupManagerPresenter.createGroup(mInputView.getText().toString(),
                        type,
                        data.getStringArrayListExtra("select"),
                        new TIMValueCallBack<String>() {
                            @Override
                            public void onError(int i, String s) {
                                if (i == 80001){
                                    Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_fail_because_wording), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_fail), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onSuccess(String s) {
                                Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_succeed), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                );
            }
        }
    }
}
