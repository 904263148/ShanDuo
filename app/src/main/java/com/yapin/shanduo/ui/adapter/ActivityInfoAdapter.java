package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yapin.shanduo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/4/26 0026 14:44
 */
public class ActivityInfoAdapter extends RecyclerView.Adapter<ActivityInfoAdapter.ViewHolder>{

    private Context context;
    private Activity activity;

    public ActivityInfoAdapter (Context context , Activity activity){
        this.context = context;
        this.activity = activity;
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


}
