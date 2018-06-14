package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;
import com.yich.layout.picwatcherlib.PicWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：L on 2018/5/14 0014 10:30
 */
public class TrendGridViewAdapter extends BaseAdapter{

    private Context context;
    private List<String> list;
    private Activity activity;

    private List<ImageView> thumUrlsImageView = new ArrayList<>();

    private ImageView imageView;
    private int clickPosition;

    public TrendGridViewAdapter(Context context ,List<String> list , Activity activity){
        this.context = context;
        this.list = list;
        this.activity = activity;
        for (int i = 0 ; i < list.size() ; i++){
            ImageView imageView = new ImageView(activity);
            thumUrlsImageView.add(imageView);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        imageView = thumUrlsImageView.get(position);
//        if(convertView == null){
//            imageView = new ImageView(activity);
            imageView.setLayoutParams(new GridView.LayoutParams(Utils.dip2px(context ,110) , Utils.dip2px(context , 110)));
//        }else{
//            imageView = (ImageView) convertView;
//        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtil.load(activity , ApiUtil.IMG_URL + list.get(position) , imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicWatcher.showImages(activity , (ImageView) v, position , thumUrlsImageView , list);
            }
        });

        return imageView;
    }
}
