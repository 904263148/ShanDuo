package com.yapin.shanduo.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.PayResult;
import com.yapin.shanduo.model.entity.TransactionrecordInfo;
import com.yapin.shanduo.presenter.MywalletPresenter;
import com.yapin.shanduo.ui.contract.MywalletContract;
import com.yapin.shanduo.ui.fragment.MyactivityFragment;
import com.yapin.shanduo.ui.fragment.PayDialogFragment;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/3.
 */
//, PopupWindow.OnDismissListener, View.OnClickListener
public class MywalletActivity extends BaseActivity implements MywalletContract.View ,PayDialogFragment.DialogDismiss  {

    private MywalletPresenter presenter;
    private Context context;
    private Activity activity;

//    private PopupWindow popupWindow;
//    private View popView;
//    private PopupView popupView;

    private static final int TRANSACTIONRECORD=1;

    private PayDialogFragment payDialogFragment;

    private static final int SDK_PAY_FLAG = 1;

    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_beans)
    TextView tv_beans;


    private FlickerPurseInfo flickerPurseInfo = new FlickerPurseInfo();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        ButterKnife.bind(this);

        presenter = new MywalletPresenter();
        presenter.init(context,this);
        //设置PopupWindow的View
//        popView = LayoutInflater.from(this).inflate(R.layout.popwindow_commonroof, null);
        initView();
        presenter.mywallet();


    }

    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;
    }

    @OnClick({R.id.iv_back, R.id.ll_recharge ,R.id.ll_Transactionrecord})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_recharge:
//                openPopupWindow();
                payDialogFragment = PayDialogFragment.newInstance(Constants.RECHARGE , 3 , "" , "" ,"");
                payDialogFragment.setDismissListener(MywalletActivity.this);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                payDialogFragment.show(ft, "tag");
                break;
            case R.id.ll_Transactionrecord:
                StartActivityUtil.start(activity , TransactionrecordActivity.class , TRANSACTIONRECORD);
                break;
        }
    }

