package org.group05.police.controller;

import org.apache.ibatis.session.SqlSession;
import org.group05.police.Mapper.MovieMapper;
import org.group05.police.DTO.MovieModel;
import org.group05.police.tools.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")
@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class MovieController {
    @GetMapping("/allInfo")
    public List<MovieModel> searchByMovieInfo(@RequestParam(required = false) String title,
                                              @RequestParam(required = false) String director,
                                              @RequestParam(required = false) String actor,
                                              @RequestParam(required = false) String catalog,
                                              @RequestParam(required = false) String score,
                                              @RequestParam(required = false) String ratio){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }

        MovieMapper movieMapper=new MovieMapper();
        System.out.println("/////////////////");
        System.out.println(title);
        Map<String,Object> params=new HashMap<>();
        params.put("title",title);
        params.put("director",director);
        params.put("actor",actor);
        params.put("catalog",catalog);
        if(!score.isEmpty()){
            params.put("score",Float.parseFloat(score));
        }

        params.put("ratio",ratio);
        System.out.println(params);
        List<MovieModel> resList=movieMapper.searchByMovieInfo(params,sqlSession);
        System.out.println(resList.size());
        //for(MovieModel my : resList){
            //my.printm();
        //}
        System.out.println("/////////////////");
        return resList;
    }


    @GetMapping("/countAllInfo")
    public int countByMovieInfo(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String director,
                                @RequestParam(required = false) String actor,
                                @RequestParam(required = false) String catalog,
                                @RequestParam(required = false) String score,
                                @RequestParam(required = false) String ratio){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }

        MovieMapper movieMapper=new MovieMapper();
        System.out.println("/////////////////");
        System.out.println(title);
        Map<String,Object> params=new HashMap<>();
        params.put("title",title);
        params.put("director",director);
        params.put("actor",actor);
        params.put("catalog",catalog);
        params.put("score",score);
        int res=movieMapper.countByMovieInfo(params,sqlSession);
        System.out.println("/////////////////");
        return res;
    }
}
