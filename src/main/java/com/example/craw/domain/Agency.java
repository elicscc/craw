package com.example.craw.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Agency implements Serializable {
    private Integer id;
    private String companyName;
    private String linkName;
    private String linkMobile;
    private String mainBiz;
    private String country;
}
