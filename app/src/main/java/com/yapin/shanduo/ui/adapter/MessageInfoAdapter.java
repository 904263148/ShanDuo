package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.MyMessageInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.FooterLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/4/26 0026 14:44
 */
public class MessageInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<MyMessageInfo.Message> list;

    public MessageInfoAdapter(Context context, Activity activity, List<MyMessageInfo.Message> list) {
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
                view = LayoutInflater.from(context).inflate(R.layout.message_item_layout, parent, false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final MyMessageInfo.Message message = list.get(position);
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;
            GlideUtil.load(context , activity , ApiUtil.IMG_URL + message.getPortraitId() , holder.ivHead);
            holder.tvName.setText(message.getReplyName());
            Drawable drawable;
            if ("0".equals(message.getGender())) {
                drawable = activity.getResources().getDrawable(R.drawable.icon_women);
                holder.tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
            } else {
                drawable = activity.getResources().getDrawable(R.drawable.icon_men);
                holder.tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tvHomeAge.setCompoundDrawables(drawable, null, null, null);
            holder.tvHomeAge.setCompoundDrawablePadding(2);
            holder.tvHomeAge.setText(message.getAge()+"");
            int level = message.getVip();
            if(level == 0){
                holder.tvVip.setVisibility(View.GONE);
            }else if(level < 9){
                holder.tvVip.setVisibility(View.VISIBLE);
                holder.tvVip.setText("VIP"+level);
                holder.tvVip.setBackgroundResource(R.drawable.rounded_tv_vip);
            }else {
                holder.tvVip.setVisibility(View.VISIBLE);
                holder.tvVip.setText("SVIP"+(level-10));
                holder.tvVip.setBackgroundResource(R.drawable.rounded_tv_svip);
            }
            holder.tvContent.setText(Utils.unicodeToString(message.getReplyComment()));
            if(message.getPicture().size() == 0){
                holder.ivImg.setVisibility(View.GONE);
            }else {
                holder.ivImg.setVisibility(View.VISIBLE);
                GlideUtil.load(activity , ApiUtil.IMG_URL + message.getPicture().get(0) , holder.ivImg);
            }
            holder.tvUserName.setText(message.getIssueName() +"：");
            holder.tvIssueContent.setText(Utils.unicodeToString(message.getContent()));

            if("1".equals(message.getType())){
                holder.tvComment.setVisibility(View.GONE);
            }else {
                holder.tvComment.setVisibility(View.VISIBLE);
                holder.tvComment.setText(Html.fromHtml("<font color = '#5e75ff'>" + message.getName() + "：</font><font color = '#333333'>" + Utils.unicodeToString(message.getComment()) + "</font>"));
            }

            holder.etReplay.setHint("回复"+message.getReplyName()+"：");
            holder.etReplay.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(TextUtils.isEmpty(s)){
                        holder.tvPublish.setVisibility(View.GONE);
                    }else {
                        holder.tvPublish.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.tvPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onReplayClick(message , holder.etReplay.getText().toString().trim());
                    holder.etReplay.setText("");
                }
            });

            holder.llTrend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTrendClick(message.getDynamicId());
                }
            });

        } else {
            FooterHolder holder = (FooterHolder) viewHolder;
            holder.footerLoading.onLoad(Constants.TYPE_FOOTER_FULL == list.get(position).getType_show());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType_show();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onReplayClick(MyMessageInfo.Message message , String str);

        void onTrendClick(String trendId);
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
        @BindView(R.id.tv_vip)
        TextView tvVip;
        @BindView(R.id.tv_home_age)
        TextView tvHomeAge;
        @BindView(R.id.rl_user_info)
        RelativeLayout rlUserInfo;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.ll_trend)
        LinearLayout llTrend;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.et_replay)
        EditText etReplay;
        @BindView(R.id.tv_publish)
        TextView tvPublish;
        @BindView(R.id.tv_issue_content)
        TextView tvIssueContent;

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
