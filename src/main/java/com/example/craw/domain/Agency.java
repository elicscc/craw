package com.example.craw.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("rsagency")
public class Agency implements Serializable {
    @TableId
    private Integer id;
    private String companyName;
    private String linkName;
    private String linkMobile;
    private String mainBiz;
    private String country;
}
