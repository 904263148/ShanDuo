package com.yapin.shanduo.model.entity;

import java.util.Date;

public class ShanduoUser {
    private Integer id;

    private String userName;

    private String passWord;

    private String headPortraitId;

    private String phoneNumber;

    private Integer shanduoAgeId;

    private String birthday;

    private String gender;

    private String remarks;

    private Integer shanduoJurisdictionId;

    private Date createDate;

    private Date updateDate;

    private String delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getHeadPortraitId() {
        return headPortraitId;
    }

    public void setHeadPortraitId(String headPortraitId) {
        this.headPortraitId = headPortraitId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getShanduoAgeId() {
        return shanduoAgeId;
    }

    public void setShanduoAgeId(Integer shanduoAgeId) {
        this.shanduoAgeId = shanduoAgeId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getShanduoJurisdictionId() {
        return shanduoJurisdictionId;
    }

    public void setShanduoJurisdictionId(Integer shanduoJurisdictionId) {
        this.shanduoJurisdictionId = shanduoJurisdictionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}