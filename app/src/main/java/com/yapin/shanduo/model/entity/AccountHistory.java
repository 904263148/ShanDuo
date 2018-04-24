package com.yapin.shanduo.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AccountHistory {

    @Id(autoincrement = true)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String img_url;
    @Generated(hash = 584231623)
    public AccountHistory(long id, @NotNull String name, @NotNull String img_url) {
        this.id = id;
        this.name = name;
        this.img_url = img_url;
    }
    @Generated(hash = 1046294005)
    public AccountHistory() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImg_url() {
        return this.img_url;
    }
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }



}
