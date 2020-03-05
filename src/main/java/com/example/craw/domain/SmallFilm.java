package com.example.craw.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SmallFilm implements Serializable {
    private Integer id;
    private boolean mosaic;//0是无码 1是有码
    private String image;
    private String title;
    private Date date;
    private Integer filmurl;// 来源网站域名 （需要查询weburl）
    private String filmadd;// 网站页面地址
    private Date createdate;// 创建时间
    private String live;// 在线观看地址
    private Integer urltype;// 下载地址类型 
    private String url;// 下载地址 或Code或迅雷连接
}
