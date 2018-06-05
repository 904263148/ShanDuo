package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by dell on 2018/6/4.
 */

public class FlickerPurseInfo implements Parcelable {

    private double money;       //余额
    private Integer beans;      //闪多豆
    private Integer refresh;      //刷新次数

    public FlickerPurseInfo(){}

    public FlickerPurseInfo(double money, Integer beans, Integer refresh) {
        this.money = money;
        this.beans = beans;
        this.refresh = refresh;
    }

    public double getMoney() {

        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Integer getBeans() {
        return beans;
    }

    public void setBeans(Integer beans) {
        this.beans = beans;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.money);
        dest.writeValue(this.beans);
        dest.writeValue(this.refresh);
    }

    protected FlickerPurseInfo(Parcel in) {
        this.money = in.readDouble();
        this.beans = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refresh = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<FlickerPurseInfo> CREATOR = new Parcelable.Creator<FlickerPurseInfo>() {
        @Override
        public FlickerPurseInfo createFromParcel(Parcel source) {
            return new FlickerPurseInfo(source);
        }

        @Override
        public FlickerPurseInfo[] newArray(int size) {
            return new FlickerPurseInfo[size];
        }
    };
}