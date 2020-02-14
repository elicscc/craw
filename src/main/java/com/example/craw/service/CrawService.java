package com.example.craw.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.example.craw.domain.PageResult;
import com.example.craw.domain.SmallFilm;
import com.example.craw.service.ex.EmptyException;


public interface CrawService {
	Integer craw(int type) throws RuntimeException;

	Integer craw2(int type) throws RuntimeException;

	Integer crawSH()throws RuntimeException;

	PageResult findByMosaic(Integer page, Integer rows, boolean mosaic) throws EmptyException;

	List<SmallFilm> show() throws EmptyException;

	Integer updateDFUrl() throws IOException;

	Integer updateSHUrl() throws IOException;

	PageResult selectTitle(Integer currentPage,Integer rows,Date beginDate ,Date endDate ,Integer state,String title) throws EmptyException;

	Integer crawALL();

	Integer crawSHALL();

	/**
	 * 链接测试
	 */
	void test(String s);
}
