package com.example.craw.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("item")
public class Item {
    @TableField(value = "PRODUCT_ID")
    private String PRODUCT_ID;
    private String status;
    @TableField(value = "CONTACT")
    private String CONTACT;
    private String pclass;
    @TableField(value = "FINAME")
    private String FINAME;
    private String wxlogo;
    private String logoname;
    @TableField(value = "APPLYCONDITION")
    private String APPLYCONDITION;
    private String flow;
    @TableField(value = "TARGETOBLIGOR")
    private String TARGETOBLIGOR;
    @TableField(value = "APPLYMATERIAL")
    private String APPLYMATERIAL;
    private String method;
    @TableField(value = "PRODUCTTYPE")
    private String PRODUCTTYPE;
    private String approvalComment;
    private String logopath;
    @TableField(value = "GUARANTYTYPE")
    private String GUARANTYTYPE;
    @TableField(value = "PRODUCTNAME")
    private String PRODUCTNAME;
    @TableField(value = "PRODUCTDEC")
    private String PRODUCTDEC;
    @TableField(value = "RATEUPPER")
    private BigDecimal RATEUPPER;
    @TableField(value = "RATELOWER")
    private BigDecimal RATELOWER;
    private BigDecimal completeRate;
    @TableField(value = "DURATIONUPPER")
    private Integer DURATIONUPPER;
    @TableField(value = "PRODUCTFEATURE")
    private String PRODUCTFEATURE;
    private Integer loanType ;
    @TableField(value = "FIID")
    private Integer FIID ;
    private Integer applyNum ;
    @TableField(value = "LOANAMOUNTUPPER")
    private Integer LOANAMOUNTUPPER ;
    @TableField(value = "DURATIONLOWER")
    private Integer DURATIONLOWER ;
    private Integer completeNum ;
    @TableField(value = "TOTALCREDITAMOUNT")
    private Integer TOTALCREDITAMOUNT ;
    @TableField(value = "LOANAMOUNTLOWER")
    private Integer LOANAMOUNTLOWER ;
    @TableId
    private String id;
}
