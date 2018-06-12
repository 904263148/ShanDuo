package com.yapin.shanduo.im.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMCustomElem;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMImageType;
import com.tencent.TIMLocationElem;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.tlslibrary.helper.Util;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.adapters.ChatAdapter;
import com.yapin.shanduo.im.ui.ImageViewActivity;
import com.yapin.shanduo.im.utils.FileUtil;
import com.yapin.shanduo.ui.activity.PlaceActivity;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.Utils;

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

    public LocationMessage(String latitude , String longitude , String desc , String imgId){
        message = new TIMMessage();

        //添加位置信息
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(imgId.getBytes());
        elem.setExt(longitude.getBytes());
        elem.setSound(latitude.getBytes());
        elem.setDesc(desc);

        //将elem添加到消息
        if(message.addElement(elem) != 0) {
            Log.d(TAG, "addElement failed");
            return;
        }

    }

    @Override
    public void showMessage(final ChatAdapter.ViewHolder viewHolder, final Context context) {
        clearView(viewHolder);
        final TIMCustomElem e = (TIMCustomElem) message.getElement(0);
        switch (message.status()){
            case Sending:

                ImageView imageView = new ImageView(ShanDuoPartyApplication.getContext());
                GlideUtil.load(ShanDuoPartyApplication.getContext() , e.getData() , imageView);
                clearView(viewHolder);
                getBubbleView(viewHolder).addView(imageView);
                break;
            case SendSucc:

                showThumb(viewHolder , e.getData());
                        getBubbleView(viewHolder).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navToLocationView(e ,context);
                            }
                        });
                break;
        }
        showStatus(viewHolder);
    }

    @Override
    public String getSummary() {
        return ShanDuoPartyApplication.getContext().getString(R.string.summary_location);
    }

    @Override
    public void save() {

    }

    private void showThumb(final ChatAdapter.ViewHolder viewHolder,byte [] imgId){
        ImageView imageView = new ImageView(ShanDuoPartyApplication.getContext());
        GlideUtil.load(ShanDuoPartyApplication.getContext() , imgId , imageView);
        getBubbleView(viewHolder).addView(imageView);
    }

    private void navToLocationView(final TIMCustomElem location, final Context context){
        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra("lat", Double.parseDouble(Utils.byteArrayToStr(location.getSound())));
        intent.putExtra("lon", Double.parseDouble(Utils.byteArrayToStr(location.getExt())));
        intent.putExtra("place", location.getDesc());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
