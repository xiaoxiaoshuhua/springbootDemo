package com.example.demo1.base.controller;

import com.example.demo1.aop.AopTest;
import com.example.demo1.aop.AopTestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/indexController")
@RestController
public class IndexController {

    @Autowired
    @Lazy(value = true)
    private AopTest aopTest;

    @RequestMapping(value = {"", "/"})
    public String index() {
        aopTest.aopTest("testAop");
        return "index";
    }

    public static void main(String[] args) throws Exception{

    }

}
