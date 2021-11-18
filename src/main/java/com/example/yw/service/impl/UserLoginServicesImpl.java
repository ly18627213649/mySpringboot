package com.example.yw.service.impl;

import com.example.yw.mapper.UserLoginMapper;
import com.example.pojo.UserLogin;
import com.example.yw.service.UserLoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginServicesImpl implements UserLoginServices {
    @Autowired
    UserLoginMapper userLoginMapper;
    @Override
    public List<UserLogin> queryAll() {
        return userLoginMapper.queryAll();
    }

    @Override
    public int add(UserLogin userLogin) {
        return userLoginMapper.add(userLogin);
    }

    @Override
    public UserLogin queryByName(String username) {
        return userLoginMapper.queryByName(username);
    }
}
