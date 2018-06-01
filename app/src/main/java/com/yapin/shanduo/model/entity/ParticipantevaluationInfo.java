package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/5/30.
 */

public class ParticipantevaluationInfo implements Parcelable {

//    （userId，score，evaluated）
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

    public ParticipantevaluationInfo(String userId, String score, String evaluated) {

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

    protected ParticipantevaluationInfo(Parcel in) {
        this.userId = in.readString();
        this.score = in.readString();
        this.evaluated = in.readString();
    }

    public static final Parcelable.Creator<ParticipantevaluationInfo> CREATOR = new Parcelable.Creator<ParticipantevaluationInfo>() {
        @Override
        public ParticipantevaluationInfo createFromParcel(Parcel source) {
            return new ParticipantevaluationInfo(source);
        }

        @Override
        public ParticipantevaluationInfo[] newArray(int size) {
            return new ParticipantevaluationInfo[size];
        }
    };
}
