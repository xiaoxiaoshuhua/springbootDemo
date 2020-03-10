package com.example.demo1.aop.adviseImpl;

import com.example.demo1.aop.baseTest.MyJoinPoint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装了一个expression，包括其对应的处理方法名称，以及解构后的对象等
 */
public class MyPointMatcherParser implements MyJoinPoint {

    private Logger logger = LoggerFactory.getLogger(MyPointMatcherParser.class);

    private String expression;

    private String methodName;

    private String returnType;

    private String classPathPrex;

    private String methodPath;

    private MyPointExpression myPointExpression;

    public MyPointMatcherParser(String expression, String methodName) {
        this.expression = expression;
        this.methodName = methodName;
        if (this.myPointExpression == null) {
            this.myPointExpression = obtainPointExpression();
        }
    }

    /**
     * 获取解析后的匹配表达式对象
     */
    public MyPointExpression obtainPointExpression() {
        if (StringUtils.isEmpty(expression)) return null;
        if (expression.startsWith("execution(") && expression.endsWith(")")) {
            //进一步解析
            String coreExpression = expression.substring(10, expression.length() - 1);
            String[] coreExpressions = coreExpression.split(" ");
            returnType = coreExpressions[0];
            String middileExpression = coreExpressions[1];
            if (middileExpression.endsWith("(..)")) {
                middileExpression = middileExpression.substring(0, middileExpression.length() - 4);
            } else {
                logger.error("对于匹配方法的匹配模式当前仅支持(..)这种任意参数的模式，其他不支持");
            }
            //以..为分隔符，前面是类名前缀，后面可以指定具体的类和方法8
            String[] strings = middileExpression.split("\\.\\.");
            classPathPrex = strings[0];
            methodPath = strings[1];
        } else {
            logger.error("暂不支持的表达式类型,[{}]", expression);
        }
        return null;
    }


    @Override
    public Boolean matched(String beanClassName, String methodName) {
        if (StringUtils.isNotEmpty(expression)) {
//            logger.info("匹配拦截的方法,[{}]", expression);
            if (beanClassName.startsWith(classPathPrex)) {
                if (methodPath.equals("*.*")) return true;
                //如果methodPath是指定的，则此处进一步处理
            }
        }
        return false;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
