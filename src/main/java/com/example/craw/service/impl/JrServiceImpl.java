package com.example.craw.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.DoJr;
import com.example.craw.domain.Item;
import com.example.craw.domain.User;
import com.example.craw.mapper.ItemMapper;
import com.example.craw.service.JrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JrServiceImpl implements JrService {


  @Autowired
  private ItemMapper itemMapper;

    @Override
    public Page<Item> selectReqByAll(DoJr jr) {
        Page<Item> result=new Page<Item>(jr.getCurrentPage(),jr.getRows());
        QueryWrapper<Item> queryWrapper =  new QueryWrapper<>();
         if (null!=jr.getFinancename()) { queryWrapper.like("FINAME",jr.getFinancename());}
         if (null!=jr.getQueryname()) { queryWrapper.like("PRODUCTNAME",jr.getQueryname());}
         if (!StringUtils.isEmpty(jr.getGuatype())){queryWrapper.eq("GUARANTYTYPE",jr.getGuatype());}
//         if (!StringUtils.isEmpty(jr.getLoandate())) {
//             if ("more".equals(jr.getLoandate())) {
//                 queryWrapper.gt("DURATIONUPPER", 13);
//             } else {
//                 queryWrapper.lt("DURATIONUPPER", jr.getLoandate());
//             }
//         }
         if (!StringUtils.isEmpty(jr.getLoanLimit())){
             if ("more".equals(jr.getLoanLimit())){
                 queryWrapper.gt("LOANAMOUNTUPPER",1001);
             }else {
                 queryWrapper.eq("LOANAMOUNTUPPER",jr.getLoanLimit());
             }
         }
        // if (!StringUtils.isEmpty(jr.getFinanceChannel())){queryWrapper.eq("e",jr.getFinanceChannel());}
        return itemMapper.selectPage(result,queryWrapper);
    }

    @Override
    public Item findItemById(String id) {
        return itemMapper.selectById(id);
    }
}
