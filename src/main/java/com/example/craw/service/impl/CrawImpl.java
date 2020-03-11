package com.example.craw.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.Req;
import com.example.craw.domain.WebUrl;
import com.example.craw.mapper.WebUrlMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    @Autowired
    private WebUrlMapper webUrlMapper;

    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";
    private static final int OUTTIME = 70000;
    private static final String regEx_publishDate = "code=";
    private static final String reg01 = "http:\\/\\/dl";
    private static final String regEx_End = "\"";
    private static String web = null;
    private static String sh = null;
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
                    smallFilm.setFilmAdd(href);
                    href = web + href;
                    String u = Jsoup.connect(href).get().toString();
                    String e = u.split(regEx_publishDate)[1];
                    u = e.split(regEx_End)[0];
                    QueryWrapper<SmallFilm> queryWrapper=new QueryWrapper<>();
                    queryWrapper.eq("url",u);
                    if (null!=mapper.selectOne(queryWrapper)) {
                        //System.err.println("跳过");
                        continue;
                    }
                    String src = elementLi.select("img[src]").attr("src");
                    String title = elementLi.select("h3").text();
                    // System.err.println(title);
                    String time = elementLi.getElementsByClass("bg_top").text();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(time);
                    smallFilm.setTitle(title);
                    smallFilm.setUrlType(1);
                    smallFilm.setUrl(u);
                    smallFilm.setDate(date);
                    smallFilm.setLive(null);
                    smallFilm.setImage(src);
                    smallFilm.setMosaic(!(type == 2));
                    smallFilm.setCreateDate(new Date());
                    smallFilm.setFilmUrl(1);
                   // System.err.println(smallFilm);
                    mapper.insert(smallFilm);
                    sum++;
                   // System.err.println(sum);
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
                    smallFilm.setFilmAdd(href);
                    href = web + href;
                    String u = Jsoup.connect(href).get().toString();
                    String live = u.split("\\+ \"")[1].split("\"")[0];
                    //System.err.println(live);
                    if (u.split(reg01).length == 1) {
                        //System.err.println("跳过");
                        continue;
                    }
                    String e = u.split(reg01)[1];
                    u = e.split(regEx_End)[0];
                    u = "http://dl" + u;
                    QueryWrapper<SmallFilm> queryWrapper=new QueryWrapper<>();
                    queryWrapper.eq("url",u);
                    if (null!=mapper.selectOne(queryWrapper)) {
                        continue;
                    }
                    String src = elementLi.select("img[src]").attr("src");
                    String time = elementLi.getElementsByClass("bg_top").text();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(time);
                    String title = elementLi.select("h3").text();
                    smallFilm.setUrlType(2);
                    smallFilm.setUrl(u);
                    smallFilm.setTitle(title);
                    smallFilm.setDate(date);
                    smallFilm.setLive(live);
                    smallFilm.setImage(src);
                    smallFilm.setMosaic(!(type == 1));
                    smallFilm.setCreateDate(new Date());
                    smallFilm.setFilmUrl(1);
                   // System.err.println(smallFilm);
                    mapper.insert(smallFilm);
                    sum++;
                    //System.err.println(sum);
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
                smallFilm.setFilmAdd(href);
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
                QueryWrapper<SmallFilm> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("url",href);
                if (null!=mapper.selectOne(queryWrapper)) {
                    //System.err.println("跳过");
                    continue;
                }
                live = Jsoup.connect(live).userAgent(USERAGENT).timeout(OUTTIME).get().toString();
                if (live.split("\\+\"").length == 1) {
                    //System.err.println(live);
                    throw new RuntimeException("异常");
                }
                live = live.split("\\+\"")[1].split("\"")[0];
                //System.err.println(live);
                smallFilm.setUrl(href);
                smallFilm.setImage(image);
                smallFilm.setTitle(title);
                smallFilm.setDate(date);
                smallFilm.setCreateDate(new Date());
                smallFilm.setMosaic(false);
                smallFilm.setUrlType(2);
                smallFilm.setLive(live);
                smallFilm.setFilmUrl(2);
                //System.err.println(smallFilm);
                mapper.insert(smallFilm);
                sum++;
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("crawSH连接超时");
        }
        return sum;
    }

    @Override
    public Page<SmallFilm> findByMosaic(Integer page, Integer rows, boolean mosaic) {
        Page<SmallFilm> re=new Page<SmallFilm>(page,rows);
        QueryWrapper<SmallFilm> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mosaic",mosaic);
        return mapper.selectPage(re,queryWrapper);
    }

    @Override
    public Page<SmallFilm> show(Integer currentPage,Integer rows) {
        Page<SmallFilm> re=new Page<SmallFilm>(currentPage,rows);
        QueryWrapper<SmallFilm> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("date");
        return mapper.selectPage(re,queryWrapper);
    }



    @Override
    public  Page<SmallFilm> selectTitle(Integer currentPage, Integer rows, Date beginDate, Date endDate, Integer state, String title) throws EmptyException {
        Page<SmallFilm> re=new Page<SmallFilm>(currentPage,rows);
        QueryWrapper<SmallFilm> queryWrapper=new QueryWrapper<>();
        if (state<2){ queryWrapper.eq("mosaic",state);}
        if (title!=null){queryWrapper.like("title",title);}
        if (beginDate!=null){ queryWrapper.ge("date",beginDate);}
        if (endDate!=null){ queryWrapper.le("date",endDate);}
        queryWrapper.orderByDesc("date");
        return mapper.selectPage(re,queryWrapper);
    }

