package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/6/21.
 */

public class TaskInfo implements Parcelable {
    private Integer release;
    private Integer join;

    public TaskInfo() {
    }

    public Integer getRelease() {
        return release;
    }

    public void setRelease(Integer release) {
        this.release = release;
    }

    public Integer getJoin() {
        return join;
    }

    public void setJoin(Integer join) {
        this.join = join;
    }

    public TaskInfo(Integer release, Integer join) {

        this.release = release;
        this.join = join;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.release);
        dest.writeValue(this.join);
    }

    protected TaskInfo(Parcel in) {
        this.release = (Integer) in.readValue(Integer.class.getClassLoader());
        this.join = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<TaskInfo> CREATOR = new Parcelable.Creator<TaskInfo>() {
        @Override
        public TaskInfo createFromParcel(Parcel source) {
            return new TaskInfo(source);
        }

        @Override
        public TaskInfo[] newArray(int size) {
            return new TaskInfo[size];
        }
    };
}
