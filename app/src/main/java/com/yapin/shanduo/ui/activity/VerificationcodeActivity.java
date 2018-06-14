package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.GetCodePresenter;
import com.yapin.shanduo.presenter.LoginPasswordPresenter;
import com.yapin.shanduo.presenter.VerificationPresenter;
import com.yapin.shanduo.ui.contract.GetCodeContract;
import com.yapin.shanduo.ui.contract.LoginPasswordContract;
import com.yapin.shanduo.ui.contract.VerificationConract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.InputMethodUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/6/7.
 */

public class VerificationcodeActivity extends BaseActivity implements VerificationConract.View , GetCodeContract.View {

    private GetCodePresenter codePresenter;
    private VerificationPresenter presenter;
    private Context context;
    private Activity activity;


    private ProgressDialog dialog;

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.et_code)
    EditText et_code;
    int payId;
    String phone;
    String code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationcode);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);

        codePresenter = new GetCodePresenter();
        codePresenter.init(context,this);

        presenter = new VerificationPresenter();
        presenter.init(context ,this);

        Bundle bundle = getIntent().getExtras();
        payId = bundle.getInt("payid");
        if (payId == 5){
            et_phone.setText(PrefJsonUtil.getProfile(context).getPhone());
        }else if (payId == 3){
            et_phone.setText(PrefJsonUtil.getProfile(context).getPhone());
        }

    }

    @OnClick({R.id.tv_finish ,R.id.bt_logout ,R.id.tv_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_finish:
                finish();
                break;
            case R.id.bt_logout:
                phone = et_phone.getText().toString().trim();
                code = et_code.getText().toString().trim();
                if (payId == 1) {
                    presenter.getverification("3", phone, code);
                }else if (payId == 5){
                    presenter.getverification("3", phone, code);
                }else if (payId == 3){
                    presenter.getverification("4", phone, code);
                }
                break;
            case R.id.tv_code:
                if (payId == 1) {
                    codePresenter.getCode(et_phone.getText().toString().trim(), "3");
                }else if (payId == 5){
                    codePresenter.getCode(et_phone.getText().toString().trim(), "3");
                }else if (payId == 3){
                    codePresenter.getCode(et_phone.getText().toString().trim(), "4");
                }
                break;
        }
    }

    @Override
    public void initView() {

        dialog = new ProgressDialog(this);
        dialog.setMessage(context.getString(R.string.dialog_loading));
        dialog.setCanceledOnTouchOutside(false);



    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context, data);
        if (payId == 1 || payId == 5) {
            Bundle bundle = new Bundle();
            bundle.putString("phone", phone);
            bundle.putString("code", code);
            bundle.putInt("payId", payId);
            StartActivityUtil.start(activity, CodeLogpwdActivity.class, bundle, Constants.SETFOGGET);
        }else if (payId == 3){
            Bundle bundle = new Bundle();
            bundle.putString("phone", phone);
            bundle.putString("code", code);
            bundle.putInt("payId", 3);
            StartActivityUtil.start(activity, NewPaymentPasswordActivity.class, bundle, Constants.PAY_SETFOGGET);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {
            case Constants.SETFOGGET:
                setResult(RESULT_OK);
                finish();
                break;
            case Constants.PAY_SETFOGGET:
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
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void show(String data) {
        dialog.dismiss();
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_code.setEnabled(false);
                tv_code.setText(context.getString(R.string.reg_get_check_count_timer, millisUntilFinished / 1000));
                tv_code.setBackground(context.getDrawable(R.drawable.rounded_unable));
            }

            @Override
            public void onFinish() {
                tv_code.setEnabled(true);
                tv_code.setText(context.getString(R.string.reg_get_check));
                tv_code.setBackground(context.getDrawable(R.drawable.rounded_confrim_login));
            }
        }.start();
    }

    @Override
    public void loading(int type) {
        if (type == Constants.IS_CODE) {
            tv_code.setEnabled(false);
            tv_code.setText(context.getString(R.string.reg_get_check));
            tv_code.setBackground(context.getDrawable(R.drawable.rounded_unable));
        }
        dialog.show();
        InputMethodUtil.hideInputMethod(activity);
    }

    @Override
    public void networkError(int type) {
        if (type == Constants.IS_CODE) {
            tv_code.setEnabled(true);
            tv_code.setText(context.getString(R.string.reg_get_check));
            tv_code.setBackground(context.getDrawable(R.drawable.rounded_confrim_login));
        }
        ToastUtil.showShortToast(context , "网络连接异常");
        dialog.dismiss();
    }

    @Override
    public void error(int type, String msg) {
        if (type == Constants.IS_CODE) {
            tv_code.setEnabled(true);
            tv_code.setText(context.getString(R.string.reg_get_check));
            tv_code.setBackground(context.getDrawable(R.drawable.rounded_confrim_login));
        }
        ToastUtil.showShortToast(context,msg);
        dialog.dismiss();
    }

    @Override
    public void showFailed(int type, String msg) {
        if (type == Constants.IS_CODE) {
            tv_code.setEnabled(true);
            tv_code.setText(context.getString(R.string.reg_get_check));
            tv_code.setBackground(context.getDrawable(R.drawable.rounded_confrim_login));
        }
        ToastUtil.showShortToast(context , msg);
        dialog.dismiss();
    }
}
