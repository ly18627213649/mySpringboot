package com.example.designMode.decorator;

/**
 * 装饰模式测试
 *
 * @author liyang
 * @since  2019/11/19 14:45
 */
public class Test {

    public static void main(String[] args) {

        // 实例化具体装饰对象
        Component component = new ConcreteDecorator(new ConcreteComponent());

        component.biu();
    }

}
