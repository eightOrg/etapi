package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;

/**
 * Created by Administrator on 2016/10/17.
 */
public class UserNote extends BasePojo {
    private int userId;
    private String createDate;
    private String modCode;
    private String noteName;
    private double noteExpense;
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

    public String getModCode() {
        return modCode;
    }

    public void setModCode(String modCode) {
        this.modCode = modCode;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public double getNoteExpense() {
        return noteExpense;
    }

    public void setNoteExpense(double noteExpense) {
        this.noteExpense = noteExpense;
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
