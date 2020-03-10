package com.example.demo1.aop;

import org.springframework.stereotype.Service;

@Service
public class AopTestImpl implements AopTest {

    @Override
    public void aopTest(String aa) {
        System.out.println("aopTest:" + aa);
    }
}
