package com.hlt.exceptions;

public class ProductCategoryOperationException extends RuntimeException{

//    private static final SerialVersionUID = 1182563719599527969L;

    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}