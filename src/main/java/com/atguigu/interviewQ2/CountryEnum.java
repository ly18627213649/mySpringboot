package com.atguigu.interviewQ2;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;

/**
 * 枚举温习 + CountDownLatch 学习
 */
public enum  CountryEnum {

    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

    @Getter
    private Integer retcode;
    @Getter
    private String retMessage;
    CountryEnum(int retCode, String retMessage) {
        this.retcode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum getCountryEnum(int index){

        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum element: myArray){
            if (element.retcode == index){
                return element;
            }
        }
        return null;
    }
}

class UnifySixCountriesDemo {

    public static void main(String[] args) throws InterruptedException {
        // 计数器
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "国被灭了！");
                countDownLatch.countDown();
            }, CountryEnum.getCountryEnum(i).getRetMessage()).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + " 秦国统一中原。");
    }
}
