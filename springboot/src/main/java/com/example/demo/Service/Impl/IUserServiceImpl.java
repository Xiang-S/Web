package com.example.demo.Service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Service.IUserService;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    public User getByUsernameAndPassword(User user) {
        return userMapper.selectAll(user.getUsername(), user.getPassword());
    }

    @Override
    public User checkUsername(String username) {
        return userMapper.checkedUsername(username);
    }

    @Override
    public Boolean insertUser(User user) {
        return userMapper.insertUser(user);
    }
}
