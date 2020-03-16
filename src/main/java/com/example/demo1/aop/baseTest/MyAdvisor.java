package com.example.demo1.aop.baseTest;

public interface MyAdvisor {

    /**
     * 排序
     * @return
     */
    int getOrder();

    /**
     * 设定排序
     * @param order
     */
    void setOrder(int order);
}
