package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.JoinActUser;
import com.yapin.shanduo.widget.RatingBarView;

import java.util.List;

/**
 * Created by dell on 2018/6/3.
 */

public class TransactionrecordAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context context;

    private List<JoinActUser.ActUser> list;

    public TransactionrecordAdapter(Context context,  List<JoinActUser.ActUser> list) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;


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
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_transactionrecord, parent, false);
            holder.im_modeimage = (ImageView) convertView.findViewById(R.id.im_modeimage);
            holder.tv_vip = (TextView) convertView.findViewById(R.id.tv_vip);
            holder.tv_lunarday = (TextView) convertView.findViewById(R.id.tv_lunarday);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

                 holder.tv_money.setText("充值VIP");
                 holder.tv_lunarday.setText("5月1日");
                 holder.tv_time.setText("15:15");
                 holder.tv_money.setText("-12.00元");

        return convertView;
    }


    class ViewHolder {
        private TextView tv_money;
        private TextView tv_time;
        private TextView tv_lunarday;
        private TextView tv_vip;
        private ImageView im_modeimage;

    }
}
