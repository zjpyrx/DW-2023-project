package org.group05.police.Mapper;

import org.apache.ibatis.session.SqlSession;
import org.group05.police.DTO.MovieModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieMapper {
    public List<MovieModel> searchByMovieInfo(Map<String,Object> params, SqlSession sqlSession){

        return sqlSession.selectList("MovieMapper.searchByMovieInfo",params);
    }
    public int countByMovieInfo(Map<String,Object> params, SqlSession sqlSession){
        return sqlSession.selectOne("MovieMapper.countByMovieInfo",params);
    }
}
