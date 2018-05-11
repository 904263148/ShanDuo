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

import com.tencent.qcloud.tlslibrary.helper.Util;
import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.LikePresenter;
import com.yapin.shanduo.ui.activity.MainActivity;
import com.yapin.shanduo.ui.contract.LikeContract;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.FooterLoading;

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
            holder.tvAge.setText(list.get(position).getAge()+"");

            holder.tvMile.setText(list.get(position).getLocation()+"km");
            holder.tvContent.setText(list.get(position).getContent());

            if(TextUtils.isEmpty(Utils.vipLevel(list.get(position).getVip()))){
                holder.tvVip.setVisibility(View.GONE);
            }else {
                holder.tvVip.setText(Utils.vipLevel(list.get(position).getVip()));
            }
            int size = list.get(position).getPicture().size();
            switch (size){
                case 0:
                    holder.rlImg.setVisibility(View.GONE);
                    break;
                case 1:
                    GlideUtil.load(context ,activity ,list.get(position).getPicture().get(0) ,holder.ivImg1 ,5 );
                    holder.ivImg2.setVisibility(View.GONE);
                    holder.ivImg3.setVisibility(View.GONE);
                    break;
                case 2:
                    GlideUtil.load(context ,activity ,list.get(position).getPicture().get(0) ,holder.ivImg1 ,5 );
                    GlideUtil.load(context ,activity ,list.get(position).getPicture().get(1) ,holder.ivImg2 ,5 );
                    holder.ivImg3.setVisibility(View.GONE);
                    break;
                default:
                    GlideUtil.load(context ,activity ,list.get(position).getPicture().get(0) ,holder.ivImg1 ,5 );
                    GlideUtil.load(context ,activity ,list.get(position).getPicture().get(1) ,holder.ivImg2 ,5 );
                    GlideUtil.load(context ,activity ,list.get(position).getPicture().get(2) ,holder.ivImg3 ,5 );
                        break;
            }

            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPopupWindow.openPopupWindow(list.get(position) , Constants.HOME_TREND);
                }
            });

            holder.ivGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(likeClickListener != null)
                        likeClickListener.onLikeClick(list.get(position).getId());
                }
            });

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

    public interface OnLikeClickListener{
        void onLikeClick(String id);
    }

    private OnLikeClickListener likeClickListener;

    public void setLikeClickListener(OnLikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_vip)
        TextView tvVip;
        @BindView(R.id.iv_img1)
        ImageView ivImg1;
        @BindView(R.id.iv_img2)
        ImageView ivImg2;
        @BindView(R.id.iv_img3)
        ImageView ivImg3;
        @BindView(R.id.iv_good)
        ImageView ivGood;
        @BindView(R.id.iv_comment)
        ImageView ivComment;
        @BindView(R.id.iv_trend_forward)
        ImageView ivForward;
        @BindView(R.id.iv_trend_more)
        ImageView ivMore;
        @BindView(R.id.tv_mile)
        TextView tvMile;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rl_img)
        RelativeLayout rlImg;

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