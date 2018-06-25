package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/6/25.
 */

public class GetVipLevelInfo implements Parcelable {
    private Integer experience;
    private Integer level;

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public GetVipLevelInfo() {
    }

    public GetVipLevelInfo(Integer experience, Integer level) {

        this.experience = experience;
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.experience);
        dest.writeValue(this.level);
    }

    protected GetVipLevelInfo(Parcel in) {
        this.experience = (Integer) in.readValue(Integer.class.getClassLoader());
        this.level = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetVipLevelInfo> CREATOR = new Parcelable.Creator<GetVipLevelInfo>() {
        @Override
        public GetVipLevelInfo createFromParcel(Parcel source) {
            return new GetVipLevelInfo(source);
        }

        @Override
        public GetVipLevelInfo[] newArray(int size) {
            return new GetVipLevelInfo[size];
        }
    };
}
