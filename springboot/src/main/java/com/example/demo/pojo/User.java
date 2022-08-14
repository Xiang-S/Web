package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    private int id;
    private String  username;
    @TableField(select = false)
    private String password;
}
