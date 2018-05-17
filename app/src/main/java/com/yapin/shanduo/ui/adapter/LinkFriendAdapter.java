package com.yapin.shanduo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：L on 2018/5/17 0017 16:00
 */
public class LinkFriendAdapter extends BaseAdapter {

    private ArrayList<User> list = null;
    private Context mContext;

    public LinkFriendAdapter(Context mContext, ArrayList<User> list) {
        this.mContext = mContext;
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

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        final User user = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.link_friend_item, null);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.catalog = (TextView) view.findViewById(R.id.catalog);
            viewHolder.ivHead =  view.findViewById(R.id.iv_head);
            viewHolder.tvAge = view.findViewById(R.id.tv_home_age);
            viewHolder.tvVip = view.findViewById(R.id.tv_vip);
            viewHolder.tvOnline = view.findViewById(R.id.tv_online_state);
            viewHolder.tvSign = view.findViewById(R.id.tv_sign);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取首字母作为目录catalog
        String catalog = list.get(position).getFirstLetter();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(catalog)){
            viewHolder.catalog.setVisibility(View.VISIBLE);
            viewHolder.catalog.setText(user.getFirstLetter().toUpperCase());
        }else{
            viewHolder.catalog.setVisibility(View.GONE);
        }

        viewHolder.tvName.setText(this.list.get(position).getName());

        return view;

    }

    final static class ViewHolder {
        TextView catalog;
        ImageView ivHead;
        TextView tvName;
        TextView tvAge;
        TextView tvVip;
        TextView tvOnline;
        TextView tvSign;
    }

    /**
     * 获取catalog首次出现位置
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getFirstLetter();
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }

}