//    @Override
//    public Integer crawALL() {
//        int sum = extracted2(1) + extracted2(2) + extracted(1) + extracted(2);
//        return sum;
//    }
//
//    private int extracted2(int type) {
//        int sum = 0;
//        StringBuilder url = new StringBuilder(web);
//        StringBuilder url1 = null;
//        url.append("/move/").append(type);
//        try {
//            url1 = new StringBuilder(url);
//            url1.append("/index.html");
//            Document doc = Jsoup.connect(url1.toString()).get();
//            Elements index = doc.getElementsByClass("box movie_list");
//            if (index == null) {
//                throw new RuntimeException("网页变更了！");
//            }
//            String s = index.text().substring(5, 8).trim();
//
//            for (int i = 1; i <= Integer.parseInt(s); i++) {
//                if (i > 1) {
//                    url1 = new StringBuilder(url);
//                    url1.append("/index_").append(i).append(".html");
//
//                }
//                doc = Jsoup.connect(url1.toString()).get();
//                Elements divsBig = doc.getElementsByClass("box movie2_list");
//                Elements elementsUl = divsBig.select("li");
//                for (Element elementLi : elementsUl) {
//                    String href = elementLi.select("a[href]").attr("href");
//                    smallFilm.setFilmAdd(href);
//                    href = web + href;
//                    String u = Jsoup.connect(href).get().toString();
//                    String live = u.split("\\+ \"")[1].split("\"")[0];
//                    //System.err.println(live);
//                    if (u.split(reg01).length == 1) {
//                        continue;
//                    }
//                    String e = u.split(reg01)[1];
//                    u = e.split(regEx_End)[0];
//                    u = "http://dl" + u;
//                    Integer row = mapper.selectByUrl(u);
//                    if (row >= 1) {
//                        continue;
//                    }
//                    String src = elementLi.select("img[src]").attr("src");
//                    String time = elementLi.getElementsByClass("bg_top").text();
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = simpleDateFormat.parse(time);
//                    String title = elementLi.select("h3").text();
//                    smallFilm.setUrltype(2);
//                    smallFilm.setUrl(u);
//                    smallFilm.setTitle(title);
//                    smallFilm.setDate(date);
//                    smallFilm.setLive(live);
//                    smallFilm.setImage(src);
//                    smallFilm.setMosaic(!(type == 1));
//                    smallFilm.setCreatedate(new Date());
//                    smallFilm.setFilmurl(1);
//                    //System.err.println(smallFilm);
//                    mapper.insertFilm(smallFilm);
//                    sum++;
//                    System.err.println(sum);
//                }
//            }
//        } catch (IOException | ParseException e) {
//            throw new RuntimeException("extracted2连接超时");
//        }
//        return sum;
//    }
//
//    private int extracted(int type) {
//        int sum = 0;
//        StringBuilder url = new StringBuilder(web);
//        StringBuilder url1 = null;
//
//        url.append("/down/").append(type);
//        try {
//            url1 = new StringBuilder(url);
//            url1.append("/index.html");
//            Document doc = Jsoup.connect(url1.toString()).get();
//            Elements index = doc.getElementsByClass("box movie_list");
//            if (index == null) {
//                throw new RuntimeException("网页变更了！");
//            }
//            String s = index.text().substring(5, 8).trim();
//
//            for (int i = 1; i <= Integer.parseInt(s); i++) {
//                if (i > 1) {
//                    url1 = new StringBuilder(url);
//                    url1.append("/index_").append(i).append(".html");
//
//                }
//                doc = Jsoup.connect(url1.toString()).get();
//                Elements divsBig = doc.getElementsByClass("box movie2_list");
//                Elements elementsUl = divsBig.select("li");
//                for (Element elementLi : elementsUl) {
//                    String href = elementLi.select("a[href]").attr("href");
//                    smallFilm.setFilmadd(href);
//                    href = web + href;
//                    String u = Jsoup.connect(href).get().toString();
//                    if (u.split(regEx_publishDate).length == 1) {
//                        continue;
//                    }
//                    String e = u.split(regEx_publishDate)[1];
//                    u = e.split(regEx_End)[0];
//                    Integer row = mapper.selectByUrl(u);
//                    if (row >= 1) {
//                        continue;
//                    }
//                    String src = elementLi.select("img[src]").attr("src");
//                    String title = elementLi.select("h3").text();
//                    // System.err.println(title);
//                    String time = elementLi.getElementsByClass("bg_top").text();
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = simpleDateFormat.parse(time);
//                    smallFilm.setTitle(title);
//                    smallFilm.setUrltype(1);
//                    smallFilm.setUrl(u);
//                    smallFilm.setDate(date);
//                    smallFilm.setLive(null);
//                    smallFilm.setImage(src);
//                    smallFilm.setMosaic(!(type == 2));
//                    smallFilm.setCreatedate(new Date());
//                    smallFilm.setFilmurl(1);
//                    //System.err.println(smallFilm);
//                    mapper.insertFilm(smallFilm);
//                    sum++;
//                    System.err.println(sum);
//                }
//            }
//        } catch (IOException | ParseException e) {
//            throw new RuntimeException("extracted连接超时");
//        }
//        return sum;
//    }

