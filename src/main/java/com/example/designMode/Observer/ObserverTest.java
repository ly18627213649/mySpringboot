package com.example.designMode.Observer;
/**
 *  观察者模式, 测试类
 * @author Honey
 * @since 2021/4/1 0:03
 */
public class ObserverTest {
    public static void main(String[] args) {
        // 创建一个观察者目标
        JavaStackObservable javaStackObservable = new JavaStackObservable();

        // 添加一个观察者
        javaStackObservable.addObserver(new ReaderObserver("张三"));
        javaStackObservable.addObserver(new ReaderObserver("李四"));
        javaStackObservable.addObserver(new ReaderObserver("王五"));
        javaStackObservable.addObserver(new ReaderObserver("顺溜"));

        // 观察者目标发布文章g
        javaStackObservable.publish("什么是观察者模式?");
    }
}
