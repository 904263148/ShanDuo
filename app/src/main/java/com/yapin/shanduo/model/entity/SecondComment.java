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
        private Integer userId;//评论人ID
        private String userName;//评论人昵称
        private String portraitId;//评论人头像
        private Integer replyId;//被回复人ID
        private String replyName;//被回复人昵称
        private String comment;//回复内容
        private long createDate;//回复时间

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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPortraitId() {
            return portraitId;
        }

        public void setPortraitId(String portraitId) {
            this.portraitId = portraitId;
        }

        public Integer getReplyId() {
            return replyId;
        }

        public void setReplyId(Integer replyId) {
            this.replyId = replyId;
        }

        public String getReplyName() {
            return replyName;
        }

        public void setReplyName(String replyName) {
            this.replyName = replyName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public Comments() {
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
            dest.writeValue(this.userId);
            dest.writeString(this.userName);
            dest.writeString(this.portraitId);
            dest.writeValue(this.replyId);
            dest.writeString(this.replyName);
            dest.writeString(this.comment);
            dest.writeLong(this.createDate);
        }

        protected Comments(Parcel in) {
            this.type = in.readInt();
            this.id = in.readString();
            this.dynamicId = in.readString();
            this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.userName = in.readString();
            this.portraitId = in.readString();
            this.replyId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.replyName = in.readString();
            this.comment = in.readString();
            this.createDate = in.readLong();
        }

        public static final Creator<Comments> CREATOR = new Creator<Comments>() {
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

    public static final Creator<SecondComment> CREATOR = new Creator<SecondComment>() {
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
