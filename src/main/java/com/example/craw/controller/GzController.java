package com.example.craw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.DoGz;
import com.example.craw.service.GzService;
import com.example.craw.service.impl.PaGzAgencyImpl;
import com.example.craw.service.impl.PaGzImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RequestMapping("/gz")
@RestController // 不跳转页面
public class GzController extends BaseController {
    @Autowired
    private PaGzAgencyImpl aMapper;
    @Autowired
    private PaGzImpl paGz;
    @Autowired
    private GzService gzService;

    @PostMapping("/req")
    public JsonResult select(@RequestBody DoGz doGz)  {
        if (StringUtils.isEmpty(doGz.getTitle()) ) {
            doGz.setTitle(null) ;
        } else {
            doGz.setTitle(doGz.getTitle().trim()) ;
        }
        Page m=null;
        if (doGz.getState() == 3) {
            m = gzService.selectAgencyByAll(doGz.getCurrentPage(), doGz.getRows(), doGz.getTitle());
        } else if (doGz.getState() == 1) {
            m = gzService.selectReqByAll(doGz.getCurrentPage(), doGz.getRows(), doGz.getBeginDate(), doGz.getEndDate(), doGz.getTitle(),doGz.getState());
        } else if (doGz.getState() == 2) {
            m = gzService.selectRsReqByAll(doGz.getCurrentPage(), doGz.getRows(), doGz.getBeginDate(), doGz.getEndDate(), doGz.getTitle(),doGz.getState());
        }
        return new JsonResult(SUCCESS,"查询成功", m);
    }

    @GetMapping  ("/pa")
    public JsonResult pa()  {
       // System.err.println("进入了");
        try {
            aMapper.listRsAgency();
            paGz.listReq();
            paGz.listRsReq();
        }catch (IOException e){
            throw  new RuntimeException("爬取失败！");
        }

        return new JsonResult(SUCCESS,"成功", null);
    }
}
