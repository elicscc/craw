package com.example.craw.service;




import com.example.craw.domain.PageResult;

import java.util.Date;


public interface GzService {
    PageResult selectReqByAll(Integer currentPage, Integer rows, Date beginDate , Date endDate , String title);
    PageResult selectRsReqByAll(Integer currentPage, Integer rows, Date beginDate , Date endDate , String title);
    PageResult selectAgencyByAll(Integer currentPage, Integer rows , String title);

}
