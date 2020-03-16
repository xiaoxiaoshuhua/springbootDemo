package com.example.demo1.aop.adviseImpl;

import com.example.demo1.aop.baseTest.MyAbstractAdvise;
import com.example.demo1.aop.baseTest.MyJoinPoint;
import com.example.demo1.aopTest.MyAspectJTest;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;


public class MyMethodBeforeAbstractAdvise extends MyAbstractAdvise {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        ApplicationContext applicationContext = ((MyReflectiveMethodInterceptor) invocation).getApplicationContext();
        MyAspectJTest myAspectJTest = (MyAspectJTest) applicationContext.getBean("myAspectJTest");
        Method[] declaredMethods = getBeanType().getDeclaredMethods();
        for (Method method:declaredMethods) {
            if (method.getName().equals(getMethodName())){
                method.invoke(myAspectJTest,(MyJoinPoint)invocation);
            }
        }
//        Class<?>[] cArg = new Class<?>[1];
//        cArg[0] = MyJoinPoint.class;
//        getBeanType().getDeclaredMethod(getMethodName(),cArg).invoke(myAspectJTest);
        return invocation.proceed();
    }
}
