package com.example.craw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.DoGz;
import com.example.craw.domain.DoJr;
import com.example.craw.service.GzService;
import com.example.craw.service.JrService;
import com.example.craw.service.impl.PaGzAgencyImpl;
import com.example.craw.service.impl.PaGzImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RequestMapping("/jr")
@RestController // 不跳转页面
public class JrController extends BaseController {
    @Autowired
    private JrService jrService;

    @PostMapping("/select")
    public JsonResult select(@RequestBody DoJr doJr) {
        if (StringUtils.isEmpty(doJr.getQueryname())) {
            doJr.setQueryname(null);
        } else {
            doJr.setQueryname(doJr.getQueryname().trim());
        }
        if (StringUtils.isEmpty(doJr.getFinancename())) {
            doJr.setFinancename(null);
        } else {
            doJr.setFinancename(doJr.getFinancename().trim());
        }
        if (StringUtils.isEmpty(doJr.getLoanLimit())) {
            doJr.setLoanLimit(null);
        }
        if (StringUtils.isEmpty(doJr.getLoandate())) {
            doJr.setLoandate(null);
        }
        if (StringUtils.isEmpty(doJr.getGuatype())) {
            doJr.setGuatype(null);
        }
        if (StringUtils.isEmpty(doJr.getFinanceChannel())) {
            doJr.setFinanceChannel(null);
        }

        return new JsonResult(SUCCESS, "查询成功", jrService.selectReqByAll(doJr));
    }

    @GetMapping("/item/{id}")
    public JsonResult item(@PathVariable("id") String id) {

        return new JsonResult(SUCCESS, "查询成功", jrService.findItemById(id));
    }
//    @GetMapping  ("/pa")
//    public JsonResult pa()  {
//       // System.err.println("进入了");
//        try {
//            aMapper.listRsAgency();
//            paGz.listReq();
//            paGz.listRsReq();
//        }catch (IOException e){
//            throw  new RuntimeException("爬取失败！");
//        }
//
//        return new JsonResult(SUCCESS,"成功", null);
//    }


}
