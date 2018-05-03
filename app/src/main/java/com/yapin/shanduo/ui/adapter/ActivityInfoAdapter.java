package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.ui.activity.MainActivity;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 作者：L on 2018/4/26 0026 14:44
 */
public class ActivityInfoAdapter extends RecyclerView.Adapter<ActivityInfoAdapter.ViewHolder>{

    private Context context;
    private Activity activity;
    private OpenPopupWindow openPopupWindow;

    public ActivityInfoAdapter (Context context , Activity activity){
        this.context = context;
        this.activity = activity;
        openPopupWindow = (MainActivity)activity;
    }

    @NonNull
    @Override
    public ActivityInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_item ,parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityInfoAdapter.ViewHolder holder, int position) {
        holder.tvKind.setText("类别:唱歌");

        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopupWindow.openPopupWindow();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private ActivityInfoAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ActivityInfoAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_kind)
        TextView tvKind;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_man_count)
        TextView tvMan;
        @BindView(R.id.tv_woman_count)
        TextView tvWoman;
        @BindView(R.id.tv_host)
        TextView tvHost;
        @BindView(R.id.tv_memo)
        TextView tvMemo;
        @BindView(R.id.tv_place)
        TextView tvPlace;
        @BindView(R.id.tv_mile)
        TextView tvMile;
        @BindView(R.id.tv_end_time)
        TextView tvEndTime;
        @BindView(R.id.iv_more)
        ImageView ivMore;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(getLayoutPosition());
                }
            });

            ButterKnife.bind(this , itemView);
        }
    }


    private void showShare() {
/*        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);*/


        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();

        oks.setShareContentCustomizeCallback(new ShareCustomize());
        //oks.setVenueName("Tomorrow");
        //oks.setVenueDescription(bean.getProduct().getDescription());
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        // oks.setCallback(new OneKeyShareCallback());
        // 去自定义不同平台的字段内容
        // oks.setShareContentCustomizeCallback(new
        // ShareContentCustomizeDemo());
        // 在九宫格设置自定义的图标
        /*Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        String label = "Tomorrow";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                ToastUtil.showShortToast(context, "Twitter");
            }
        };
        oks.setCustomerLogo(logo, label, listener);

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        oks.addHiddenPlatform(Twitter.NAME);*/
//        oks.addHiddenPlatform(Douban.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }

    private class ShareCustomize implements ShareContentCustomizeCallback {

        @Override
        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
//            share_url = "http://qrproduct.tomorrow.cn.com/?uid="+bean.getSeller().getSellerUserId()+"&pid="+bean.getProduct().getId();
//            if (Wechat.NAME.equals(platform.getName())
//                    || WechatMoments.NAME.equals(platform.getName())
//                    || WechatFavorite.NAME.equals(platform.getName())) {
//                paramsToShare.setText(bean.getProduct().getName());
//                paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//            } else {
//                paramsToShare.setText(bean.getProduct().getName());
//            }
//
//            paramsToShare.setTitle(bean.getProduct().getName());
//
//            paramsToShare.setTitleUrl(ConfigUtil.SHARE_URL + bean.getProduct().getId());
//
//            List<String> list = Utils.getIdList(bean.getProduct().getShowPicture());
//            paramsToShare.setImageUrl(list == null || list.isEmpty() ? "" : Utils.getImagePath(list.get(0)));
//
//            paramsToShare.setUrl(share_url);
//
//            paramsToShare.setSite("Tomorrow");  //QZone分享完之后返回应用时提示框上显示的名称
//
//            paramsToShare.setSiteUrl(share_url);

            paramsToShare.setComment("");
        }
    }

}
