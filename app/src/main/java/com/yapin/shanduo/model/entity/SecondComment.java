package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.yapin.shanduo.utils.Constants;

import java.util.List;

/**
 * 作者：L on 2018/5/16 0016 10:55
 */
public class SecondComment implements Parcelable {

    private int totalPage;
    private int page;
    private List<Comments> list;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Comments> getList() {
        return list;
    }

    public void setList(List<Comments> list) {
        this.list = list;
    }

    public static class Comments implements Parcelable {

        private int type = Constants.TYPE_SHOW;
        private String id;//2级评论ID
        private String dynamicId;//动态ID
        private Integer userIdOne;//评论人ID
        private String nameOne;//评论人昵称
        private String portraitId;//评论人头像
        private Integer userIdTwo;//被回复人ID
        private String nameTwo;//被回复人昵称
        private String comment;//回复内容
        private List<String> picture;//回复图片

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

        public String getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(String dynamicId) {
            this.dynamicId = dynamicId;
        }

        public Integer getUserIdOne() {
            return userIdOne;
        }

        public void setUserIdOne(Integer userIdOne) {
            this.userIdOne = userIdOne;
        }

        public String getNameOne() {
            return nameOne;
        }

        public void setNameOne(String nameOne) {
            this.nameOne = nameOne;
        }

        public String getPortraitId() {
            return portraitId;
        }

        public void setPortraitId(String portraitId) {
            this.portraitId = portraitId;
        }

        public Integer getUserIdTwo() {
            return userIdTwo;
        }

        public void setUserIdTwo(Integer userIdTwo) {
            this.userIdTwo = userIdTwo;
        }

        public String getNameTwo() {
            return nameTwo;
        }

        public void setNameTwo(String nameTwo) {
            this.nameTwo = nameTwo;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public List<String> getPicture() {
            return picture;
        }

        public void setPicture(List<String> picture) {
            this.picture = picture;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.type);
            dest.writeString(this.id);
            dest.writeString(this.dynamicId);
            dest.writeValue(this.userIdOne);
            dest.writeString(this.nameOne);
            dest.writeString(this.portraitId);
            dest.writeValue(this.userIdTwo);
            dest.writeString(this.nameTwo);
            dest.writeString(this.comment);
            dest.writeStringList(this.picture);
        }

        public Comments() {
        }

        protected Comments(Parcel in) {
            this.type = in.readInt();
            this.id = in.readString();
            this.dynamicId = in.readString();
            this.userIdOne = (Integer) in.readValue(Integer.class.getClassLoader());
            this.nameOne = in.readString();
            this.portraitId = in.readString();
            this.userIdTwo = (Integer) in.readValue(Integer.class.getClassLoader());
            this.nameTwo = in.readString();
            this.comment = in.readString();
            this.picture = in.createStringArrayList();
        }

        public static final Parcelable.Creator<Comments> CREATOR = new Parcelable.Creator<Comments>() {
            @Override
            public Comments createFromParcel(Parcel source) {
                return new Comments(source);
            }

            @Override
            public Comments[] newArray(int size) {
                return new Comments[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalPage);
        dest.writeInt(this.page);
        dest.writeTypedList(this.list);
    }

    public SecondComment() {
    }

    protected SecondComment(Parcel in) {
        this.totalPage = in.readInt();
        this.page = in.readInt();
        this.list = in.createTypedArrayList(Comments.CREATOR);
    }

    public static final Parcelable.Creator<SecondComment> CREATOR = new Parcelable.Creator<SecondComment>() {
        @Override
        public SecondComment createFromParcel(Parcel source) {
            return new SecondComment(source);
        }

        @Override
        public SecondComment[] newArray(int size) {
            return new SecondComment[size];
        }
    };
}
