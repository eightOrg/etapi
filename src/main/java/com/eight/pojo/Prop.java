package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;

/**
 * Created by miaoch on 2016/10/17.
 */
public class Prop extends BasePojo {
    private int id;
    private String name;
    private int spend;
    private String description;
    private int stock;
    private String picture;
    private int buyLimit;
    private int buyLimitTime;
    private int useLimit;
    private int useLimitTime;
    private long createTime;
    private long changeTime;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpend() {
        return spend;
    }

    public void setSpend(int spend) {
        this.spend = spend;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }

    public int getBuyLimitTime() {
        return buyLimitTime;
    }

    public void setBuyLimitTime(int buyLimitTime) {
        this.buyLimitTime = buyLimitTime;
    }

    public int getUseLimit() {
        return useLimit;
    }

    public void setUseLimit(int useLimit) {
        this.useLimit = useLimit;
    }

    public int getUseLimitTime() {
        return useLimitTime;
    }

    public void setUseLimitTime(int useLimitTime) {
        this.useLimitTime = useLimitTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
