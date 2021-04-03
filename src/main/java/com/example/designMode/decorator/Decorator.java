package com.example.designMode.decorator;

/**
 *  装饰类
 *
 *  装饰模式: 动态的给一个对象添加一些额外的职责,并不影响被装饰类本身的核心功能, 就增加功能来讲, 装饰模式比生成子类更加灵活
 *  @author ly
 *  @since  2019/11/16
 */
public class Decorator implements Component{

    /**
     * 引用被装饰对象
     */
    public Component component;

    /**
     * 构造方法
     * @param component
     */
    public Decorator(Component component) {

        this.component = component;
    }

    @Override
    public void biu() {
        component.biu();
    }
}
