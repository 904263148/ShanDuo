package com.yapin.shanduo.im.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMImageType;
import com.tencent.TIMLocationElem;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.adapters.ChatAdapter;
import com.yapin.shanduo.im.ui.ImageViewActivity;
import com.yapin.shanduo.im.utils.FileUtil;

import java.io.IOException;

/**
 * 作者：L on 2018/5/7 0007 09:52
 *
 * 地理位置
 */
public class LocationMessage extends Message{

    private static final String TAG = "LocationMessage";

    public LocationMessage(TIMMessage message){
        this.message = message;
    }

    public LocationMessage(double longitude , double latitude , String desc){
        message = new TIMMessage();

        //添加位置信息
        TIMLocationElem elem = new TIMLocationElem();
        elem.setLatitude(longitude);   //设置纬度
        elem.setLongitude(latitude);   //设置经度
        elem.setDesc(desc);

        //将elem添加到消息
        if(message.addElement(elem) != 0) {
            Log.d(TAG, "addElement failed");
            return;
        }

    }


    @Override
    public void showMessage(final ChatAdapter.ViewHolder viewHolder, Context context) {
        clearView(viewHolder);
        TIMImageElem e = (TIMImageElem) message.getElement(0);
        switch (message.status()){
            case Sending:

                ImageView imageView = new ImageView(ShanDuoPartyApplication.getContext());
                imageView.setImageBitmap(getThumb(e.getPath()));
                clearView(viewHolder);
                getBubbleView(viewHolder).addView(imageView);
                break;
            case SendSucc:
                for(final TIMImage image : e.getImageList()) {
                    if (image.getType() == TIMImageType.Thumb){
                        final String uuid = image.getUuid();
                        if (FileUtil.isCacheFileExist(uuid)){
                            showThumb(viewHolder,uuid);
                        }else{
                            image.getImage(new TIMValueCallBack<byte[]>() {
                                @Override
                                public void onError(int code, String desc) {//获取图片失败
                                    //错误码code和错误描述desc，可用于定位请求失败原因
                                    //错误码code含义请参见错误码表
                                    Log.e(TAG, "getImage failed. code: " + code + " errmsg: " + desc);
                                }

                                @Override
                                public void onSuccess(byte[] data) {//成功，参数为图片数据
                                    FileUtil.createFile(data, uuid);
                                    showThumb(viewHolder,uuid);
                                }
                            });
                        }
                    }
                    if (image.getType() == TIMImageType.Original){
                        final String uuid = image.getUuid();
//                        setImageEvent(viewHolder, uuid,context);
                        getBubbleView(viewHolder).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }
                break;
        }
        showStatus(viewHolder);
    }

    @Override
    public String getSummary() {
        return null;
    }

    @Override
    public void save() {

    }

    /**
     * 生成缩略图
     * 缩略图是将原图等比压缩，压缩后宽、高中较小的一个等于198像素
     * 详细信息参见文档
     */
    private Bitmap getThumb(String path){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int reqWidth, reqHeight, width=options.outWidth, height=options.outHeight;
        if (width > height){
            reqWidth = 198;
            reqHeight = (reqWidth * height)/width;
        }else{
            reqHeight = 198;
            reqWidth = (width * reqHeight)/height;
        }
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        try{
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            Matrix mat = new Matrix();
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            ExifInterface ei =  new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    mat.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    mat.postRotate(180);
                    break;
            }
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
        }catch (IOException e){
            return null;
        }
    }

    private void showThumb(final ChatAdapter.ViewHolder viewHolder,String filename){
        Bitmap bitmap = BitmapFactory.decodeFile(FileUtil.getCacheFilePath(filename));
        ImageView imageView = new ImageView(ShanDuoPartyApplication.getContext());
        imageView.setImageBitmap(bitmap);
        getBubbleView(viewHolder).addView(imageView);
    }

    private void navToLocationview(final TIMLocationElem location, final Context context){

    }

}
