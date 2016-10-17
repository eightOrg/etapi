package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;


/**
 * Created by Administrator on 2016/10/17.
 */
public class UserAllRec extends BasePojo {
    private int id;
    private int userId;
    private int openCode;
    private String openName;
    private String description;
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOpenCode() {
        return openCode;
    }

    public void setOpenCode(int openCode) {
        this.openCode = openCode;
    }

    public String getOpenName() {
        return openName;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
