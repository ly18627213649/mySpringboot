package com.example.demo.thread;

import com.example.xhlang.ThreadPoolFactory;
import com.example.xhlang.ThreadPoolUtil;
import com.example.xhlang.task.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  线程业务类
 *  @author ly
 *  @since  2020/3/22
 */
class MyThreadService extends AbstractTask {

    protected Logger log = LoggerFactory.getLogger(MyThreadService.class);

    @Override
    public void doRun() {

        System.out.println("开始执行.....!");


        System.out.println("具体业务.....!");


        System.out.println("结束执行.....!");

    }

    @Override
    public String getTaskDesc() {
        return null;
    }

}

/**
 * 线程测试 ThreadPoolFactory
 */
class  threadPoolFactoryTest{

    public static void main(String[] args) {

         final String TaskDesc = "我的测试线程";

        // 先注册线程
        ThreadPoolFactory.register(TaskDesc, 5, 10, 10);

        // 创建线程
        MyThreadService myThreadService = new MyThreadService();

        // 执行
        ThreadPoolFactory.execute(TaskDesc, myThreadService);
    }
}


/**
 * 线程测试 ThreadPoolUtil
 */
class  threadPoolUtilTest{

    public static void main(String[] args) {

        // 创建线程
        MyThreadService myThreadService = new MyThreadService();

        // 执行
        ThreadPoolUtil.submit(myThreadService);
    }
}
