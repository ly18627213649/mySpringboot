package com.example.yw.mapper;

import com.example.pojo.UserLogin;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserLoginMapper {

    // 通用
    List<String> getStrList(@Param("sql")String sql);
    Map<String,Object> getMap(@Param("sql")String sql);

    // 查询
    List<UserLogin> queryAll();

    // 添加数据
    int add(@Param("userLogin") UserLogin userLogin);

    //根据用户名查询数据
    UserLogin queryByName(@Param("username") String username);

}
