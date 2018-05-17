package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.ui.activity.MainActivity;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.FooterLoading;
import com.yapin.shanduo.widget.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/5/9 0009 11:16
 */
public class TrendInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TrendInfo.Trend> list;
    private OpenPopupWindow openPopupWindow;
    private Activity activity;

    private TrendGridViewAdapter adapter;

    public TrendInfoAdapter(Context context, Activity activity, List<TrendInfo.Trend> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        openPopupWindow = (MainActivity) activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.TYPE_SHOW:
                view = LayoutInflater.from(context).inflate(R.layout.item_trend_layout, parent, false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            GlideUtil.load(context, activity, list.get(position).getPortraitId(), holder.ivHead);
            holder.tvName.setText(list.get(position).getName());

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
            holder.tvAge.setText(list.get(position).getAge() + "");

            holder.tvMile.setText(list.get(position).getDistance() + "km");
            holder.tvContent.setText(list.get(position).getContent());

            holder.tvRelayCount.setText(list.get(position).getDynamicCount()+"");
            holder.tvLikeCount.setText(list.get(position).getPraise()+"");

            if (TextUtils.isEmpty(Utils.vipLevel(list.get(position).getVip()))) {
                holder.tvVip.setVisibility(View.GONE);
            } else {
                holder.tvVip.setText(Utils.vipLevel(list.get(position).getVip()));

            }
            holder.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPopupWindow.openPopupWindow(list.get(position), Constants.HOME_TREND);
                }
            });

            holder.tvLikeCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (likeClickListener != null)
                        likeClickListener.onLikeClick(list.get(position).getId());
                }
            });

            int size = list.get(position).getPicture().size();
            switch (size){
                case 0:
                    holder.gridview.setVisibility(View.GONE);
                    holder.rlImg1.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.gridview.setVisibility(View.GONE);
                    holder.rlImg1.setVisibility(View.VISIBLE);
                    GlideUtil.load(context ,activity , ApiUtil.IMG_URL+list.get(position).getPicture().get(0) ,holder.ivImg1 ,5 );
                    holder.ivImg2.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.gridview.setVisibility(View.GONE);
                    holder.rlImg1.setVisibility(View.VISIBLE);
                    GlideUtil.load(context ,activity ,ApiUtil.IMG_URL+list.get(position).getPicture().get(0) ,holder.ivImg1 ,5 );
                    GlideUtil.load(context ,activity ,ApiUtil.IMG_URL+list.get(position).getPicture().get(1) ,holder.ivImg2 ,5 );
                    break;
                default:
                    holder.gridview.setVisibility(View.VISIBLE);
                    holder.rlImg1.setVisibility(View.GONE);
                    adapter = new TrendGridViewAdapter(context, list.get(position).getPicture(), activity);
                    holder.gridview.setAdapter(adapter);
                    break;
            }

        } else {
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

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnLikeClickListener {
        void onLikeClick(String id);
    }

    private OnLikeClickListener likeClickListener;

    public void setLikeClickListener(OnLikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_vip)
        TextView tvVip;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_mile)
        TextView tvMile;
        @BindView(R.id.rl_user_info)
        RelativeLayout rlUserInfo;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.gridview)
        MyGridView gridview;
        @BindView(R.id.tv_publish_time)
        TextView tvPublishTime;
        @BindView(R.id.rl_trend_info)
        RelativeLayout rlTrendInfo;
        @BindView(R.id.iv_share)
        ImageView ivShare;
        @BindView(R.id.iv_img1)
        ImageView ivImg1;
        @BindView(R.id.iv_img2)
        ImageView ivImg2;
        @BindView(R.id.rl_img1)
        RelativeLayout rlImg1;
        @BindView(R.id.tv_replay_count)
        TextView tvRelayCount;
        @BindView(R.id.tv_like_count)
        TextView tvLikeCount;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(getLayoutPosition());
                }
            });

            ButterKnife.bind(this, itemView);
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