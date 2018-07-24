package com.yapin.shanduo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：L on 2018/7/24 0024 09:26
 */
public class CustomerServiceInfo implements Parcelable {

    private boolean success;

    private List<Customer> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Customer> getResult() {
        return result;
    }

    public void setResult(List<Customer> result) {
        this.result = result;
    }

    public static class Customer implements Parcelable {

        private String name , userId , picture;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.userId);
            dest.writeString(this.picture);
        }

        public Customer() {
        }

        protected Customer(Parcel in) {
            this.name = in.readString();
            this.userId = in.readString();
            this.picture = in.readString();
        }

        public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
            @Override
            public Customer createFromParcel(Parcel source) {
                return new Customer(source);
            }

            @Override
            public Customer[] newArray(int size) {
                return new Customer[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.result);
    }

    public CustomerServiceInfo() {
    }

    protected CustomerServiceInfo(Parcel in) {
        this.success = in.readByte() != 0;
        this.result = in.createTypedArrayList(Customer.CREATOR);
    }

    public static final Parcelable.Creator<CustomerServiceInfo> CREATOR = new Parcelable.Creator<CustomerServiceInfo>() {
        @Override
        public CustomerServiceInfo createFromParcel(Parcel source) {
            return new CustomerServiceInfo(source);
        }

        @Override
        public CustomerServiceInfo[] newArray(int size) {
            return new CustomerServiceInfo[size];
        }
    };
}
