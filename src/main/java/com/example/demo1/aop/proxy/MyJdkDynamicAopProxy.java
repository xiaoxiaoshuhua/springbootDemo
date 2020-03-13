package com.example.demo1.aop.proxy;

import com.example.demo1.aop.baseTest.MyAdvisor;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class MyJdkDynamicAopProxy implements InvocationHandler, MyAopProxy, Serializable {

    private Object target;

    private Class beanType;

    private List<MyAdvisor> matchedAdvisors;

    private List<Class<?>> interfaces = new ArrayList<>();



    public MyJdkDynamicAopProxy(Object target, Class beanType, List<MyAdvisor> matchedAdvisors) {
        this.target = target;
        this.beanType = beanType;
        this.matchedAdvisors = matchedAdvisors;
    }

    @Override
    public Object getProxy() {
        Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(beanType, ClassUtils.getDefaultClassLoader());
        boolean hasReasonableProxyInterface = true;
        if (hasReasonableProxyInterface) {
            // Must allow for introductions; can't just set interfaces to the target's interfaces only.
            for (Class<?> ifc : targetInterfaces) {
                interfaces.add(ifc);
            }
        }
        Class<?>[] proxyInterfaces = (Class<?>[]) interfaces.toArray();
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), proxyInterfaces, this);
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
