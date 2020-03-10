package com.example.demo1.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Aspect
//@Component
public class MyAspect2 {

    private Logger log = LoggerFactory.getLogger(MyAspect2.class);


    @Around("execution(* com.example.demo1.aop..*.*(..))")
    public Object aroundInvoke(ProceedingJoinPoint point)
            throws Throwable {
        this.log.info("[***Service***]执行参数2："
                + Arrays.toString(point.getArgs()));
        Object object = point.proceed(point.getArgs());
        this.log.info("[***Service***]返回结果2："
                + object);
        return object;
    }

    @Before("execution(* com.example.demo1.aop..*.*(..))")
    public void doBefore(JoinPoint point) throws Throwable {
        this.log.info("[***Service-Before***]执行参数2："
                + Arrays.toString(point.getArgs()));
//        Object object = point.proceed(point.getArgs());
//        this.log.info("[***Service-After***]返回结果："
//                + object);
    }

    @After("execution(* com.example.demo1.aop..*.*(..))")
    public void doAfter(JoinPoint point) throws Throwable {
        this.log.info("[***Service-After***]执行参数2："
                + Arrays.toString(point.getArgs()));
//        Object object = point.proceed(point.getArgs());
//        this.log.info("[***Service-After***]返回结果："
//                + object);
    }
}
