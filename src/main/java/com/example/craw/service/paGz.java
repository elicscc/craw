package com.example.craw.service;

import com.alibaba.fastjson.JSONArray;
import com.example.craw.domain.Agency;


import com.example.craw.domain.Req;
import com.example.craw.mapper.RqMapper;
import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@Service
public class paGz  {
    @Autowired
    private RqMapper rq;

    public  void  listRsAgency() throws IOException {
    Connection con= Jsoup.connect("http://gzh-vip.com/hr/urgentReq/listRsAgency.action").ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
con.data("q","{\"q\":{\"key\":\"\"},\"start\":0,\"limit\":23,\"page\":1,\"pageSize\":6}");
        con.header("Content-Type", "application/x-www-form-urlencoded");//这是重点
        Document post = con.post();

        String text = post.text();

        JSONObject jsonObject = JSONObject.fromObject(text)  ;
        String data = jsonObject.getString("data");

        List<Agency> list=new ArrayList<>();
      list=  JSONArray.parseArray(data,Agency.class);
        rq.saveAgency(list);



    }


    public  void  listRsReq() throws IOException {
        Connection con= Jsoup.connect("http://gzh-vip.com/hr/urgentReq/listRsReq.action").ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
        con.data("q","{\"q\":{\"key\":\"\"},\"start\":0,\"limit\":23,\"page\":1,\"pageSize\":6}");
        con.header("Content-Type", "application/x-www-form-urlencoded");//这是重点
        Document post = con.post();

        String text = post.text();

        JSONObject jsonObject = JSONObject.fromObject(text)  ;
        String data = jsonObject.getString("data");

        List<Req> list=new ArrayList<>();
        list=  JSONArray.parseArray(data,Req.class);
       rq.saveRsReq(list);
       // System.out.println(list);
    }


    public  void  listReq() throws IOException {
        Connection con= Jsoup.connect("http://gzh-vip.com/hr/urgentReq/listReq.action").ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
        con.data("q","{\"q\":{\"key\":\"\"},\"start\":0,\"limit\":552,\"page\":1,\"pageSize\":6}");
        con.header("Content-Type", "application/x-www-form-urlencoded");//这是重点
        Document post = con.post();

        String text = post.text();

        JSONObject jsonObject = JSONObject.fromObject(text)  ;
        String data = jsonObject.getString("data");

        List<Req> list=new ArrayList<>();
        list=  JSONArray.parseArray(data,Req.class);
        rq.saveReq(list);
        //System.out.println(list);
    }

}
