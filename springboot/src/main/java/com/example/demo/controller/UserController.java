package com.example.demo.controller;


import com.example.demo.Service.IUserService;
import com.example.demo.controller.utils.R;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R checkedRegister(@RequestBody User user){
        if(userService.checkUsername(user.getUsername()) != null){
            return new R(false,"exist");
        }else {
            userService.insertUser(user);
            return new R(true,"注册成功");
        }
    }
}
