package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.CreditItem;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.widget.FooterLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/6/1 0001 10:31
 */
public class CreditDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<CreditItem> list;
    private int type;

    public CreditDetailAdapter(Context context, Activity activity, List<CreditItem> list , int type) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        this.type = type;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.TYPE_TOP:
                view = LayoutInflater.from(context).inflate(R.layout.credit_top_item, parent, false);
                return new ViewHolder(view);
            case Constants.TYPE_BOTTOM:
                view = LayoutInflater.from(context).inflate(R.layout.credit_bottom_item, parent, false);
                return new PresenterHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CreditItem item = list.get(position);
        if(holder instanceof ViewHolder){
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvName.setText(item.getUser_name());
            GlideUtil.load(context , activity , ApiUtil.IMG_URL+item.getHead_portrait_id() , viewHolder.ivHead);
            if(type == 0){
                viewHolder.tvHidden.setVisibility(View.GONE);
                viewHolder.tvContent.setTextColor(activity.getResources().getColor(R.color.home_title_color));
            }else {
                viewHolder.tvHidden.setVisibility(View.VISIBLE);
                viewHolder.tvContent.setTextColor(activity.getResources().getColor(R.color.font_color_gray));
            }
            viewHolder.tvContent.setText(item.getEvaluation_content());
            switch (item.getScore()){
                case 1:
                    viewHolder.ivHeart2.setBackgroundResource(R.drawable.icon_heart_gray);
                    viewHolder.ivHeart3.setBackgroundResource(R.drawable.icon_heart_gray);
                    viewHolder.ivHeart4.setBackgroundResource(R.drawable.icon_heart_gray);
                    viewHolder.ivHeart5.setBackgroundResource(R.drawable.icon_heart_gray);
                    break;
                case 2:
                    viewHolder.ivHeart2.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart3.setBackgroundResource(R.drawable.icon_heart_gray);
                    viewHolder.ivHeart4.setBackgroundResource(R.drawable.icon_heart_gray);
                    viewHolder.ivHeart5.setBackgroundResource(R.drawable.icon_heart_gray);
                    break;
                case 3:
                    viewHolder.ivHeart2.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart3.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart4.setBackgroundResource(R.drawable.icon_heart_gray);
                    viewHolder.ivHeart5.setBackgroundResource(R.drawable.icon_heart_gray);
                    break;
                case 4:
                    viewHolder.ivHeart2.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart3.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart4.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart5.setBackgroundResource(R.drawable.icon_heart_gray);
                    break;
                case 5:viewHolder.ivHeart2.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart3.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart4.setBackgroundResource(R.drawable.icon_heart_red);
                    viewHolder.ivHeart5.setBackgroundResource(R.drawable.icon_heart_red);
                    break;
            }

        }else if (holder instanceof PresenterHolder){
            PresenterHolder presenterHolder = (PresenterHolder) holder;
            presenterHolder.tvName.setText(item.getPresenter_name());
            GlideUtil.load(context , activity , ApiUtil.IMG_URL+item.getHead_portrait_id() , presenterHolder.ivHead);
            presenterHolder.tvMode.setText(item.getMode());
            presenterHolder.tvActName.setText(item.getActivity_name());
            int level = item.getVipGrade();
            if(level == 0){
                presenterHolder.tvVip.setVisibility(View.GONE);
            }else if(level > 0 && level < 9){
                presenterHolder.tvVip.setVisibility(View.VISIBLE);
                presenterHolder.tvVip.setText("VIP"+level);
                presenterHolder.tvVip.setBackgroundResource(R.drawable.rounded_tv_vip);
            }else {
                presenterHolder.tvVip.setVisibility(View.VISIBLE);
                presenterHolder.tvVip.setText("SVIP"+(level-10));
                presenterHolder.tvVip.setBackgroundResource(R.drawable.rounded_tv_svip);
            }
            Drawable drawable = null;
            if ("0".equals(item.getGender())) {
                drawable = activity.getResources().getDrawable(R.drawable.icon_women);
                presenterHolder.tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
            } else {
                drawable = activity.getResources().getDrawable(R.drawable.icon_men);
                presenterHolder.tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            presenterHolder.tvHomeAge.setCompoundDrawables(drawable, null, null, null);
            presenterHolder.tvHomeAge.setCompoundDrawablePadding(2);
            presenterHolder.tvHomeAge.setText(item.getBirthday() + "");
        }else if (holder instanceof FooterHolder) {
            FooterHolder footerHolder = (FooterHolder) holder;
            footerHolder.footerLoading.onLoad(Constants.TYPE_FOOTER_FULL == list.get(position).getType());
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
        void onItemClick(int position, int type);

        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_heart1)
        ImageView ivHeart1;
        @BindView(R.id.iv_heart2)
        ImageView ivHeart2;
        @BindView(R.id.iv_heart3)
        ImageView ivHeart3;
        @BindView(R.id.iv_heart4)
        ImageView ivHeart4;
        @BindView(R.id.iv_heart5)
        ImageView ivHeart5;
        @BindView(R.id.ll_heart)
        LinearLayout llHeart;
        @BindView(R.id.tv_hidden)
        TextView tvHidden;
        @BindView(R.id.rl_tag)
        RelativeLayout rlTag;
        @BindView(R.id.tv_content)
        TextView tvContent;
        
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());
                }
            });
        }
    }

    class PresenterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_vip)
        TextView tvVip;
        @BindView(R.id.tv_home_age)
        TextView tvHomeAge;
        @BindView(R.id.tv_act_name)
        TextView tvActName;
        @BindView(R.id.tv_mode)
        TextView tvMode;
        @BindView(R.id.rl_user_info)
        RelativeLayout rlUserInfo;

        PresenterHolder(View itemView) {
            super(itemView);
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
