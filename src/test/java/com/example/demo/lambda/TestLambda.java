package com.example.demo.lambda;

import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * 语法格式六: lambda 参数的数据类型可以省略, 因为jvm可以根据上下为问推断出数据类型,这叫做类型推断
 * <p>
 * 左右遇一括号省, 一个参数, 省略参数括号, 一行实现体,省略右侧大括号
 * 左侧推断类型省
 * <p>
 * 二:lambda 表达式需要函数式接口的支持,
 * 函数式接口: 接口中只有一个抽象方法的接口, 称为函数式接口, 可以用注解@FunctionalInterface 修饰, 可以检查是否是函数式接口
 * <p>
 * 三: 4大内置函数式接口
 * <p>
 * Consumer<T> : 消费型接口  void accept(T t);
 * Supplier<T> : 供给型接口  T get();
 * Function<T,R>: 函数型接口   R apply(T t);
 * Predicate<T>:  断言性接口   boolean test(T t);
 */
public class TestLambda {

    @Test
    public void test() {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello lambda");
            }
        };
        r.run();


        System.out.println("------------");

        Runnable r1 = () -> System.out.println("hello lambda");
        r1.run();

        System.out.println("lambda test");
    }

    // 对接口的实现,有参数,无返回值
    @Test
    public void test2() {

        Consumer<String> con = x -> System.out.println("打牌:" + x);
        con.accept("上大人, 孔乙己");
    }

    @Test
    public void test3() {
        // 参数类型可以省略不写
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数借口");
            return Integer.compare(x, y);
        };

    }

    // lambda 实现自己的函数接口,乘法
    @Test
    public void test5() {

        MyPredicate myPredicate = x -> x * x;

        System.out.println(myPredicate.getString(10));
    }

    // 集合排序, 用sort 方法, 用lambda 表达式实现Comparator 接口
    @Test
    public void test6() {

        List<String> list = Arrays.asList("王五", "zhangsan", "李四");

        Collections.sort(list, (e1, e2) -> {

            return e1.compareTo(e2);

        });
        // 打印
        for (String str : list) {
            System.out.println(str);
        }
    }

    // lambda 实现自己的函数接口,字符串处理
    @Test
    public void test7() {

        //StrHandler strHandler = string -> string.toUpperCase();

        String sfdfds = strHandler("sfdfds", string -> string.toUpperCase());

        System.out.println(sfdfds);

    }

    // 需求: 字符串处理,参数为lambda 接口
    public String strHandler(String str, StrHandler strHandler) {
        return strHandler.getString(str);
    }


}
