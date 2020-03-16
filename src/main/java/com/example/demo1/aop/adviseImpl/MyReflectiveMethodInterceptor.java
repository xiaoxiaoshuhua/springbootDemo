package com.example.demo1.aop.adviseImpl;

import com.example.demo1.aop.baseTest.MyAdvisor;
import com.example.demo1.aop.baseTest.MyJoinPoint;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;


public class MyReflectiveMethodInterceptor implements MethodInterceptor , MyJoinPoint , MethodInvocation{


    protected final Method method;

    protected Object[] arguments;

    protected final Object proxy;

    @Nullable
    private final Class<?> targetClass;

    private Object target;

    private final List<MyAdvisor> myAdvisors;

    private ApplicationContext applicationContext;

    int interceptorIndex = -1;


    public MyReflectiveMethodInterceptor(List<MyAdvisor> myAdvisors,Object target,Method method,
                                         Object[] arguments,Object proxy,Class<?> targetClass,ApplicationContext applicationContext){
        this.myAdvisors = myAdvisors;
        this.method = method;
        this.target = target;
        this.proxy = proxy;
        this.targetClass = targetClass;
        this.arguments = arguments;
        this.applicationContext = applicationContext;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Boolean matched(String beanClassName, String methodName) {
        return null;
    }

    @Override
    public Object proceed() throws Throwable {
        if (interceptorIndex == myAdvisors.size()-1)return this;
        MyAspectJAdvisor myAdvisor = (MyAspectJAdvisor) myAdvisors.get(++interceptorIndex);
        Object myAdvise = myAdvisor.getMyAdvise();
        if (myAdvise instanceof MethodInterceptor){
            return ((MethodInterceptor) myAdvise).invoke(this);
        }
        return null;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return null;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
