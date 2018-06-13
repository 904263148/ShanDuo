package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.entity.SecondComment;
import com.yapin.shanduo.ui.activity.MainActivity;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.TimeUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.FooterLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/5/16 0016 17:11
 */
public class TrendCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<CommentInfo.Comment> list;

    public TrendCommentAdapter(Context context, Activity activity, List<CommentInfo.Comment> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.TYPE_SHOW:
                view = LayoutInflater.from(context).inflate(R.layout.trend_comment_item, parent, false);
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

            GlideUtil.load(context ,activity , ApiUtil.IMG_URL+list.get(position).getPortraitId() , holder.ivHead);

            holder.tvName.setText( Utils.unicodeToString(list.get(position).getName()) );
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
            holder.tvAge.setText(list.get(position).getAge() +"");

            holder.tvOneComment.setText(Utils.unicodeToString(list.get(position).getComment()));

            List<SecondComment.Comments> commentList = list.get(position).getComments();
            if(list.get(position).getCount() == 0){
                holder.llTwoReplay.setVisibility(View.GONE);
            }else if (list.get(position).getCount() == 1) {
                holder.llTwoReplay.setVisibility(View.VISIBLE);
                holder.tvTwoReplay1.setText(Html.fromHtml("<font color = '#4660ff'>" + commentList.get(0).getUserName() +":</font>" +"<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(0).getComment())+"</font>"));
                holder.tvTwoReplay2.setVisibility(View.GONE);
                holder.tvTwoReplay3.setVisibility(View.GONE);
                holder.tvReplayCount.setVisibility(View.GONE);
            }else if(list.get(position).getCount() == 2){
                holder.llTwoReplay.setVisibility(View.VISIBLE);
                holder.tvTwoReplay1.setText(Html.fromHtml("<font color = '#4660ff'>" + commentList.get(0).getUserName() +":</font>" + "<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(0).getComment())+"</font>"));
                holder.tvTwoReplay2.setText(Html.fromHtml("<font color = '#4660ff'>" + commentList.get(1).getUserName() +":</font>" + "<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(1).getComment())+"</font>"));
                holder.tvTwoReplay3.setVisibility(View.GONE);
                holder.tvReplayCount.setVisibility(View.GONE);
            }else if(list.get(position).getCount() == 3){
                holder.llTwoReplay.setVisibility(View.VISIBLE);
                holder.tvTwoReplay1.setText(Html.fromHtml("<font color ='#4660ff'>" + commentList.get(0).getUserName() +":</font>"  + "<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(0).getComment())+"</font>"));
                holder.tvTwoReplay2.setText(Html.fromHtml("<font color = '#4660ff'>" + commentList.get(1).getUserName() +":</font>" + "<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(1).getComment())+"</font>"));
                holder.tvTwoReplay3.setText(Html.fromHtml("<font color = '#4660ff'>" + commentList.get(2).getUserName() +":</font>" + "<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(2).getComment())+"</font>"));
                holder.tvReplayCount.setVisibility(View.GONE);
            }else{
                holder.llTwoReplay.setVisibility(View.VISIBLE);
                holder.tvReplayCount.setVisibility(View.VISIBLE);
                holder.tvTwoReplay1.setText(Html.fromHtml("<font color = '#4660ff'>" + commentList.get(0).getUserName() +":</font>" + "<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(0).getComment())+"</font>"));
                holder.tvTwoReplay2.setText(Html.fromHtml("<font color = '#4660ff'>" + commentList.get(1).getUserName() +":</font>" + "<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(1).getComment())+"</font>"));
                holder.tvTwoReplay3.setText(Html.fromHtml("<font color = '#4660ff'>" + commentList.get(2).getUserName() +":</font>" + "<font color = '#999999'>"+ Utils.unicodeToString(commentList.get(2).getComment())+"</font>"));
                holder.tvReplayCount.setText(Html.fromHtml("<font color = '#4660ff'>回复量" + list.get(position).getCount() +"条></font>"));
            }

            String diff = TimeUtil.getTimeDiff(TimeUtil.getDateToString(list.get(position).getCreateDate()), TimeUtil.getNowTime());
            if(TextUtils.isEmpty(diff)){
                holder.tvDate.setText(TimeUtil.getDateToMMDD(list.get(position).getCreateDate()));
                holder.tvTime.setText(TimeUtil.getDateTohhmm(list.get(position).getCreateDate()));
            }else {
                holder.tvDate.setText(diff);
                holder.tvTime.setText("");
            }

            holder.ivHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHeadClick(list.get(position).getUserId());
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

        void onHeadClick(int id);

    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_one_comment)
        TextView tvOneComment;
        @BindView(R.id.tv_two_replay1)
        TextView tvTwoReplay1;
        @BindView(R.id.tv_two_replay2)
        TextView tvTwoReplay2;
        @BindView(R.id.tv_two_replay3)
        TextView tvTwoReplay3;
        @BindView(R.id.ll_two_replay)
        LinearLayout llTwoReplay;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_replay_count)
        TextView tvReplayCount;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_time)
        TextView tvTime;

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
