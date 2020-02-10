package com.example.craw.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.craw.domain.PageResult;
import com.example.craw.domain.SmallFilm;
import com.example.craw.mapper.CrawFilmMapper;
import com.example.craw.service.CrawService;
import com.example.craw.service.ex.EmptyException;
import org.springframework.util.StringUtils;
import sun.net.www.http.HttpClient;

/**
 * move/1 和 down/2 是无码
 * down 是迅雷种子
 * move 有在线观看连接 有下载地址
 *
 * @author Alex
 */
@Service
public class CrawImpl implements CrawService {
    @Autowired
    private CrawFilmMapper mapper;

    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";
    private static final int OUTTIME = 70000;
    private static final String regEx_publishDate = "code=";
    private static final String reg01 = "http:\\/\\/dl";
    private static final String regEx_End = "\"";
    private static String web;
    private static String sh;
    private static SmallFilm smallFilm = new SmallFilm();

    @Override
    public Integer craw(int type) {
        int sum = 0;
        StringBuilder url = new StringBuilder(web);
        StringBuilder url1 = null;
        url.append("/down/").append(type);

        try {
            for (int i = 1; i <= 2; i++) {
                if (i > 1) {
                    url1 = new StringBuilder(url);
                    url1.append("/index_").append(i).append(".html");

                } else {
                    url1 = url;
                }
                Document doc = Jsoup.connect(url1.toString()).get();
                Elements divsBig = doc.getElementsByClass("box movie2_list");
                Elements elementsUl = divsBig.select("li");
                for (Element elementLi : elementsUl) {
                    String href = elementLi.select("a[href]").attr("href");
                    smallFilm.setFilmadd(href);
                    href = web + href;
                    String u = Jsoup.connect(href).get().toString();
                    String e = u.split(regEx_publishDate)[1];
                    u = e.split(regEx_End)[0];
                    Integer row = mapper.selectByUrl(u);
                    if (row >= 1) {
                        continue;
                    }
                    String src = elementLi.select("img[src]").attr("src");
                    String title = elementLi.select("h3").text();
                    // System.err.println(title);
                    String time = elementLi.getElementsByClass("bg_top").text();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(time);
                    smallFilm.setTitle(title);
                    smallFilm.setUrltype(1);
                    smallFilm.setUrl(u);
                    smallFilm.setDate(date);
                    smallFilm.setLive(null);
                    smallFilm.setImage(src);
                    smallFilm.setMosaic(!(type == 2));
                    smallFilm.setCreatedate(new Date());
                    smallFilm.setFilmurl(1);
                    //System.err.println(smallFilm);
                    mapper.insertFilm(smallFilm);
                    sum++;
                    System.err.println(sum);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("craw连接超时");
        }
        return sum;
    }

    @Override
    public Integer craw2(int type) {
        int sum = 0;
        StringBuilder url = new StringBuilder(web);
        StringBuilder url1 = null;
        url.append("/move/").append(type);
        try {
            for (int i = 1; i <= 2; i++) {
                if (i > 1) {
                    url1 = new StringBuilder(url);
                    url1.append("/index_").append(i).append(".html");
                } else {
                    url1 = url;
                }
                Document doc = Jsoup.connect(url1.toString()).get();
                Elements divsBig = doc.getElementsByClass("box movie2_list");
                Elements elementsUl = divsBig.select("li");
                for (Element elementLi : elementsUl) {
                    String href = elementLi.select("a[href]").attr("href");
                    smallFilm.setFilmadd(href);
                    href = web + href;
                    String u = Jsoup.connect(href).get().toString();
                    String live = u.split("\\+ \"")[1].split("\"")[0];
                    //System.err.println(live);
                    if (u.split(reg01).length == 1) {
                        continue;
                    }
                    String e = u.split(reg01)[1];
                    u = e.split(regEx_End)[0];
                    u = "http://dl" + u;
                    Integer row = mapper.selectByUrl(u);
                    if (row >= 1) {
                        continue;
                    }
                    String src = elementLi.select("img[src]").attr("src");
                    String time = elementLi.getElementsByClass("bg_top").text();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(time);
                    String title = elementLi.select("h3").text();
                    smallFilm.setUrltype(2);
                    smallFilm.setUrl(u);
                    smallFilm.setTitle(title);
                    smallFilm.setDate(date);
                    smallFilm.setLive(live);
                    smallFilm.setImage(src);
                    smallFilm.setMosaic(!(type == 1));
                    smallFilm.setCreatedate(new Date());
                    smallFilm.setFilmurl(1);
                    //System.err.println(smallFilm);
                    mapper.insertFilm(smallFilm);
                    sum++;
                    System.err.println(sum);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("craw2连接超时");
        }
        return sum;
    }

    @Override
    public Integer crawSH() {
        int sum = 0;
        String url = sh + "/vod/html9/";
        Document doc;
        try {
            doc = Jsoup.connect(url).userAgent(USERAGENT).timeout(OUTTIME).get();
            Elements divsBig = doc.select("li[class=col-md-2 col-sm-3 col-xs-4 ]");
            for (Element elementLi : divsBig) {
                String href = elementLi.select("a[href]").attr("href");
                String image = elementLi.select("a[data-original]").attr("data-original");
                String title = elementLi.select("a[title]").attr("title");
                String time = elementLi.select("div[class=subtitle text-time text-overflow]").text();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = simpleDateFormat.parse(time);
                smallFilm.setFilmadd(href);
                href = sh + href;
                Document document = Jsoup.connect(href).userAgent(USERAGENT).timeout(OUTTIME).get();
                if (document == null || StringUtils.isEmpty(document)) {
                    return 0;
                }
                String live = sh + document
                        .select("a[title=在线播放1]").attr("href");
                href = sh + Jsoup.connect(href).userAgent(USERAGENT).timeout(OUTTIME).get().select("a[title=下载地址1]")
                        .attr("href");
                href = Jsoup.connect(href).userAgent(USERAGENT).timeout(OUTTIME).get().select("div[class=download]")
                        .select("a[href]").attr("href");
                Integer row = mapper.selectByUrl(href);
                if (row >= 1) {
                    continue;
                }
                live = Jsoup.connect(live).userAgent(USERAGENT).timeout(OUTTIME).get().toString().split("\\+\"")[1]
                        .split("\"")[0];
                //System.err.println(live);
                smallFilm.setUrl(href);
                smallFilm.setImage(image);
                smallFilm.setTitle(title);
                smallFilm.setDate(date);
                smallFilm.setCreatedate(new Date());
                smallFilm.setMosaic(false);
                smallFilm.setUrltype(2);
                smallFilm.setLive(live);
                smallFilm.setFilmurl(2);
                //System.err.println(smallFilm);
                mapper.insertFilm(smallFilm);
                sum++;
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("crawSH连接超时");
        }

        return sum;
    }

    @Override
    public PageResult findByMosaic(Integer page, Integer rows, boolean mosaic) throws EmptyException {
        int total = mapper.countByMosaic(mosaic);
        if (total > 0) {
            int start = (page - 1) * rows;
            List<SmallFilm> m = mapper.selectByMosaic(start, rows, mosaic);
            return new PageResult(m, total, page, rows);
        }
        return PageResult.empty(rows);


    }

    @Override
    public List<SmallFilm> show() throws EmptyException {
        List<SmallFilm> m = mapper.showByTime();
        if (m.size() == 0) {
            throw new EmptyException("内容为空！");
        }
        return m;
    }

    @Override
    public PageResult selectTitle(Integer currentPage, Integer rows, Date beginDate, Date endDate, Integer state, String title) throws EmptyException {

        int total = mapper.countByTitle(beginDate, endDate, state, title);
        if (total > 0) {
            int start = (currentPage - 1) * rows;
            List<SmallFilm> m = mapper.selectByTitle(start, rows, beginDate, endDate, state, title);
            return new PageResult(m, total, currentPage, rows);
        }
        return PageResult.empty(rows);
    }

    @Override
    public Integer crawALL() {

        int sum = extracted2(1) + extracted2(2) + extracted(1) + extracted(2);
        return sum;


    }

    private int extracted2(int type) {
        int sum = 0;
        StringBuilder url = new StringBuilder(web);
        StringBuilder url1 = null;
        url.append("/move/").append(type);
        try {
            url1 = new StringBuilder(url);
            url1.append("/index.html");
            Document doc = Jsoup.connect(url1.toString()).get();
            Elements index = doc.getElementsByClass("box movie_list");
            if (index == null) {
                throw new RuntimeException("网页变更了！");
            }
            String s = index.text().substring(5, 8).trim();

            for (int i = 1; i <= Integer.parseInt(s); i++) {
                if (i > 1) {
                    url1 = new StringBuilder(url);
                    url1.append("/index_").append(i).append(".html");

                }
                doc = Jsoup.connect(url1.toString()).get();
                Elements divsBig = doc.getElementsByClass("box movie2_list");
                Elements elementsUl = divsBig.select("li");
                for (Element elementLi : elementsUl) {
                    String href = elementLi.select("a[href]").attr("href");
                    smallFilm.setFilmadd(href);
                    href = web + href;
                    String u = Jsoup.connect(href).get().toString();
                    String live = u.split("\\+ \"")[1].split("\"")[0];
                    //System.err.println(live);
                    if (u.split(reg01).length == 1) {
                        continue;
                    }
                    String e = u.split(reg01)[1];
                    u = e.split(regEx_End)[0];
                    u = "http://dl" + u;
                    Integer row = mapper.selectByUrl(u);
                    if (row >= 1) {
                        continue;
                    }
                    String src = elementLi.select("img[src]").attr("src");
                    String time = elementLi.getElementsByClass("bg_top").text();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(time);
                    String title = elementLi.select("h3").text();
                    smallFilm.setUrltype(2);
                    smallFilm.setUrl(u);
                    smallFilm.setTitle(title);
                    smallFilm.setDate(date);
                    smallFilm.setLive(live);
                    smallFilm.setImage(src);
                    smallFilm.setMosaic(!(type == 1));
                    smallFilm.setCreatedate(new Date());
                    smallFilm.setFilmurl(1);
                    //System.err.println(smallFilm);
                    mapper.insertFilm(smallFilm);
                    sum++;
                    System.err.println(sum);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("extracted2连接超时");
        }
        return sum;
    }

    private int extracted(int type) {
        int sum = 0;
        StringBuilder url = new StringBuilder(web);
        StringBuilder url1 = null;

        url.append("/down/").append(type);
        try {
            url1 = new StringBuilder(url);
            url1.append("/index.html");
            Document doc = Jsoup.connect(url1.toString()).get();
            Elements index = doc.getElementsByClass("box movie_list");
            if (index == null) {
                throw new RuntimeException("网页变更了！");
            }
            String s = index.text().substring(5, 8).trim();

            for (int i = 1; i <= Integer.parseInt(s); i++) {
                if (i > 1) {
                    url1 = new StringBuilder(url);
                    url1.append("/index_").append(i).append(".html");

                }
                doc = Jsoup.connect(url1.toString()).get();
                Elements divsBig = doc.getElementsByClass("box movie2_list");
                Elements elementsUl = divsBig.select("li");
                for (Element elementLi : elementsUl) {
                    String href = elementLi.select("a[href]").attr("href");
                    smallFilm.setFilmadd(href);
                    href = web + href;
                    String u = Jsoup.connect(href).get().toString();
                    if (u.split(regEx_publishDate).length == 1) {
                        continue;
                    }
                    String e = u.split(regEx_publishDate)[1];
                    u = e.split(regEx_End)[0];
                    Integer row = mapper.selectByUrl(u);
                    if (row >= 1) {
                        continue;
                    }
                    String src = elementLi.select("img[src]").attr("src");
                    String title = elementLi.select("h3").text();
                    // System.err.println(title);
                    String time = elementLi.getElementsByClass("bg_top").text();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(time);
                    smallFilm.setTitle(title);
                    smallFilm.setUrltype(1);
                    smallFilm.setUrl(u);
                    smallFilm.setDate(date);
                    smallFilm.setLive(null);
                    smallFilm.setImage(src);
                    smallFilm.setMosaic(!(type == 2));
                    smallFilm.setCreatedate(new Date());
                    smallFilm.setFilmurl(1);
                    //System.err.println(smallFilm);
                    mapper.insertFilm(smallFilm);
                    sum++;
                    System.err.println(sum);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("extracted连接超时");
        }
        return sum;
    }

    @Override
    public Integer crawSHALL() {
        return null;
    }

    /**
     * 检验url正确性
     *
     * @return
     * @throws IOException
     */
    @Override
    public Integer newWeb() throws IOException {
        int s = 0;
        web = mapper.selectByName("web");
        sh = mapper.selectByName("sh");


        Document doc = Jsoup.connect(web + "/move/1").get();
        Elements divsBig = doc.getElementsByClass("box movie2_list");
        if (divsBig.isEmpty()) {
            String str = doc.toString().split("href=\"")[1].split("/\"")[0];
            // System.err.println(str);
            web = str;
            mapper.updateByName("web", str);
            s++;
        }

        URL serverUrl = new URL(sh);
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setConnectTimeout(OUTTIME);
        conn.setRequestMethod("GET");
        conn.setInstanceFollowRedirects(false);
        conn.addRequestProperty("Accept-Charset", "UTF-8;");
        conn.addRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
        conn.setInstanceFollowRedirects(true);

        String status = conn.getURL().toString();

        if (!sh.equals(status)) {
            sh = status;
            mapper.updateByName("sh", status);
            s++;
        }
        return s;
    }

    @Override
    public void test(String s) {
        try {

            URL serverUrl = new URL(s);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(OUTTIME);
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false);
            conn.addRequestProperty("Accept-Charset", "UTF-8;");
            conn.addRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
            conn.setInstanceFollowRedirects(true);

            URL status = conn.getURL();
            System.err.println(status);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
