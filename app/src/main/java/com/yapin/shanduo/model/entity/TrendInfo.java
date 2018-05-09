package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.yapin.shanduo.utils.Constants;

import java.util.Date;
import java.util.List;

/**
 * 作者：L on 2018/5/9 0009 18:32
 */
public class TrendInfo implements Parcelable {

    private int totalpage;

    private int page;

    private List<Trend> list;

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Trend> getList() {
        return list;
    }

    public void setList(List<Trend> list) {
        this.list = list;
    }

    public static class Trend implements Parcelable {

        private int type = Constants.TYPE_SHOW;

        private String id;//动态ID
        private Integer userId;//用户ID
        private String name;//用户昵称
        private String portraitId;//头像
        private String age;//年龄
        private String content;//动态内容
        private List<String> picture;//动态图片或视频
        private Integer praise;//点赞人数
        private boolean isPraise;//是否点赞
        private double location;//距离
        private String remarks;//备注
        private Date createDate;//发动态的时间
        private Integer vip;
        private String gender;//性别

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getPortraitId() {
            return portraitId;
        }

        public void setPortraitId(String portraitId) {
            this.portraitId = portraitId;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getPicture() {
            return picture;
        }

        public void setPicture(List<String> picture) {
            this.picture = picture;
        }

        public Integer getPraise() {
            return praise;
        }

        public void setPraise(Integer praise) {
            this.praise = praise;
        }

        public boolean isPraise() {
            return isPraise;
        }

        public void setPraise(boolean praise) {
            isPraise = praise;
        }

        public double getLocation() {
            return location;
        }

        public void setLocation(double location) {
            this.location = location;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public Integer getVip() {
            return vip;
        }

        public void setVip(Integer vip) {
            this.vip = vip;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.type);
            dest.writeString(this.id);
            dest.writeValue(this.userId);
            dest.writeString(this.name);
            dest.writeString(this.portraitId);
            dest.writeString(this.age);
            dest.writeString(this.content);
            dest.writeStringList(this.picture);
            dest.writeValue(this.praise);
            dest.writeByte(this.isPraise ? (byte) 1 : (byte) 0);
            dest.writeDouble(this.location);
            dest.writeString(this.remarks);
            dest.writeLong(this.createDate != null ? this.createDate.getTime() : -1);
            dest.writeValue(this.vip);
            dest.writeString(this.gender);
        }

        public Trend() {
        }

        protected Trend(Parcel in) {
            this.type = in.readInt();
            this.id = in.readString();
            this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.name = in.readString();
            this.portraitId = in.readString();
            this.age = in.readString();
            this.content = in.readString();
            this.picture = in.createStringArrayList();
            this.praise = (Integer) in.readValue(Integer.class.getClassLoader());
            this.isPraise = in.readByte() != 0;
            this.location = in.readDouble();
            this.remarks = in.readString();
            long tmpCreateDate = in.readLong();
            this.createDate = tmpCreateDate == -1 ? null : new Date(tmpCreateDate);
            this.vip = (Integer) in.readValue(Integer.class.getClassLoader());
            this.gender = in.readString();
        }

        public static final Parcelable.Creator<Trend> CREATOR = new Parcelable.Creator<Trend>() {
            @Override
            public Trend createFromParcel(Parcel source) {
                return new Trend(source);
            }

            @Override
            public Trend[] newArray(int size) {
                return new Trend[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalpage);
        dest.writeInt(this.page);
        dest.writeTypedList(this.list);
    }

    public TrendInfo() {
    }

    protected TrendInfo(Parcel in) {
        this.totalpage = in.readInt();
        this.page = in.readInt();
        this.list = in.createTypedArrayList(Trend.CREATOR);
    }

    public static final Parcelable.Creator<TrendInfo> CREATOR = new Parcelable.Creator<TrendInfo>() {
        @Override
        public TrendInfo createFromParcel(Parcel source) {
            return new TrendInfo(source);
        }

        @Override
        public TrendInfo[] newArray(int size) {
            return new TrendInfo[size];
        }
    };
}
