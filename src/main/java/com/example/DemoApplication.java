package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 */
//@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
//@EnableScheduling		//定时任务
//@EnableTransactionManagement //事务
//@MapperScan(basePackages = {"/mapper"})
@MapperScan("com.example.mapper")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
