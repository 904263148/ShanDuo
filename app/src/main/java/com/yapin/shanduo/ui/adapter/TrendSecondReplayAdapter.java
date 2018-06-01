package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.yapin.shanduo.model.entity.SecondComment;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.TimeUtil;
import com.yapin.shanduo.widget.FooterLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/5/19 0019 10:31
 */
public class TrendSecondReplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SecondComment.Comments> list;
    private Activity activity;

    private TrendSecondReplayAdapter adapter;

    public TrendSecondReplayAdapter(Context context, Activity activity, List<SecondComment.Comments> list) {
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
                view = LayoutInflater.from(context).inflate(R.layout.replay_item, parent, false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {
            TrendSecondReplayAdapter.ViewHolder holder = (TrendSecondReplayAdapter.ViewHolder) viewHolder;
            GlideUtil.load(context, activity, ApiUtil.IMG_URL + list.get(position).getPortraitId(), holder.ivHead);
            holder.tvName.setText(list.get(position).getUserName());
            String diff = TimeUtil.getTimeDiff(TimeUtil.getDateToString(list.get(position).getCreateDate()), TimeUtil.getNowTime());
            if(TextUtils.isEmpty(diff)){
                holder.tvDate.setText(TimeUtil.getDateToMMDD(list.get(position).getCreateDate()));
                holder.tvTime.setText(TimeUtil.getDateTohhmm(list.get(position).getCreateDate()));
            }else {
                holder.tvDate.setText(diff);
                holder.tvTime.setText("");
            }
            holder.tvOneComment.setText(Html.fromHtml("<font color = '#999999'>回复</font><font color = '#4861ff'>@" + list.get(position).getReplyName() +"</font><font color = '#333333'>:"+ list.get(position).getComment()+"</font>"));
        } else {
            FooterHolder holder = (FooterHolder) viewHolder;
            holder.footerLoading.setBackgroundColor(activity.getResources().getColor(R.color.bg_color));
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

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_one_comment)
        TextView tvOneComment;
        @BindView(R.id.tv_tag)
        TextView tvTag;

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
        @BindView(R.id.ll_bg)
        LinearLayout llBg;

        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
