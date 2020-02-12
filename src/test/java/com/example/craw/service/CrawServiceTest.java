package com.example.craw.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawServiceTest {
    @Autowired
    CrawService service;
    @Test
    public void craw(){
        service.craw(1);
        //service.craw(2);
    }
    @Test
    public void craw2(){
        service.craw2(1);
        //service.craw2(2);
    }
    @Test
    public void find(){
        System.err.println(service.findByMosaic(1,10,false));
        
    }
    @Test
    public void crawSH(){
        System.err.println(service.crawSH());
        
    }
    @Test
    public void newWeb() throws IOException{
        System.err.println(service.newWeb());
        
    }
    @Test
    public void te(){
    	service.test("");
    	
    }
    
    
}
