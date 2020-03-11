package com.example.craw.domain;



import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("req")
public class Req  implements Serializable {
    @TableId
    private Integer id;
    private String companyName;
    private String province;
    private String city;
    private String address;
    private String remark;
    private String linkName;
    private String linkMobile;
    private String jobName;
    private String jobReq;
    private String count;
    private String wage;
    private Date createTime;
    private Integer type;

}
