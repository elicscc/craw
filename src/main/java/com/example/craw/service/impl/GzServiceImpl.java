package com.example.craw.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.Agency;
import com.example.craw.domain.Req;
import com.example.craw.mapper.AgencyMapper;
import com.example.craw.mapper.ReqMapper;
import com.example.craw.service.GzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


@Service
public class GzServiceImpl implements GzService {
    @Autowired
    private ReqMapper reqMapper;
    @Autowired
    private AgencyMapper agencyMapper;

    @Override
    public Page<Req> selectReqByAll(Integer currentPage, Integer rows, Date beginDate, Date endDate, String title, Integer state) {
        Page<Req> page=new Page<Req>(currentPage,rows);
        QueryWrapper<Req> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",state);
        if (title!=null){queryWrapper.like("job_name",title);}
        if (beginDate!=null){ queryWrapper.ge("create_time",beginDate);}
        if (endDate!=null){ queryWrapper.le("create_time",endDate);}
        return reqMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Page<Req> selectRsReqByAll(Integer currentPage, Integer rows, Date beginDate, Date endDate, String title, Integer state) {
        Page<Req> page=new Page<Req>(currentPage,rows);
        QueryWrapper<Req> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",state);
        if (title!=null){queryWrapper.like("job_name",title);}
        if (beginDate!=null){ queryWrapper.ge("create_time",beginDate);}
        if (endDate!=null){ queryWrapper.le("create_time",endDate);}
        return reqMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Page<Agency> selectAgencyByAll(Integer currentPage, Integer rows, String title) {
        Page<Agency> page=new Page<Agency>(currentPage,rows);
        QueryWrapper<Agency> queryWrapper=new QueryWrapper<>();
        if (title!=null){queryWrapper.like("company_name",title);}
        return agencyMapper.selectPage(page,queryWrapper);
    }


//    @Override
//    public PageResult selectReqByAll(Integer currentPage, Integer rows, Date beginDate, Date endDate, String title) {
//        int total =  rqMapper.countReq(beginDate,endDate,title);
//      //  System.err.println(total);
//        if (total > 0) {
//            int start = (currentPage - 1) * rows;
//            List<Req> list = rqMapper.selectReq(start, rows,beginDate,endDate, title);
//            return new PageResult(list, total, currentPage, rows);
//        }
//        return PageResult.empty(rows);
//    }
//
//    @Override
//    public PageResult selectRsReqByAll(Integer currentPage, Integer rows, Date beginDate, Date endDate, String title) {
//        int total =  rqMapper.countRsReq(beginDate,endDate,title);
//       // System.err.println(total);
//        if (total > 0) {
//            int start = (currentPage - 1) * rows;
//            List<Req> list = rqMapper.selectRsReq(start, rows,beginDate,endDate, title);
//            return new PageResult(list, total, currentPage, rows);
//        }
//        return PageResult.empty(rows);
//    }
//
//    @Override
//    public PageResult selectAgencyByAll(Integer currentPage, Integer rows, String title) {
//        int total =  rqMapper.countAgency(title);
//        if (total > 0) {
//            int start = (currentPage - 1) * rows;
//            List<Agency> list = rqMapper.selectAgency(start, rows, title);
//            return new PageResult(list, total, currentPage, rows);
//        }
//        return PageResult.empty(rows);
//    }
}
