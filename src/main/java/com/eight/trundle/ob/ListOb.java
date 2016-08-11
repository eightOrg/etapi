package com.eight.trundle.ob;

import com.eight.trundle.db.pojo.Identifiable;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import java.util.List;

/**
 * Created by miaoch on 2016/4/5.
 */
public class ListOb<T extends Identifiable> extends BaseOb {

    private int total;     //共多少条
    private int count;     //每页多少条
    private int pagetotal;     //共多少页
    private boolean hasNextPage; //有下一页
    private boolean hasPrePage; //有上一页

    private int nopage;  //第几页


    private List<T> listob;

    public ListOb() {
    }

    public ListOb(List<T> listob) {
        this.listob = listob;
    }
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public List<T> getListob() {
        return listob;
    }

    public void setListob(List<T> listob) {
        this.listob = listob;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPagetotal() {
        return pagetotal;
    }

    public void setPagetotal(int pagetotal) {
        this.pagetotal = pagetotal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNopage() {
        return nopage;
    }

    public void setNopage(int nopage) {
        this.nopage = nopage;
    }

    public void setPage(Paginator p) {

        total = p.getTotalCount();     //共多少条
        count = p.getLimit();     //每页多少条
        pagetotal = p.getTotalPages();     //共多少页
        nopage = p.getPage();  //第几页
        hasNextPage = p.isHasNextPage(); //有下一页
        hasPrePage = p.isHasPrePage(); //有上一页

    }
}
