package com.yapin.shanduo.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.qcloud.sdk.Constant;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.PayOrder;
import com.yapin.shanduo.model.entity.PayResult;
import com.yapin.shanduo.presenter.PayOrderPresenter;
import com.yapin.shanduo.presenter.PeabeanPresenter;
import com.yapin.shanduo.ui.activity.MainActivity;
import com.yapin.shanduo.ui.activity.MembercenterActivity;
import com.yapin.shanduo.ui.activity.ScrollingActivity;
import com.yapin.shanduo.ui.contract.PayOrderContract;
import com.yapin.shanduo.ui.contract.PeabeanContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.PasswordInputView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：L on 2018/6/6 0006 09:07
 */
public class PayDialogFragment extends DialogFragment implements PayOrderContract.View ,PeabeanContract.View ,PasswordInputView.onTextChangeListener{

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
//    @BindView(R.id.bt_confirm)
//    Button btConfirm;
    @BindView(R.id.ll_tag)
    LinearLayout llTag;
//    @BindView(R.id.ll_bean)
//    LinearLayout ll_bean;
//    @BindView(R.id.tv_bean)
//    TextView tv_bean;
    @BindView(R.id.ll_frequency)
    LinearLayout ll_frequency;
    @BindView(R.id.ll_Pea_frequency)    //刷新次数
    LinearLayout ll_Pea_frequency;
//    @BindView(R.id.ll_Pea_bean)    //闪多豆支付
//    LinearLayout ll_Pea_bean;
    @BindView(R.id.ll_balance) //余额支付
    LinearLayout ll_balance;
    @BindView(R.id.ll_a_Recharge)   //充值
    LinearLayout ll_a_Recharge;
    @BindView(R.id.et_a_Recharge)   //金额
    EditText et_a_Recharge;
    @BindView(R.id.bt_Refresh) //刷新确认but
    Button bt_Refresh;
    @BindView(R.id.bt_Recharge) //充值
    Button bt_Recharge;
    @BindView(R.id.bt_vip) //VIP
    Button bt_vip;
    @BindView(R.id.bt_Settop) //置顶
    Button bt_Settop;
    @BindView(R.id.tv_Pea_frequency)
    TextView tv_Pea_frequency;
//    @BindView(R.id.tv_Pea_bean_pay)
//    TextView tv_Pea_bean_pay;


    private PasswordInputView again_paypswd_pet;    //支付密码
    private LinearLayout ll_cancel;     //取消按钮
    private TextView tv_Determine;//确认按钮
    private static final int MEMBERCENTER = 20;

    int a=6;

    private Context context;
    private Activity activity;
    private String Password;

    private String payId = Constants.PAY_BALANCE;

    private PayOrderPresenter payOrderPresenter;
    private PeabeanPresenter peabeanPresenter;

    private DialogDismiss dialogDismiss;

    private int type;
    private String typeId;
    private int position;
    private String month , activityId , money;

    private static final int SDK_PAY_FLAG = 1;

    public static PayDialogFragment newInstance(int type, int position ,String month , String activityId , String money) {
        Bundle args = new Bundle();
        args.putInt("type" , type);
        args.putInt("position" , position);
        args.putString("month" , month);
        args.putString("activityId" , activityId);
        args.putString("money" , money);
        PayDialogFragment fragment = new PayDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        position = getArguments().getInt("position");
        month = getArguments().getString("month");
        activityId = getArguments().getString("activityId");
        money = getArguments().getString("money");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题栏
        View view = inflater.inflate(R.layout.popwindow_commonroof, container, false);
        ButterKnife.bind(this, view);
        payOrderPresenter = new PayOrderPresenter();
        payOrderPresenter.init(this);
        peabeanPresenter = new PeabeanPresenter();
        peabeanPresenter.init(context ,this);

        initView();
        return view;
    }

