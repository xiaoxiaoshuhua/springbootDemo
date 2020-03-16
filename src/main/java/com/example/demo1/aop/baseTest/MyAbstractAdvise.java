package com.example.demo1.aop.baseTest;

import org.aopalliance.intercept.MethodInterceptor;

public abstract class MyAbstractAdvise implements MyAdvise , MethodInterceptor {

    private Class beanType;

    private String methodName;

    public void setAdviseMeta(Class beanType,String methodName){
        this.beanType = beanType;
        this.methodName = methodName;
    }

    public Class getBeanType() {
        return beanType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
