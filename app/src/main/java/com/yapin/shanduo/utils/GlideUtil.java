package com.yapin.shanduo.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yapin.shanduo.R;
import com.yapin.shanduo.widget.CircleImageView;
import com.yapin.shanduo.widget.glide.GlideCircleTransform;
import com.yapin.shanduo.widget.glide.GlideRoundTransform;

public class GlideUtil {

    private static GlideCircleTransform circleTransform;
    private static GlideRoundTransform roundTransform;

    /**
     * 初始化transform
     *
     * @param context context
     * @return transform
     */
    public static GlideCircleTransform transform(Context context) {
        if (circleTransform == null) {
            circleTransform = new GlideCircleTransform(context);
        }
        return circleTransform;
    }

    /**
     * 初始化transform
     *
     * @param context context
     * @return transform
     */
    public static GlideRoundTransform transform(Context context, int dp) {
        if (roundTransform == null) {
            roundTransform = new GlideRoundTransform(context, dp);
        }
        return roundTransform;
    }

    /**
     * 加载圆角缩略图
     *
     * @param activity  activity
     * @param url       头像url
     * @param imageView 头像view
     * @param dp        圆角dp
     */
    public static void load(Context context, Activity activity, String url, ImageView imageView, int dp) {
        Glide.with(activity).load(url == null ? "" : url).error(R.color.color_white)
                .placeholder(R.color.color_white)
                .transform(transform(context, dp))
                .into(imageView);
    }

    /**
     * 加载头像url
     *
     * @param context   context
     * @param activity  activity
     * @param url       头像url
     * @param imageView 头像view
     */
    public static void load(Context context, Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url == null ? "" : url)
                .placeholder(R.color.color_white)
                .error(R.drawable.head)
                .transform(transform(context))
                .into(imageView);
    }

    /**
     * 加载头像url
     *
     * @param context   context
     * @param activity  activity
     * @param url       头像url
     * @param imageView 头像view
     */
    public static void loadIMHead(Context context, Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url == null ? "" : url)
                .placeholder(R.drawable.head_group)
                .error(R.drawable.head_group)
                .transform(transform(context))
                .into(imageView);
    }

    /**
     * 加载头像url
     *
     * @param activity  activity
     * @param imageView 头像view
     */
    public static void load(Activity activity, int res, ImageView imageView) {
        Glide.with(activity).load(res)
                .placeholder(R.color.color_white)
                .error(R.color.color_white)
                .transform(transform(activity))
                .into(imageView);
    }

    /**
     * 加载头像url
     *
     * @param context   context
     * @param url       头像url
     * @param imageView 头像view
     */
    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url == null ? "" : url)
                .placeholder(R.color.color_white)
                .error(R.drawable.head)
                .transform(transform(context))
                .into(imageView);
    }

    /**
     * 加载缩略图
     *
     * @param activity  activity
     * @param url       头像url
     * @param imageView 头像view
     */
    public static void load(Activity activity, String url, ImageView imageView) {
        android.util.Log.d("img_url",url);
        Glide.with(activity).load(url == null ? "" : url).error(R.color.color_white)
                .placeholder(R.color.color_white)
                .into(imageView);
    }

    /**
     * 加载缩略图
     */
    public static void load(Context context, byte [] imgId, ImageView imageView) {
        String url = ApiUtil.IMG_URL + Utils.byteArrayToStr(imgId);
        Glide.with(context).load(Utils.byteArrayToStr(imgId) == null ? "" : url)
                .error(R.color.color_white)
                .placeholder(R.color.color_white)
                .into(imageView);
    }

    /**
     * Glide 加载图片保存到本地
     *
     * imgUrl 图片地址
     * imgName 图片名称
     */
    public static void getBitmap(final Context context, String url , final String imgName) {
        if(FileUtil.exist(Environment.getExternalStorageDirectory() + Constants.PICTURE_PATH + "/" + imgName )){
            ToastUtil.showShortToast(context , "图片已下载");
            return;
        }
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    FileUtil.saveBitmap(context , imgName , bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
