package com.example.designMode.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 第二种: jdk动态代理
 *
 * 原理: 只能代理接口(不支持抽象类),代理类都需要实现invocationhandler类,实现invoke方法
 *
 * @author liyang
 * @since  2019/11/19 10:43
 */
public class DynamicProxyHandler implements InvocationHandler {

    /**
     * 引用具体的目标对象
     */
    private Object target;

    /**
     * 构造方法
     * @param target
     */
    public DynamicProxyHandler(final Object target){

        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("买房前准备");
        Object result = method.invoke(target, args);
        System.out.println("买房后装修");
        return result;
    }
}


/**
 * 动态代理测试
 *
 * @author liyang
 * @since  2019/11/19 10:45
 */
class DynamicProxyText {

    public static void main(String[] args) {

        /**
         * ClassLoader loader: 指定当前目标对象使用的类加载器,获取加载器的方法是固定的
         * Class<?>[] interfaces: 指定目标对象实现的接口的类型,使用泛型方式确认类型
         * InvocationHandler: 指定动态处理器,执行目标对象的方法时,会触发事件处理器的方法
         */
        IService proxyInstance = (IService) Proxy.newProxyInstance(IService.class.getClassLoader(), new Class[]{IService.class}, new DynamicProxyHandler(new BuyHouseImpl()));

        proxyInstance.buyHouse();

    }
}