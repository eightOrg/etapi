package com.eight.trundle.ob;

import com.eight.trundle.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miaoch on 2016/4/5.
 */

public class BaseOb {
    private boolean flag = true;  //true=正确  false=失败
    private int code = Constants.CODE_OK;
    private String msg = Constants.CODEMAP.get(Constants.CODE_OK);
    private Map extVal = new HashMap();

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

    public BaseOb setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseOb setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseOb setMsg(String msg) {
        this.msg = msg;
        return this;
    }


    public Map getExtVal() {
        return extVal;
    }

    public void setExtVal(Map extVal) {
        this.extVal = extVal;
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
