package com.yapin.shanduo.im.model;

import android.content.Context;
import android.util.Log;

import com.tencent.TIMLocationElem;
import com.tencent.TIMMessage;
import com.yapin.shanduo.im.adapters.ChatAdapter;

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
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {

    }

    @Override
    public String getSummary() {
        return null;
    }

    @Override
    public void save() {

    }
}
