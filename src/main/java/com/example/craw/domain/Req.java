package com.example.craw.domain;



import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Req  implements Serializable {
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
    private Integer count;
    private String wage;
    private Date createTime;

}
