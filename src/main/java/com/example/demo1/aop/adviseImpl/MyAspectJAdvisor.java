package com.example.demo1.aop.adviseImpl;

import com.example.demo1.aop.baseTest.MyAdvise;
import com.example.demo1.aop.baseTest.MyAdvisor;
import com.example.demo1.aop.baseTest.MyJoinPoint;

public class MyAspectJAdvisor implements MyAdvisor {

    private MyAdvise myAdvise;

    private MyJoinPoint myJoinPoint;

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
}
