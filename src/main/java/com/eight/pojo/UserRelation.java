package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;

/**
 * Created by miaoch on 2016/10/17.
 */
public class UserRelation extends BasePojo {
    private int souId;
    private int desId;
    private int friendly;
    private String remark;
    private long createTime;
    private long changeTime;
    private String state;

    public int getSouId() {
        return souId;
    }

    public void setSouId(int souId) {
        this.souId = souId;
    }

    public int getDesId() {
        return desId;
    }

    public void setDesId(int desId) {
        this.desId = desId;
    }

    public int getFriendly() {
        return friendly;
    }

    public void setFriendly(int friendly) {
        this.friendly = friendly;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