//    @Override
//    public Integer crawSHALL() {
//        return null;
//    }

    /**
     * 检验url正确性
     *
     * @return
     * @throws IOException
     */
    @Override
    public Integer updateDFUrl() throws IOException {
        int s = 0;
        QueryWrapper<WebUrl> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("web_name","df");
        WebUrl re = webUrlMapper.selectOne(queryWrapper);
        web=re.getWebUrl();
        Document doc = Jsoup.connect(CrawImpl.web + "/move/1").get();
        Elements divsBig = doc.getElementsByClass("box movie2_list");
        if (divsBig.isEmpty()) {
            String str = doc.toString().split("href=\"")[1].split("/\"")[0];
            // System.err.println(str);
            web = str;
            re.setWebUrl(str);
            webUrlMapper.update(re,queryWrapper);
            s++;
           // System.err.println("dfurl变更");
        }
        return s;
    }

    @Override
    public Integer updateSHUrl() throws IOException {
        int s = 0;
        QueryWrapper<WebUrl> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("web_name","sh");
        WebUrl re = webUrlMapper.selectOne(queryWrapper);
        sh = re.getWebUrl();
        URL serverUrl = new URL(CrawImpl.sh);
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
            re.setWebUrl(status);
            webUrlMapper.update(re,queryWrapper);
            s++;
            //System.err.println("shurl变更");
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
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
            conn.setInstanceFollowRedirects(true);
            URL status = conn.getURL();
            //System.err.println(status);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
