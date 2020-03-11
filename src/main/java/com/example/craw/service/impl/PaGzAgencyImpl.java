package com.example.craw.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.craw.domain.Agency;

import com.example.craw.mapper.AgencyMapper;

import com.example.craw.mapper.ReqMapper;
import com.example.craw.service.PaGzAgencyService;

import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaGzAgencyImpl extends ServiceImpl<AgencyMapper, Agency> implements PaGzAgencyService {

@Autowired
private PaGzAgencyImpl aMapper;

    public  void  listRsAgency() throws IOException {
        Connection con= Jsoup.connect("http://gzh-vip.com/hr/urgentReq/listRsAgency.action").ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
        con.data("q","{\"q\":{\"key\":\"\"},\"start\":0,\"limit\":30,\"page\":1,\"pageSize\":6}");
        con.header("Content-Type", "application/x-www-form-urlencoded");//这是重点
        Document post = con.post();
        String text = post.text();
        JSONObject jsonObject = JSONObject.fromObject(text)  ;
        String data = jsonObject.getString("data");
        List<Agency> list = JSONArray.parseArray(data, Agency.class);
        // Collectors.toList方法是获取list类型的收集器  distinct方法进行去重  collect进行转换
       List<Agency> list2 = list.stream().distinct().collect(Collectors.toList());
        aMapper.saveOrUpdateBatch(list2);
      //  System.out.println(list);
      // System.out.println(list2.size());
        //System.out.println(list2);


    }




}
