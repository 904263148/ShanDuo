package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.JoinActUser;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.List;

/**
 * 作者：L on 2018/5/26 0026 09:16
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<JoinActUser.ActUser> list;
    private Activity activity;

    public GridViewAdapter(Context context, Activity activity,List<JoinActUser.ActUser> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public interface OnItemClickListener{
        void onItemClick(int id);
    }

    public OnItemClickListener listener;

    public void setClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        GridViewAdapter.ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new GridViewAdapter.ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.head_text_layout, null);
            viewHolder.imageView = view.findViewById(R.id.iv_head);
            viewHolder.textView = view.findViewById(R.id.tv_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (GridViewAdapter.ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(Utils.unicodeToString(list.get(position).getUser_name()));
        GlideUtil.load(context ,activity , ApiUtil.IMG_URL + list.get(position).getHead_portrait_id() , viewHolder.imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(list.get(position).getId());
            }
        });
        return view;
    }

    final static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}