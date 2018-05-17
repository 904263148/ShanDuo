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
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.ui.activity.MainActivity;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.widget.FooterLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/5/16 0016 17:11
 */
public class TrendCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private Activity activity;
    private OpenPopupWindow openPopupWindow;
    private List<CommentInfo.Comment> list;

    public TrendCommentAdapter (Context context , Activity activity , List<CommentInfo.Comment> list){
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
                view = LayoutInflater.from(context).inflate(R.layout.trend_comment_item,parent,false);
                return new TrendCommentAdapter.ViewHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_footer_loading,parent,false);
                return new TrendCommentAdapter.FooterHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if(viewHolder instanceof TrendCommentAdapter.ViewHolder){
            TrendCommentAdapter.ViewHolder holder = (TrendCommentAdapter.ViewHolder) viewHolder;

        }else{
            TrendCommentAdapter.FooterHolder holder = (TrendCommentAdapter.FooterHolder) viewHolder;
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

    private TrendCommentAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(TrendCommentAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder{


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
