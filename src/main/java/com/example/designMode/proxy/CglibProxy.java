package com.example.designMode.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 第三种: clig 动态代理
 *
 * 原理: 底层通过创建子类继承目标对象, 对于final修饰的目标对象无法代理.
 * @author liyang
 * @since  2019/11/19 11:48
 */
public class CglibProxy implements MethodInterceptor {

    /**
     * 引用具体目标对象
     */
    private Object target;

    public Object getInstance(final Object target) {
         this.target = target;
         Enhancer enhancer = new Enhancer();
         enhancer.setSuperclass(this.target.getClass());
         enhancer.setCallback(this);
         return enhancer.create();
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("买房前准备");
        Object result = methodProxy.invoke(target, args);
        System.out.println("买房后装修");
        return result;
    }
}


/**
 * 测试
 *
 * @author liyang
 * @since  2019/11/19 11:48
 */
class CglibProxyTest{

    public static void main(String[] args) {

        CglibProxy cglibProxy = new CglibProxy();

        BuyHouseImpl buyHouseCglibProxy  = (BuyHouseImpl)cglibProxy.getInstance(new BuyHouseImpl());

        buyHouseCglibProxy.buyHouse();

    }
}