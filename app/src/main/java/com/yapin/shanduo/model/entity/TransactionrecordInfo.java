package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.yapin.shanduo.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2018/6/4.
 */

public class TransactionrecordInfo implements Parcelable {

    private int totalPage;
    private List<TransactionrecordInfo.Trend> list;
    public TransactionrecordInfo(){}
    private int type = Constants.TYPE_SHOW;
    private String id;

    public List<Trend> getList() {
        return list;
    }

    public void setList(List<Trend> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionrecordInfo(int totalPage, List<TransactionrecordInfo> list, int type, String id) {

        this.totalPage = totalPage;
        this.id = id;
    }

    public static class Trend implements Parcelable {
        private int type = Constants.TYPE_SHOW;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Trend(int type) {

            this.type = type;
        }

        private String moneyType;       //类型1充值 2消费 9赠送闪多豆 10消费闪多豆
        private String amount;      //金额
        private String remarks;     //说明
        private long createDate;       //时间

        public Trend(String moneyType, String amount, String remarks, long createDate) {
            this.moneyType = moneyType;
            this.amount = amount;
            this.remarks = remarks;
            this.createDate = createDate;
        }

        public String getMoneyType() {
            return moneyType;
        }

        public void setMoneyType(String moneyType) {
            this.moneyType = moneyType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public Trend() {
        }

        public Trend(int type, String moneyType, String amount, String remarks, long createDate) {
            this.type = type;
            this.moneyType = moneyType;
            this.amount = amount;
            this.remarks = remarks;
            this.createDate = createDate;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.type);
            dest.writeString(this.moneyType);
            dest.writeString(this.amount);
            dest.writeString(this.remarks);
            dest.writeLong(this.createDate);
        }

        protected Trend(Parcel in) {
            this.type = in.readInt();
            this.moneyType = in.readString();
            this.amount = in.readString();
            this.remarks = in.readString();
            this.createDate = in.readLong();
        }

        public static final Creator<Trend> CREATOR = new Creator<Trend>() {
            @Override
            public Trend createFromParcel(Parcel source) {
                return new Trend(source);
            }

            @Override
            public Trend[] newArray(int size) {
                return new Trend[size];
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
        dest.writeTypedList(this.list);
        dest.writeString(this.id);
    }

    protected TransactionrecordInfo(Parcel in) {
        this.totalPage = in.readInt();
        this.list = in.createTypedArrayList(Trend.CREATOR);
        this.id = in.readString();
    }

    public static final Creator<TransactionrecordInfo> CREATOR = new Creator<TransactionrecordInfo>() {
        @Override
        public TransactionrecordInfo createFromParcel(Parcel source) {
            return new TransactionrecordInfo(source);
        }

        @Override
        public TransactionrecordInfo[] newArray(int size) {
            return new TransactionrecordInfo[size];
        }
    };
}
