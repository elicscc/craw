package com.example.craw.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoJr implements Serializable {
    private Integer currentPage;
    private Integer rows;
    private String queryname;
    private String financename;
    private String loandate;
    private String guatype;
    private String financeChannel;
    private String loanLimit;


}
