package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) // 链式访问,因为开启这个set方法返回的是对象
public class UserLogin {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
}
