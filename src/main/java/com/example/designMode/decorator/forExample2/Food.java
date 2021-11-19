package com.example.designMode.decorator.forExample2;

/**
 * 一个食物父类,让其它食物都继承这个类
 */
public class Food {

    private String food_name;

    public Food() {
    }

    public Food(String food_name) {
        this.food_name = food_name;
    }

    public String make() {
        return food_name;
    };
}
