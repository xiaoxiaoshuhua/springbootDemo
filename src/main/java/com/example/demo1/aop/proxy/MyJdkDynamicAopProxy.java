package com.example.demo1.aop.proxy;

import com.example.demo1.aop.adviseImpl.MyAspectJAdvisor;
import com.example.demo1.aop.adviseImpl.MyReflectiveMethodInterceptor;
import com.example.demo1.aop.baseTest.MyAdvisor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.comparator.ComparableComparator;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyJdkDynamicAopProxy implements InvocationHandler, MyAopProxy, Serializable {

    private Object target;

    private Class beanType;

    private List<MyAdvisor> matchedAdvisors;

    private List<Class<?>> interfaces = new ArrayList<>();

    private ApplicationContext applicationContext;



    public MyJdkDynamicAopProxy(Object target, Class beanType, List<MyAdvisor> matchedAdvisors,ApplicationContext applicationContext) {
        this.target = target;
        this.beanType = beanType;
        this.matchedAdvisors = matchedAdvisors;
        this.applicationContext = applicationContext;
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
        Class<?>[] proxyInterfaces = new Class[interfaces.size()];
        int i = 0;
        for (Class<?> clazz: interfaces){
            proxyInterfaces[i++] = clazz;
        }
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), proxyInterfaces, this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        System.out.println("invoke begin");
        System.out.println("method :" + method.getName() + " is invoked!");
        //获取匹配的advisors，然后在前后执行即可
        if (!CollectionUtils.isEmpty(matchedAdvisors)){
//            for(int i=0;i<matchedAdvisors.size()-1;i++) {
//                if (i + 1 < matchedAdvisors.size()){
//                    MyAspectJAdvisor myAdvisor = (MyAspectJAdvisor) matchedAdvisors.get(i);
//                    MyAspectJAdvisor myAdvisor1 = (MyAspectJAdvisor) matchedAdvisors.get(i+1);
//                    if (myAdvisor.getOrder() < myAdvisor1.getOrder()){
//                        matchedAdvisors.add(i,myAdvisor1);
//                        matchedAdvisors.add(i+1,myAdvisor);
//                    }
//
//                }
//            }
//            matchedAdvisors.stream().sorted(Comparator.comparing(MyAspectJAdvisor::getOrder));
            Collections.sort(matchedAdvisors, new Comparator<MyAdvisor>(){
                public int compare(MyAdvisor o1, MyAdvisor o2) {
                    //按照CityModel的city_code字段进行降序排列
                    if(o1.getOrder() < o2.getOrder()){
                        return 1;
                    }
                    if(o1.getOrder() == o2.getOrder()){
                        return 0;
                    }
                    return -1;
                }
            });
            MethodInvocation invocation = new MyReflectiveMethodInterceptor(matchedAdvisors,target,
                    method,args,this,o.getClass(),applicationContext);
            invocation.proceed();
        }
//        method.invoke(target, args);
        System.out.println("invoke end");
        return null;
    }


}
