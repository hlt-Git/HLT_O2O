package com.hlt.util;

//将页数pageIndex转化为行数rowIndex
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}