    public void setData(String month , String activityId , String money){
        this.month = month;
        this.activityId = activityId;
        this.money = money;
    }

    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        switch (type){
            case Constants.OPEN_BY_VIP:
                tvTitle.setText("开通会员");
                ll_a_Recharge.setVisibility(View.GONE);
                ll_frequency.setVisibility(View.GONE);
//                ll_bean.setVisibility(View.GONE);
                llTag.setVisibility(View.GONE);
                tvMemo1.setVisibility(View.GONE);
                tvMemo2.setVisibility(View.GONE);
                bt_Refresh.setVisibility(View.GONE);
                bt_Recharge.setVisibility(View.GONE);
                bt_Settop.setVisibility(View.GONE);
                ll_Pea_frequency.setVisibility(View.GONE);
//                ll_Pea_bean.setVisibility(View.GONE);
                if(position == 0){
                    typeId = Constants.TYPE_VIP;
                }else if (position == 1){
                    typeId = Constants.TYPE_SVIP;
                }
                break;
            case Constants.REFRESH:
                tvTitle.setText("刷新活动");
//                ll_bean.setVisibility(View.GONE);
                ll_a_Recharge.setVisibility(View.GONE);
                bt_Recharge.setVisibility(View.GONE);
                bt_Settop.setVisibility(View.GONE);
                ll_frequency.setVisibility(View.GONE);
                bt_vip.setVisibility(View.GONE);
                llTag.setVisibility(View.GONE);
//                tv_bean.setText("200");
                break;
            case Constants.SET_TOP:
                tvTitle.setText("置顶活动");
                ll_a_Recharge.setVisibility(View.GONE);
                ll_frequency.setVisibility(View.GONE);
//                ll_bean.setVisibility(View.GONE);
                bt_Refresh.setVisibility(View.GONE);
                bt_Recharge.setVisibility(View.GONE);
                bt_vip.setVisibility(View.GONE);
                ll_Pea_frequency.setVisibility(View.GONE);
//                ll_Pea_bean.setVisibility(View.GONE);

                break;
            case Constants.RECHARGE:
                tvTitle.setText("充值");
                llTag.setVisibility(View.GONE);
                ll_balance.setVisibility(View.GONE);
                ll_frequency.setVisibility(View.GONE);
//                ll_bean.setVisibility(View.GONE);
                ll_Pea_frequency.setVisibility(View.GONE);
//                ll_Pea_bean.setVisibility(View.GONE);
                bt_Refresh.setVisibility(View.GONE);
                bt_Settop.setVisibility(View.GONE);
                bt_vip.setVisibility(View.GONE);
                break;

        }

    }
    //R.id.ll_Pea_bean,
    @OnClick({R.id.ib_back , R.id.ll_alipay , R.id.ll_balance  , R.id.ll_wechat, R.id.bt_vip ,R.id.bt_Settop ,
            R.id.ll_Pea_frequency , R.id.bt_cancel , R.id.bt_Refresh ,R.id.bt_Recharge ,R.id.tv_memo2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ib_back:
                dialogDismiss.dismiss();
                break;
            case R.id.bt_cancel:
                dialogDismiss.dismiss();
                break;
            case R.id.ll_Pea_frequency:
                payId = Constants.PEA_FREQUENCY;
                ll_frequency.setVisibility(View.GONE);
//                ll_bean.setVisibility(View.GONE);
                tv_Pea_frequency.setTextColor(getResources().getColor(R.color.home_title_color));
                tv_Pea_frequency.setTextSize(12);
//                tv_Pea_bean_pay.setTextColor(getResources().getColor(R.color.home_title_color));
//                tv_Pea_bean_pay.setTextSize(10);
                tvBalancePay.setTextColor(getResources().getColor(R.color.home_title_color));
                tvBalancePay.setTextSize(10);
                tvWechatPay.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvWechatPay.setTextSize(10);
                tvAlipay.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvAlipay.setTextSize(10);
                break;
//            case R.id.ll_Pea_bean:
//                payId = Constants.PAY_PEABEAN;
////                ll_bean.setVisibility(View.VISIBLE);
//                ll_frequency.setVisibility(View.GONE);
//                tv_Pea_frequency.setTextColor(getResources().getColor(R.color.home_title_color));
//                tv_Pea_frequency.setTextSize(10);
////                tv_Pea_bean_pay.setTextColor(getResources().getColor(R.color.home_title_color));
////                tv_Pea_bean_pay.setTextSize(12);
//                tvBalancePay.setTextColor(getResources().getColor(R.color.home_title_color));
//                tvBalancePay.setTextSize(10);
//                tvWechatPay.setTextColor(getResources().getColor(R.color.font_color_gray));
//                tvWechatPay.setTextSize(10);
//                tvAlipay.setTextColor(getResources().getColor(R.color.font_color_gray));
//                tvAlipay.setTextSize(10);
//                break;
            case R.id.ll_balance:
                payId = Constants.PAY_BALANCE;
//                ll_bean.setVisibility(View.GONE);
                ll_frequency.setVisibility(View.GONE);
                tvBalancePay.setTextColor(getResources().getColor(R.color.home_title_color));
                tvBalancePay.setTextSize(12);
                tvWechatPay.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvWechatPay.setTextSize(10);
                tvAlipay.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvAlipay.setTextSize(10);
                tv_Pea_frequency.setTextColor(getResources().getColor(R.color.home_title_color));
                tv_Pea_frequency.setTextSize(10);
//                tv_Pea_bean_pay.setTextColor(getResources().getColor(R.color.home_title_color));
//                tv_Pea_bean_pay.setTextSize(10);
                break;
            case R.id.ll_wechat:
                payId = Constants.PAY_WECHAT;
//                ll_bean.setVisibility(View.GONE);
                ll_frequency.setVisibility(View.GONE);
                tvWechatPay.setTextColor(getResources().getColor(R.color.home_title_color));
                tvWechatPay.setTextSize(12);
                tvBalancePay.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvBalancePay.setTextSize(10);
                tvAlipay.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvAlipay.setTextSize(10);
                tv_Pea_frequency.setTextColor(getResources().getColor(R.color.home_title_color));
                tv_Pea_frequency.setTextSize(10);
//                tv_Pea_bean_pay.setTextColor(getResources().getColor(R.color.home_title_color));
//                tv_Pea_bean_pay.setTextSize(10);
                break;
            case R.id.ll_alipay:
                payId = Constants.PAY_ALIPAY;
//                ll_bean.setVisibility(View.GONE);
                ll_frequency.setVisibility(View.GONE);
                tvAlipay.setTextColor(getResources().getColor(R.color.home_title_color));
                tvAlipay.setTextSize(12);
                tvBalancePay.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvBalancePay.setTextSize(10);
                tvWechatPay.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvWechatPay.setTextSize(10);
                tv_Pea_frequency.setTextColor(getResources().getColor(R.color.home_title_color));
                tv_Pea_frequency.setTextSize(10);
//                tv_Pea_bean_pay.setTextColor(getResources().getColor(R.color.home_title_color));
//                tv_Pea_bean_pay.setTextSize(10);
                break;
            case R.id.bt_Refresh:
//                if (payId == Constants.PAY_PEABEAN){
//                    peabeanPresenter.peabean(activityId , "2");
//                }else
                if (payId == Constants.PEA_FREQUENCY){
                    peabeanPresenter.peabean(activityId , "1");
                }else if (payId == Constants.PAY_WECHAT){
                    payOrderPresenter.payOrder("3", "", "4", "", "", activityId);
                }else if (payId == Constants.PAY_BALANCE){
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View v = LayoutInflater.from(activity).inflate(R.layout.popwindow_paymentpassword, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder2.setView(v);
                    final AlertDialog dialog = builder2.show();
                    dialog.getWindow().setLayout(650, LinearLayout.LayoutParams.WRAP_CONTENT);
                    again_paypswd_pet = (PasswordInputView)v.findViewById(R.id.again_paypswd_pet);
                    ll_cancel = (LinearLayout)v.findViewById(R.id.ll_cancel);
                    tv_Determine = (TextView)v.findViewById(R.id.tv_Determine);
                    again_paypswd_pet.setOnChangeListener(this);
                    ImageView iv_back = (ImageView)v.findViewById(R.id.iv_back);
                    iv_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismiss();
                            dialog.dismiss();
                        }
                    });

                    ll_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismiss();
                            dialog.dismiss();
                        }
                    });
                    tv_Determine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payOrderPresenter.payOrder("1", Password, "4", "", "", activityId);
                            dismiss();
                            dialog.dismiss();
                        }
                    });

