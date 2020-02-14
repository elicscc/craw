package com.example.craw.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.craw.service.ex.EmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.craw.domain.PageResult;
import com.example.craw.domain.SmallFilm;
import com.example.craw.service.CrawService;
import com.example.craw.service.ex.ConnException;

@RequestMapping("/craw")
@RestController // 不跳转页面
public class CrawController extends BaseController {
    @Autowired
    private CrawService service;

    /**
     * 爬取东方
     *
     * @return
     * @throws IOException
     */
    @GetMapping("pa")
    public JsonResult<Integer> craw() throws IOException, RuntimeException {
        service.updateDFUrl();
        int s = service.craw(1) + service.craw(2) + service.craw2(1) + service.craw2(2);
        return new JsonResult<Integer>(SUCCESS, s);
    }

    // @GetMapping("t666")
    public JsonResult<Integer> craw666() {
        throw new EmptyException("内容为空！");
        // throw new  RuntimeException("测试");
    }

    /**
     * 爬取四虎
     *
     * @return
     * @throws IOException
     */
    @GetMapping("pash")
    public JsonResult<Integer> pash() throws IOException, RuntimeException {
        service.updateSHUrl();
        int s = service.crawSH();
        return new JsonResult<Integer>(SUCCESS, s);
    }

    /**
     * 爬所有网页
     *
     * @return
     * @throws IOException
     * @throws ConnException
     */
    @GetMapping("paall")
    public JsonResult<Integer> paAll() throws IOException, RuntimeException {
       // service.updateSHUrl();
        service.updateDFUrl();
        int s = service.crawALL();// + service.crawSHALL();
        return new JsonResult<Integer>(SUCCESS, s);
    }

    @GetMapping("wm")
    public JsonResult<PageResult> wm(Integer page, Integer rows) {
        PageResult m = service.findByMosaic(page, rows, false);
        return new JsonResult<PageResult>(SUCCESS, m);
    }

    @GetMapping("ym")
    public JsonResult<PageResult> ym(Integer page, Integer rows) {
        PageResult m = service.findByMosaic(page, rows, true);
        return new JsonResult<PageResult>(SUCCESS, m);
    }

    @GetMapping("show")
    public JsonResult<List<SmallFilm>> show() {
        List<SmallFilm> m = service.show();
        return new JsonResult<List<SmallFilm>>(SUCCESS, m);
    }

    @PostMapping("select")
    public JsonResult<PageResult> selectTitle(Integer currentPage, Integer rows, String beginDate, String endDate,
                                              Integer state, String title) throws ParseException {
        if (title == "" || title == " ") {
            title = null;
        } else {
            title = title.trim();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date begDate, eDate;
        if (("").equals(beginDate)) {
            begDate = null;
        } else {

            begDate = simpleDateFormat.parse(beginDate);
        }
        if (("").equals(endDate)) {
            eDate = null;
        } else {
            eDate = simpleDateFormat.parse(endDate);
        }

        PageResult m = service.selectTitle(currentPage, rows, begDate, eDate, state, title);
        return new JsonResult<PageResult>(SUCCESS, m);
    }

}
