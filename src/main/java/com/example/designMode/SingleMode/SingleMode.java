package com.example.designMode.SingleMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单例模式
 *
 * 使用枚举获取当前对象
 *
 * @author liyang
 * @since  2019/11/14 18:00
 */
public class SingleMode {

    protected Logger log = LoggerFactory.getLogger(SingleMode.class);

    // 构造方法私有
    private SingleMode(){}

    // 对外提供获取对象方法
    public static SingleMode me(){return single.INSTANCE.singleMode;}

    /**
     * 枚举实现单例
     */
    private enum single{

        INSTANCE;

        private SingleMode singleMode;

        single(){singleMode = new SingleMode();}
    }

    /**
     * 本类其他方法
     */
    public void print(){

        System.out.println("-----------1231231231-----");
    }
}
