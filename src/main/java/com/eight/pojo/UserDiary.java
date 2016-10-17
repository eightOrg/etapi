package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;

/**
 * Created by Administrator on 2016/10/17.
 */
public class UserDiary extends BasePojo {
    private int userId;
    private String createDate;
    private String weather;
    private String mood;
    private String content;
    private String picture;
    private long createTime;
    private long changeTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
}
