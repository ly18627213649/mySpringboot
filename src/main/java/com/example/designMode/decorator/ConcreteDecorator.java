package com.example.designMode.decorator;

/**
 * 具体装饰产品类
 *
 * @author liyang
 * @since  2019/11/19 14:32
 */
public class ConcreteDecorator extends Decorator{
    /**
     * 构造方法
     * @param component
     */
    public ConcreteDecorator(Component component) {
        super(component);
    }

    public void biu() {

        System.out.println("ready?go!");

        component.biu();
    }
}
