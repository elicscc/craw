package com.example.craw.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.Agency;
import com.example.craw.domain.Req;

import java.util.Date;


public interface GzService {
    Page<Req> selectReqByAll(Integer currentPage, Integer rows, Date beginDate , Date endDate , String title, Integer state);
    Page<Req>   selectRsReqByAll(Integer currentPage, Integer rows, Date beginDate , Date endDate , String title,Integer state);
    Page<Agency>   selectAgencyByAll(Integer currentPage, Integer rows , String title);

}
