package com.example.demo1.aop.baseTest;

public interface MyJoinPoint {

    Boolean matched(String beanClassName, String methodName);

    Object proceed() throws Throwable;
}
