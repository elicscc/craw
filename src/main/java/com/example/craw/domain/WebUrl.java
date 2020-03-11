package com.example.craw.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("weburl")
public class WebUrl implements Serializable {
    @TableId
    private Integer id;
    private String webUrl;
    private String webName;
}
