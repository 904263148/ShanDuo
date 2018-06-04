package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/5/28.
 */

public class ActivityevaluationInfo implements Parcelable {

//    activityId  活动id
//    othersScore  评分
//    beEvaluated  评价
//    beEvaluationSign  评价标识

    private String userId;
    private String score;
    private String evaluated;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }


    public ActivityevaluationInfo(){}
    public ActivityevaluationInfo(String userId, String score, String evaluated) {

        this.userId = userId;
        this.score = score;
        this.evaluated = evaluated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.score);
        dest.writeString(this.evaluated);
    }

    protected ActivityevaluationInfo(Parcel in) {
        this.userId = in.readString();
        this.score = in.readString();
        this.evaluated = in.readString();
    }

    public static final Parcelable.Creator<ActivityevaluationInfo> CREATOR = new Parcelable.Creator<ActivityevaluationInfo>() {
        @Override
        public ActivityevaluationInfo createFromParcel(Parcel source) {
            return new ActivityevaluationInfo(source);
        }

        @Override
        public ActivityevaluationInfo[] newArray(int size) {
            return new ActivityevaluationInfo[size];
        }
    };
}
