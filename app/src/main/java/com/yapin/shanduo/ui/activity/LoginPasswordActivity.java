package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.FriendshipInfo;
import com.yapin.shanduo.im.model.GroupInfo;
import com.yapin.shanduo.im.model.UserInfo;
import com.yapin.shanduo.presenter.LoginPasswordPresenter;
import com.yapin.shanduo.ui.contract.LoginPasswordContract;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/31.
 */

public class LoginPasswordActivity extends BaseActivity implements LoginPasswordContract.View ,TextWatcher, View.OnClickListener{

    private LoginPasswordPresenter presenter;
    private Context context;
    private Activity activity;


    @BindView(R.id.et_Old_cipher)   //旧密码
    EditText et_Old_cipher;
    @BindView(R.id.et_Newpassword_one)  //第一次
    EditText et_Newpassword_one;
    @BindView(R.id.et_Newpassword_two)
    EditText et_Newpassword_two;     //第二次
    @BindView(R.id.bt_modification)
    Button bt_modification; //按钮


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);

        presenter = new LoginPasswordPresenter();
        presenter.init(context,this);

        initview();
        initData();

    }

    @OnClick({R.id.iv_back})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initview() {

        et_Old_cipher.addTextChangedListener(this);
        et_Newpassword_one.addTextChangedListener(this);
        et_Newpassword_two.addTextChangedListener(this);
        bt_modification.setSelected(false);
        bt_modification.setOnClickListener(this);
        et_Old_cipher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearAll();
                return false;
            }
        });
        et_Newpassword_one.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearAll();
                return false;
            }
        });
        et_Newpassword_two.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearAll();
                return false;
            }
        });


    }

    private void initData() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,"修改成功");
        returnlogin();
    }

    public  void  returnlogin(){
        PrefUtil.setToken(context , "");
        PrefJsonUtil.setProfile(context , "");

        LoginBusiness.logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
//                        Log.d("TIM_logout",s);
            }

            @Override
            public void onSuccess() {
//                        Log.d("TIM_logout","退出登录成功");
                TlsBusiness.logout(UserInfo.getInstance().getId());
                UserInfo.getInstance().setId(null);
                MessageEvent.getInstance().clear();
                FriendshipInfo.getInstance().clear();
                GroupInfo.getInstance().clear();
                StartActivityUtil.start(activity , LoginActivity.class);
                finish();
            }
        });
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
//        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {
//        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        /**
         * 当三个EditText的内容都不为空的时候，
         * Button为蓝色，否则为灰色通过
         * bt_confirm.setSelected(true)实现蓝色，
         *  bt_confirm.setSelected(false);实现灰色
         */
        if(!TextUtils.isEmpty(et_Newpassword_two.getText().toString())&&!TextUtils.isEmpty(et_Old_cipher.getText().toString())
                &&!TextUtils.isEmpty(et_Newpassword_one.getText().toString())){
            bt_modification.setSelected(true);
        }else{
            bt_modification.setSelected(false);
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private ProgressDialog dialog;
    @Override
    public void onClick(View v) {
        if(checkNull()){
            return;
        }
        if(!et_Newpassword_two.getText().toString().equals(et_Newpassword_one.getText().toString())){
            et_Newpassword_two.setText("");
            requstFocus(et_Newpassword_two, "两次密码不一致", Color.RED,true);
            return;
        }
        if(et_Newpassword_two.getText().toString().equals(et_Newpassword_one.getText().toString()) && et_Newpassword_one.getText().toString().equals(et_Old_cipher.getText().toString())){
            et_Newpassword_two.setText("");
            et_Newpassword_one.setText("");
            requstFocus(et_Newpassword_two, "旧密码和新密码一致", Color.RED,true);
            return;
        }
        dialog=ProgressDialog.show(this,"","修改中,请稍后...",true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(!"".equals(et_Old_cipher.getText().toString())){
//                    et_Old_cipher.setText("");
//                    requstFocus(et_Old_cipher, "原密码错误", Color.RED, true);
//                }else{
                String password = et_Old_cipher.getText().toString().trim();
                String newpassword = et_Newpassword_two.getText().toString().trim();
                presenter.loginpassword(password , newpassword);
//                    Toast.makeText(LoginPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                }
                dialog.dismiss();
            }
        },2000);
    }

    private boolean checkNull() {
        if(TextUtils.isEmpty(et_Old_cipher.getText().toString())){
            requstFocus(et_Old_cipher, null, Color.GRAY,true);
            return true;
        }
        if(et_Old_cipher.getText().toString().length()<8){
            et_Old_cipher.setText("");
            requstFocus(et_Old_cipher, "原密码格式错误", Color.RED,true);
            return true;
        }
        if(TextUtils.isEmpty(et_Newpassword_one.getText().toString())){
            requstFocus(et_Newpassword_one, null, Color.GRAY,true);
            return true;
        }
        if(et_Newpassword_one.getText().toString().length()<8){
            et_Newpassword_one.setText("");
            requstFocus(et_Newpassword_one, "新密码格式错误", Color.RED,true);
            return true;
        }
        if(TextUtils.isEmpty(et_Newpassword_two.getText().toString())){
            requstFocus(et_Newpassword_two,null, Color.GRAY,true);
            return true;
        }
        return false;
    }
    public void requstFocus(EditText et,String hint,int hintColor,boolean needFocus){
        if(hint==null){
            hint="请输入六位密码";
//            et_Old_cipher.set("若包含字母，请注意区分大小写");
//            et_Newpassword_one.setHint("8-16位，至少包含数字/字母/字符2种组合");
//            et_Newpassword_two.setHint("8-16位，至少包含数字/字母/字符2种组合");
        }
        et.setHint(hint);
        et.setHintTextColor(hintColor);
        if(needFocus){
            et.requestFocus();
        }
    }
    public void clearAll(){
        requstFocus(et_Old_cipher, "若包含字母，请注意区分大小写", Color.GRAY,false);
        requstFocus(et_Newpassword_one, "8-16位，至少包含数字/字母/字符2种组合", Color.GRAY,false);
        requstFocus(et_Newpassword_two,"8-16位，至少包含数字/字母/字符2种组合", Color.GRAY,false);
    }
}
