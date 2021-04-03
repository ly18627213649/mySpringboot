package com.example.designMode.proxy;

/**
 * 目标对象(或委托对象), 实现服务接口
 */
public class BuyHouseImpl implements IService {


    @Override
    public void buyHouse() {
        System.out.println("我要买房子...");
    }

    @Override
    public void buyFurniture() {
        System.out.println("我要买家具...");
    }
}
