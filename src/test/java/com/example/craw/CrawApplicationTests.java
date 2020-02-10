package com.example.craw;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.craw.domain.SmallFilm;
import com.example.craw.mapper.CrawFilmMapper;



@SpringBootTest
class CrawApplicationTests {
@Autowired 
CrawFilmMapper mapper;
private 
	@Test
	void contextLoads() {
	    
	}
	@Test
	private void s() {
	    SmallFilm s=new SmallFilm() ;
	    s.setFilmadd("测试");
	    s.setImage("测试");
	    mapper.insertFilm(s);
	    
	}

}
