package com.example.craw.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
//@RunWith(SpringRunner.class)
@SpringBootTest
public class agencypatest {
    @Autowired
    private PaGzAgencyImpl paGzAgency;

    @Autowired
    private  PaGzImpl paGz;

    @Test
    public void  sss() throws IOException {
        paGzAgency.listRsAgency();
    }

    @Test
    public void  sss2() throws IOException {
        //paGz.listReq();
        paGz.listRsReq();
    }
}
