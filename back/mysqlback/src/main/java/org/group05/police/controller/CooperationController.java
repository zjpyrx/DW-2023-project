package org.group05.police.controller;

import org.apache.ibatis.session.SqlSession;
import org.group05.police.tools.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.group05.police.DTO.CooperationModel;
import org.group05.police.Mapper.CooperationMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coop")
@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class CooperationController {
    @GetMapping("/ActorsByDirector")
    public List<CooperationModel> searchAByD(@RequestParam(required = false) String director,
                                             @RequestParam(required = false) String count){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        System.out.println("searchAByD");
        System.out.println(director);
        System.out.println(count);
        CooperationMapper cooperationMapper = new CooperationMapper();
        Map<String,Object> params=new HashMap<>();
        params.put("director",director);
        params.put("count",Integer.parseInt(count));
        List<CooperationModel> resList=cooperationMapper.searchAByD(params,sqlSession);
        System.out.println("searchAByD");
        return resList;
    }

    @GetMapping("/ActorsByActor")
    public List<CooperationModel> searchAByA(@RequestParam(required = false) String actor,
                                             @RequestParam(required = false) String count){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        System.out.println("22222222222222");
        CooperationMapper cooperationMapper = new CooperationMapper();
        Map<String,Object> params=new HashMap<>();
        params.put("actor",actor);
        params.put("count",Integer.parseInt(count));
        System.out.println(params);
        List<CooperationModel> resList=cooperationMapper.searchAByA(params,sqlSession);
        System.out.println("22222222222222");
        return resList;
    }

    @GetMapping("/DirectorsByActor")
    public List<CooperationModel> searchDByA(@RequestParam(required = false) String actor,
                                             @RequestParam(required = false) String count){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        CooperationMapper cooperationMapper = new CooperationMapper();
        Map<String,Object> params=new HashMap<>();
        params.put("actor",actor);
        params.put("count",Integer.parseInt(count));
        List<CooperationModel> resList=cooperationMapper.searchDByA(params,sqlSession);

        return resList;
    }
    @GetMapping("/dacomment")
    public List<CooperationModel> searchDAcomment(@RequestParam(required = false) String catalog){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        CooperationMapper cooperationMapper = new CooperationMapper();
        Map<String,Object> params=new HashMap<>();
        params.put("type",catalog);
        List<CooperationModel> resList=cooperationMapper.searchDAComment(params,sqlSession);

        return resList;
    }
    @GetMapping("/aacomment")
    public List<CooperationModel> searchAAcomment(@RequestParam(required = false) String catalog){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        CooperationMapper cooperationMapper = new CooperationMapper();
        Map<String,Object> params=new HashMap<>();
        params.put("type",catalog);
        List<CooperationModel> resList=cooperationMapper.searchAAComment(params,sqlSession);

        return resList;
    }
}
