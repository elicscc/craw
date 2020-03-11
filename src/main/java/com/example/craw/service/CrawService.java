package com.example.craw.service;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.craw.domain.SmallFilm;


public interface CrawService {
	Integer craw(int type) ;

	Integer craw2(int type) ;

	Integer crawSH();

	Page<SmallFilm> findByMosaic(Integer page, Integer rows, boolean mosaic) ;

	Page<SmallFilm> show(Integer page, Integer rows) ;

	Integer updateDFUrl() throws IOException;

	Integer updateSHUrl() throws IOException;

	Page<SmallFilm> selectTitle(Integer currentPage,Integer rows,Date beginDate ,Date endDate ,Integer state,String title) ;

	//Integer crawALL();

	//Integer crawSHALL();

	/**
	 * 链接测试
	 */
	void test(String s);
}
