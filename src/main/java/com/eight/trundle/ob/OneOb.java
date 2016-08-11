package com.eight.trundle.ob;

import com.eight.trundle.db.pojo.Identifiable;

/**
 * Created by miaoch on 2016/4/5.
 */
public class OneOb<T extends Identifiable> extends BaseOb {
    public OneOb(T ob) {
        this.ob = ob;
    }
    public OneOb(boolean flag,int code,String msg) {

        super.setCode(code);
        super.setMsg(msg);
        super.setFlag(flag);

    }

    public OneOb() { }

    private T ob;

    public T getOb() {
        return ob;
    }

    public void setOb(T ob) {
        this.ob = ob;
    }
}
