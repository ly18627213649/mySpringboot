package com.example.app.mapper;

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

    // 删除
    int delete(@Param("username")  String username);

    // 批量插入
    int saveBatch(List<Map<String,String>> list);

}
