package com.example.demo1.aop;

import com.example.demo1.annotationTest.MyAfter;
import com.example.demo1.annotationTest.MyAround;
import com.example.demo1.annotationTest.MyAspectJ;
import com.example.demo1.annotationTest.MyBefore;
import com.example.demo1.aop.adviseImpl.*;
import com.example.demo1.aop.baseTest.MyAbstractAdvise;
import com.example.demo1.aop.baseTest.MyAdvisor;
import com.example.demo1.aop.baseTest.MyMethodMatcher;
import com.example.demo1.aop.proxy.MyJdkDynamicAopProxy;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MyAspectJAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware, MyMethodMatcher {

    private Logger logger = LoggerFactory.getLogger(MyAspectJAutoProxyCreator.class);

    private BeanFactory beanFactory;

    /**
     * 需要汇集所有的advisors
     */
    private List<MyAdvisor> advisors = new ArrayList<>();

    /**
     * key:expression; value:pointMatcherParser
     */
    private Map<String, MyPointMatcherParser> pointMatcherParserMap = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 需要汇集所有的expression跟对应的处理method
     */
//    private Map<String, String> expressionMethodMap = new HashMap<>();//key:expression; value:method 用于先确定是否匹配，如果匹配则可以找到对应的方法
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //先构建advisor，应该查看pointMatcherParserMap中是否已经存在过expression，如果已经存在的话，说明这个advisor已经在里面了，不需要再去重构这个advisor了，暂时先这么搞
        if (CollectionUtils.isEmpty(advisors)) {
            try {
                buildAdvisors(advisors);
            } catch (Exception e) {
                logger.error("构建advisors失败", e);
            }
        }
        //然后进行匹配，如果匹配成功，则创建代理，不成功，则原样返回
        List<MyAdvisor> matchedAdvisorsOfBean = getMatchedAdvisorsOfBean(beanName, bean.getClass());
        if (!CollectionUtils.isEmpty(matchedAdvisorsOfBean)) {
            Object proxy = createProxy(
                    bean.getClass(), beanName, matchedAdvisorsOfBean, bean,applicationContext);
            return proxy;
        }
        return bean;
    }

    /**
     * 创建代理
     */
    private Object createProxy(Class<?> beanType, String beanName, List<MyAdvisor> matchedAdvisorsOfBean, Object sourceBean,ApplicationContext applicationContext) {
        return new MyJdkDynamicAopProxy(sourceBean, beanType, matchedAdvisorsOfBean,applicationContext).getProxy();
    }

    /**
     * 获取bean匹配的advisors
     *
     * @param beanName
     * @param beanType
     * @return
     */
    private List<MyAdvisor> getMatchedAdvisorsOfBean(String beanName, Class beanType) {
        System.out.println("进行匹配的bean：" + beanName);
        List<MyAdvisor> myAdvisors = new ArrayList<>();
        for (MyAdvisor myAdvisor : advisors) {
            MyAspectJAdvisor myAspectJAdvisor = (MyAspectJAdvisor) myAdvisor;
            MyAspectJExpressionPointcut joinPointTest = (MyAspectJExpressionPointcut) myAspectJAdvisor.getMyJoinPoint();
            MyPointMatcherParser myPointMatcherParser = pointMatcherParserMap.get(joinPointTest.getExpression());
            if (AnnotationUtils.findAnnotation(beanType, MyAspectJ.class) == null &&
                    AnnotationUtils.findAnnotation(beanType, Aspect.class) == null &&
                    myPointMatcherParser.matched(beanType.getName(), null)) {
                myAdvisors.add(myAdvisor);
            }
        }
        return myAdvisors;
    }

    private void buildAdvisors(List<MyAdvisor> advisors) throws Exception {
        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors((ListableBeanFactory) this.beanFactory, Object.class, true, false);
        for (String beanName : beanNames) {
            Class<?> beanType = this.beanFactory.getType(beanName);
            if (AnnotationUtils.findAnnotation(beanType, MyAspectJ.class) != null) {
                //构造advisor
                for (Method method : beanType.getDeclaredMethods()) {
                    MyAspectJAdvisor myAspectJAdvisor = new MyAspectJAdvisor();
                    if (method.isAnnotationPresent(MyBefore.class)) {
                        myAspectJAdvisor.setOrder(1);
                        MyBefore annotation = method.getAnnotation(MyBefore.class);
                        //构造advise对象
                        MyMethodBeforeAbstractAdvise myMethodBeforeAdvise = new MyMethodBeforeAbstractAdvise();
                        myMethodBeforeAdvise.setAdviseMeta(beanType, method.getName());
                        //构造pointCut对象
                        String expression = buildExpressionParser(method, annotation.value());
                        buildAspectJPointcut(method, myAspectJAdvisor, myMethodBeforeAdvise, expression);
                        advisors.add(myAspectJAdvisor);
                    }
                    if (method.isAnnotationPresent(MyAround.class)) {
                        myAspectJAdvisor.setOrder(0);
                        MyAround annotation = method.getAnnotation(MyAround.class);
                        //构造advise对象
                        MyMethodAroundAbstractAdvise myMethodAroundAdvise = new MyMethodAroundAbstractAdvise();
                        myMethodAroundAdvise.setAdviseMeta(beanType, method.getName());
                        //构造pointCut对象
                        String expression = buildExpressionParser(method, annotation.value());
                        buildAspectJPointcut(method, myAspectJAdvisor, myMethodAroundAdvise, expression);
                        advisors.add(myAspectJAdvisor);
                    }
                    if (method.isAnnotationPresent(MyAfter.class)) {
                        myAspectJAdvisor.setOrder(-1);
                        MyAfter annotation = method.getAnnotation(MyAfter.class);
                        //构造advise对象
                        MyMethodAfterAbstractAdvise myMethodAfterAbstractAdvise = new MyMethodAfterAbstractAdvise();
                        myMethodAfterAbstractAdvise.setAdviseMeta(beanType, method.getName());
                        //构造pointCut对象
                        String expression = buildExpressionParser(method, annotation.value());
                        buildAspectJPointcut(method, myAspectJAdvisor, myMethodAfterAbstractAdvise, expression);
                        advisors.add(myAspectJAdvisor);
                    }
                }
            }
        }
    }

    private void buildAspectJPointcut(Method method, MyAspectJAdvisor myAspectJAdvisor, MyAbstractAdvise myMethodBeforeAdvise, String expression) {
        MyAspectJExpressionPointcut myAspectJExpressionPointcut = new MyAspectJExpressionPointcut(expression, method.getName());
        myAspectJAdvisor.setMyAdvise(myMethodBeforeAdvise);
        myAspectJAdvisor.setMyJoinPoint(myAspectJExpressionPointcut);
    }

    private String buildExpressionParser(Method method, String value) throws Exception {
        String expression = value;
        if (StringUtils.isEmpty(expression)) {
            logger.error("方法" + method.getName() + "对应的expression为空");
            throw new Exception("expression为空");
        }
        if (pointMatcherParserMap.get(expression) == null) {
            //如果PointExpression不存在的话，则构建
            MyPointMatcherParser myPointMatcherParser = new MyPointMatcherParser(expression, method.getName());
            pointMatcherParserMap.put(expression, myPointMatcherParser);
        }
        return expression;
    }

    @Override
    public void matches() {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


}
