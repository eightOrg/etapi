package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;

/**
 * Created by Administrator on 2016/10/17.
 */
public class UserModule extends BasePojo {
    private int userId;
    private String modCode;
    private String parModCode;
    private String subModCode;
    private String subRewrite;
    private long createTime;
    private long changeTime;
    private String state;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getModCode() {
        return modCode;
    }

    public void setModCode(String modCode) {
        this.modCode = modCode;
    }

    public String getParModCode() {
        return parModCode;
    }

    public void setParModCode(String parModCode) {
        this.parModCode = parModCode;
    }

    public String getSubModCode() {
        return subModCode;
    }

    public void setSubModCode(String subModCode) {
        this.subModCode = subModCode;
    }

    public String getSubRewrite() {
        return subRewrite;
    }

    public void setSubRewrite(String subRewrite) {
        this.subRewrite = subRewrite;
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
