package com.yapin.shanduo.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.maps.AMap;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.PayOrder;
import com.yapin.shanduo.model.entity.PayResult;
import com.yapin.shanduo.presenter.Addactivitypresenter;
import com.yapin.shanduo.presenter.PayOrderPresenter;
import com.yapin.shanduo.ui.contract.AddactivityContract;
import com.yapin.shanduo.ui.contract.PayOrderContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.DateTimePickDialogUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.EmojiEditText;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/4.
 */

public class AddactivityActivity extends RightSlidingActivity implements AddactivityContract.View,View.OnClickListener , PayOrderContract.View ,PopupWindow.OnDismissListener{

    @BindView(R.id.et_add_title)
    EmojiEditText et_add_title;
    @BindView(R.id.et_add_numberofgirls)
    EditText et_add_numberofgirls;
    @BindView(R.id.et_add_Numberofboys)
    EditText et_add_Numberofboys;
    @BindView(R.id.tv_add_place)
    TextView tv_add_place;
    @BindView(R.id.tv_add_starttime)
    TextView tv_add_starttime;
    @BindView(R.id.tv_add_Deadline)
    TextView tv_add_Deadline;
    @BindView(R.id.et_add_Modeofconsumption)
    TextView et_add_Modeofconsumption;
    @BindView(R.id.et_add_title_remarks)
    EditText et_add_title_remarks;
    @BindView(R.id.but_add_Releaseactivities)
    Button but_add_Releaseactivities;
    @BindView(R.id.tv_add_place_remarks)
    EmojiEditText tv_add_place_remarks;

    String activityCutoffTime;
    String activityStartTime;
    String mode;
    String lat;
    String lon;
    String remarks;
    String activityAddress;
    String manNumber="0";
    String womanNumber="0";
    String activityName;
    String textlonlat;
    String detailedAddress;

    private static final int SDK_PAY_FLAG = 1;

    private Addactivitypresenter presenter;
    private Context context;
    private Activity activity;

    private PayOrderPresenter payOrderPresenter;

    private boolean isTop = false;

    private String actId;

    private PopupWindow popupWindow;
    private View popView;
    private PopupView popupView;

    private String payId;
    private String passord = "";

    int a;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new Addactivitypresenter();
        presenter.init(context,this);
        payOrderPresenter = new PayOrderPresenter();
        payOrderPresenter.init(this);
        tv_add_starttime.setOnClickListener(this);

        //设置PopupWindow的View
        popView = LayoutInflater.from(this).inflate(R.layout.popwindow_commonroof, null);

        tv_add_Deadline.setVisibility(View.GONE);

        et_add_Modeofconsumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                final String[] emotion = {"我请客", "AA制","男的AA，美女随意"};

                //    设置一个单项选择下拉框
                builder1.setSingleChoiceItems(emotion, a , new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        a = which;
                        et_add_Modeofconsumption.setText(emotion[which]);
                    }
                });
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mode = et_add_Modeofconsumption.getText().toString().trim();
                    }
                });
                builder1.show();
            }
        });

        tv_add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(activity ,MapGaodeActivity.class);
                startActivityForResult(intent , 1);
            }
        });

    }
    //获取定位选择的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            setText(data.getStringExtra("textTitle"));
            textlonlat = data.getStringExtra("textlonlat");
        }
    }
    //绑定定位选择的数据
    public void setText(String data){
        tv_add_place.setText(data);
    }

    @Override
    public void initView() {
        Utils.checkPermission(activity);
    }

    @OnClick({R.id.but_add_Releaseactivities,R.id.tv_add_starttime,R.id.tv_add_finish ,R.id.et_add_Remarks})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_add_finish:
                onBackPressed();
                break;
            case R.id.but_add_Releaseactivities:
                if(checkNull()){
                    return;
                }
                String []ary = textlonlat.split("\\,");
                lat=ary[0];
                lon=ary[1];
