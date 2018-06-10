package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.NewPaymentPasswordPresenter;
import com.yapin.shanduo.presenter.PaymentPasswordPresenter;
import com.yapin.shanduo.ui.contract.NewPaymentPasswordContract;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.PasswordInputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/6/1.
 */

public class NewPaymentPasswordActivity extends BaseActivity implements NewPaymentPasswordContract.View , PasswordInputView.onTextChangeListener{

    private NewPaymentPasswordPresenter presenter;
    private Context context;
    private Activity activity;

    String password;
    String newPassword;

    String phone;
    String code;
    int payId;

    @BindView(R.id.again_paypswd_pet)
    PasswordInputView again_paypswd_pet;

    private static final int SETUP=1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpaymentpassword);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new NewPaymentPasswordPresenter();
        presenter.init(context,this);

        Bundle bundle = this.getIntent().getExtras();
        password = bundle.getString("password");
        again_paypswd_pet.setOnChangeListener(this);

        Bundle bundle1 = getIntent().getExtras();
        phone = bundle1.getString("phone");
        code = bundle1.getString("code");
        payId = bundle1.getInt("payId");

    }

    @OnClick({R.id.bt_Submission , R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_Submission:
                if (payId == 3){
                    presenter.newpaymentpassword("1" , code ,"", newPassword);
                }else {
                    presenter.newpaymentpassword("2" , "" ,password, newPassword);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,"修改成功");
        init();
    }

    public void init(){
        setResult(RESULT_OK);
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

    @Override
    public void getLength(int length) {
        newPassword = again_paypswd_pet.getText().toString().trim();
    }
}
