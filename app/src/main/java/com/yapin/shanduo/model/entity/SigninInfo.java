package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/6/8.
 */

public class SigninInfo implements Parcelable {

    private int count; // 签到天数
    private boolean isSignin; // 当天是否已签到

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSignin() {
        return isSignin;
    }

    public void setSignin(boolean signin) {
        isSignin = signin;
    }

    public SigninInfo(){}

    public SigninInfo(int count, boolean isSignin) {

        this.count = count;
        this.isSignin = isSignin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeByte(this.isSignin ? (byte) 1 : (byte) 0);
    }

    protected SigninInfo(Parcel in) {
        this.count = in.readInt();
        this.isSignin = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SigninInfo> CREATOR = new Parcelable.Creator<SigninInfo>() {
        @Override
        public SigninInfo createFromParcel(Parcel source) {
            return new SigninInfo(source);
        }

        @Override
        public SigninInfo[] newArray(int size) {
            return new SigninInfo[size];
        }
    };
}
