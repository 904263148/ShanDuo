package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.ActivityevaluationInfo;
import com.yapin.shanduo.model.entity.JoinActUser;
import com.yapin.shanduo.model.entity.ParticipantevaluationInfo;
import com.yapin.shanduo.ui.activity.ParticipantevaluationActivity;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.widget.RatingBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/5/30.
 */

public class ParticipantevaluationAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context context;
    private Activity activity;
    private List<JoinActUser.ActUser> list;
    List<ActivityevaluationInfo> infos = new ArrayList<>();
    int level = 0;
    String activityId;
    String score = "";

    String userId = "";

    private int clickPosition;

    public ParticipantevaluationAdapter(Context context, Activity activity, List<JoinActUser.ActUser> list) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
        this.list = list;

        if (list != null) {
            for (JoinActUser.ActUser actUser : list) {
                ActivityevaluationInfo info = new ActivityevaluationInfo();
                info.setUserId(actUser.getId().toString());
                infos.add(info);
            }
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_evaluate, parent, false);
            holder.iv_Headportrait = (ImageView) convertView.findViewById(R.id.iv_Headportrait);
            holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            holder.custom_ratingbar = (RatingBarView) convertView.findViewById(R.id.custom_ratingbar);
            holder.et_evaluate = (EditText) convertView.findViewById(R.id.et_evaluate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_nickname.setText(list.get(position).getUser_name());
        GlideUtil.load(context, activity, ApiUtil.IMG_URL + list.get(position).getHead_portrait_id(), holder.iv_Headportrait);

        holder.custom_ratingbar.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {
                level = RatingScore;
                infos.get(position).setScore(String.valueOf(RatingScore));
            }
        });

//        Log.e(this.getClass().getName(), "getView: " + infos);

        String id = list.get(position).getId().toString();

//        Log.e(this.getClass().getName(), "list.get(position).getId(): " + id);

        infos.get(position).setUserId(id);

//        holder.et_evaluate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickPosition = position;
//            }
//        });

//        holder.et_evaluate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                String evaluated;
//                evaluated = holder.et_evaluate.getText().toString().trim();
//                if (b) {
//                    if (position == clickPosition){
//                            infos.get(position).setEvaluated(evaluated);
//                        }
//                    }
//                    Log.e(this.getClass().getName(), "ontext " + holder.et_evaluate.getText().toString().trim());
//                }
//        });

        holder.et_evaluate.setTag(position);//存tag值
        holder.et_evaluate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    index = (Integer) view.getTag();
                }
                return false;
            }
        });

        class MyTextWatcher implements TextWatcher {
            public MyTextWatcher(ViewHolder holder) {
                mHolder = holder;
            }

            private ViewHolder mHolder;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && !"".equals(editable.toString())) {
                    int position = (Integer) mHolder.et_evaluate.getTag();
                    infos.get(position).setEvaluated(editable.toString());
                }
            }
        }

        holder.et_evaluate.addTextChangedListener(new MyTextWatcher(holder));
        convertView.setTag(holder);


        return convertView;
    }
    private Integer index = -1;




    public List<ActivityevaluationInfo> getData() {
        return infos;
    }

    class ViewHolder {
        private TextView tv_nickname;
        private ImageView iv_Headportrait;
        private RatingBarView custom_ratingbar;
        private EditText et_evaluate;

    }

}

