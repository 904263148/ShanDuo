package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yapin.shanduo.utils.GlideUtil;

import java.util.List;

/**
 * 作者：L on 2018/5/4 0004 09:17
 */
public class MyViewPagerAdapter extends PagerAdapter {

    private List<String> list;
    private Context context;
    private Activity activity;

    public MyViewPagerAdapter(List<String> list, Context context , Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView vp_iv = new ImageView(context);
        vp_iv.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtil.load(context ,activity , list.get(position) ,vp_iv , 20);
        container.addView(vp_iv);
        return vp_iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
