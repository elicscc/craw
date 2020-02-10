package com.example.craw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.craw.mapper")
public class CrawApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawApplication.class, args);
	}

}
