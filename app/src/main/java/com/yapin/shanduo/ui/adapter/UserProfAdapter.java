package com.yapin.shanduo.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.entity.TokenInfo;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;

import java.util.List;

/**
 * 作者：L on 2018/5/24 0024 17:04
 */
public class UserProfAdapter extends ArrayAdapter<TokenInfo> {


    private int resourceId;
    private View view;
    private UserProfAdapter.ViewHolder viewHolder;

    private Context context;

    private List<TokenInfo> list;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public UserProfAdapter(Context context, int resource, List<TokenInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        resourceId = resource;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (UserProfAdapter.ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new UserProfAdapter.ViewHolder();
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
        viewHolder.tvName.setText(list.get(position).getName());
        viewHolder.tvSign.setText(list.get(position).getSignature());

        GlideUtil.load(context ,ApiUtil.IMG_URL+list.get(position).getPicture()  , viewHolder.ivHead);

        Drawable drawable = null;
        if ("0".equals(list.get(position).getGender())) {
            drawable = context.getResources().getDrawable(R.drawable.icon_women);
            viewHolder.tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
        } else {
            drawable = context.getResources().getDrawable(R.drawable.icon_men);
            viewHolder.tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        viewHolder.tvAge.setCompoundDrawables(drawable, null, null, null);
        viewHolder.tvAge.setCompoundDrawablePadding(2);
        viewHolder.tvAge.setText(list.get(position).getAge()+"");

        int level = list.get(position).getVip();
        if(level == 0){
            viewHolder.tvVip.setVisibility(View.GONE);
        }else if(level > 0 && level < 9){
            viewHolder.tvVip.setVisibility(View.VISIBLE);
            viewHolder.tvVip.setText("VIP"+level);
            viewHolder.tvVip.setBackgroundResource(R.drawable.rounded_tv_vip);
        }else {
            viewHolder.tvVip.setVisibility(View.VISIBLE);
            viewHolder.tvVip.setText("SVIP"+(level-10));
            viewHolder.tvVip.setBackgroundResource(R.drawable.rounded_tv_svip);
        }

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