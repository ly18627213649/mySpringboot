package com.example.demo;

import com.example.bean.Person;
import com.example.controller.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.soap.Name;
import java.util.logging.Logger;

/**
 * springboot单元测试
 *
 * 可以在测试期间很方便的像编码一样自动注入
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	Person person;

	@Test
	public void contextLoads() {
		System.out.print(person);
	}
	@Test
	public void logger(){

        org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	}


}
