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
    private String salt;
    private String email;
    private String mobile;
    private Integer valid=1;//0表示禁用账号
    private Integer deptId;
   // private Integer roleIds;

}
