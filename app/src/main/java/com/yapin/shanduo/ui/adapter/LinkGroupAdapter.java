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
import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.TimeUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.List;

/**
 * 作者：L on 2018/6/16 0016 14:21
 */
public class LinkGroupAdapter extends BaseAdapter{

    private List<IMGroupInfo.GroupInfo> list;
    private Context context;
    private Activity activity;

    public LinkGroupAdapter(Context context ,Activity activity, List<IMGroupInfo.GroupInfo> list){
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IMGroupInfo.GroupInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        IMGroupInfo.GroupInfo info = getItem(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.link_group_item, null);
            viewHolder.tvName =  view.findViewById(R.id.tv_name);
            viewHolder.ivHead =  view.findViewById(R.id.iv_head);
            viewHolder.tvSign = view.findViewById(R.id.tv_sign);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(info.getName());
        GlideUtil.loadIMHead(context ,activity , info.getFaceUrl() , viewHolder.ivHead);
        viewHolder.tvSign.setText("最近活跃时间:" + TimeUtil.getDateToChat(info.getLastMsgTime()*1000));
        return view;
    }

    final static class ViewHolder {
        ImageView ivHead;
        TextView tvName;
        TextView tvSign;
    }

}
