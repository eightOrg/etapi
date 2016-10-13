package com.eight.trundle.ob;


/**
 * Created by miaoch on 2016/4/5.
 */
public class OneOb<T> extends BaseOb {
    private T ob;

    public OneOb(T ob) {
        this.ob = ob;
    }

    public OneOb() { }

    public T getOb() {
        return ob;
    }

    public OneOb<T> setOb(T ob) {
        this.ob = ob;
        return this;
    }
}
