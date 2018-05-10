package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户登录成功token
 * @ClassName: UserToken
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author fanshixin
 * @date 2018年4月16日 下午3:25:19
 *
 */
public class TokenInfo{
	private String token;//token
	private String userId;//闪多号
	private String name;//昵称
	private String picture;//头像图片URL
	private String phone;//手机号
	private String ageId;//年龄段ID
	private String birthday;//生日
	private String gender;//性别 ,0女,1男
	private String emotion;//情感状态,0,保密,1,已婚,2,未婚
	private String signature;//个性签名
	private String background;//背景图片URL
	private String labelId;//个性标签
	private String hometown;//家乡
	private String occupation;//职业
	private String school;//学校
	private String jurisdiction;//权限 普通用户,商户,vip,svip
	private String remarks;//备注
	private String userSig;//IM聊天使用签名
	private String vip;//vip等级

	public String getUserSig() {
		return userSig;
	}

	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAgeId() {
		return ageId;
	}

	public void setAgeId(String ageId) {
		this.ageId = ageId;
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

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
