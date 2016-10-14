package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;
import com.eight.trundle.db.pojo.Identifiable;

import java.io.Serializable;

/**
 * Created by miaoch on 2016/8/11.
 */
public class UserRegister extends BasePojo {
    private String mobilephone;
    private String password;
    private String code;
    private long createTime;
    private String state;

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
