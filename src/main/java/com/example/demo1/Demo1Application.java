package com.example.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.DebuggingClassWriter;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\cglibClass");
        System.out.println("SystemProperties:"+System.getProperties().getProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles"));
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        
        System.out.println("SystemProperties:"+System.getProperties().getProperty("sun.misc.ProxyGenerator.saveGeneratedFiles"));
        SpringApplication.run(Demo1Application.class, args);
    }

}
