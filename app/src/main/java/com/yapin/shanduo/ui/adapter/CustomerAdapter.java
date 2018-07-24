package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.CustomerServiceInfo;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;

import java.util.List;

/**
 * 作者：L on 2018/7/24 0024 14:07
 */
public class CustomerAdapter extends ArrayAdapter<CustomerServiceInfo.Customer> {

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;
    private Activity activity;
    private Context context;

    private List<CustomerServiceInfo.Customer> list;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public CustomerAdapter(Context context, Activity activity , int resource, List<CustomerServiceInfo.Customer> objects) {
        super(context, resource, objects);
        this.context = context;
        resourceId = resource;
        this.list = objects;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.catalog = view.findViewById(R.id.catalog);
            viewHolder.tvName = view.findViewById(R.id.tv_name);
            viewHolder.ivHead =  view.findViewById(R.id.iv_head);
            viewHolder.tvAge = view.findViewById(R.id.tv_home_age);
            viewHolder.tvVip = view.findViewById(R.id.tv_vip);
            viewHolder.tvSign = view.findViewById(R.id.tv_sign);
            viewHolder.tvOnline = view.findViewById(R.id.tv_online_state);
            view.setTag(viewHolder);
        }
        viewHolder.catalog.setVisibility(View.GONE);
        viewHolder.tvOnline.setVisibility(View.GONE);
        viewHolder.tvAge.setVisibility(View.GONE);
        viewHolder.tvVip.setVisibility(View.GONE);
        viewHolder.tvSign.setVisibility(View.GONE);
        viewHolder.tvName.setText(list.get(position).getName());
        GlideUtil.load(context , activity , ApiUtil.IMG_URL+list.get(position).getPicture() , viewHolder.ivHead);
        return view;
    }


    public class ViewHolder{
        ImageView ivHead;
        TextView tvName;
        TextView tvAge;
        TextView tvVip;
        TextView tvSign;
        TextView catalog;
        TextView tvOnline;
    }
}
