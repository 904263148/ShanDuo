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
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.widget.FooterLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/5/9 0009 11:16
 */
public class TrendInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private Activity activity;
    private List<String> list;

    public TrendInfoAdapter (Context context , Activity activity ){
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trend_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
             ViewHolder holder = (ViewHolder) viewHolder;
             holder.tvName.setText("测试");
//             GlideUtil.load(context ,activity ,"http://img3.qihuiwang.com/fb4b96020327594effd3676051660df9_jpg.jpg" , holder.ivImg1 , 10);
//             GlideUtil.load(context ,activity ,"http://pic1.to8to.com/case/1401/10/20140110_8cc831587a4d0c6298b1n9fiwwugmzdt.jpg" , holder.ivImg2 , 10);
//             GlideUtil.load(context ,activity ,"http://oss.huangye88.net/live/user/1312983/1450340648091523800-0.jpg" , holder.ivImg3 , 10);

    }

    @Override
    public int getItemCount() {
        return 4;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return list.get(position).getType();
//    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private ActivityInfoAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ActivityInfoAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

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