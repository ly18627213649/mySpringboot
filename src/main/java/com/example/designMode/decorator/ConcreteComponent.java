package com.example.designMode.decorator;
/**
 * 被装饰对象
 *
 * @author liyang
 * @since  2019/11/19 14:28
 */
public class ConcreteComponent implements Component {

    /**
     * 实现具体的功能
     */
    @Override
    public void biu() {
        System.out.println("biubiubiubiu...");
    }
}
