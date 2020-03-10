package com.example.demo1.base;

public abstract class TestController {

    public abstract String test() throws Exception;

    public String test(String msg) throws Exception{
        throw new Exception();
    }
}