//                    builder2.show();
                }else {
                    payOrderPresenter.payOrder(payId, "", "4", "", "", activityId);
                }
                break;
            case R.id.bt_vip:
                if (payId == Constants.PAY_BALANCE) {
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(activity);
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View v = LayoutInflater.from(activity).inflate(R.layout.popwindow_paymentpassword, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder3.setView(v);
                    final AlertDialog dialog = builder3.show();
                    dialog.getWindow().setLayout(650, LinearLayout.LayoutParams.WRAP_CONTENT);
                    again_paypswd_pet = (PasswordInputView)v.findViewById(R.id.again_paypswd_pet);
                    ll_cancel = (LinearLayout)v.findViewById(R.id.ll_cancel);
                    tv_Determine = (TextView)v.findViewById(R.id.tv_Determine);
                    again_paypswd_pet.setOnChangeListener(this);

                    ImageView iv_back = (ImageView)v.findViewById(R.id.iv_back);
                    iv_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismiss();
                            dialog.dismiss();
                        }
                    });

                    ll_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogDismiss.dismiss();
                            dialog.dismiss();
                        }
                    });
                    tv_Determine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payOrderPresenter.payOrder(payId, Password, typeId, "", month, "");
                            dialogDismiss.dismiss();
                            dialog.dismiss();
                        }
                    });

