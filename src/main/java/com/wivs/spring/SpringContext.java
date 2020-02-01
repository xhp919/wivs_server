package com.wivs.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {
    public static ClassPathXmlApplicationContext context;
    static {
        context = new ClassPathXmlApplicationContext("spring.xml");
    }
}
