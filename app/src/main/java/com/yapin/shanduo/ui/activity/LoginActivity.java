package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.UserInfo;
import com.yapin.shanduo.presenter.LoginPresenter;
import com.yapin.shanduo.ui.contract.LoginContract;
import com.yapin.shanduo.ui.manage.UserManage;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.InputMethodUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View , View.OnFocusChangeListener{

    @BindView(R.id.et_login_user)
    EditText et_login_user;
    @BindView(R.id.et_login_pwd)
    EditText et_login_pwd;
    @BindView(R.id.iv_name)
    ImageView ivPhone;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;

    private LoginPresenter presenter;
    private Context context;
    private Activity activity;

    private static final int REGISTER = 1;

    private ProgressDialog dialog;
    private boolean isInitView = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new LoginPresenter();
        presenter.init(context,this);
    }

    @Override
    public void initView() {
        if (isInitView)
            return;
        isInitView = true;

        setIsEvent(Constants.IS_EVENT);

        dialog = new ProgressDialog(this);
        dialog.setMessage("加载中...");
        dialog.setCanceledOnTouchOutside(false);

        et_login_user.setOnFocusChangeListener(this);
        et_login_pwd.setOnFocusChangeListener(this);


    }

    @OnClick({R.id.iv_back , R.id.ll_reg , R.id.tv_login , R.id.tv_go_reg})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back :
                onBackPressed();
                break;
            case R.id.tv_go_reg:
            case R.id.ll_reg :
                StartActivityUtil.start(activity , RegisterActivity.class , REGISTER);
                break;
            case R.id.tv_login :

                String username = et_login_user.getText().toString().trim();
                String password = et_login_pwd.getText().toString().trim();
                UserManage.getInstance().saveUserInfo(LoginActivity.this, username, password);
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.login(username, password);
                }
                break;
        }
    }

    @Override
    public void success(String data) {
        dialog.dismiss();
        ToastUtil.showShortToast(context,data);
        Log.e("token", PrefJsonUtil.getProfile(context).getToken());

        StartActivityUtil.start(activity,MainActivity.class);
        onBackPressed();
    }

    @Override
    public void loading() {
        InputMethodUtil.hideInputMethod(activity);
        dialog.show();
    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
        dialog.dismiss();
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context,msg);
        dialog.dismiss();
    }

    @Override
    public void showFailed(String msg) {
        dialog.dismiss();
        ToastUtil.showShortToast(context ,msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case REGISTER :
                onBackPressed();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            switch (v.getId()) {
                case R.id.et_login_user:
                    ivPhone.setBackgroundResource(R.drawable.icon_name_checked);
                    if (TextUtils.isEmpty(et_login_pwd.getText().toString().trim())) {
                        ivPwd.setBackgroundResource(R.drawable.icon_password);
                    } else {
                        ivPwd.setBackgroundResource(R.drawable.icon_pwd_checked);
                    }
                    break;
                case R.id.et_login_pwd:
                    ivPwd.setBackgroundResource(R.drawable.icon_pwd_checked);
                    if ( TextUtils.isEmpty(et_login_user.getText().toString().trim()) ) {
                        ivPhone.setBackgroundResource(R.drawable.icon_name_unchecked);
                    } else {
                        ivPhone.setBackgroundResource(R.drawable.icon_name_checked);
                    }
                    break;
            }
        }
    }
}
