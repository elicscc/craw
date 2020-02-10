package com.example.craw.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.example.craw.domain.PageResult;
import com.example.craw.domain.SmallFilm;
import com.example.craw.service.ex.EmptyException;
import org.springframework.util.StringUtils;

public interface CrawService {
	Integer craw(int type);

	Integer craw2(int type);

	Integer crawSH();

	PageResult findByMosaic(Integer page, Integer rows, boolean mosaic) throws EmptyException;

	List<SmallFilm> show() throws EmptyException;

	Integer newWeb() throws IOException;

	PageResult selectTitle(Integer currentPage,Integer rows,Date beginDate ,Date endDate ,Integer state,String title) throws EmptyException;

	Integer crawALL();

	Integer crawSHALL();

	/**
	 * 链接测试
	 */
	void test(String s);
}
