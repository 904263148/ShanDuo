package com.yapin.shanduo.model.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.tencent.qcloud.sdk.Constant;
import com.yapin.shanduo.utils.Constants;

import java.util.List;

public class ActivityInfo implements Parcelable {

	private int totalpage;

	private int page;

	private List<ActivityInfo.Act> list;

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Act> getList() {
		return list;
	}

	public void setList(List<Act> list) {
		this.list = list;
	}

	public static class Act implements Parcelable {

		private int type = Constants.TYPE_SHOW;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		private String id; //活动id

		private String userName; //用户昵称

		private String activityName;//标题

		private String mode; //消费方式

		private String activityStartTime; //活动开始时间

		private String activityAddress; //活动地址

		private String remarks; //活动备注

		private String manNumber; //男生人数

		private String womanNumber; //女生人数

		private String score; //评分

		private String activityType; //活动类型

		private Integer userId;  //用户id

		private String activityCutoffTime; //活动截止时间

		private String headPortraitId; //头像

		private String birthday;//生日

		private Integer age; //年龄

		private Integer topFlag; //置顶标记

		private double lon; //经度

		private double lat; //纬度

		private double location; //距离

		private String gender; //性别：0:女 1:男

		private Integer vipGrade; //会员等级

		private Integer evaluationSign; // 评价标识

		public Integer getVipGrade() {
			return vipGrade;
		}

		public void setVipGrade(Integer vipGrade) {
			this.vipGrade = vipGrade;
		}

		public Integer getEvaluationSign() {
			return evaluationSign;
		}

		public void setEvaluationSign(Integer evaluationSign) {
			this.evaluationSign = evaluationSign;
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

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getActivityName() {
			return activityName;
		}

		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

		public String getActivityStartTime() {
			return activityStartTime;
		}

		public void setActivityStartTime(String activityStartTime) {
			this.activityStartTime = activityStartTime;
		}

		public String getActivityAddress() {
			return activityAddress;
		}

		public void setActivityAddress(String activityAddress) {
			this.activityAddress = activityAddress;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getManNumber() {
			return manNumber;
		}

		public void setManNumber(String manNumber) {
			this.manNumber = manNumber;
		}

		public String getWomanNumber() {
			return womanNumber;
		}

		public void setWomanNumber(String womanNumber) {
			this.womanNumber = womanNumber;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getActivityType() {
			return activityType;
		}

		public void setActivityType(String activityType) {
			this.activityType = activityType;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getActivityCutoffTime() {
			return activityCutoffTime;
		}

		public void setActivityCutoffTime(String activityCutoffTime) {
			this.activityCutoffTime = activityCutoffTime;
		}

		public String getHeadPortraitId() {
			return headPortraitId;
		}

		public void setHeadPortraitId(String headPortraitId) {
			this.headPortraitId = headPortraitId;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public Integer getTopFlag() {
			return topFlag;
		}

		public void setTopFlag(Integer topFlag) {
			this.topFlag = topFlag;
		}

		public double getLon() {
			return lon;
		}

		public void setLon(double lon) {
			this.lon = lon;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLocation() {
			return location;
		}

		public void setLocation(double location) {
			this.location = location;
		}

		public Act() {
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(this.type);
			dest.writeString(this.id);
			dest.writeString(this.userName);
			dest.writeString(this.activityName);
			dest.writeString(this.mode);
			dest.writeString(this.activityStartTime);
			dest.writeString(this.activityAddress);
			dest.writeString(this.remarks);
			dest.writeString(this.manNumber);
			dest.writeString(this.womanNumber);
			dest.writeString(this.score);
			dest.writeString(this.activityType);
			dest.writeValue(this.userId);
			dest.writeString(this.activityCutoffTime);
			dest.writeString(this.headPortraitId);
			dest.writeString(this.birthday);
			dest.writeValue(this.age);
			dest.writeValue(this.topFlag);
			dest.writeDouble(this.lon);
			dest.writeDouble(this.lat);
			dest.writeDouble(this.location);
			dest.writeString(this.gender);
			dest.writeValue(this.vipGrade);
			dest.writeValue(this.evaluationSign);
		}

		protected Act(Parcel in) {
			this.type = in.readInt();
			this.id = in.readString();
			this.userName = in.readString();
			this.activityName = in.readString();
			this.mode = in.readString();
			this.activityStartTime = in.readString();
			this.activityAddress = in.readString();
			this.remarks = in.readString();
			this.manNumber = in.readString();
			this.womanNumber = in.readString();
			this.score = in.readString();
			this.activityType = in.readString();
			this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.activityCutoffTime = in.readString();
			this.headPortraitId = in.readString();
			this.birthday = in.readString();
			this.age = (Integer) in.readValue(Integer.class.getClassLoader());
			this.topFlag = (Integer) in.readValue(Integer.class.getClassLoader());
			this.lon = in.readDouble();
			this.lat = in.readDouble();
			this.location = in.readDouble();
			this.gender = in.readString();
			this.vipGrade = (Integer) in.readValue(Integer.class.getClassLoader());
			this.evaluationSign = (Integer) in.readValue(Integer.class.getClassLoader());
		}

		public static final Creator<Act> CREATOR = new Creator<Act>() {
			@Override
			public Act createFromParcel(Parcel source) {
				return new Act(source);
			}

			@Override
			public Act[] newArray(int size) {
				return new Act[size];
			}
		};
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.totalpage);
		dest.writeInt(this.page);
		dest.writeTypedList(this.list);
	}

	public ActivityInfo() {
	}

	protected ActivityInfo(Parcel in) {
		this.totalpage = in.readInt();
		this.page = in.readInt();
		this.list = in.createTypedArrayList(Act.CREATOR);
	}

	public static final Parcelable.Creator<ActivityInfo> CREATOR = new Parcelable.Creator<ActivityInfo>() {
		@Override
		public ActivityInfo createFromParcel(Parcel source) {
			return new ActivityInfo(source);
		}

		@Override
		public ActivityInfo[] newArray(int size) {
			return new ActivityInfo[size];
		}
	};
}
