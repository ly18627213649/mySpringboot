package com.example.demo;

import com.example.mapper.UserLoginMapper;
import com.example.pojo.UserLogin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
@SpringBootTest
public class UserLoginTest {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        // template模板,拿来即用
        connection.close();
    }

    @Autowired
    UserLoginMapper userLoginMapper;

    @Test
    void toTest(){
        List<UserLogin> list = userLoginMapper.queryAll();
        list.forEach(e -> System.out.println(e));
    }
}
