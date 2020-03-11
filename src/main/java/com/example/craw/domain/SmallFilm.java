package com.example.craw.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("smallfilm")
public class SmallFilm implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Boolean mosaic;//0是无码 1是有码
    private String image;
    private String title;
    private Date date;
    private Integer filmUrl;// 来源网站域名 （需要查询weburl）
    private String filmAdd;// 网站页面地址
    private Date createDate;// 创建时间
    private String live;// 在线观看地址
    private Integer urlType;// 下载地址类型
    private String url;// 下载地址 或Code或迅雷连接
}
