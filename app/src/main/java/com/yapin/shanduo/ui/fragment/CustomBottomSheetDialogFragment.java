package com.yapin.shanduo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;

import java.util.HashMap;

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
public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment implements PlatformActionListener{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.share_popup,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({R.id.share_wechat_coment , R.id.share_wechat ,R.id.share_qq , R.id.share_friend ,R.id.share_qzone , R.id.share_sina ,R.id.share_report })
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
                break;
        }
    }

    private void showShare(String name) {
        Platform.ShareParams sp = new Platform.ShareParams();

        if(name.equals(Wechat.NAME)){
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setText("闪多测试。");
        }

        sp.setTitle("测试分享的标题");
        sp.setTitleUrl("http://www.baidu.com"); // 标题的超链接
        sp.setText("测试分享的文本");
        sp.setImageUrl("http://imgtu.5011.net/uploads/content/20170209/4934501486627131.jpg");
        sp.setSite("闪多");
        sp.setSiteUrl("http://www.baidu.com");
        sp.setUrl("http://www.baidu.com");

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
}