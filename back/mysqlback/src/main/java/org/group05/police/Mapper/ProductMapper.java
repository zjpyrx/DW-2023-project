package org.group05.police.Mapper;

import org.apache.ibatis.session.SqlSession;
import org.group05.police.DTO.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductMapper {
    public List<ProductModel> searchProductByTitle(Map<String,String> params, SqlSession sqlSession){
        List<Map<String,String>> movieID=sqlSession.selectList("ProductMapper.getMovieID",params);
        System.out.println(movieID);
        List<ProductModel> res= new ArrayList<>();
        for(Map<String,String> movieid : movieID){
            params.put("movieID",movieid.get("movie_id"));
            List<ProductModel> tempres=sqlSession.selectList("ProductMapper.searchProductByTitle",params);
            for(ProductModel temp : tempres){
                temp.setTitle(movieid.get("title"));
            }
            res.addAll(tempres);
        }
        return res;
    }
}
