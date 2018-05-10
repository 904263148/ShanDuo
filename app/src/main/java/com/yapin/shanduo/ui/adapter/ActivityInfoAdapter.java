package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.ui.activity.MainActivity;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.widget.FooterLoading;

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
public class ActivityInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private Activity activity;
    private OpenPopupWindow openPopupWindow;
    private List<ActivityInfo.Act> list;

    public ActivityInfoAdapter (Context context , Activity activity , List<ActivityInfo.Act> list){
        this.context = context;
        this.activity = activity;
        this.list = list;
        openPopupWindow = (MainActivity)activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case Constants.TYPE_SHOW:
                view = LayoutInflater.from(context).inflate(R.layout.act_item,parent,false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_footer_loading,parent,false);
                return new FooterHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if(viewHolder instanceof ViewHolder){
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.tvKind.setText(list.get(position).getActivityType());
            holder.tvTime.setText(list.get(position).getActivityStartTime());
            holder.tvType.setText(list.get(position).getMode());
            holder.tvMan.setText("0/"+list.get(position).getManNumber());
            holder.tvWoman.setText("0/"+list.get(position).getWomanNumber());
            holder.tvHost.setText(list.get(position).getUserName());
            holder.tvMemo.setText(list.get(position).getRemarks());
            holder.tvPlace.setText(list.get(position).getActivityAddress());
            holder.tvMile.setText(list.get(position).getLocation()+"km");
            holder.tvEndTime.setText("截止日期:"+list.get(position).getActivityCutoffTime());
            GlideUtil.load(context , activity , list.get(position).getHeadPortraitId() ,holder.ivHead);

            Drawable drawable = null;
            if ("0".equals(list.get(position).getGender())) {
                drawable = activity.getResources().getDrawable(R.drawable.icon_women);
                holder.tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
            } else {
                drawable = activity.getResources().getDrawable(R.drawable.icon_men);
                holder.tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tvAge.setCompoundDrawables(drawable, null, null, null);
            holder.tvAge.setCompoundDrawablePadding(2);
            holder.tvAge.setText(list.get(position).getAge()+"");

            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPopupWindow.openPopupWindow(list.get(position) , Constants.HOME_ACT);
                }
            });
        }else{
            FooterHolder holder = (FooterHolder) viewHolder;
            holder.footerLoading.onLoad(Constants.TYPE_FOOTER_FULL == list.get(position).getType());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
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
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_home_age)
        TextView tvAge;

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

    class FooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footer)
        FooterLoading footerLoading;

        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
