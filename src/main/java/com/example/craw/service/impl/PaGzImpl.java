package com.example.craw.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.example.craw.domain.Req;
import com.example.craw.mapper.ReqMapper;
import com.example.craw.service.PaGzService;
import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaGzImpl extends ServiceImpl<ReqMapper, Req> implements PaGzService {

@Autowired
private  PaGzImpl paGz;

    public  void  listRsReq() throws IOException {
        Connection con= Jsoup.connect("http://gzh-vip.com/hr/urgentReq/listRsReq.action").ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
        con.data("q","{\"q\":{\"key\":\"\"},\"start\":0,\"limit\":1000,\"page\":1,\"pageSize\":6}");
        con.header("Content-Type", "application/x-www-form-urlencoded");//这是重点
        Document post = con.post();
        String text = post.text();
        JSONObject jsonObject = JSONObject.fromObject(text)  ;
        String data = jsonObject.getString("data");
        List<Req> list = JSONArray.parseArray(data, Req.class);
        List<Req> list2 = list.stream().distinct().collect(Collectors.toList());
        for (Req req:list2) {
            req.setType(2);
        }
        paGz.saveOrUpdateBatch(list2);

    }


    public  void  listReq() throws IOException {
        Connection con= Jsoup.connect("http://gzh-vip.com/hr/urgentReq/listReq.action").ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
        con.data("q","{\"q\":{\"key\":\"\"},\"start\":0,\"limit\":1000,\"page\":1,\"pageSize\":6}");
        con.header("Content-Type", "application/x-www-form-urlencoded");//这是重点
        Document post = con.post();
        String text = post.text();
        JSONObject jsonObject = JSONObject.fromObject(text);
        String data = jsonObject.getString("data");
       // System.err.println(data);
        List<Req> list=  JSONArray.parseArray(data,Req.class);
        //System.err.println(list);
        List<Req> list2 = list.stream().distinct().collect(Collectors.toList());

        for (Req req:list2) {
            req.setType(1);
        }
        //System.err.println(list2);
        paGz.saveOrUpdateBatch(list2);

    }

}
