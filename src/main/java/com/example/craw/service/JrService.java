package com.example.craw.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.Agency;
import com.example.craw.domain.DoJr;
import com.example.craw.domain.Item;
import com.example.craw.domain.Req;

import java.util.Date;


public interface JrService {
    Page<Item> selectReqByAll(DoJr jr);

    Item findItemById(String id);
}
