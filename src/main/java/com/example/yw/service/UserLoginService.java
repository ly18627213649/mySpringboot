package com.example.yw.service;

import com.example.yw.mapper.UserLoginMapper;
import com.example.pojo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginService {

    @Autowired
    UserLoginMapper userLoginMapper;


    // 查询
    public List<UserLogin> queryAll() { return userLoginMapper.queryAll(); }

    // 添加数据
    public int add(UserLogin userLogin) {
        return userLoginMapper.add(userLogin);
    }

    // //根据用户名查询数据
    public UserLogin queryByName(String username) {
        return userLoginMapper.queryByName(username);
    }
}
