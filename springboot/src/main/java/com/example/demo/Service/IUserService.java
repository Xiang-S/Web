package com.example.demo.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.User;

public interface IUserService extends IService<User> {
    User getByUsernameAndPassword(User user);
    User checkUsername(String username);
    Boolean insertUser(User user);
}
