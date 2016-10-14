package com.eight.pojo;

import java.io.Serializable;

/**
 * Created by miaoch on 2016/8/11.
 */
public class User implements Serializable {
    private int id;
    private String state;
    private long createTime;
    private long changeTime;
    private String username;
    private String password;
    private String mobilephone;
    private String realname;
    private String description;
    private int areaId;
    private int level;
    private int gold;
    private int exp;
    private String picture;
    private Double monIncome;
    private Double dayIncome;
    private Double monExpense;
    private Double dayExpense;
    private String isRemind;
    private String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Long changeTime) {
        this.changeTime = changeTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(Double monIncome) {
        this.monIncome = monIncome;
    }

    public Double getDayIncome() {
        return dayIncome;
    }

    public void setDayIncome(Double dayIncome) {
        this.dayIncome = dayIncome;
    }

    public Double getMonExpense() {
        return monExpense;
    }

    public void setMonExpense(Double monExpense) {
        this.monExpense = monExpense;
    }

    public Double getDayExpense() {
        return dayExpense;
    }

    public void setDayExpense(Double dayExpense) {
        this.dayExpense = dayExpense;
    }

    public String getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(String isRemind) {
        this.isRemind = isRemind;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
