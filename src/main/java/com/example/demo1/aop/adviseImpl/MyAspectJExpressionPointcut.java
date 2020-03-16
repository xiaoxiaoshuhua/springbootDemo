package com.example.demo1.aop.adviseImpl;

import com.example.demo1.aop.baseTest.MyJoinPoint;

public class MyAspectJExpressionPointcut implements MyJoinPoint {

    private String expression;

    private String methodName;

    public MyAspectJExpressionPointcut(String expression, String methodName){
        this.expression = expression;
        this.methodName = methodName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public Boolean matched(String beanClassName, String methodName) {
        return null;
    }

    @Override
    public Object proceed() throws Throwable {
        return null;
    }
}
