package com.demo.untils.ob;

/**
 * Created by miaoch on 2016/4/5.
 */

public    class BaseOb {
    private boolean flag=true;  //true=正确  false=失败
    private int code=200 ;    //0=正确     ！0=其他
    private   String msg="ok";    // 返回的消息说明  ，可为null

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
