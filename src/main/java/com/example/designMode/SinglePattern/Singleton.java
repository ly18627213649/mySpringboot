package com.example.designMode.SinglePattern;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 单例写法
 * @author Honey
 * @since 2021/4/1 20:22
 */
public class Singleton {

    // 第一步: 构造器私有化
    private Singleton(){

    }

    // 第一种: 饿汉式,final起到强调这是一个单例
    public final static Singleton singleton = new Singleton();

    // 第二种: 静态代码块
    // 这种方式适用于初始化对象的时候需要传入复杂的参数时, 比如读取properties资源文件里的值, 作为初始化参数
    public static Singleton singleton2;

    static{
        Properties properties = new Properties();
        InputStream stream = Singleton.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取参数
        String param = properties.getProperty("xxxkey");
        // 将参数作为对象初始化参数
        singleton2 = new Singleton();
    }

    // 第三种: 枚举
    private enum bbb{
        INSTANCE;

        private Singleton singleton;

        private bbb(){
            singleton = new Singleton();
        }

        public Singleton getInstance(){
            return singleton;
        }
    }

    public static Singleton getInstance3(){
        return bbb.INSTANCE.getInstance();
    }

    // 第四种: 懒汉式,加锁保证线程安全, 双重判断提高效;使用volatile 防止第一个线程初始化对象的时候重排序
    private volatile static Singleton singleInstance;

    public static Singleton getInstance4(){
        if (singleInstance == null){
            synchronized (Singleton.class){
                if (singleInstance == null){
                    singleInstance = new Singleton();
                }
            }
        }
        return singleInstance;
    }

    // 第五种: 静态内部类方式(推荐使用)
    // 静态内部内不会随着外部内的加载和初始化而初始化,它是要单独加载和初始的,在内部内被加载和初始化的时候,才去创建对象.
    // 因为是在内部内加载和初始化时创建的,因此是线程安全的
    private static class ccc{
        private final static Singleton singleton = new Singleton();
    }

    public static Singleton getInstance5(){
        return ccc.singleton;
    }
}
