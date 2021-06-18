package com.example.demo.timeTask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * TimeTask  定时任务例子
 *
 * @author liyang
 * @since  2019/11/13 16:18
 */
class TimeTaskService extends TimerTask {

    @Override
    public void run() {

        System.out.println("这里执行业务方法.......");
    }
}


class taskTest{

    public static void main(String[] args) {

        // 创建Timer实例
        Timer timer = new Timer();

        // 执行任务, 启动后过5秒执行,每隔3秒执行一次
        timer.schedule(new TimeTaskService(), TimeUnit.SECONDS.toMillis(5),TimeUnit.SECONDS.toMillis(3));

        // 或者每天的data 时间执行, 每隔1000重复执行
        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day =calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的16:10:00执行，
        calendar.set(year, month, day, 16, 10, 00);
        Date date = calendar.getTime();

        timer.schedule(new TimeTaskService(),date,1000);

    }
}


