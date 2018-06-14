package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.UserInfo;
import com.yapin.shanduo.presenter.LoginPresenter;
import com.yapin.shanduo.ui.contract.LoginContract;
import com.yapin.shanduo.ui.manage.UserManage;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.InputMethodUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View , TIMCallBack{

    @BindView(R.id.et_login_user)
    EditText et_login_user;
    @BindView(R.id.et_login_pwd)
    EditText et_login_pwd;
    @BindView(R.id.tv_forget)
    TextView tv_forget;
//    @BindView(R.id.iv_name)
//    ImageView ivPhone;
//    @BindView(R.id.iv_pwd)
//    ImageView ivPwd;

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
        tv_forget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        //.setText(Html.fromHtml("<u>"+"忘记了密码？"+"</u>"));
    }

    @Override
    public void initView() {
        if (isInitView)
            return;
        isInitView = true;

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.dialog_loading));
        dialog.setCanceledOnTouchOutside(false);

//        et_login_user.setOnFocusChangeListener(this);
//        et_login_pwd.setOnFocusChangeListener(this);


    }

    @OnClick({R.id.iv_back , R.id.ll_reg , R.id.tv_login ,R.id.tv_forget})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_forget:
                Bundle b = new Bundle();
                b.putInt("payid" , 1);
                StartActivityUtil.start(activity , VerificationcodeActivity.class ,b , Constants.FORGET);
                break;
            case R.id.iv_back :
                onBackPressed();
                break;
            case R.id.ll_reg :
                StartActivityUtil.start(activity , RegisterActivity.class , REGISTER);
                break;
            case R.id.tv_login :

                String username = et_login_user.getText().toString().trim();
                String password = et_login_pwd.getText().toString().trim();
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
        PrefUtil.setToken(context , PrefJsonUtil.getProfile(context).getToken());

        UserInfo.getInstance().setUserSig(PrefJsonUtil.getProfile(context).getUserSig());
        UserInfo.getInstance().setId(PrefJsonUtil.getProfile(context).getUserId());

        UserManage.getInstance().saveUserInfo(LoginActivity.this, et_login_user.getText().toString().trim(), et_login_pwd.getText().toString().trim());

        Log.d("user_im_sign","用户的IM签名"+PrefJsonUtil.getProfile(context).getUserSig());
        Log.d("user_id","用户的id"+PrefJsonUtil.getProfile(context).getUserId());

        //登录之前要初始化群和好友关系链缓存
        FriendshipEvent.getInstance().init();
        GroupEvent.getInstance().init();
        LoginBusiness.loginIm(PrefJsonUtil.getProfile(context).getUserId(), PrefJsonUtil.getProfile(context).getUserSig(), this);

        setResult(RESULT_OK);
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

    String msg1 = "";

    @Override
    public void error(String msg) {
        dialog.dismiss();
        ToastUtil.showShortToast(context ,msg);

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
    public void onError(int i, String s) {
        Log.d("TIM_Change_Profile",s);
    }

    @Override
    public void onSuccess() {
        Log.d("TIM_Change_Profile","success");
    }
}
