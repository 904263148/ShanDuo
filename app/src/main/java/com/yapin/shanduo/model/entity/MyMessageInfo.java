package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.yapin.shanduo.utils.Constants;

import java.util.List;

/**
 * 作者：L on 2018/6/27 0027 17:20
 */
public class MyMessageInfo implements Parcelable {

    private int totalPage;

    private int page;

    private List<MyMessageInfo.Message> list;

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

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

    public static class Message implements Parcelable {
        String portraitId;       //                                   评论人头像
        Integer vip;         //                          : 11 ,评论人vip
        Integer age;         //                          : 24                                               评论人年龄
        String type;         //                           : 2
        Integer  replyUserId;         //                                   : 10001                                                   ,评论人用户ID
        String replyName;         //                                : 范西涯                                   ,评论人用户昵称
        String id;         //                         : 61b9f08cf1854ab1858e7136aa1b0798                                ,评论ID
        String replyComment;         //                          :评论人评论内容
        String commentId;         //                              : 如果type=1 则没有，为2则是该评论属于的1级评论id
        String gender   ;         //                      : 评论人性别
        Integer issueId  ;         //                  :动态发布人用户ID
        String issueName;         //                          :动态发布人昵称
        String content  ;         //              :被评论人的评论
        List<String> picture  ;         //                          : [], 动态图片
        String location ;         //                  :地点
        String dynamicId;         //                      :动态ID
        String name;         //                  :被评论人昵称
        Integer userId;         //                          : 10011, 被评论人用户ID
        String comment  ;         //  : 被评论人的评论

        private int type_show = Constants.TYPE_SHOW;

        public String getPortraitId() {
            return portraitId;
        }

        public void setPortraitId(String portraitId) {
            this.portraitId = portraitId;
        }

        public Integer getVip() {
            return vip;
        }

        public void setVip(Integer vip) {
            this.vip = vip;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(Integer replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getReplyName() {
            return replyName;
        }

        public void setReplyName(String replyName) {
            this.replyName = replyName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReplyComment() {
            return replyComment;
        }

        public void setReplyComment(String replyComment) {
            this.replyComment = replyComment;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getIssueId() {
            return issueId;
        }

        public void setIssueId(Integer issueId) {
            this.issueId = issueId;
        }

        public String getIssueName() {
            return issueName;
        }

        public void setIssueName(String issueName) {
            this.issueName = issueName;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(String dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getType_show() {
            return type_show;
        }

        public void setType_show(int type_show) {
            this.type_show = type_show;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.portraitId);
            dest.writeValue(this.vip);
            dest.writeValue(this.age);
            dest.writeString(this.type);
            dest.writeValue(this.replyUserId);
            dest.writeString(this.replyName);
            dest.writeString(this.id);
            dest.writeString(this.replyComment);
            dest.writeString(this.commentId);
            dest.writeString(this.gender);
            dest.writeValue(this.issueId);
            dest.writeString(this.issueName);
            dest.writeString(this.content);
            dest.writeStringList(this.picture);
            dest.writeString(this.location);
            dest.writeString(this.dynamicId);
            dest.writeString(this.name);
            dest.writeValue(this.userId);
            dest.writeString(this.comment);
            dest.writeInt(this.type_show);
        }

        public Message() {
        }

        protected Message(Parcel in) {
            this.portraitId = in.readString();
            this.vip = (Integer) in.readValue(Integer.class.getClassLoader());
            this.age = (Integer) in.readValue(Integer.class.getClassLoader());
            this.type = in.readString();
            this.replyUserId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.replyName = in.readString();
            this.id = in.readString();
            this.replyComment = in.readString();
            this.commentId = in.readString();
            this.gender = in.readString();
            this.issueId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.issueName = in.readString();
            this.content = in.readString();
            this.picture = in.createStringArrayList();
            this.location = in.readString();
            this.dynamicId = in.readString();
            this.name = in.readString();
            this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.comment = in.readString();
            this.type_show = in.readInt();
        }

        public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
            @Override
            public Message createFromParcel(Parcel source) {
                return new Message(source);
            }

            @Override
            public Message[] newArray(int size) {
                return new Message[size];
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

    public MyMessageInfo() {
    }

    protected MyMessageInfo(Parcel in) {
        this.totalPage = in.readInt();
        this.page = in.readInt();
        this.list = in.createTypedArrayList(Message.CREATOR);
    }

    public static final Parcelable.Creator<MyMessageInfo> CREATOR = new Parcelable.Creator<MyMessageInfo>() {
        @Override
        public MyMessageInfo createFromParcel(Parcel source) {
            return new MyMessageInfo(source);
        }

        @Override
        public MyMessageInfo[] newArray(int size) {
            return new MyMessageInfo[size];
        }
    };
}
