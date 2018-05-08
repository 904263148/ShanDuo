package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/5/4.
 */

public class ShanduoActivity implements Parcelable {

    private String token;
    private String activityType;    //活动类型
    private String activityStartTime;   //活动开始时间
    private String activityAddress;     //活动地址
    private String mode;    //消费方式
    private String manNumber;   //男生人数
    private String womanNumber;     //女生人数
    private String remarks;     //备注
    private String activityCutoffTime;      //活动报名截止时间
    private String lon;     //经度
    private String lat;     //维度

    @Override
    public String toString() {
        return "ShanduoActivity{" +
                "token='" + token + '\'' +
                ", activityType='" + activityType + '\'' +
                ", activityStartTime='" + activityStartTime + '\'' +
                ", activityAddress='" + activityAddress + '\'' +
                ", mode='" + mode + '\'' +
                ", manNumber='" + manNumber + '\'' +
                ", womanNumber='" + womanNumber + '\'' +
                ", remarks='" + remarks + '\'' +
                ", activityCutoffTime='" + activityCutoffTime + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getManNumber() {
        return manNumber;
    }

    public void setManNumber(String manNumber) {
        this.manNumber = manNumber;
    }

    public String getWomanNumber() {
        return womanNumber;
    }

    public void setWomanNumber(String womanNumber) {
        this.womanNumber = womanNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getActivityCutoffTime() {
        return activityCutoffTime;
    }

    public void setActivityCutoffTime(String activityCutoffTime) {
        this.activityCutoffTime = activityCutoffTime;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public ShanduoActivity(String token, String activityType, String activityStartTime, String activityAddress, String mode, String manNumber, String womanNumber, String remarks, String activityCutoffTime, String lon, String lat) {

        this.token = token;
        this.activityType = activityType;
        this.activityStartTime = activityStartTime;
        this.activityAddress = activityAddress;
        this.mode = mode;
        this.manNumber = manNumber;
        this.womanNumber = womanNumber;
        this.remarks = remarks;
        this.activityCutoffTime = activityCutoffTime;
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.activityType);
        dest.writeString(this.activityStartTime);
        dest.writeString(this.activityAddress);
        dest.writeString(this.mode);
        dest.writeString(this.manNumber);
        dest.writeString(this.womanNumber);
        dest.writeString(this.remarks);
        dest.writeString(this.activityCutoffTime);
        dest.writeString(this.lon);
        dest.writeString(this.lat);
    }

    protected ShanduoActivity(Parcel in) {
        this.token = in.readString();
        this.activityType = in.readString();
        this.activityStartTime = in.readString();
        this.activityAddress = in.readString();
        this.mode = in.readString();
        this.manNumber = in.readString();
        this.womanNumber = in.readString();
        this.remarks = in.readString();
        this.activityCutoffTime = in.readString();
        this.lon = in.readString();
        this.lat = in.readString();
    }

    public static final Creator<ShanduoActivity> CREATOR = new Creator<ShanduoActivity>() {
        @Override
        public ShanduoActivity createFromParcel(Parcel source) {
            return new ShanduoActivity(source);
        }

        @Override
        public ShanduoActivity[] newArray(int size) {
            return new ShanduoActivity[size];
        }
    };
}
