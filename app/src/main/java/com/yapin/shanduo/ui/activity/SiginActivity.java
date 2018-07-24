package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.SigninInfo;
import com.yapin.shanduo.model.entity.TaskInfo;
import com.yapin.shanduo.presenter.CheckcheckinPresenter;
import com.yapin.shanduo.presenter.SigninPresenter;
import com.yapin.shanduo.presenter.TaskPresenter;
import com.yapin.shanduo.ui.contract.CheckcheckinContract;
import com.yapin.shanduo.ui.contract.SigninContract;
import com.yapin.shanduo.ui.contract.TaskContract;
import com.yapin.shanduo.ui.fragment.CustomBottomSheetDialogFragment;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/6/21.
 */

public class SiginActivity extends RightSlidingActivity implements SigninContract.View ,CheckcheckinContract.View ,
        TaskContract.View{

    private SigninPresenter presenter;
    private CheckcheckinPresenter checkcheckinPresenter;
    private TaskPresenter taskPresenter;
    private Context context;
    private Activity activity;

    private SigninInfo signinInfo = new SigninInfo();
    private TaskInfo taskInfo = new TaskInfo();

    private CustomBottomSheetDialogFragment fragment;

    @BindView(R.id.tv_sigin_Title)
    TextView tv_sigin_Title;
    @BindView(R.id.ll_sigina)
    LinearLayout ll_sigina;
    @BindView(R.id.tv_sigin_todaya)
    TextView tv_sigin_todaya;
    @BindView(R.id.tv_sigin_redenvelopesa)
    TextView tv_sigin_redenvelopesa;
    @BindView(R.id.tv_sigin_Alreadyreceiveda)
    TextView tv_sigin_Alreadyreceiveda;
    @BindView(R.id.ll_siginb)
    LinearLayout ll_siginb;
    @BindView(R.id.tv_sigin_todayb)
    TextView tv_sigin_todayb;
    @BindView(R.id.tv_sigin_redenvelopesb)
    TextView tv_sigin_redenvelopesb;
    @BindView(R.id.tv_sigin_Alreadyreceivedb)
    TextView tv_sigin_Alreadyreceivedb;
    @BindView(R.id.ll_siginc)
    LinearLayout ll_siginc;
    @BindView(R.id.tv_sigin_todayc)
    TextView tv_sigin_todayc;
    @BindView(R.id.tv_sigin_redenvelopesc)
    TextView tv_sigin_redenvelopesc;
    @BindView(R.id.tv_sigin_Alreadyreceivedc)
    TextView tv_sigin_Alreadyreceivedc;
    @BindView(R.id.ll_sigind)
    LinearLayout ll_sigind;
    @BindView(R.id.tv_sigin_todayd)
    TextView tv_sigin_todayd;
    @BindView(R.id.tv_sigin_redenvelopesd)
    TextView tv_sigin_redenvelopesd;
    @BindView(R.id.tv_sigin_Alreadyreceivedd)
    TextView tv_sigin_Alreadyreceivedd;
    @BindView(R.id.ll_sigine)
    LinearLayout ll_sigine;
    @BindView(R.id.tv_sigin_todaye)
    TextView tv_sigin_todaye;
    @BindView(R.id.tv_sigin_redenvelopese)
    TextView tv_sigin_redenvelopese;
    @BindView(R.id.tv_sigin_Alreadyreceivede)
    TextView tv_sigin_Alreadyreceivede;
    @BindView(R.id.ll_siginf)
    LinearLayout ll_siginf;
    @BindView(R.id.tv_sigin_todayf)
    TextView tv_sigin_todayf;
    @BindView(R.id.tv_sigin_redenvelopesf)
    TextView tv_sigin_redenvelopesf;
    @BindView(R.id.tv_sigin_Alreadyreceivedf)
    TextView tv_sigin_Alreadyreceivedf;
    @BindView(R.id.ll_siging)
    LinearLayout ll_siging;
    @BindView(R.id.tv_sigin_todayg)
    TextView tv_sigin_todayg;
    @BindView(R.id.tv_sigin_redenvelopesg)
    TextView tv_sigin_redenvelopesg;
    @BindView(R.id.tv_sigin_Alreadyreceivedg)
    TextView tv_sigin_Alreadyreceivedg;

    @BindView(R.id.tv_sigin_Releasetimes)
    TextView tv_sigin_Releasetimes;
    @BindView(R.id.tv_Gotorelease)
    TextView tv_Gotorelease;
    @BindView(R.id.tv_Numberofparticipants)
    TextView tv_Numberofparticipants;
    @BindView(R.id.tv_Gotosignup)
    TextView tv_Gotosignup;
    @BindView(R.id.tv_Toshare)
    TextView tv_Toshare;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this );

        presenter = new SigninPresenter();
        presenter.init(context,this);

        checkcheckinPresenter = new CheckcheckinPresenter();
        checkcheckinPresenter.init(context,this);

        taskPresenter = new TaskPresenter();
        taskPresenter.init(context,this);

        fragment = new CustomBottomSheetDialogFragment();

        presenter.getsignin();
        taskPresenter.gettask();

    }

    @OnClick({R.id.ll_sigina ,R.id.ll_siginb ,R.id.ll_siginc ,R.id.ll_sigind ,R.id.ll_sigine ,
            R.id.ll_siginf ,R.id.ll_siging ,R.id.tv_Gotorelease , R.id.tv_Gotosignup ,R.id.tv_Toshare})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_sigina:
                checkcheckinPresenter.setcheck();
                break;
            case R.id.ll_siginb:
                checkcheckinPresenter.setcheck();
                break;
            case R.id.ll_siginc:
                checkcheckinPresenter.setcheck();
                break;
            case R.id.ll_sigind:
                checkcheckinPresenter.setcheck();
                break;
            case R.id.ll_sigine:
                checkcheckinPresenter.setcheck();
                break;
            case R.id.ll_siginf:
                checkcheckinPresenter.setcheck();
                break;
            case R.id.ll_siging:
                checkcheckinPresenter.setcheck();
                break;
            case R.id.tv_Gotorelease:
                StartActivityUtil.start(activity, AddactivityActivity.class);
                finish();
                break;
            case R.id.tv_Gotosignup:
                StartActivityUtil.start(activity, MainActivity.class);
                finish();
                break;
            case R.id.tv_Toshare:
