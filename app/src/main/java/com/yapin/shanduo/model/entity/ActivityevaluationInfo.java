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

    private String activityId;
    private String othersScore;
    private String beEvaluated;
    private String beEvaluationSign;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getOthersScore() {
        return othersScore;
    }

    public void setOthersScore(String othersScore) {
        this.othersScore = othersScore;
    }

    public String getBeEvaluated() {
        return beEvaluated;
    }

    public void setBeEvaluated(String beEvaluated) {
        this.beEvaluated = beEvaluated;
    }

    public String getBeEvaluationSign() {
        return beEvaluationSign;
    }

    public void setBeEvaluationSign(String beEvaluationSign) {
        this.beEvaluationSign = beEvaluationSign;
    }

    public ActivityevaluationInfo(String activityId, String othersScore, String beEvaluated, String beEvaluationSign) {

        this.activityId = activityId;
        this.othersScore = othersScore;
        this.beEvaluated = beEvaluated;
        this.beEvaluationSign = beEvaluationSign;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityId);
        dest.writeString(this.othersScore);
        dest.writeString(this.beEvaluated);
        dest.writeString(this.beEvaluationSign);
    }

    protected ActivityevaluationInfo(Parcel in) {
        this.activityId = in.readString();
        this.othersScore = in.readString();
        this.beEvaluated = in.readString();
        this.beEvaluationSign = in.readString();
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
