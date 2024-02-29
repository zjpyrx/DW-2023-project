package org.group05.police.controller;

import org.apache.ibatis.session.SqlSession;
import org.group05.police.Mapper.TimeMapper;
import org.group05.police.DTO.MovieModel;
import org.group05.police.tools.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.YearMonth;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/movie")
@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class TimeController {
    @GetMapping("/year")
    public List<MovieModel> searchByYear(@RequestParam String year){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        System.out.println("year: "+year);
        TimeMapper timeMapper=new TimeMapper();
        Map<String,Object> params=new HashMap<>();
        params.put("year",Integer.parseInt(year));
        List<MovieModel> resList=timeMapper.searchByYear(params,sqlSession);
        return resList;
    }

    @GetMapping("/quarter")
    public List<MovieModel> searchByQuarter(@RequestParam String year,
                                            @RequestParam String quarter){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        System.out.println(year);
        System.out.println(quarter);
        TimeMapper timeMapper=new TimeMapper();
        Map<String,Object> params=new HashMap<>();
        params.put("year",year);
        params.put("quarter",quarter);
        List<MovieModel> resList=timeMapper.searchByQuarter(params,sqlSession);
        return resList;
    }

    @GetMapping("/month")
    public List<MovieModel> searchByMonth(@RequestParam String month){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
        int myYear = yearMonth.getYear();
        int myMonth=yearMonth.getMonthValue();

        TimeMapper timeMapper=new TimeMapper();
        Map<String,Object> params=new HashMap<>();
        System.out.println(myYear);
        System.out.println(myMonth);
        params.put("year",myYear);
        params.put("month",myMonth);
        List<MovieModel> resList=timeMapper.searchByMonth(params,sqlSession);
        return resList;
    }
    @GetMapping("/date")
    public List<MovieModel> searchByDate(@RequestParam String date){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        LocalDate myDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        int year = myDate.getYear();
        int month = myDate.getMonthValue();
        int day = myDate.getDayOfMonth();

        TimeMapper timeMapper=new TimeMapper();
        Map<String,Object> params=new HashMap<>();
        params.put("year",year);
        params.put("month",month);
        params.put("day",day);
        List<MovieModel> resList=timeMapper.searchByDate(params,sqlSession);
        return resList;
    }

}
