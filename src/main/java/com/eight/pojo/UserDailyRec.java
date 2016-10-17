package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;


/**
 * Created by Administrator on 2016/10/17.
 */
public class UserDailyRec extends BasePojo {
    private int userId;
    private String createDate;
    private String comTask;
    private String isSign;
    private String isRecord;
    private String isDiary;
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

    public String getComTask() {
        return comTask;
    }

    public void setComTask(String comTask) {
        this.comTask = comTask;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }

    public String getIsRecord() {
        return isRecord;
    }

    public void setIsRecord(String isRecord) {
        this.isRecord = isRecord;
    }

    public String getIsDiary() {
        return isDiary;
    }

    public void setIsDiary(String isDiary) {
        this.isDiary = isDiary;
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
