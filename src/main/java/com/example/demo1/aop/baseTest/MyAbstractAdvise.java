package com.example.demo1.aop.baseTest;

public abstract class MyAbstractAdvise implements MyAdvise {

    private Class beanType;

    private String methodName;

    public void setAdviseMeta(Class beanType,String methodName){
        this.beanType = beanType;
        this.methodName = methodName;
    }

}
