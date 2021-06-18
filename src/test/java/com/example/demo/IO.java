package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO {

    public static void main(String[] args) throws IOException {
        String c;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("输入字符, 按下 'q' 键退出。");
        // 读取字符
        do {
            c = (String) br.readLine();
            System.out.println(c);
        } while (!c.equals("end"));
    }
}
