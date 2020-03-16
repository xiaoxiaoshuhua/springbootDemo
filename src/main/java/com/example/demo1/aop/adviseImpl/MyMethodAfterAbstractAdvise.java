package com.example.demo1.aop.adviseImpl;

import com.example.demo1.aop.baseTest.MyAbstractAdvise;
import org.aopalliance.intercept.MethodInvocation;

public class MyMethodAfterAbstractAdvise extends MyAbstractAdvise {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        return invocation.proceed();
    }
}
