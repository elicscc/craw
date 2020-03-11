package com.example.craw.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.DoGz;
import com.example.craw.service.ex.EmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.example.craw.domain.SmallFilm;
import com.example.craw.service.CrawService;
import com.example.craw.service.ex.ConnException;
@CrossOrigin
@RequestMapping("/craw")
@RestController
public class CrawController extends BaseController {
    @Autowired
    private CrawService service;

    /**
     * 爬取东方
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/padf")
    public JsonResult craw() throws IOException, RuntimeException {
        //System.err.println("开始检查url");
        service.updateDFUrl();
       // System.err.println("开始pa");
        int s = service.craw(1) + service.craw(2) + service.craw2(1) + service.craw2(2);
        return new JsonResult(SUCCESS, "成功",s);
    }

    // @GetMapping("t666")
    public JsonResult craw666() {
        throw new EmptyException("内容为空！");
        // throw new  RuntimeException("测试");
    }

    /**
     * 爬取四虎
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/pash")
    public JsonResult pash() throws IOException, RuntimeException {
        service.updateSHUrl();
        int s = service.crawSH();
        return new JsonResult(SUCCESS,"成功", s);
    }

    /**
     * 爬所有网页
     *
     * @return
     * @throws IOException
     * @throws ConnException
     */
//    @GetMapping("paall")
//    public JsonResult paAll() throws IOException, RuntimeException {
//       // service.updateSHUrl();
//        service.updateDFUrl();
//        int s = service.crawALL();// + service.crawSHALL();
//        return new JsonResult(SUCCESS,"成功", s);
//    }
//    @GetMapping("/wm")
//    public JsonResult wm(Integer page, Integer rows) {
//        Page m = service.findByMosaic(page, rows, false);
//        return new JsonResult(SUCCESS, "成功",m);
//    }
//    @GetMapping("/ym")
//    public JsonResult ym(Integer page, Integer rows) {
//        Page m = service.findByMosaic(page, rows, true);
//        return new JsonResult(SUCCESS, "成功",m);
//    }
//    @GetMapping("/show")
//    public JsonResult show() {
//
//        return new JsonResult(SUCCESS,"成功", service.show(1,10));
//    }
    @PostMapping("/select")
    public JsonResult selectTitle(@RequestBody DoGz doGz) throws ParseException {
        if (StringUtils.isEmpty(doGz.getTitle())) {
            doGz.setTitle(null) ;
        } else {
            doGz.setTitle(doGz.getTitle().trim()) ;
        }

        return new JsonResult(SUCCESS, "成功",service.selectTitle(doGz.getCurrentPage(), doGz.getRows(), doGz.getBeginDate(), doGz.getEndDate(),doGz.getState() , doGz.getTitle()));
    }

}
