package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：L on 2018/5/26 0026 14:36
 */
public class JoinActUser {

    private int totalPage;

    private int page;

    private List<JoinActUser.ActUser> list;

    public int getTotalpage() {
        return totalPage;
    }

    public void setTotalpage(int totalpage) {
        this.totalPage = totalpage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ActUser> getList() {
        return list;
    }

    public void setList(List<ActUser> list) {
        this.list = list;
    }

    public static class ActUser implements Parcelable {
        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        private String user_name;

        private String head_portrait_id;

        private Integer id , joinActivity;

        public String getHead_portrait_id() {
            return head_portrait_id;
        }

        public void setHead_portrait_id(String head_portrait_id) {
            this.head_portrait_id = head_portrait_id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getJoinActivity() {
            return joinActivity;
        }

        public void setJoinActivity(Integer joinActivity) {
            this.joinActivity = joinActivity;
        }

        public ActUser() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.user_name);
            dest.writeString(this.head_portrait_id);
            dest.writeValue(this.id);
            dest.writeValue(this.joinActivity);
        }

        protected ActUser(Parcel in) {
            this.user_name = in.readString();
            this.head_portrait_id = in.readString();
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.joinActivity = (Integer) in.readValue(Integer.class.getClassLoader());
        }

        public static final Creator<ActUser> CREATOR = new Creator<ActUser>() {
            @Override
            public ActUser createFromParcel(Parcel source) {
                return new ActUser(source);
            }

            @Override
            public ActUser[] newArray(int size) {
                return new ActUser[size];
            }
        };
    }

}
