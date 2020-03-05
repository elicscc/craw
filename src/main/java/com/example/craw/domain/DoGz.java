package com.example.craw.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DoGz {
  private   Integer currentPage;
    private  Integer rows;
    private Date beginDate;
    private   Date endDate;
    private  Integer state;
    private   String title;
}
