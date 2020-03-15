package com.example.craw.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.craw.domain.Agency;
import com.example.craw.domain.Item;

import com.example.craw.mapper.ItemMapper;
import com.example.craw.service.ItemCrawService;

import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class ItemCrawImpl  extends ServiceImpl<ItemMapper, Item> implements ItemCrawService {

    @Autowired
    private  ItemCrawImpl itemCraw;
    public  void  listRsAgency() throws IOException {
        for (int i=1;i<=9;i++) {
            Connection con = Jsoup.connect("http://whjr.wh.cn/financial/selectFinancialPage").ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
            con.data("orderType", "3");
            con.data("pageNum", i+"" );
            con.header("Content-Type", "application/x-www-form-urlencoded");//这是重点
            Document post = con.post();
            String text = post.text();
            JSONObject jsonObject = JSONObject.fromObject(text);
            String data = jsonObject.getString("data");
            jsonObject = JSONObject.fromObject(data);
            String data2 = jsonObject.getString("rows");
             System.out.println(data2);
            List<Item> list = JSONArray.parseArray(data2, Item.class);
              System.out.println(list);
            // Collectors.toList方法是获取list类型的收集器  distinct方法进行去重  collect进行转换
            //List<Agency> list2 = list.stream().distinct().collect(Collectors.toList());
            itemCraw.saveOrUpdateBatch(list);
            //  System.out.println(list);
            // System.out.println(list2.size());
            //System.out.println(list2);
        }

    }

}
