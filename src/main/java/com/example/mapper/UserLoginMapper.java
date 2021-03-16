package com.example.mapper;

import com.example.pojo.UserLogin;

import java.util.List;

public interface UserLoginMapper {
    // 查询
     List<UserLogin> queryAll();
    // 添加数据
     int add(UserLogin userLogin);
    //根据用户名查询数据
     UserLogin queryByName(String username);

}
