package com.example.craw.service.impl;


import com.example.craw.domain.Agency;
import com.example.craw.domain.PageResult;
import com.example.craw.domain.Req;
import com.example.craw.domain.SmallFilm;
import com.example.craw.mapper.RqMapper;
import com.example.craw.service.GzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


@Service
public class GzServiceImpl implements GzService {
    @Autowired
    private RqMapper rqMapper;

    @Override
    public PageResult selectReqByAll(Integer currentPage, Integer rows, Date beginDate, Date endDate, String title) {
        int total =  rqMapper.countReq(beginDate,endDate,title);
      //  System.err.println(total);
        if (total > 0) {
            int start = (currentPage - 1) * rows;
            List<Req> list = rqMapper.selectReq(start, rows,beginDate,endDate, title);
            return new PageResult(list, total, currentPage, rows);
        }
        return PageResult.empty(rows);
    }

    @Override
    public PageResult selectRsReqByAll(Integer currentPage, Integer rows, Date beginDate, Date endDate, String title) {
        int total =  rqMapper.countRsReq(beginDate,endDate,title);
       // System.err.println(total);
        if (total > 0) {
            int start = (currentPage - 1) * rows;
            List<Req> list = rqMapper.selectRsReq(start, rows,beginDate,endDate, title);
            return new PageResult(list, total, currentPage, rows);
        }
        return PageResult.empty(rows);
    }

    @Override
    public PageResult selectAgencyByAll(Integer currentPage, Integer rows, String title) {
        int total =  rqMapper.countAgency(title);
        if (total > 0) {
            int start = (currentPage - 1) * rows;
            List<Agency> list = rqMapper.selectAgency(start, rows, title);
            return new PageResult(list, total, currentPage, rows);
        }
        return PageResult.empty(rows);
    }
}
