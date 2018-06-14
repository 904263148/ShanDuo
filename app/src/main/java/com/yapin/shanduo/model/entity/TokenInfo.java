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
public class TokenInfo implements Parcelable {
	private String token;//token
	private String userId;//闪多号
	private String name;//昵称
	private String picture;//头像图片URL
	private String phone;//手机号
	private String birthday;//生日
	private Integer age;//年龄
	private String gender;//性别 ,0女,1男
	private String emotion;//情感状态,0,保密,1,已婚,2,未婚
	private String signature;//个性签名
	private String background;//背景图片URL
	private String hometown;//家乡
	private String occupation;//职业
	private String school;//学校
	private String jurisdiction;//权限 普通用户,商户
	private Integer vip;//vip等级
	private Integer level;//等级
	private Integer attention;//好友数量
	private Integer dynamic;//动态数量
	private Integer activity;//活动数量
	private String remarks;//备注
	private String userSig;//IM聊天使用签名

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getAttention() {
		return attention;
	}

	public void setAttention(Integer attention) {
		this.attention = attention;
	}

	public Integer getDynamic() {
		return dynamic;
	}

	public void setDynamic(Integer dynamic) {
		this.dynamic = dynamic;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUserSig() {
		return userSig;
	}

	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
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

	public TokenInfo() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.token);
		dest.writeString(this.userId);
		dest.writeString(this.name);
		dest.writeString(this.picture);
		dest.writeString(this.phone);
		dest.writeString(this.birthday);
		dest.writeValue(this.age);
		dest.writeString(this.gender);
		dest.writeString(this.emotion);
		dest.writeString(this.signature);
		dest.writeString(this.background);
		dest.writeString(this.hometown);
		dest.writeString(this.occupation);
		dest.writeString(this.school);
		dest.writeString(this.jurisdiction);
		dest.writeValue(this.vip);
		dest.writeValue(this.level);
		dest.writeValue(this.attention);
		dest.writeValue(this.dynamic);
		dest.writeValue(this.activity);
		dest.writeString(this.remarks);
		dest.writeString(this.userSig);
	}

	protected TokenInfo(Parcel in) {
		this.token = in.readString();
		this.userId = in.readString();
		this.name = in.readString();
		this.picture = in.readString();
		this.phone = in.readString();
		this.birthday = in.readString();
		this.age = (Integer) in.readValue(Integer.class.getClassLoader());
		this.gender = in.readString();
		this.emotion = in.readString();
		this.signature = in.readString();
		this.background = in.readString();
		this.hometown = in.readString();
		this.occupation = in.readString();
		this.school = in.readString();
		this.jurisdiction = in.readString();
		this.vip = (Integer) in.readValue(Integer.class.getClassLoader());
		this.level = (Integer) in.readValue(Integer.class.getClassLoader());
		this.attention = (Integer) in.readValue(Integer.class.getClassLoader());
		this.dynamic = (Integer) in.readValue(Integer.class.getClassLoader());
		this.activity = (Integer) in.readValue(Integer.class.getClassLoader());
		this.remarks = in.readString();
		this.userSig = in.readString();
	}

	public static final Creator<TokenInfo> CREATOR = new Creator<TokenInfo>() {
		@Override
		public TokenInfo createFromParcel(Parcel source) {
			return new TokenInfo(source);
		}

		@Override
		public TokenInfo[] newArray(int size) {
			return new TokenInfo[size];
		}
	};
}
