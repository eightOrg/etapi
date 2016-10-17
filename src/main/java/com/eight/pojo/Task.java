package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;

/**
 * Created by Administrator on 2016/10/17.
 */
public class Task extends BasePojo {
    private int id;
    private String taskName;
    private String description;
    private String type;
    private int exp;
    private int gold;
    private int friendly;
    private long createTime;
    private long changeTime;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getFriendly() {
        return friendly;
    }

    public void setFriendly(int friendly) {
        this.friendly = friendly;
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
