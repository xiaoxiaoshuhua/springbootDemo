package com.example.demo1.aop;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

public class AopTestUtils {

    private final String AUTO_PROXY_CREATOR_BEAN_NAME_TEST = "org.springframework.aop.config.internalAutoProxyCreator";

    public BeanDefinition registerAopTestCreator(Class<?> cls, BeanDefinitionRegistry registry, @Nullable Object source) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(cls);
        beanDefinition.setSource(source);
        beanDefinition.getPropertyValues().add("order", Ordered.HIGHEST_PRECEDENCE);
        beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME_TEST, beanDefinition);
        return beanDefinition;
    }
}
