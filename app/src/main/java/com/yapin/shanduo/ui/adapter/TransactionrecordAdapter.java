package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.JoinActUser;
import com.yapin.shanduo.model.entity.TransactionrecordInfo;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.TimeUtil;
import com.yapin.shanduo.widget.FooterLoading;
import com.yapin.shanduo.widget.RatingBarView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/6/3.
 */

public class TransactionrecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TransactionrecordInfo.Trend> list;
    private Activity activity;

    private TrendGridViewAdapter adapter;

    public TransactionrecordAdapter(Context context, Activity activity, List<TransactionrecordInfo.Trend> list) {
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
                view = LayoutInflater.from(context).inflate(R.layout.item_transactionrecord, parent, false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;
            if ("1".equals(list.get(position).getMoneyType())){
                holder.tv_money.setText("+"+list.get(position).getAmount());
            }else if ("2".equals(list.get(position).getMoneyType())){
                holder.tv_money.setText("-"+list.get(position).getAmount());
            }else if ("9".equals(list.get(position).getMoneyType())){
                holder.tv_money.setText("+"+list.get(position).getAmount());
            }else if ("10".equals(list.get(position).getMoneyType())){
                holder.tv_money.setText("-"+list.get(position).getAmount());
            }
            holder.tv_vip.setText(list.get(position).getRemarks());

            holder.tv_lunarday.setText(TimeUtil.getDateToString(list.get(position).getCreateDate()));
//            Drawable drawable = null;
//            if ("0".equals(list.get(position).getGender())) {
//                drawable = activity.getResources().getDrawable(R.drawable.icon_women);
//                holder.tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
//            } else {
//                drawable = activity.getResources().getDrawable(R.drawable.icon_men);
//                holder.tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
//            }
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            holder.tvAge.setCompoundDrawables(drawable, null, null, null);
//            holder.tvAge.setCompoundDrawablePadding(2);
//            holder.tvAge.setText(list.get(position).getAge() + "");


        } else {
//            MyDynamicsAdapter.FooterHolder holder = (MyDynamicsAdapter.FooterHolder) viewHolder;
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

        @BindView(R.id.im_modeimage)
        ImageView im_modeimage;
        @BindView(R.id.tv_money)
        TextView tv_money;
//        @BindView(R.id.tv_time)
//        TextView tv_time;
        @BindView(R.id.tv_vip)
        TextView tv_vip;
        @BindView(R.id.tv_lunarday)
        TextView tv_lunarday;


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
