package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.yapin.shanduo.utils.Constants;

/**
 * 作者：L on 2018/6/1 0001 09:23
 */
public class CreditItem implements Parcelable {

    private int type = Constants.TYPE_TOP;

    private String mode , activity_name , evaluation_content , user_name , head_portrait_id , father_head , presenter_head , presenter_name , gender ,id;
    private int birthday ,score , father_id , father_reputation , vipGrade;

    public String getPresenter_head() {
        return presenter_head;
    }

    public void setPresenter_head(String presenter_head) {
        this.presenter_head = presenter_head;
    }

    public String getPresenter_name() {
        return presenter_name;
    }

    public void setPresenter_name(String presenter_name) {
        this.presenter_name = presenter_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVipGrade() {
        return vipGrade;
    }

    public void setVipGrade(int vipGrade) {
        this.vipGrade = vipGrade;
    }

    public String getFather_head() {
        return father_head;
    }

    public void setFather_head(String father_head) {
        this.father_head = father_head;
    }

    public int getFather_id() {
        return father_id;
    }

    public void setFather_id(int father_id) {
        this.father_id = father_id;
    }

    public int getFather_reputation() {
        return father_reputation;
    }

    public void setFather_reputation(int father_reputation) {
        this.father_reputation = father_reputation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getEvaluation_content() {
        return evaluation_content;
    }

    public void setEvaluation_content(String evaluation_content) {
        this.evaluation_content = evaluation_content;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHead_portrait_id() {
        return head_portrait_id;
    }

    public void setHead_portrait_id(String head_portrait_id) {
        this.head_portrait_id = head_portrait_id;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public CreditItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.mode);
        dest.writeString(this.activity_name);
        dest.writeString(this.evaluation_content);
        dest.writeString(this.user_name);
        dest.writeString(this.head_portrait_id);
        dest.writeString(this.father_head);
        dest.writeString(this.presenter_head);
        dest.writeString(this.presenter_name);
        dest.writeString(this.gender);
        dest.writeString(this.id);
        dest.writeInt(this.birthday);
        dest.writeInt(this.score);
        dest.writeInt(this.father_id);
        dest.writeInt(this.father_reputation);
        dest.writeInt(this.vipGrade);
    }

    protected CreditItem(Parcel in) {
        this.type = in.readInt();
        this.mode = in.readString();
        this.activity_name = in.readString();
        this.evaluation_content = in.readString();
        this.user_name = in.readString();
        this.head_portrait_id = in.readString();
        this.father_head = in.readString();
        this.presenter_head = in.readString();
        this.presenter_name = in.readString();
        this.gender = in.readString();
        this.id = in.readString();
        this.birthday = in.readInt();
        this.score = in.readInt();
        this.father_id = in.readInt();
        this.father_reputation = in.readInt();
        this.vipGrade = in.readInt();
    }

    public static final Creator<CreditItem> CREATOR = new Creator<CreditItem>() {
        @Override
        public CreditItem createFromParcel(Parcel source) {
            return new CreditItem(source);
        }

        @Override
        public CreditItem[] newArray(int size) {
            return new CreditItem[size];
        }
    };
}
