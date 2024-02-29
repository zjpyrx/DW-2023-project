package org.group05.police.Mapper;

import org.apache.ibatis.session.SqlSession;
import org.group05.police.DTO.MovieModel;

import java.util.List;
import java.util.Map;


public class TimeMapper {
    public List<MovieModel> searchByYear(Map<String,Object> params, SqlSession sqlSession){
        return sqlSession.selectList("TimeMapper.searchByYear",params);
    }
    public List<MovieModel> searchByQuarter(Map<String,Object> params, SqlSession sqlSession){

        return sqlSession.selectList("TimeMapper.searchByQuarter",params);
    }
    public List<MovieModel> searchByMonth(Map<String,Object> params, SqlSession sqlSession){

        return sqlSession.selectList("TimeMapper.searchByMonth",params);
    }
    public List<MovieModel> searchByDate(Map<String,Object> params, SqlSession sqlSession){

        return sqlSession.selectList("TimeMapper.searchByDate",params);
    }
}
