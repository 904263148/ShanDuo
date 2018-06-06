package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：L on 2018/6/5 0005 09:31
 */
public class PayOrder implements Parcelable {

    private String appid ,partnerid , prepayid  , noncestr , timestamp , sign , order;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appid);
        dest.writeString(this.partnerid);
        dest.writeString(this.prepayid);
        dest.writeString(this.noncestr);
        dest.writeString(this.timestamp);
        dest.writeString(this.sign);
        dest.writeString(this.order);
    }

    public PayOrder() {
    }

    protected PayOrder(Parcel in) {
        this.appid = in.readString();
        this.partnerid = in.readString();
        this.prepayid = in.readString();
        this.noncestr = in.readString();
        this.timestamp = in.readString();
        this.sign = in.readString();
        this.order = in.readString();
    }

    public static final Parcelable.Creator<PayOrder> CREATOR = new Parcelable.Creator<PayOrder>() {
        @Override
        public PayOrder createFromParcel(Parcel source) {
            return new PayOrder(source);
        }

        @Override
        public PayOrder[] newArray(int size) {
            return new PayOrder[size];
        }
    };
}
