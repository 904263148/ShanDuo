package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.PaymentPasswordPresenter;
import com.yapin.shanduo.ui.contract.PaymentPasswordContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.PasswordInputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/6/1.
 */

public class PaymentPasswordActivity extends BaseActivity implements PaymentPasswordContract.View , PasswordInputView.onTextChangeListener{

    private PaymentPasswordPresenter presenter;
    private Context context;
    private Activity activity;

    @BindView(R.id.again_paypswd_pet)
    PasswordInputView again_paypswd_pet;

    private static final int NEWPAYMENTPASSWORD=1;

    String password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentpassword);

        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);

        presenter = new PaymentPasswordPresenter();
        presenter.init(context,this);

        again_paypswd_pet.setOnChangeListener(this);
    }

    @OnClick({R.id.iv_back ,R.id.tv_code_pwd})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_code_pwd:
                Bundle b = new Bundle();
                b.putInt("payid" , 3);
                StartActivityUtil.start(activity , VerificationcodeActivity.class ,b , Constants.PAY_FORGET);
                break;
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,"验证成功");
        Bundle bundle = new Bundle();
        bundle.putString("password",password);
        StartActivityUtil.start(activity , NewPaymentPasswordActivity.class, bundle ,NEWPAYMENTPASSWORD);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {
            case NEWPAYMENTPASSWORD:
                setResult(RESULT_OK);
                finish();
                break;
            case Constants.PAY_FORGET:
                setResult(RESULT_OK);
                finish();
                break;
        }
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

    @Override
    public void getLength(int length) {
        if(length == 6){
            password = again_paypswd_pet.getText().toString().trim();
            presenter.paymentpassword(password);
        }
    }
}
