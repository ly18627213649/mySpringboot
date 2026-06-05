package com.example.demo;

import java.util.concurrent.Executors;

public class AAA {

    public static void main(String[] args) {
        Thread thread = new Thread(){
            public void run(){
                System.out.println("1");
            }
        };
        Thread thread2 = new Thread(){
            public void run(){
                System.out.println("2");
            }
        };
        thread.start();
        thread2.start();
        System.out.println("3");

        System.out.println((short)10/10.2*2);
    }

}
