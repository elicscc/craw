//package com.example.craw.mapper;
//
//
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.example.craw.domain.SmallFilm;
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CrawFilmTest {
//    @Autowired
//    CrawFilmMapper mapper;
//    @Test
//    public void s() {
//        SmallFilm s=new SmallFilm() ;
//        s.setFilmadd("测试");
//        s.setImage("测试");
//        Integer row= mapper.insertFilm(s);
//        System.err.println(row);
//
//    }
//
//
//
//    @Test
//    public void selectByName() {
//        System.err.println(mapper.selectByName("web"));
//    }
//    @Test
//    public void selectMo() {
//
//        List<SmallFilm> m=mapper.selectByMosaic(1,20,false);
//        for (SmallFilm smallFilm : m) {
//            System.err.println(smallFilm);
//        }
//
//    }
//
//}
