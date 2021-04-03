package com.example.designMode.SimpleFactory;

/**
 * 实现类
 */
public class AliSMSImpl implements IService {

    @Override
    public void sendSMS() {
        System.out.println("具体短信业务");
    }
}
