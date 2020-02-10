package com.example.craw.mapper;

import java.util.Date;
import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.example.craw.domain.SmallFilm;

public interface CrawFilmMapper {
    Integer insertFilm(SmallFilm smallFilm);

    Integer selectByUrl(String url);

    /**
     * 根据是否有码查询
     * 
     * @param m
     * @return
     */
    List<SmallFilm> selectByMosaic(@Param("start")Integer start,@Param("rows")Integer rows,@Param("mosaic")boolean mosaic);

    List<SmallFilm> showByTime();

    void updateByName(@Param("webname")String webname,@Param("weburl")String weburl);

    String selectByName(String webname);

    List<SmallFilm> selectByTitle(@Param("start")Integer start,@Param("rows")Integer rows,@Param("beginDate")Date beginDate ,@Param("endDate")Date endDate ,@Param("state")Integer state,@Param("title")String title);


	int countByMosaic(boolean mosaic);

	int countByTitle(@Param("beginDate")Date beginDate ,@Param("endDate")Date endDate ,@Param("state")Integer state,@Param("title")String title);
}
