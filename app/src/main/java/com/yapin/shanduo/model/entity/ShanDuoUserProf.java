package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：L on 2018/5/25 0025 16:39
 */
public class ShanDuoUserProf implements Parcelable {

    private boolean isAttention;
    private String gender , name , picture;
    private Integer activity , level , dynamic , vip , userId , age , attention;

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDynamic() {
        return dynamic;
    }

    public void setDynamic(Integer dynamic) {
        this.dynamic = dynamic;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ShanDuoUserProf() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isAttention ? (byte) 1 : (byte) 0);
        dest.writeString(this.gender);
        dest.writeString(this.name);
        dest.writeString(this.picture);
        dest.writeValue(this.activity);
        dest.writeValue(this.level);
        dest.writeValue(this.dynamic);
        dest.writeValue(this.vip);
        dest.writeValue(this.userId);
        dest.writeValue(this.age);
        dest.writeValue(this.attention);
    }

    protected ShanDuoUserProf(Parcel in) {
        this.isAttention = in.readByte() != 0;
        this.gender = in.readString();
        this.name = in.readString();
        this.picture = in.readString();
        this.activity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.level = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dynamic = (Integer) in.readValue(Integer.class.getClassLoader());
        this.vip = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.attention = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ShanDuoUserProf> CREATOR = new Creator<ShanDuoUserProf>() {
        @Override
        public ShanDuoUserProf createFromParcel(Parcel source) {
            return new ShanDuoUserProf(source);
        }

        @Override
        public ShanDuoUserProf[] newArray(int size) {
            return new ShanDuoUserProf[size];
        }
    };
}
