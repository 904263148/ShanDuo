package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.ui.activity.MyDynamicsActivity;
import com.yapin.shanduo.ui.activity.TrendInfoActivity;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.TimeUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.FooterLoading;
import com.yapin.shanduo.widget.MyGridView;
import com.yich.layout.picwatcherlib.ImageWatcher;
import com.yich.layout.picwatcherlib.PicWatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/5/15.
 */

public class MyDynamicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TrendGridViewAdapter.OnItemClickListener{

    private Context context;
    private List<TrendInfo.Trend> list;
    private Activity activity;

    /**
     * 控制是否显示Checkbox
     */
    private boolean showCheckBox;

    public boolean isShowCheckBox() {
        return showCheckBox;
    }
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }

    private TrendGridViewAdapter adapter;

    public MyDynamicsAdapter(Context context, Activity activity, List<TrendInfo.Trend> list ) {
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
                view = LayoutInflater.from(context).inflate(R.layout.item_my_dynamics, parent, false);
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
            GlideUtil.load(context, activity,ApiUtil.IMG_URL+ list.get(position).getPortraitId(), holder.ivHead);
            holder.tvName.setText(Utils.unicodeToString(list.get(position).getName()));
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
            holder.tvAge.setText(list.get(position).getAge() + "");
            //防止复用导致的checkbox显示错乱
            holder.item_checkbox.setTag(position);
            //判断当前checkbox的状态
            if (showCheckBox) {
                holder.item_checkbox.setVisibility(View.VISIBLE);
                //防止显示错乱
                holder.item_checkbox.setChecked(mCheckStates.get(position, false));
            } else {
                holder.item_checkbox.setVisibility(View.GONE);
                //取消掉Checkbox后不再保存当前选择的状态
                holder.item_checkbox.setChecked(false);
                mCheckStates.clear();
            }
            //点击监听
            holder.rl_detele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (showCheckBox==true) {
                        if (showCheckBox) {
                            holder.item_checkbox.setChecked(!holder.item_checkbox.isChecked());
                        }
                        onItemClickListener.onCheckbox(view, position ,list.get(position).getId() );
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("trendInfo", list.get(position));
                        bundle.putInt("id", 1);
                        StartActivityUtil.start(activity, TrendInfoActivity.class, bundle);
                    }
                }
            });
            //长按监听
            holder.rl_detele.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    return onItemClickListener.onItemLongClick(view , position ,list.get(position).getId());
                }
            });
            //对checkbox的监听 保存选择状态 防止checkbox显示错乱
            holder.item_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int pos = (int) compoundButton.getTag();
                    if (b) {
                        mCheckStates.put(pos, true);
                    } else {
                        mCheckStates.delete(pos);
                    }

                }
            });

            holder.tvMile.setText(list.get(position).getLocation() + "");
            if (list.get(position).getLocation().equals("")){
                holder.tvMile.setVisibility(View.GONE);
            }

            holder.tvContent.setText(Utils.unicodeToString(list.get(position).getContent()));

            holder.tvRelayCount.setText(list.get(position).getDynamicCount()+"");
            holder.tvLikeCount.setText(list.get(position).getPraise()+"");

            String diff = TimeUtil.getTimeDiff(TimeUtil.getDateToString(list.get(position).getCreateDate()), TimeUtil.getNowTime());
            if(TextUtils.isEmpty(diff)){
                holder.tvDate.setText(TimeUtil.getDateToMMDD(list.get(position).getCreateDate()));
                holder.tvPublishTime.setText(TimeUtil.getDateTohhmm(list.get(position).getCreateDate()));
            }else {
                holder.tvDate.setText(diff);
                holder.tvPublishTime.setText("");
            }

            int level = list.get(position).getVip();
            if(level == 0){
                holder.tvVip.setVisibility(View.GONE);
            }else if(level > 0 && level < 9){
                holder.tvVip.setVisibility(View.VISIBLE);
                holder.tvVip.setText("VIP"+level);
                holder.tvVip.setBackgroundResource(R.drawable.rounded_tv_vip);
            }else {
                holder.tvVip.setVisibility(View.VISIBLE);
                holder.tvVip.setText("SVIP"+(level-10));
                holder.tvVip.setBackgroundResource(R.drawable.rounded_tv_svip);
            }

            holder.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int userId = list.get(position).getUserId();
                    String Id = list.get(position).getId();
                    setpopupwindow.onpopupwindow( position ,userId , Id);
                }
            });

            holder.tvLikeCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (likeClickListener != null)
                        likeClickListener.onLikeClick(list.get(position).getId());
                }
            });

            int size = list.get(position).getPicture().size();
            switch (size){
                case 0:
                    holder.gridview.setVisibility(View.GONE);
                    holder.rlImg1.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.gridview.setVisibility(View.GONE);
                    holder.rlImg1.setVisibility(View.VISIBLE);
                    GlideUtil.load(context ,activity , ApiUtil.IMG_URL+list.get(position).getPicture().get(0) ,holder.ivImg1 ,5 );
                    holder.ivImg2.setVisibility(View.GONE);

                    final List<ImageView> thumUrlsImageView = new ArrayList<>();
                    thumUrlsImageView.add(holder.ivImg1);
                    holder.ivImg1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PicWatcher.showImages(activity, holder.ivImg1, 0, thumUrlsImageView, list.get(position).getPicture(), new ImageWatcher.OnPictureLongPressListener() {
                                @Override
                                public void onPictureLongPress(ImageView v, String url, int pos) {

                                }
                            });
                        }
                    });
                    break;
                case 2:
                    holder.gridview.setVisibility(View.GONE);
                    holder.rlImg1.setVisibility(View.VISIBLE);
                    GlideUtil.load(context ,activity ,ApiUtil.IMG_URL+list.get(position).getPicture().get(0) ,holder.ivImg1 ,5 );
                    GlideUtil.load(context ,activity ,ApiUtil.IMG_URL+list.get(position).getPicture().get(1) ,holder.ivImg2 ,5 );

                    final List<ImageView> thumUrlsImageView2 = new ArrayList<>();
                    thumUrlsImageView2.add(holder.ivImg1);
                    thumUrlsImageView2.add(holder.ivImg2);
                    holder.ivImg1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PicWatcher.showImages(activity, holder.ivImg1, 0, thumUrlsImageView2, list.get(position).getPicture(), new ImageWatcher.OnPictureLongPressListener() {
                                @Override
                                public void onPictureLongPress(ImageView v, String url, int pos) {

                                }
                            });
                        }
                    });
                    holder.ivImg2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PicWatcher.showImages(activity, holder.ivImg2, 1, thumUrlsImageView2, list.get(position).getPicture(), new ImageWatcher.OnPictureLongPressListener() {
                                @Override
                                public void onPictureLongPress(ImageView v, String url, int pos) {

                                }
                            });
                        }
                    });
                    break;
                default:
                    holder.gridview.setVisibility(View.VISIBLE);
                    holder.rlImg1.setVisibility(View.GONE);
                    adapter = new TrendGridViewAdapter(context, list.get(position).getPicture(), activity);
                    adapter.setOnItemClickListener(this);
                    holder.gridview.setAdapter(adapter);
                    break;
            }
