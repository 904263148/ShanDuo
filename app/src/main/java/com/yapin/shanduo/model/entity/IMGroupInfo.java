package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：L on 2018/6/14 0014 16:24
 */
public class IMGroupInfo implements Parcelable {

    private String ActionStatus;
    private String ErrorCode;
    private List<GroupInfo> GroupIdList;
    private int TotalCount;

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

    public List<GroupInfo> getGroupIdList() {
        return GroupIdList;
    }

    public void setGroupIdList(List<GroupInfo> groupIdList) {
        GroupIdList = groupIdList;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    static class GroupInfo implements Parcelable {
        private String FaceUrl;
        private String GroupId;
        private String LastMsgTime;
        private String Name;

        public String getFaceUrl() {
            return FaceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            FaceUrl = faceUrl;
        }

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String groupId) {
            GroupId = groupId;
        }

        public String getLastMsgTime() {
            return LastMsgTime;
        }

        public void setLastMsgTime(String lastMsgTime) {
            LastMsgTime = lastMsgTime;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.FaceUrl);
            dest.writeString(this.GroupId);
            dest.writeString(this.LastMsgTime);
            dest.writeString(this.Name);
        }

        public GroupInfo() {
        }

        protected GroupInfo(Parcel in) {
            this.FaceUrl = in.readString();
            this.GroupId = in.readString();
            this.LastMsgTime = in.readString();
            this.Name = in.readString();
        }

        public static final Parcelable.Creator<GroupInfo> CREATOR = new Parcelable.Creator<GroupInfo>() {
            @Override
            public GroupInfo createFromParcel(Parcel source) {
                return new GroupInfo(source);
            }

            @Override
            public GroupInfo[] newArray(int size) {
                return new GroupInfo[size];
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
        dest.writeTypedList(this.GroupIdList);
        dest.writeInt(this.TotalCount);
    }

    public IMGroupInfo() {
    }

    protected IMGroupInfo(Parcel in) {
        this.ActionStatus = in.readString();
        this.ErrorCode = in.readString();
        this.GroupIdList = in.createTypedArrayList(GroupInfo.CREATOR);
        this.TotalCount = in.readInt();
    }

    public static final Parcelable.Creator<IMGroupInfo> CREATOR = new Parcelable.Creator<IMGroupInfo>() {
        @Override
        public IMGroupInfo createFromParcel(Parcel source) {
            return new IMGroupInfo(source);
        }

        @Override
        public IMGroupInfo[] newArray(int size) {
            return new IMGroupInfo[size];
        }
    };
}
