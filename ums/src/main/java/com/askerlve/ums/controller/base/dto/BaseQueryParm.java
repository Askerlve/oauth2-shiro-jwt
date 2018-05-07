package com.askerlve.ums.controller.base.dto;

import java.io.Serializable;


public class BaseQueryParm implements Serializable {


    private static final long serialVersionUID = -8119221925102476893L;

    private static final String ORDER_ASC="asc";

    protected int pageSize = 1;

    protected int pageNum = 10;

    protected String sortOrder = BaseQueryParm.ORDER_ASC;

    protected String sortName;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public boolean isAsc(){
        return BaseQueryParm.ORDER_ASC.equals(this.sortOrder) ? true : false;
    }
}