//                    builder3.show();
                }else {
                    payOrderPresenter.payOrder(payId, "", typeId, "", month, "");
                }
                break;
            case R.id.bt_Settop:
                if (payId == Constants.PAY_BALANCE) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View v = LayoutInflater.from(activity).inflate(R.layout.popwindow_paymentpassword, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder1.setView(v);
                    final AlertDialog dialog = builder1.show();
                    dialog.getWindow().setLayout(650, LinearLayout.LayoutParams.WRAP_CONTENT);
                    again_paypswd_pet = (PasswordInputView)v.findViewById(R.id.again_paypswd_pet);
                    ll_cancel = (LinearLayout)v.findViewById(R.id.ll_cancel);
                    tv_Determine = (TextView)v.findViewById(R.id.tv_Determine);
                    again_paypswd_pet.setOnChangeListener(this);

                    ImageView iv_back = (ImageView)v.findViewById(R.id.iv_back);
                    iv_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismiss();
                            dialog.dismiss();
                        }
                    });

                    ll_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismiss();
                            dialog.dismiss();
                        }
                    });
                    tv_Determine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payOrderPresenter.payOrder(payId, Password, "5", "", "", activityId);
                            dismiss();
                            dialog.dismiss();
                        }
                    });

//                    builder1.show();
                }else {
                    payOrderPresenter.payOrder(payId, "", "5", "", "", activityId);
                }
                break;
            case R.id.bt_Recharge:
                    String money = (et_a_Recharge.getText().toString());
                    payOrderPresenter.payOrder(payId, "", "1", money + ".00", "", "");

                break;
            case R.id.tv_memo2:
                StartActivityUtil.start(activity, MembercenterActivity.class , MEMBERCENTER);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MEMBERCENTER:
                break;
        }
    }

    @Override
    public void success(PayOrder data) {
        if(Constants.PAY_BALANCE.equals(payId)){
            ToastUtil.showShortToast(context,"支付成功");
        }else if(Constants.PAY_WECHAT.equals(payId)){
            payWechat(data);
        }else {
            payAlibaba(data.getOrder());
        }
    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,data.toString());
        dialogDismiss.dismiss();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"服务器连接异常");
//        dialogDismiss.dismiss();
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context,msg.toString());
        dialogDismiss.dismiss();
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context,msg.toString());
        dialogDismiss.dismiss();
    }

    @Override
    public void getLength(int length) {
        Password = again_paypswd_pet.getText().toString().trim();
    }


    public interface DialogDismiss{
        void dismiss();
    }

    public void setDismissListener(DialogDismiss dismissListener){
        this.dialogDismiss = dismissListener;
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
                        StartActivityUtil.start(getActivity() , MainActivity.class);
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
