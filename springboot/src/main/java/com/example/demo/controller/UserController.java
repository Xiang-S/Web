package com.example.demo.controller;


import com.example.demo.Service.IUserService;
import com.example.demo.controller.utils.R;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping("/login")
    public R checkedLogin(@RequestBody User user) {
        if (userService.getByUsernameAndPassword(user) != null) {
            return new R(true, "登录成功");
        } else
            return new R(false, "该用户不存在");
    }

    @PostMapping("/register")
    public R checkedRegister(@RequestBody User user) {
        userService.insertUser(user);
        return new R(true, "注册成功");
    }
    @PostMapping("/checkUsername")
    public R checkedUsername(@RequestBody Map<String,String> map) {
        if (userService.checkUsername(map.get("username")) != null) {
            return new R(false,"账号已存在");
        }else {
            return new R(true,"验证通过");
        }
    }
}
