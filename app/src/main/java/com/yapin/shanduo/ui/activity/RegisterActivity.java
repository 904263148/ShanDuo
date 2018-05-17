package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.GetCodePresenter;
import com.yapin.shanduo.presenter.RegisterPresenter;
import com.yapin.shanduo.ui.contract.GetCodeContract;
import com.yapin.shanduo.ui.contract.RegisterContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.InputMethodUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements GetCodeContract.View , RegisterContract.View , View.OnFocusChangeListener{

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPwd;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.iv_name)
    ImageView ivPhone;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;

    private Context context;
    private Activity activity;
    private GetCodePresenter codePresenter;
    private RegisterPresenter registerPresenter;

    private ProgressDialog dialog;

    private boolean isInitView = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        codePresenter = new GetCodePresenter();
        codePresenter.init(context,this);
        registerPresenter = new RegisterPresenter();
        registerPresenter.init(context , this);
    }

    @Override
    public void initView() {

        setIsEvent(Constants.IS_EVENT);

        dialog = new ProgressDialog(this);
        dialog.setMessage(context.getString(R.string.dialog_loading));
        dialog.setCanceledOnTouchOutside(false);

        etCode.setOnFocusChangeListener(this);
        etPhone.setOnFocusChangeListener(this);
        etPwd.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            switch (v.getId()) {
                case R.id.et_phone:
                    ivPhone.setBackgroundResource(R.drawable.icon_name_checked);
                    if ( TextUtils.isEmpty(etCode.getText().toString().trim())) {
                        ivCode.setBackgroundResource(R.drawable.icon_code_unchecked);
                    } else {
                        ivCode.setBackgroundResource(R.drawable.icon_code_checked);
                    }
                    if (TextUtils.isEmpty(etPwd.getText().toString().trim())) {
                        ivPwd.setBackgroundResource(R.drawable.icon_password);
                    } else {
                        ivPwd.setBackgroundResource(R.drawable.icon_pwd_checked);
                    }
                    break;
                case R.id.et_code:
                    ivCode.setBackgroundResource(R.drawable.icon_code_checked);
                    if ( TextUtils.isEmpty(etPhone.getText().toString().trim()) ) {
                        ivPhone.setBackgroundResource(R.drawable.icon_name_unchecked);
                    } else {
                        ivPhone.setBackgroundResource(R.drawable.icon_name_checked);
                    }
                    if (TextUtils.isEmpty(etPwd.getText().toString().trim())) {
                        ivPwd.setBackgroundResource(R.drawable.icon_password);
                    } else {
                        ivPwd.setBackgroundResource(R.drawable.icon_pwd_checked);
                    }
                    break;
                case R.id.et_password:
                    ivPwd.setBackgroundResource(R.drawable.icon_pwd_checked);
                    if ( TextUtils.isEmpty(etPhone.getText().toString().trim()) ) {
                        ivPhone.setBackgroundResource(R.drawable.icon_name_unchecked);
                    } else {
                        ivPhone.setBackgroundResource(R.drawable.icon_name_checked);
                    }
                    if ( TextUtils.isEmpty(etCode.getText().toString().trim())) {
                        ivCode.setBackgroundResource(R.drawable.icon_code_unchecked);
                    } else {
                        ivCode.setBackgroundResource(R.drawable.icon_code_checked);
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.iv_back , R.id.tv_code , R.id.tv_reg})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_code:
                codePresenter.getCode(etPhone.getText().toString().trim() , Constants.GET_CODE_REG);
                break;
            case R.id.tv_reg:
                registerPresenter.register(etPhone.getText().toString().trim() ,etCode.getText().toString().trim() , etPwd.getText().toString().trim() );
                break;
        }
    }

    @Override
    public void show(String data) {
        dialog.dismiss();
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCode.setEnabled(false);
                tvCode.setText(context.getString(R.string.reg_get_check_count_timer, millisUntilFinished / 1000));
                tvCode.setBackground(context.getDrawable(R.drawable.rounded_unable));
            }

            @Override
            public void onFinish() {
                tvCode.setEnabled(true);
                tvCode.setText(context.getString(R.string.reg_get_check));
                tvCode.setBackground(context.getDrawable(R.drawable.rounded_confrim_login));
            }
        }.start();
    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context , data);
        dialog.dismiss();
        setResult(RESULT_OK);
        onBackPressed();
    }

    @Override
    public void loading(int type) {
        if (type == Constants.IS_CODE) {
            tvCode.setEnabled(false);
            tvCode.setText(context.getString(R.string.reg_get_check));
            tvCode.setBackground(context.getDrawable(R.drawable.rounded_unable));
        }
        dialog.show();
        InputMethodUtil.hideInputMethod(activity);
    }

    @Override
    public void networkError(int type) {
        if (type == Constants.IS_CODE) {
            tvCode.setEnabled(true);
            tvCode.setText(context.getString(R.string.reg_get_check));
            tvCode.setBackground(context.getDrawable(R.drawable.rounded_confrim_login));
        }
        ToastUtil.showShortToast(context , "网络连接异常");
        dialog.dismiss();
    }

    @Override
    public void error(int type, String msg) {
        if (type == Constants.IS_CODE) {
            tvCode.setEnabled(true);
            tvCode.setText(context.getString(R.string.reg_get_check));
            tvCode.setBackground(context.getDrawable(R.drawable.rounded_confrim_login));
        }
        ToastUtil.showShortToast(context,msg);
        dialog.dismiss();
    }

    @Override
    public void showFailed(int type, String msg) {
        if (type == Constants.IS_CODE) {
            tvCode.setEnabled(true);
            tvCode.setText(context.getString(R.string.reg_get_check));
            tvCode.setBackground(context.getDrawable(R.drawable.rounded_confrim_login));
        }
        ToastUtil.showShortToast(context , msg);
        dialog.dismiss();
    }

}
