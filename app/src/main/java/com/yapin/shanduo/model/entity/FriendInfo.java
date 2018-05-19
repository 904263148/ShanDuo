package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：L on 2018/5/19 0019 15:06
 */
public class FriendInfo implements Parcelable {

    private boolean success;
    private List<fInfo> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<fInfo> getResult() {
        return result;
    }

    public void setResult(List<fInfo> result) {
        this.result = result;
    }

    public static class fInfo implements Parcelable {

        private Integer userId;//闪多号
        private String name;//昵称
        private String picture;//头像
        private String gender;//性别
        private Integer age;//性别 ,0女,1男
        private Integer vip;//VIP等级:vip(0-8),svip(11-18)
        private String signature;//个性签名

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getVip() {
            return vip;
        }

        public void setVip(Integer vip) {
            this.vip = vip;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.userId);
            dest.writeString(this.name);
            dest.writeString(this.picture);
            dest.writeString(this.gender);
            dest.writeValue(this.age);
            dest.writeValue(this.vip);
            dest.writeString(this.signature);
        }

        public fInfo() {
        }

        protected fInfo(Parcel in) {
            this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.name = in.readString();
            this.picture = in.readString();
            this.gender = in.readString();
            this.age = (Integer) in.readValue(Integer.class.getClassLoader());
            this.vip = (Integer) in.readValue(Integer.class.getClassLoader());
            this.signature = in.readString();
        }

        public static final Creator<fInfo> CREATOR = new Creator<fInfo>() {
            @Override
            public fInfo createFromParcel(Parcel source) {
                return new fInfo(source);
            }

            @Override
            public fInfo[] newArray(int size) {
                return new fInfo[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.result);
    }

    public FriendInfo() {
    }

    protected FriendInfo(Parcel in) {
        this.success = in.readByte() != 0;
        this.result = in.createTypedArrayList(fInfo.CREATOR);
    }

    public static final Creator<FriendInfo> CREATOR = new Creator<FriendInfo>() {
        @Override
        public FriendInfo createFromParcel(Parcel source) {
            return new FriendInfo(source);
        }

        @Override
        public FriendInfo[] newArray(int size) {
            return new FriendInfo[size];
        }
    };
}
