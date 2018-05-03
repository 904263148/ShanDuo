package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yapin.shanduo.R;

import qdx.bezierviewpager_compile.vPage.CardPagerAdapter;

/**
 * 作者：L on 2018/4/26 0026 10:13
 */
public class ImageHomeAdapter extends RecyclingPagerAdapter{

    private Context context;
    private Activity activity;

    private int [] imgs;

    private int size;
    private boolean isInfiniteLoop;

    public ImageHomeAdapter(Context context, Activity activity,int [] imgs) {
        this.context = context;
        this.activity = activity;
        this.size = imgs.length;
        this.imgs = imgs;
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        return isInfiniteLoop ? Integer.MAX_VALUE : imgs.length;
    }

    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            view.setTag(R.string.app_name, holder);
        } else {
            holder = (ViewHolder) view.getTag(R.string.app_name);
        }
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView.setImageResource(imgs[position]);
        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImageHomeAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
