package com.example.craw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.DoGz;
import com.example.craw.service.GzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/gz")
@RestController // 不跳转页面
public class GzController extends BaseController {

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
}
