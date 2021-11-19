package com.example.designMode.adapter;

/**
 * 测试
 * 思路:将两种完全不同的事物联系到一起，就像现实生活中的变压器。
 * 思路就是将设配器引用成自己的属性,然后使用设配器的功能
 */
public class Test {

    public static void main(String[] args) {

        // 手机
        Phone phone = new Phone();

        // 手机设配器
        VoltageAdapter adapter = new VoltageAdapter();

        // 添加设配器
        phone.setAdapter(adapter);

        // 手机充电
        phone.charge();
    }
}
