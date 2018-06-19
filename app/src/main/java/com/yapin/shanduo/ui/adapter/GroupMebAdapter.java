package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.IMGroupUserInfo;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.widget.FooterLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/6/19 0019 11:05
 */
public class GroupMebAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<IMGroupUserInfo.GroupMebInfo> list;

    public GroupMebAdapter(Context context, Activity activity, List<IMGroupUserInfo.GroupMebInfo> list) {
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
                view = LayoutInflater.from(context).inflate(R.layout.link_friend_item, parent, false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        IMGroupUserInfo.GroupMebInfo info = list.get(position);
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.catalog.setVisibility(View.GONE);
            holder.tvHomeAge.setVisibility(View.GONE);
            holder.tvVip.setVisibility(View.GONE);
            holder.llTag.setVisibility(View.GONE);

            GlideUtil.load(context ,activity ,list.get(position).getPicture() , holder.ivHead);
            if(info.getNameCard() == null || TextUtils.isEmpty(info.getNameCard())){
                holder.tvName.setText(list.get(position).getName());
            }else {
                holder.tvName.setText(info.getNameCard() + "("+ info.getName() +")");
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


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.catalog)
        TextView catalog;
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_home_age)
        TextView tvHomeAge;
        @BindView(R.id.tv_vip)
        TextView tvVip;
        @BindView(R.id.ll_tag)
        LinearLayout llTag;

        public ViewHolder(View itemView) {
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

    class FooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footer)
        FooterLoading footerLoading;

        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
