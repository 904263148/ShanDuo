package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/6/5.
 */

public class NumberInfo implements Parcelable {
    private String attention;   //好友数量
    private String dynamic;  //动态数量
    private String activity;  //活动数量

    public NumberInfo(){}

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public NumberInfo(String attention, String dynamic, String activity) {

        this.attention = attention;
        this.dynamic = dynamic;
        this.activity = activity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.attention);
        dest.writeString(this.dynamic);
        dest.writeString(this.activity);
    }

    protected NumberInfo(Parcel in) {
        this.attention = in.readString();
        this.dynamic = in.readString();
        this.activity = in.readString();
    }

    public static final Creator<NumberInfo> CREATOR = new Creator<NumberInfo>() {
        @Override
        public NumberInfo createFromParcel(Parcel source) {
            return new NumberInfo(source);
        }

        @Override
        public NumberInfo[] newArray(int size) {
            return new NumberInfo[size];
        }
    };
}
