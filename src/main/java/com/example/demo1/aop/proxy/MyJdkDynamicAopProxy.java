package com.example.demo1.aop.proxy;

import com.example.demo1.aop.baseTest.MyAdvisor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

public class MyJdkDynamicAopProxy implements InvocationHandler, MyAopProxy, Serializable {

    private Object target;

    private Class beanType;

    private List<MyAdvisor> matchedAdvisors;

    public MyJdkDynamicAopProxy(Object target, Class beanType, List<MyAdvisor> matchedAdvisors) {
        this.target = target;
        this.beanType = beanType;
        this.matchedAdvisors = matchedAdvisors;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), beanType.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        System.out.println("invoke begin");
        System.out.println("method :" + method.getName() + " is invoked!");
        method.invoke(target, args);
        System.out.println("invoke end");
        return null;
    }


}
