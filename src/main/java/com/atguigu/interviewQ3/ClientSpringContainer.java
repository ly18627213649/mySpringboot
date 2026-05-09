package com.atguigu.interviewQ3;

import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringContainer {

    public static void main(String[] args) {
        // 从这里入口, 打断点, 看spring是如何三级缓存如何创建循环依赖对象
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //  DefaultSingletonBeanRegistry
    }
}
