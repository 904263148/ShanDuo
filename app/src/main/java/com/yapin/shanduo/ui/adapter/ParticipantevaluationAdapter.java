package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.ParticipantevaluationInfo;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.widget.RatingBarView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/5/30.
 */

public class ParticipantevaluationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private Activity activity;
    private List<ActivityInfo.Act> list;
    private int positiona;

    public ParticipantevaluationAdapter (Context context , Activity activity , List<ActivityInfo.Act> list , int positiona ,int typeId){
        this.context = context;
        this.activity = activity;
        this.list = list;
        this.positiona = positiona;
//        this.typeId = typeId;
//        Log.i("typeidaa", typeId+"");
//        openPopupWindow = (MainActivity)activity;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_evaluate,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ViewHolder){
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.tv_nickname.setText(list.get(position).getUserName());
            GlideUtil.load(context , activity , ApiUtil.IMG_URL+ list.get(position).getHeadPortraitId() ,holder.iv_Headportrait);

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

        void onTextClick(int position, ActivityInfo.Act act, int type);

    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_Headportrait)
        ImageView iv_Headportrait;
        @BindView(R.id.tv_nickname)
        TextView tv_nickname;
        @BindView(R.id.custom_ratingbar)
        RatingBarView custom_ratingbar;
        @BindView(R.id.et_evaluate)
        EditText et_evaluate;

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

