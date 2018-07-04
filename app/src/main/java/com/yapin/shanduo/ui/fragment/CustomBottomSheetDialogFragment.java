package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.ConfirmPresenter;
import com.yapin.shanduo.presenter.DeletedynamicPresenter;
import com.yapin.shanduo.ui.activity.ReportActivity;
import com.yapin.shanduo.ui.contract.ConfirmContract;
import com.yapin.shanduo.ui.contract.DeletedynamicContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 作者：L on 2018/5/28 0028 16:57
 */
public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment implements PlatformActionListener , ConfirmContract.View , DeletedynamicContract.View {

    @BindView(R.id.share_delete)
    TextView tvDelete;

    private View view;

    private String id;
    private int type;

    private Context context;
    private Activity activity;

    private ConfirmPresenter presenter;
    private DeletedynamicPresenter deletedynamicPresenter;

    private int userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = Integer.parseInt(getTag());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.share_popup,null);
        ButterKnife.bind(this,view);
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        presenter = new ConfirmPresenter();
        presenter.init(context , this);
        deletedynamicPresenter = new DeletedynamicPresenter();
        deletedynamicPresenter.init(context , this);
        return view;
    }

    @Override
    public void initView() {
        if(userId == Integer.parseInt(PrefJsonUtil.getProfile(ShanDuoPartyApplication.getContext()).getUserId())){
            tvDelete.setVisibility(View.VISIBLE);
        }else {
            tvDelete.setVisibility(View.INVISIBLE);
        }
    }

    public void setType(int type , String id){
        this.type = type;
        this.id = id;
    }

    @OnClick({R.id.share_wechat_coment , R.id.share_wechat ,R.id.share_qq , R.id.share_friend ,R.id.share_qzone , R.id.share_sina ,R.id.share_report ,R.id.share_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.share_friend:
                break;
            case R.id.share_wechat_coment:
                showShare(WechatMoments.NAME);
                break;
            case R.id.share_wechat:
                showShare(Wechat.NAME);
                break;
            case R.id.share_qq:
                showShare(QQ.NAME);
                break;
            case R.id.share_qzone:
                showShare(QZone.NAME);
                break;
            case R.id.share_sina:
                showShare(SinaWeibo.NAME);
                break;
            case R.id.share_report:
                Bundle bundle = new Bundle();
                String reportType = type == 0 ? Constants.REPORT_ACT : Constants.REPORT_TREND ;
                bundle.putString("typeId" ,reportType);
                bundle.putString("id" , id);
                StartActivityUtil.start(getActivity() , ReportActivity.class ,bundle);
                break;
            case R.id.share_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(type == 0 ? R.string.title_delete_act : R.string.title_delete_trend)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                return;
                            }
                        }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(type == 0){
                            presenter.deleteconfirm(id);
                        }else {
                            deletedynamicPresenter.Deletedynamic(id);
                        }
                    }
                }).create().show();
                break;
        }
    }

    private void showShare(String name) {
        Platform.ShareParams sp = new Platform.ShareParams();

        if(name.equals(Wechat.NAME)){
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setText("闪多");
        }

        String url ;

        if(type == 0){
            url = Constants.ACT_SHARE_URL + id;
        }else {
            url = Constants.TREND_SHARE_URL + id;
        }

        Log.d("share_url" , url);

        sp.setTitle("闪多");
        sp.setTitleUrl(url); // 标题的超链接
        sp.setText("闪多");
        sp.setImageUrl(url);
        sp.setSite("闪多");
        sp.setSiteUrl(url);
        sp.setUrl(url);

        Platform platform = ShareSDK.getPlatform(name);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        platform.setPlatformActionListener(this);
        // 执行图文分享
        platform.share(sp);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
        Log.d("shareSDK" , hashMap.toString());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        //分享成功的回调
        Log.d("shareSDK" , throwable.toString());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        //取消分享的回调
        Log.d("shareSDK" , "取消了~");
    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context , data);
        Intent intent;
        if(type == 0){
            //注册广播
            intent = new Intent("actDeleteComplete");
        }else {
            //注册广播
            intent = new Intent("trendDeleteComplete");
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        dismiss();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

}