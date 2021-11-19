package com.example.designMode.decorator.forExample2;

/**
 * 测试
 * 思路: 构造方法传入一个Food类型的参数，然后在make方法中加入一些自己的逻辑,
 * 思路: 子类重写父类的方法
 */
public class Test {

    public static void main(String[] args) {
        Food food = new Bread(new Vegetable(new Cream(new Food("香肠"))));
        System.out.println(food.make());  // 结果:香肠+奶油+蔬菜+面包
    }
}
