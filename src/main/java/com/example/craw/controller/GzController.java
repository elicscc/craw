package com.example.craw.controller;

import com.example.craw.domain.DoGz;
import com.example.craw.domain.PageResult;
import com.example.craw.service.GzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/gz")
@RestController // 不跳转页面
public class GzController extends BaseController {
    @Autowired
    private GzService gzService;


    @PostMapping("/req")
    public JsonResult<PageResult> select(@RequestBody DoGz doGz)  {
        //System.out.println(doGz);
        if (doGz.getTitle() == "" || doGz.getTitle() == " ") {
            doGz.setTitle(null) ;
        } else {
            doGz.setTitle(doGz.getTitle().trim()) ;
        }
        PageResult m = null;
        if (doGz.getState() == 3) {
            m = gzService.selectAgencyByAll(doGz.getCurrentPage(), doGz.getRows(), doGz.getTitle());
        } else if (doGz.getState() == 1) {
            m = gzService.selectReqByAll(doGz.getCurrentPage(), doGz.getRows(), doGz.getBeginDate(), doGz.getEndDate(), doGz.getTitle());
        } else if (doGz.getState() == 2) {
            m = gzService.selectRsReqByAll(doGz.getCurrentPage(), doGz.getRows(), doGz.getBeginDate(), doGz.getEndDate(), doGz.getTitle());
        }


        return new JsonResult<>(SUCCESS, m);
    }

}
