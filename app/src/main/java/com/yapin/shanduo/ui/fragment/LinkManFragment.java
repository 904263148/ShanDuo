package com.yapin.shanduo.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.auth.AlipaySDK;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.PayResult;
import com.yapin.shanduo.ui.activity.AddHumanGroupActivity;
import com.yapin.shanduo.ui.adapter.LinkTabAdapter;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkManFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private Context context;
    private Activity activity;

    private LinkTabAdapter adapter;

    private static final int SDK_PAY_FLAG = 1;

    public static LinkManFragment newInstance() {
        LinkManFragment fragment = new LinkManFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_linkman_layout,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        List<String> tabList = new ArrayList<>();
        tabList.add("我的好友");
        tabList.add("我的群组");

        viewPager.setOffscreenPageLimit(2);
        adapter = new LinkTabAdapter(getChildFragmentManager(), tabList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 55 , 55);
            }
        });
    }

    private String orderParam = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018011901973829&biz_content=%7B%22body%22%3A%22%E5%BC%80%E9%80%9A%E9%97%AA%E5%A4%9AVIP12%E4%B8%AA%E6%9C%88%22%2C%22out_trade_no%22%3A%223115e6a849f94af3875d0d29ed34bba3%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%BC%80%E9%80%9A%E9%97%AA%E5%A4%9AVIP12%E4%B8%AA%E6%9C%88105.60%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22105.60%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fyapinkeji.com%2Fshanduoparty%2Fjpay%2Falipay&sign=WFqRKp7R0tsKMisxodMqnfPpOSHp64FEYdq1ZO4efTHTVkNAy2PHqa9%2F%2FG5t3Msf%2FXH9m3P4WpCNHptWImnbT0tf%2BO%2Fv1G0evgRIi6%2FxXAgyfm8eHEl6B7dJi9tA4AxYVhmFYSAxHRys%2Fk3rcHn%2FL2r8VRxv8UcjbGcICOKimbHMUHwNyeXbAJavwXJ%2BcrpNLpteZyNWAuab%2B0Cv7Gio4AdhWGmjXDausHIrr68p3jz3PwBH3x8DFA1arcTk5dzEyW0Z6jh1APxdwa4OZ0IiVXF5AaVIVRqHc74zfuMMZ%2BxA5GqvCtQA6vdaYhOn969G1lLDJgps5m7Wh76ngOE1FQ%3D%3D&sign_type=RSA2&timestamp=2018-05-22+11%3A07%3A50&version=1.0";

    @OnClick({R.id.iv_add_friend , R.id.ll_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_add_friend:
                StartActivityUtil.start(activity , this , AddHumanGroupActivity.class);
                break;
            case R.id.ll_search:

                break;
        }
    }

    public void payAlibaba(){
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

    public void payWechat(){
        final IWXAPI api = WXAPIFactory.createWXAPI(activity, null);
        api.registerApp(Constants.WECHAT_APPID);
        Runnable payRunnable1 = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                PayReq req = new PayReq();
                req.appId = Constants.WECHAT_APPID;
                req.partnerId = "";
                req.prepayId= "1101000000140415649af9fc314aa427";
                req.packageValue = "Sign=WXPay";
                req.nonceStr= "1101000000140429eb40476f8896f4c9";
                req.timeStamp= "1398746574";
                req.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
                api.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread1 = new Thread(payRunnable1);
        payThread1.start();
        Log.d("wechat","点击了");
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

}