//                Log.i("test","/n经度："+lon+"/n纬度："+lat);

                activityName =  et_add_title.getText().toString().trim();
                womanNumber = et_add_numberofgirls.getText().toString().trim();
                manNumber = et_add_Numberofboys.getText().toString().trim();
                detailedAddress = tv_add_place_remarks.getText().toString().trim();
                activityAddress = tv_add_place.getText().toString().trim();
                remarks =  et_add_title_remarks.getText().toString().trim();
                activityCutoffTime =tv_add_Deadline.getText().toString().trim();
                activityStartTime=tv_add_starttime.getText().toString().trim();
                presenter.addactivity(activityName,activityStartTime,activityAddress,mode,manNumber,womanNumber,remarks,activityCutoffTime,lon,lat,detailedAddress);
                break;

            case R.id.tv_add_starttime:
                final android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(activity);
                final String[] emotion = {"请选择活动开始时间", "请选择活动报名截止时间"};
                //    设置一个单项选择下拉框
                builder2.setSingleChoiceItems(emotion, 3, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        if (which == 0){
                        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(AddactivityActivity.this, null);
                        dateTimePicKDialog.dateTimePicKDialog(tv_add_starttime, 0, true);
                    }else {
                        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(AddactivityActivity.this, null);
                        dateTimePicKDialog.dateTimePicKDialog(tv_add_Deadline, 0, true);
                            tv_add_Deadline.setVisibility(View.VISIBLE);
                        }
                    }
                });
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder2.show();
                break;
            case R.id.et_add_Remarks:
                isTop = true;
                break;
        }
    }

    private boolean checkNull() {

        if(et_add_title.getText().toString().length()<1){
            et_add_title.setText("");
            requstFocus(et_add_title, "活动主题不能为空！", Color.RED,true);
            return true;
        }

        if(tv_add_starttime.getText().toString().length()<1){
            tv_add_starttime.setText("");
            requstFocus((EditText) tv_add_starttime, "活动开始时间不能为空！", Color.RED,true);
            return true;
        }

        if(tv_add_Deadline.getText().toString().length()<1){
            tv_add_Deadline.setText("");
            requstFocus((EditText) tv_add_Deadline, "报名截止时间不能为空！", Color.RED,true);
            return true;
        }

        if(et_add_Modeofconsumption.getText().toString().length()<1){
            et_add_Modeofconsumption.setText("");
            requstFocus((EditText) et_add_Modeofconsumption, "付款方式不能为空！", Color.RED,true);
            return true;
        }

        if(tv_add_place.getText().toString().length()<1){
            tv_add_place.setText("");
            requstFocus((EditText) tv_add_place, "活动地址不能为空！", Color.RED,true);
            return true;
        }

        return false;
    }

    public void requstFocus(EditText et,String hint,int hintColor,boolean needFocus){
        if(hint==null){
            hint="请输入：";
        }
        et.setHint(hint);
        et.setHintTextColor(hintColor);
        if(needFocus){
            et.requestFocus();
        }
    }

    @Override
    public void success(String data) {
        actId = data;
        ToastUtil.showShortToast(context,"发布成功");
        if(isTop){
            openPopupWindow();
        }else {
            finish();
        }
    }

    @Override
    public void success(PayOrder data) {
        if(Constants.PAY_BALANCE.equals(payId)){

        }else if(Constants.PAY_WECHAT.equals(payId)){
            payWechat(data);
        }else {
            payAlibaba(data.getOrder());
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

    public void openPopupWindow() {

        popupView = new PopupView(popView);

        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 0);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置背景色
        setBackgroundAlpha(0.5f);

    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

    class PopupView {
        @BindView(R.id.ib_back)
        ImageButton ibBack;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_tv1)
        TextView tvTv1;
        @BindView(R.id.tv_tv2)
        TextView tvTv2;
        @BindView(R.id.t_tv3)
        TextView tTv3;
        @BindView(R.id.tv_balance_pay)
        TextView tvBalancePay;
        @BindView(R.id.tv_wechat_pay)
        TextView tvWechatPay;
        @BindView(R.id.tv_alipay)
        TextView tvAlipay;
        @BindView(R.id.tv_memo1)
        TextView tvMemo1;
        @BindView(R.id.tv_memo2)
        TextView tvMemo2;
        @BindView(R.id.bt_cancel)
        Button btCancel;
        @BindView(R.id.bt_confirm)
        Button btConfirm;
        @BindView(R.id.ll_tag)
        LinearLayout llTag;

        public PopupView(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ib_back , R.id.ll_alipay , R.id.ll_balance  , R.id.ll_wechat , R.id.bt_cancel , R.id.bt_confirm})
        public void onClick(View view){
            switch (view.getId()){
                case R.id.ib_back:
                case R.id.bt_cancel:
                    popupWindow.dismiss();
                    break;
                case R.id.tv_balance_pay:
                    payId = Constants.PAY_BALANCE;
                    tvBalancePay.setTextColor(getResources().getColor(R.color.home_title_color));
                    tvBalancePay.setTextSize(12);
                    tvWechatPay.setTextColor(getResources().getColor(R.color.font_color_gray));
                    tvWechatPay.setTextSize(10);
                    tvAlipay.setTextColor(getResources().getColor(R.color.font_color_gray));
                    tvAlipay.setTextSize(10);
                    break;
                case R.id.tv_wechat_pay:
                    payId = Constants.PAY_WECHAT;
                    tvWechatPay.setTextColor(getResources().getColor(R.color.home_title_color));
                    tvWechatPay.setTextSize(12);
                    tvBalancePay.setTextColor(getResources().getColor(R.color.font_color_gray));
                    tvBalancePay.setTextSize(10);
                    tvAlipay.setTextColor(getResources().getColor(R.color.font_color_gray));
                    tvAlipay.setTextSize(10);
                    break;
                case R.id.tv_alipay:
                    payId = Constants.PAY_ALIPAY;
                    tvAlipay.setTextColor(getResources().getColor(R.color.home_title_color));
                    tvAlipay.setTextSize(12);
                    tvBalancePay.setTextColor(getResources().getColor(R.color.font_color_gray));
                    tvBalancePay.setTextSize(10);
                    tvWechatPay.setTextColor(getResources().getColor(R.color.font_color_gray));
                    tvWechatPay.setTextSize(10);
                    break;
                case R.id.bt_confirm:
                    payOrderPresenter.payOrder(payId , passord , Constants.TYPE_ACT_TOP , "" ,"" , actId);
                    break;
            }
        }

    }

    public void payAlibaba(final String orderParam){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderParam,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    Log.d("alipay_result",msg.obj+"");
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public void payWechat(final PayOrder order){
        final IWXAPI api = WXAPIFactory.createWXAPI(activity, null);
        api.registerApp(Constants.WECHAT_APPID);
        Runnable payRunnable1 = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq req = new PayReq();
                req.appId = order.getAppid();
                req.nonceStr= order.getNoncestr();
                req.packageValue = "Sign=WXPay";
                req.partnerId = order.getPartnerid();
                req.prepayId= order.getPrepayid();
                req.timeStamp= order.getTimestamp();
                req.sign= order.getSign();
                api.sendReq(req);//发送调起微信的请求
            }
        };
        Thread payThread1 = new Thread(payRunnable1);
        payThread1.start();
        Log.d("wechat","点击了");
    }

}
