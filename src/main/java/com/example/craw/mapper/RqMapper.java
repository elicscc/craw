package com.example.craw.mapper;


import com.example.craw.domain.Agency;

import com.example.craw.domain.Req;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

public interface RqMapper {
    void saveAgency(List<Agency> agencies);
    void saveReq(List<Req> agencies);
    void saveRsReq(List<Req> agencies);
    List<Agency> selectAgency(Integer start, Integer rows, String title);
    List<Req> selectRsReq(Integer start, Integer rows, Date beginDate, Date endDate, String title);
    List<Req> selectReq(Integer start, Integer rows, Date beginDate, Date endDate, String title);
    Integer countAgency(String title);
    Integer countRsReq(@Param("beginDate")Date beginDate , @Param("endDate")Date endDate , @Param("title")String title);
    Integer countReq(@Param("beginDate")Date beginDate , @Param("endDate")Date endDate , @Param("title")String title);
}
