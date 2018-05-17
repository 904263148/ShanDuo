package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.yapin.shanduo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态评论内容INFO
 * @ClassName: CommentInfo
 * @Description: TODO
 * @author fanshixin
 * @date 2018年5月15日 下午7:27:01
 *
 */
public class CommentInfo implements Parcelable {

	private int totalPage;
	private int page;
	private List<Comment> list;

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Comment> getList() {
		return list;
	}

	public void setList(List<Comment> list) {
		this.list = list;
	}

	public static class Comment implements Parcelable {

		private int type = Constants.TYPE_SHOW;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		private String id;//评论ID
		private String dynamicId;//动态ID
		private Integer userId;//用户ID
		private String portraitId;//头像
		private String name;//昵称
		private String comment;//评论内容
		private List<String> picture;//图片
		private String delFlag;//删除标记
		private List<SecondComment.Comments> comments;//2级回复集合

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDynamicId() {
			return dynamicId;
		}

		public void setDynamicId(String dynamicId) {
			this.dynamicId = dynamicId;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getPortraitId() {
			return portraitId;
		}

		public void setPortraitId(String portraitId) {
			this.portraitId = portraitId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public List<String> getPicture() {
			return picture;
		}

		public void setPicture(List<String> picture) {
			this.picture = picture;
		}

		public String getDelFlag() {
			return delFlag;
		}

		public void setDelFlag(String delFlag) {
			this.delFlag = delFlag;
		}

		public List<SecondComment.Comments> getComments() {
			return comments;
		}

		public void setComments(List<SecondComment.Comments> comments) {
			this.comments = comments;
		}

		public Comment() {
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(this.type);
			dest.writeString(this.id);
			dest.writeString(this.dynamicId);
			dest.writeValue(this.userId);
			dest.writeString(this.portraitId);
			dest.writeString(this.name);
			dest.writeString(this.comment);
			dest.writeStringList(this.picture);
			dest.writeString(this.delFlag);
			dest.writeList(this.comments);
		}

		protected Comment(Parcel in) {
			this.type = in.readInt();
			this.id = in.readString();
			this.dynamicId = in.readString();
			this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
			this.portraitId = in.readString();
			this.name = in.readString();
			this.comment = in.readString();
			this.picture = in.createStringArrayList();
			this.delFlag = in.readString();
			this.comments = new ArrayList<SecondComment.Comments>();
			in.readList(this.comments, SecondComment.Comments.class.getClassLoader());
		}

		public static final Creator<Comment> CREATOR = new Creator<Comment>() {
			@Override
			public Comment createFromParcel(Parcel source) {
				return new Comment(source);
			}

			@Override
			public Comment[] newArray(int size) {
				return new Comment[size];
			}
		};
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.totalPage);
		dest.writeInt(this.page);
		dest.writeTypedList(this.list);
	}

	public CommentInfo() {
	}

	protected CommentInfo(Parcel in) {
		this.totalPage = in.readInt();
		this.page = in.readInt();
		this.list = in.createTypedArrayList(Comment.CREATOR);
	}

	public static final Parcelable.Creator<CommentInfo> CREATOR = new Parcelable.Creator<CommentInfo>() {
		@Override
		public CommentInfo createFromParcel(Parcel source) {
			return new CommentInfo(source);
		}

		@Override
		public CommentInfo[] newArray(int size) {
			return new CommentInfo[size];
		}
	};
}
