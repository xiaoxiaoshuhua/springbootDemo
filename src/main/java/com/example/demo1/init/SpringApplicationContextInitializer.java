package com.example.demo1.init;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // ...自定义操作
        System.out.println("SpringApplicationContextInitializer#initialize");
    }
}
