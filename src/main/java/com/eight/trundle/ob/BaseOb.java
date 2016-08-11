package com.eight.trundle.ob;

import com.eight.trundle.Constants;

/**
 * Created by miaoch on 2016/4/5.
 */

public class BaseOb {
    private boolean flag = true;  //true=正确  false=失败
    private int code = Constants.CODE_OK;
    private String msg = Constants.CODEMAP.get(Constants.CODE_OK);

    public BaseOb(boolean flag, int code, String msg) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
    }

    public BaseOb() {
    }

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

    /**
     * 验证失败的ob模板
     * @return
     */
    public static BaseOb getLoseOb() {
        return new BaseOb(false, Constants.CODE_LOSE, Constants.CODEMAP.get(Constants.CODE_LOSE));
    }

    /**
     * 请求失败的ob模板
     * @return
     */
    public static BaseOb getFaildOb() {
        return new BaseOb(false, Constants.CODE_FAILD, Constants.CODEMAP.get(Constants.CODE_FAILD));
    }
}
