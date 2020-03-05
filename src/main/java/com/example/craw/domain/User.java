package com.example.craw.domain;


import lombok.Data;
import lombok.ToString;


import java.io.Serializable;

@Data
@ToString
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String sex;
    private Integer age;
    private String perms;
}