//            holder.shineButton.init(activity);
            if(list.get(position).isPraise()){
                holder.shineButton.setChecked(true);
            }else {
                holder.shineButton.setChecked(false);
            }
//            holder.shineButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    likeClickListener.onLikeClicka(list.get(position).getId() , position);
//                }
//            });
        } else {
            FooterHolder holder = (FooterHolder) viewHolder;
            holder.footerLoading.onLoad(Constants.TYPE_FOOTER_FULL == list.get(position).getType());
        }
    }

    //多选删除
    public void setonItemLongClick(onItemLongClick onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onSavePicClick(ImageView imageView, int position, List<ImageView> thumUrlsImageView, List<String> imgUrl) {

    }

    public interface onItemLongClick {
        boolean onItemLongClick( View view ,int pos ,String id);
        void onCheckbox(View view, int pos ,String id);
    }

    private onItemLongClick onItemClickListener;


    //分享
    public interface Onpopupwindow {
        void onpopupwindow( int position ,int userId ,String Id);
    }

    public void setOnpopupwindow(Onpopupwindow Onpopup){
        this.setpopupwindow = Onpopup;
    }

    private Onpopupwindow setpopupwindow;

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }


    public interface OnLikeClickListener {
        void onLikeClick(String id);
//        void onLikeClicka(String id , int position);
    }

    private OnLikeClickListener likeClickListener;

    public void setLikeClickListener(OnLikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_my_tag)
        TextView tvTag;
        @BindView(R.id.iv_my_head)
        ImageView ivHead;
        @BindView(R.id.tv_my_name)
        TextView tvName;
        @BindView(R.id.tv_my_vip)
        TextView tvVip;
        @BindView(R.id.tv_my_age)
        TextView tvAge;
        @BindView(R.id.tv_my_mile)
        TextView tvMile;
        @BindView(R.id.rl_user_my_info)
        RelativeLayout rlUserInfo;
        @BindView(R.id.tv_my_content)
        TextView tvContent;
        @BindView(R.id.gridview_my)
        MyGridView gridview;
        @BindView(R.id.tv_publish_my_time)
        TextView tvPublishTime;
        @BindView(R.id.rl_trend_my_info)
        RelativeLayout rlTrendInfo;
        @BindView(R.id.iv_my_share)
        ImageView ivShare;
        @BindView(R.id.iv_my_img1)
        ImageView ivImg1;
        @BindView(R.id.iv_my_img2)
        ImageView ivImg2;
        @BindView(R.id.rl_my_img1)
        RelativeLayout rlImg1;
        @BindView(R.id.tv_replay_count)
        TextView tvRelayCount;
        @BindView(R.id.tv_like_count)
        TextView tvLikeCount;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.item_checkbox)
        CheckBox item_checkbox;
        @BindView(R.id.rl_detele)
        RelativeLayout rl_detele;
        @BindView(R.id.shine_button)
        ShineButton shineButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
