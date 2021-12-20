package com.example.util;

import java.io.*;

/**
 * 统计项目代码行数
 */
public class CalcCodeNum {
    public static void main(String[] args) throws IOException {
        // java代码
        String javaPath = "E:\\Workspaces\\yunnan\\QzApplication\\src\\main\\java";
        int java_num = getProjectFileNumber(new File(javaPath), ".java");

        // resource目录
        // xml
        String xmlPah = "E:\\Workspaces\\yunnan\\QzApplication\\src\\main\\resources";
        int xml_num = getProjectFileNumber(new File(xmlPah), ".xml");

        // properties
        String propertiesPath = "E:\\Workspaces\\yunnan\\QzApplication\\src\\main\\resources";
        int properties_num = getProjectFileNumber(new File(propertiesPath), ".properties");

        // js
        String jsPath = "E:\\Workspaces\\yunnan\\QzApplication\\src\\main\\resources\\static";
        int js_num = getProjectFileNumber(new File(jsPath),".js");

        String ftlPath = "E:\\Workspaces\\yunnan\\QzApplication\\src\\main\\resources\\templates";
        int ftl_num = getProjectFileNumber(new File(ftlPath),".ftl");

        System.out.println("java:" + java_num);
        System.out.println("xml:" + xml_num);
        System.out.println("properties:" + properties_num);
        System.out.println("js:" + js_num);
        System.out.println("ftl:" + ftl_num);
        System.out.println("total:" + (java_num + xml_num + properties_num + js_num + ftl_num));
    }

    /**
     * 递归获取文件中代码行数
     * */
    private static int getProjectFileNumber(File file, String endsWith) throws IOException {
        int number = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File subFile : file.listFiles()) {
                    number += getProjectFileNumber(subFile, endsWith);
                }
            } else if (file.isFile() && file.getName().endsWith(endsWith)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (br.readLine() != null) {
                    number += 1;
                }
            } else {
                System.out.println("===" + file.getAbsolutePath());
            }
        }
        return number;
    }
}
