package com.example.designMode.proxy;

/**
 * 第一种: 静态代理对象, 实现服务接口
 *
 * @author liyang
 * @since  2019/11/19 10:06
 *
 * 代理模式: 俗称中介,给委托对象提供一个代理对象,并由代理对象控制对原对象的引用
 * 特点: 1.中间隔离作用 2. 开闭原则,可以在代理对象中增加额外功能
 */
public class BuyHouseProxy implements IService {

    /**
     * 代理对象中引用具体目标对象
     */
    private IService iService;

    /**
     * 构造方法
     * @param iService
     */
    public BuyHouseProxy(IService iService){

        this.iService = iService;
    }

    /**
     * 实现具体业务功能, 实际是引用的具体目标对象的实现
     */
    @Override
    public void buyHouse() {

        // 可以添加其它功能
        System.out.println("买房前的准备");

        iService.buyHouse();

        System.out.println("买房后的装修");
    }


    @Override
    public void buyFurniture() {

        iService.buyFurniture();

        // 可以添加其它功能
        System.out.println("买家具后的安装....");
    }
}


/**
 * 静态代理测试
 *
 * @author liyang
 * @since  2019/11/19 10:06
 */
class ProxyTest{

    public static void main(String[] args) {

        // 创建代理对象
        BuyHouseProxy proxy = new BuyHouseProxy(new BuyHouseImpl());

        proxy.buyHouse();

        proxy.buyFurniture();
    }
}
