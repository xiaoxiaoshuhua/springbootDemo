package com.example.demo1.aop.adviseImpl;

import com.example.demo1.aop.baseTest.MyAdvise;
import com.example.demo1.aop.baseTest.MyAdvisor;
import com.example.demo1.aop.baseTest.MyJoinPoint;

public class MyAspectJAdvisor implements MyAdvisor,Comparable {

    private MyAdvise myAdvise;

    private MyJoinPoint myJoinPoint;

    private int order;

    public MyAdvise getMyAdvise() {
        return myAdvise;
    }

    public void setMyAdvise(MyAdvise myAdvise) {
        this.myAdvise = myAdvise;
    }

    public MyJoinPoint getMyJoinPoint() {
        return myJoinPoint;
    }

    public void setMyJoinPoint(MyJoinPoint myJoinPoint) {
        this.myJoinPoint = myJoinPoint;
    }

    @Override
    public int compareTo(Object o) {
        if (this.myAdvise instanceof MyMethodBeforeAbstractAdvise){
            return 1;
        }
        if (this.myAdvise instanceof MyMethodAroundAbstractAdvise){
            return 0;
        }
        if (this.myAdvise instanceof MyMethodAfterAbstractAdvise){
            return -1;
        }
        return 0;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }
}