//                fragment.show(getSupportFragmentManager() , null);
//                fragment.setType(2 , null);
                break;
        }
    }

    @Override
    public void initView() {

    }

    public void initSignin(){
        if (signinInfo.getCount() == 1) {
                ll_siginb.setOnClickListener(null); //设置不可点击
                ll_siginc.setOnClickListener(null); //设置不可点击
                ll_sigind.setOnClickListener(null); //设置不可点击
                ll_sigine.setOnClickListener(null); //设置不可点击
                ll_siginf.setOnClickListener(null); //设置不可点击
                ll_siging.setOnClickListener(null); //设置不可点击
            if (signinInfo.isSignin() == false) {
                tv_sigin_Title.setText("签到可领取58闪多豆");
                tv_sigin_todaya.setText("签到可领");
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
            }else if (signinInfo.isSignin() == true){
                tv_sigin_Title.setText("明天可以领取128闪多豆");
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
                tv_sigin_redenvelopesa.setBackgroundResource(R.drawable.icon_alreadyreceived);
                tv_sigin_Alreadyreceiveda.setText("已领取");
                ll_sigina.setOnClickListener(null); //设置不可点击
            }
        }else if (signinInfo.getCount() == 2) {
                ll_sigina.setOnClickListener(null); //设置不可点击
                ll_siginc.setOnClickListener(null); //设置不可点击
                ll_sigind.setOnClickListener(null); //设置不可点击
                ll_sigine.setOnClickListener(null); //设置不可点击
                ll_siginf.setOnClickListener(null); //设置不可点击
                ll_siging.setOnClickListener(null); //设置不可点击
                ll_sigina.setOnClickListener(null); //设置不可点击
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_redenvelopesa.setBackgroundResource(R.drawable.icon_alreadyreceived);
                tv_sigin_Alreadyreceiveda.setText("已领取");
            if (signinInfo.isSignin() == false) {
                tv_sigin_Title.setText("签到可领取128闪多豆");
                tv_sigin_todayb.setText("签到可领");
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
            }else if (signinInfo.isSignin() == true){
                tv_sigin_Title.setText("明天可以领取88闪多豆");
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
                tv_sigin_redenvelopesb.setBackgroundResource(R.drawable.icon_alreadyreceived);
                tv_sigin_Alreadyreceivedb.setText("已领取");
                ll_siginb.setOnClickListener(null); //设置不可点击
            }
        }else if (signinInfo.getCount() == 3) {
            if (signinInfo.isSignin() == false) {
                tv_sigin_Title.setText("签到可领取88闪多豆");
                tv_sigin_todayc.setText("签到可领");
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
            }else if (signinInfo.isSignin() == true){
                tv_sigin_Title.setText("明天可以领取88闪多豆");
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
                tv_sigin_redenvelopesc.setBackgroundResource(R.drawable.icon_alreadyreceived);
                tv_sigin_Alreadyreceivedc.setText("已领取");
                ll_siginc.setOnClickListener(null); //设置不可点击
            }
            tv_sigin_todaya.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesa.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceiveda.setText("已领取");
            tv_sigin_todayb.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesb.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedb.setText("已领取");
            ll_siginb.setOnClickListener(null); //设置不可点击
            ll_sigina.setOnClickListener(null); //设置不可点击
            ll_sigind.setOnClickListener(null); //设置不可点击
            ll_sigine.setOnClickListener(null); //设置不可点击
            ll_siginf.setOnClickListener(null); //设置不可点击
            ll_siging.setOnClickListener(null); //设置不可点击
        }else if (signinInfo.getCount() == 4) {
            if (signinInfo.isSignin() == false) {
                tv_sigin_Title.setText("签到可领取88闪多豆");
                tv_sigin_todayd.setText("签到可领");
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
            }else if (signinInfo.isSignin() == true){
                tv_sigin_Title.setText("明天可以领取88闪多豆");
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
                tv_sigin_redenvelopesd.setBackgroundResource(R.drawable.icon_alreadyreceived);
                tv_sigin_Alreadyreceivedd.setText("已领取");
                ll_sigind.setOnClickListener(null); //设置不可点击
            }
            tv_sigin_todaya.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesa.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceiveda.setText("已领取");
            tv_sigin_todayb.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesb.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedb.setText("已领取");
            tv_sigin_redenvelopesc.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedc.setText("已领取");
            ll_siginb.setOnClickListener(null); //设置不可点击
            ll_siginc.setOnClickListener(null); //设置不可点击
            ll_sigina.setOnClickListener(null); //设置不可点击
            ll_sigine.setOnClickListener(null); //设置不可点击
            ll_siginf.setOnClickListener(null); //设置不可点击
            ll_siging.setOnClickListener(null); //设置不可点击
        }else if (signinInfo.getCount() == 5) {
            if (signinInfo.isSignin() == false) {
                tv_sigin_Title.setText("签到可领取88闪多豆");
                tv_sigin_todaye.setText("签到可领");
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
            }else if (signinInfo.isSignin() == true){
                tv_sigin_Title.setText("明天可以领取88闪多豆");
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
                tv_sigin_redenvelopese.setBackgroundResource(R.drawable.icon_alreadyreceived);
                tv_sigin_Alreadyreceivede.setText("已领取");
                ll_sigine.setOnClickListener(null); //设置不可点击
            }
            tv_sigin_todaya.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesa.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceiveda.setText("已领取");
            tv_sigin_todayb.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesb.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedb.setText("已领取");
            tv_sigin_redenvelopesc.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedc.setText("已领取");
            tv_sigin_redenvelopesd.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedd.setText("已领取");
            ll_siginb.setOnClickListener(null); //设置不可点击
            ll_siginc.setOnClickListener(null); //设置不可点击
            ll_sigind.setOnClickListener(null); //设置不可点击
            ll_sigina.setOnClickListener(null); //设置不可点击
            ll_siginf.setOnClickListener(null); //设置不可点击
            ll_siging.setOnClickListener(null); //设置不可点击
        }else if (signinInfo.getCount() == 6) {
            if (signinInfo.isSignin() == false) {
                tv_sigin_Title.setText("签到可领取88闪多豆");
                tv_sigin_todayf.setText("签到可领");
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
            }else if (signinInfo.isSignin() == true){
                tv_sigin_Title.setText("明天可以领取88闪多豆");
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_redenvelopesf.setBackgroundResource(R.drawable.icon_alreadyreceived);
                tv_sigin_Alreadyreceivedf.setText("已领取");
                ll_siginf.setOnClickListener(null); //设置不可点击
            }
            tv_sigin_todaya.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesa.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceiveda.setText("已领取");
            tv_sigin_todayb.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesb.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedb.setText("已领取");
            tv_sigin_redenvelopesc.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedc.setText("已领取");
            tv_sigin_redenvelopesd.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedd.setText("已领取");
            tv_sigin_redenvelopese.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivede.setText("已领取");
            ll_siginb.setOnClickListener(null); //设置不可点击
            ll_siginc.setOnClickListener(null); //设置不可点击
            ll_sigind.setOnClickListener(null); //设置不可点击
            ll_sigine.setOnClickListener(null); //设置不可点击
            ll_sigina.setOnClickListener(null); //设置不可点击
            ll_siging.setOnClickListener(null); //设置不可点击
        }else if (signinInfo.getCount() == 7) {
            if (signinInfo.isSignin() == false) {
                tv_sigin_Title.setText("签到可领取88闪多豆");
                tv_sigin_todayg.setText("签到可领");
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
            }else if (signinInfo.isSignin() == true){
                tv_sigin_Title.setText("明天可以领取88闪多豆");
                tv_sigin_todayg.setVisibility(View.INVISIBLE);
                tv_sigin_todaya.setVisibility(View.INVISIBLE);
                tv_sigin_todayb.setVisibility(View.INVISIBLE);
                tv_sigin_todayd.setVisibility(View.INVISIBLE);
                tv_sigin_todaye.setVisibility(View.INVISIBLE);
                tv_sigin_todayf.setVisibility(View.INVISIBLE);
                tv_sigin_todayc.setVisibility(View.INVISIBLE);
                tv_sigin_redenvelopesg.setBackgroundResource(R.drawable.icon_alreadyreceived);
                tv_sigin_Alreadyreceivedg.setText("已领取");
                ll_siging.setOnClickListener(null); //设置不可点击
            }
            tv_sigin_todaya.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesa.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceiveda.setText("已领取");
            tv_sigin_todayb.setVisibility(View.INVISIBLE);
            tv_sigin_redenvelopesb.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedb.setText("已领取");
            tv_sigin_redenvelopesc.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedc.setText("已领取");
            tv_sigin_redenvelopesd.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedd.setText("已领取");
            tv_sigin_redenvelopese.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivede.setText("已领取");
            tv_sigin_redenvelopesf.setBackgroundResource(R.drawable.icon_alreadyreceived);
            tv_sigin_Alreadyreceivedf.setText("已领取");
            ll_siginb.setOnClickListener(null); //设置不可点击
            ll_siginc.setOnClickListener(null); //设置不可点击
            ll_sigind.setOnClickListener(null); //设置不可点击
            ll_sigine.setOnClickListener(null); //设置不可点击
            ll_siginf.setOnClickListener(null); //设置不可点击
            ll_sigina.setOnClickListener(null); //设置不可点击
        }
    }

    public void inittask(){
        tv_sigin_Releasetimes.setText(taskInfo.getRelease()+"/2");
        tv_Numberofparticipants.setText(taskInfo.getJoin()+"/3");
    }

    @Override
    public void success(SigninInfo data) {
        signinInfo = data;
        initSignin();
//        presenter.getsignin();
    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,data);
        signinInfo.setSignin(true);
        initSignin();
    }

    @Override
    public void success(TaskInfo data) {
        taskInfo = data;
        inittask();
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

}
