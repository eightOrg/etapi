package com.eight.pojo;

import com.eight.trundle.db.pojo.BasePojo;

/**
 * Created by Administrator on 2016/10/17.
 */
public class Area extends BasePojo {
    private int id;
    private int parId;
    private String areaName;
    private String pyName;
    private String zipCode;
    private String areaType;
    private String special;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParId() {
        return parId;
    }

    public void setParId(int parId) {
        this.parId = parId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPyName() {
        return pyName;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }
}
