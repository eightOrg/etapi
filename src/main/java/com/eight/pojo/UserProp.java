package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;

/**
 * Created by miao on 2016/10/17.
 */
public class UserProp extends BasePojo {
    private int userId;
    private int propId;
    private int count;
    private long createTime;
    private long changeTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
