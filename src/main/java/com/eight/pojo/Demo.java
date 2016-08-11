package com.eight.pojo;

import com.eight.trundle.db.pojo.Identifiable;

/**
 * Created by miaoch on 2016/8/11.
 */
public class Demo implements Identifiable{
    private int id;
    private String state;
    private long createTime;
    private long changeTime;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public Long getChangeTime() {
        return changeTime;
    }

    @Override
    public void setChangeTime(Long changeTime) {
        this.changeTime = changeTime;
    }
}
