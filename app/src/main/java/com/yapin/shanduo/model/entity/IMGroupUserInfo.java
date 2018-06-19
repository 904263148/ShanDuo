package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.tencent.TIMGroupMemberRoleType;
import com.yapin.shanduo.utils.Constants;

import java.util.List;

/**
 * 作者：L on 2018/6/19 0019 10:17
 */
public class IMGroupUserInfo implements Parcelable {

    private String ActionStatus;
    private String ErrorCode;
    private List<IMGroupUserInfo.GroupMebInfo> MemberList;
    private int totalPage , MemberNum , page;

    public String getActionStatus() {
        return ActionStatus;
    }

    public void setActionStatus(String actionStatus) {
        ActionStatus = actionStatus;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public List<GroupMebInfo> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<GroupMebInfo> memberList) {
        MemberList = memberList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getMemberNum() {
        return MemberNum;
    }

    public void setMemberNum(int memberNum) {
        MemberNum = memberNum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public static class GroupMebInfo implements Parcelable {
        private String picture;
        private String Member_Account;
        private String NameCard , Role ,name ;

        private TIMGroupMemberRoleType roleType;

        public TIMGroupMemberRoleType getRoleType() {
            return roleType;
        }

        public void setRoleType(TIMGroupMemberRoleType roleType) {
            this.roleType = roleType;
        }

        private int type = Constants.TYPE_SHOW;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getMember_Account() {
            return Member_Account;
        }

        public void setMember_Account(String member_Account) {
            Member_Account = member_Account;
        }

        public String getNameCard() {
            return NameCard;
        }

        public void setNameCard(String nameCard) {
            NameCard = nameCard;
        }

        public String getRole() {
            return Role;
        }

        public void setRole(String role) {
            Role = role;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public GroupMebInfo() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.picture);
            dest.writeString(this.Member_Account);
            dest.writeString(this.NameCard);
            dest.writeString(this.Role);
            dest.writeString(this.name);
            dest.writeInt(this.roleType == null ? -1 : this.roleType.ordinal());
            dest.writeInt(this.type);
        }

        protected GroupMebInfo(Parcel in) {
            this.picture = in.readString();
            this.Member_Account = in.readString();
            this.NameCard = in.readString();
            this.Role = in.readString();
            this.name = in.readString();
            int tmpRoleType = in.readInt();
            this.roleType = tmpRoleType == -1 ? null : TIMGroupMemberRoleType.values()[tmpRoleType];
            this.type = in.readInt();
        }

        public static final Creator<GroupMebInfo> CREATOR = new Creator<GroupMebInfo>() {
            @Override
            public GroupMebInfo createFromParcel(Parcel source) {
                return new GroupMebInfo(source);
            }

            @Override
            public GroupMebInfo[] newArray(int size) {
                return new GroupMebInfo[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ActionStatus);
        dest.writeString(this.ErrorCode);
        dest.writeTypedList(this.MemberList);
        dest.writeInt(this.totalPage);
        dest.writeInt(this.MemberNum);
        dest.writeInt(this.page);
    }

    public IMGroupUserInfo() {
    }

    protected IMGroupUserInfo(Parcel in) {
        this.ActionStatus = in.readString();
        this.ErrorCode = in.readString();
        this.MemberList = in.createTypedArrayList(GroupMebInfo.CREATOR);
        this.totalPage = in.readInt();
        this.MemberNum = in.readInt();
        this.page = in.readInt();
    }

    public static final Parcelable.Creator<IMGroupUserInfo> CREATOR = new Parcelable.Creator<IMGroupUserInfo>() {
        @Override
        public IMGroupUserInfo createFromParcel(Parcel source) {
            return new IMGroupUserInfo(source);
        }

        @Override
        public IMGroupUserInfo[] newArray(int size) {
            return new IMGroupUserInfo[size];
        }
    };
}
