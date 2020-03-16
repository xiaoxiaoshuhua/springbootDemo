package com.example.demo1.aopTest;

import com.example.demo1.annotationTest.MyAfter;
import com.example.demo1.annotationTest.MyAround;
import com.example.demo1.annotationTest.MyAspectJ;
import com.example.demo1.annotationTest.MyBefore;
import com.example.demo1.aop.baseTest.MyJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@MyAspectJ
@Component
public class MyAspectJTest {

    private Logger log = LoggerFactory.getLogger(MyAspectJTest.class);

    @MyBefore("execution(* com.example.demo1.aop..*.*(..))")
    public void doBefore(MyJoinPoint point) throws Throwable {
        this.log.info("[***Service-Before-Test***]执行参数：");
//        Object object = point.proceed(point.getArgs());
//        this.log.info("[***Service-After***]返回结果："
//                + object);
    }

    @MyAround("execution(* com.example.demo1.aop..*.*(..))")
    public void doAround(MyJoinPoint pointTest) throws Throwable {
        this.log.info("[***Service-Aound1-Test***]执行参数：");
        pointTest.proceed();
        this.log.info("[***Service-Aound2-Test***]执行参数：");
    }

    @MyAfter("execution(* com.example.demo1.aop..*.*(..))")
    public void doAfter(MyJoinPoint pointTest) throws Throwable {
        this.log.info("[***Service-After-Test***]执行参数：");
    }
}
