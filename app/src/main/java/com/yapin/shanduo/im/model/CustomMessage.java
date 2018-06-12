package com.yapin.shanduo.im.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tencent.TIMCustomElem;
import com.tencent.TIMMessage;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.adapters.ChatAdapter;
import com.yapin.shanduo.im.ui.ImageViewActivity;
import com.yapin.shanduo.ui.activity.PlaceActivity;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 自定义消息
 */
public class CustomMessage extends Message {


    private String TAG = getClass().getSimpleName();

    private final int TYPE_TYPING = 14;
    private final int TYPE_LOCATION = 15;

    private Type type;
    private String desc;
    private String data;
    private String latitude;
    private String longitude;
    private String imgUrl;

    public CustomMessage(TIMMessage message){
        this.message = message;
        TIMCustomElem elem = (TIMCustomElem) message.getElement(0);
        parse(elem.getData());

    }

    public CustomMessage(Type type){
        message = new TIMMessage();
        String data = "";
        JSONObject dataJson = new JSONObject();
        try{
            switch (type){
                case TYPING:
                    dataJson.put("userAction",TYPE_TYPING);
                    dataJson.put("actionParam","EIMAMSG_InputStatus_Ing");
                    data = dataJson.toString();
            }
        }catch (JSONException e){
            Log.e(TAG, "generate json error");
        }
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(data.getBytes());
        message.addElement(elem);
    }

    public CustomMessage(Type type ,String latitude , String longitude , String desc , String imgId){
        this.type = type;
        message = new TIMMessage();

        String data = "";
        JSONObject dataJson = new JSONObject();

        try{
            dataJson.put("userAction",TYPE_LOCATION);
            dataJson.put("latitude"  ,latitude);
            dataJson.put("longitude"  ,longitude);
            dataJson.put("imgUrl"  ,imgId);
            dataJson.put("desc" , desc);
            data = dataJson.toString();
        }catch (JSONException e){
            Log.e(TAG, "generate json error");
        }

        //添加位置信息
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(data.getBytes());
        elem.setDesc(desc);

        //将elem添加到消息
        message.addElement(elem);

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private void parse(byte[] data){
        type = Type.INVALID;
        try{
            String str = new String(data, "UTF-8");
            JSONObject jsonObj = new JSONObject(str);
            int action = jsonObj.getInt("userAction");
            switch (action){
                case TYPE_TYPING:
                    type = Type.TYPING;
                    this.data = jsonObj.getString("actionParam");
                    if (this.data.equals("EIMAMSG_InputStatus_End")){
                        type = Type.INVALID;
                    }
                    break;
                case TYPE_LOCATION:
                    type = Type.LOCATION;
                    this.latitude = jsonObj.getString("latitude");
                    this.longitude = jsonObj.getString("longitude");
                    this.imgUrl = jsonObj.getString("imgUrl");
                    this.desc = jsonObj.getString("desc");
                    break;
            }
        }catch (IOException | JSONException e){
            Log.e(TAG, "parse json error");
        }
    }

    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder,final Context context) {
        clearView(viewHolder);
        final TIMCustomElem elem = (TIMCustomElem) message.getElement(0);
        try{
            String str = new String(elem.getData(), "UTF-8");
            JSONObject jsonObj = new JSONObject(str);
            int action = jsonObj.getInt("userAction");
            switch (action){
                case TYPE_LOCATION:
                    type = Type.LOCATION;
                    final String latitude = jsonObj.getString("latitude");
                    final String longitude = jsonObj.getString("longitude");
                    final String imgUrl = jsonObj.getString("imgUrl");
                    final String desc = jsonObj.getString("desc");
                    ImageView imageView = new ImageView(ShanDuoPartyApplication.getContext());
                    imageView.setLayoutParams(new RelativeLayout.LayoutParams(Utils.dip2px(context ,110) , Utils.dip2px(context , 70)));
                    GlideUtil.load(ShanDuoPartyApplication.getContext() , imgUrl.getBytes() , imageView);

                    getBubbleView(viewHolder).addView(imageView);
                    getBubbleView(viewHolder).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navToLocationView(latitude , longitude , desc ,context);
                        }
                    });

                    break;
            }
        }catch (IOException | JSONException e){
            Log.e(TAG, "parse json error");
        }

        showStatus(viewHolder);
    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        return ShanDuoPartyApplication.getContext().getString(R.string.summary_location);
    }

    /**
     * 保存消息或消息文件
     */
    @Override
    public void save() {

    }

    private void navToLocationView(String lat, String lon ,String desc,final Context context){
        Intent intent = new Intent(context, PlaceActivity.class);
        intent.putExtra("lat", Double.parseDouble(lat));
        intent.putExtra("lon", Double.parseDouble(lon));
        intent.putExtra("place", desc);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public enum Type{
        TYPING,
        INVALID,
        LOCATION,
    }
}
