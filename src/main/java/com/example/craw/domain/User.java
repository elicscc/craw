package com.example.craw.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {
    @TableId
    private Long id;
    private String username;
    private String password;
    private String sex;
    private Integer age;
    private String perms;
}