//    public void payWechat(){
//        final IWXAPI api = WXAPIFactory.createWXAPI(activity, null);
//        api.registerApp(Constants.WECHAT_APPID);
//        Runnable payRunnable1 = new Runnable() {  //这里注意要放在子线程
//            @Override
//            public void run() {
//                PayReq req = new PayReq();
//                req.appId = Constants.WECHAT_APPID;
//                req.nonceStr= "afe97055915e4f07bf6b7576a0e03588";
//                req.packageValue = "Sign=WXPay";
//                req.partnerId = "1504783251";
//                req.prepayId= "wx2819060668936052850a10330345385117";
//                req.timeStamp= "1527505568";
//                req.sign= "72157171D298A705C3604BF08B499098";
//                api.sendReq(req);//发送调起微信的请求
//            }
//        };
//        Thread payThread1 = new Thread(payRunnable1);
//        payThread1.start();
//        Log.d("wechat","点击了");
//    }

    @Override
    public void success(FlickerPurseInfo data) {
//        ToastUtil.showShortToast(context , data);
        flickerPurseInfo = data;
        tv_money.setText(flickerPurseInfo.getMoney()+"");
        tv_beans.setText(flickerPurseInfo.getBeans()+"");
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

//    class PopupView {
//        @BindView(R.id.ib_back)
//        ImageButton ibBack;
//        @BindView(R.id.tv_title)
//        TextView tvTitle;
//        @BindView(R.id.tv_tv1)
//        TextView tvTv1;
//        @BindView(R.id.tv_tv2)
//        TextView tvTv2;
//        @BindView(R.id.t_tv3)
//        TextView tTv3;
//        @BindView(R.id.tv_balance_pay)
//        TextView tvBalancePay;
//        @BindView(R.id.tv_wechat_pay)
//        TextView tvWechatPay;
//        @BindView(R.id.tv_alipay)
//        TextView tvAlipay;
//        @BindView(R.id.tv_memo1)
//        TextView tvMemo1;
//        @BindView(R.id.tv_memo2)
//        TextView tvMemo2;
//        @BindView(R.id.bt_cancel)
//        Button btCancel;
//        @BindView(R.id.bt_confirm)
//        Button btConfirm;
//        @BindView(R.id.ll_tag)
//        LinearLayout ll_tag;
//        @BindView(R.id.ll_balance)
//        LinearLayout ll_balance;
//
//        public PopupView(View view) {
//            ButterKnife.bind(this, view);
//        }
//
//        @OnClick({R.id.ib_back , R.id.tv_balance_pay , R.id.tv_wechat_pay , R.id.tv_alipay , R.id.bt_cancel , R.id.bt_confirm})
//        public void onClick(View view){
//            switch (view.getId()){
//                case R.id.ib_back:
//                    popupWindow.dismiss();
//                    break;
//                case R.id.tv_balance_pay:
//                    break;
//                case R.id.tv_wechat_pay:
//                    break;
//                case R.id.tv_alipay:
//                    break;
//                case R.id.bt_cancel:
//                    break;
//                case R.id.bt_confirm:
//                    break;
//            }
//        }
//
//    }
//
//    public void openPopupWindow() {
//
//        popupView = new PopupView(popView);
//
//        //防止重复按按钮
//        if (popupWindow != null && popupWindow.isShowing()) {
//            return;
//        }
//        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        //设置背景,这个没什么效果，不添加会报错
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //设置点击弹窗外隐藏自身
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        //设置动画
//        popupWindow.setAnimationStyle(R.style.PopupWindow);
//        //设置位置
//        popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 0);
//        //设置消失监听
//        popupWindow.setOnDismissListener(this);
//        //设置背景色
//        setBackgroundAlpha(0.5f);
//
//        initPopView(popupView);
//        setPopupClick();
//    }
//
//    private void initPopView(PopupView popupView){
//        popupView.tvTitle.setText("充值");
//        popupView.ll_tag.setVisibility(View.GONE);
//        popupView.ll_balance.setVisibility(View.GONE);
//        popupView.tvMemo1.setVisibility(View.GONE);
//        popupView.tvMemo2.setVisibility(View.GONE);
//    }
//
//    private void setPopupClick() {
//        popView.findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//        popView.findViewById(R.id.tv_balance_pay).setOnClickListener(this);
//        popView.findViewById(R.id.tv_wechat_pay).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                payWechat();
//            }
//        });
//        popView.findViewById(R.id.tv_alipay).setOnClickListener(this);
//        popView.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//        popView.findViewById(R.id.bt_confirm).setOnClickListener(this);
//    }
//
//    //设置屏幕背景透明效果
//    public void setBackgroundAlpha(float alpha) {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = alpha;
//        getWindow().setAttributes(lp);
//    }
//
//    @Override
//    public void onDismiss() {
//        setBackgroundAlpha(1);
//    }
//
//    private String orderParam = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018011901973829&biz_content=%7B%22body%22%3A%22%E5%BC%80%E9%80%9A%E9%97%AA%E5%A4%9AVIP12%E4%B8%AA%E6%9C%88%22%2C%22out_trade_no%22%3A%223115e6a849f94af3875d0d29ed34bba3%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%BC%80%E9%80%9A%E9%97%AA%E5%A4%9AVIP12%E4%B8%AA%E6%9C%88105.60%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22105.60%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fyapinkeji.com%2Fshanduoparty%2Fjpay%2Falipay&sign=WFqRKp7R0tsKMisxodMqnfPpOSHp64FEYdq1ZO4efTHTVkNAy2PHqa9%2F%2FG5t3Msf%2FXH9m3P4WpCNHptWImnbT0tf%2BO%2Fv1G0evgRIi6%2FxXAgyfm8eHEl6B7dJi9tA4AxYVhmFYSAxHRys%2Fk3rcHn%2FL2r8VRxv8UcjbGcICOKimbHMUHwNyeXbAJavwXJ%2BcrpNLpteZyNWAuab%2B0Cv7Gio4AdhWGmjXDausHIrr68p3jz3PwBH3x8DFA1arcTk5dzEyW0Z6jh1APxdwa4OZ0IiVXF5AaVIVRqHc74zfuMMZ%2BxA5GqvCtQA6vdaYhOn969G1lLDJgps5m7Wh76ngOE1FQ%3D%3D&sign_type=RSA2&timestamp=2018-05-22+11%3A07%3A50&version=1.0";
//
//    public void payAlibaba(){
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(activity);
//                Map<String, String> result = alipay.payV2(orderParam,true);
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    Log.d("alipay_result",msg.obj+"");
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        };
//    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TRANSACTIONRECORD:
                break;
        }
    }

    @Override
    public void dismiss() {
        payDialogFragment.dismiss();
    }
}
