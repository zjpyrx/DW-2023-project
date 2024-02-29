package org.group05.police.Mapper;

import org.apache.ibatis.session.SqlSession;
import org.group05.police.DTO.CooperationModel;
import java.util.Collections;
import java.util.Comparator;

import java.util.*;

public class CooperationMapper {

    public List<CooperationModel> sortResult(List<CooperationModel> result){
        Collections.sort(result, new Comparator<CooperationModel>() {
            @Override
            public int compare(CooperationModel model1, CooperationModel model2) {
                int comments1 = model1.getCount();
                int comments2 = model2.getCount();
                return Integer.compare(comments2, comments1); // 降序排序
            }
        });
        return result;
    }
    public List<CooperationModel> searchAByD(Map<String,Object> params, SqlSession sqlSession){
        List<CooperationModel> res=sqlSession.selectList("CooperationMapper.searchAByD",params);
        return res;
    }
    public List<CooperationModel> searchAByA(Map<String,Object> params, SqlSession sqlSession){
        List<CooperationModel> res=sqlSession.selectList("CooperationMapper.searchAByAfirst",params);
        res.addAll(sqlSession.selectList("CooperationMapper.searchAByAsecond",params));
        sortResult(res);
        return res;
    }
    public List<CooperationModel> searchDByA(Map<String,Object> params, SqlSession sqlSession){

        return sqlSession.selectList("CooperationMapper.searchDByA",params);
    }


    public List<CooperationModel> searchDAComment(Map<String, Object> params, SqlSession sqlSession) {

        // 查询指定类型电影评论数最多的前 10 个电影
        List<String> topMovieIds = sqlSession.selectList("CooperationMapper.getTopMoviesByType", params);
        //System.out.println(topMovieIds);

        // 查询每个电影的 director-actor 组合和合作次数，并计算评论数
        List<CooperationModel> result = new ArrayList<>();
        List<Map<String, String>> allCooperationList=new ArrayList<>();
        for (String movieId : topMovieIds) {
            Map<String,String> movieIds=new HashMap<>();
            movieIds.put("movieId",movieId);
            List<Map<String, String>> cooperationList = sqlSession.selectList("CooperationMapper.getDACombinations", movieIds);
            //System.out.println(cooperationList);
            allCooperationList.addAll(cooperationList);
        }

        for (Map<String, String> cooperation : allCooperationList) {
            int comments = 0;
            if(!cooperation.get("movieIds").isEmpty() && cooperation.get("movieIds")!=null){
                List<String> movieIdList=Arrays.asList(cooperation.get("movieIds").split(","));
                for(String movieId1 : movieIdList){
                    Map<String,String> movieIds1=new HashMap<>();
                    movieIds1.put("movieId1",movieId1);
                    String tempcomments = sqlSession.selectOne("CooperationMapper.getMovieComments",movieIds1);
                    //System.out.println(tempcomments);
                    if(tempcomments != null){
                        comments+=Integer.parseInt(tempcomments);
                    }
                }
            }
            cooperation.put("comments",Integer.toString(comments));
            CooperationModel topCooperation = new CooperationModel();
            Map<String,String> personID=new HashMap<>();
            personID.put("personID",cooperation.get("director1").toString());
            topCooperation.setDirector1(sqlSession.selectOne("CooperationMapper.getPersonName",personID).toString());
            personID.put("personID",cooperation.get("actor1").toString());
            topCooperation.setActor1(sqlSession.selectOne("CooperationMapper.getPersonName",personID).toString());
            topCooperation.setCount(Integer.parseInt(cooperation.get("comments")));
            result.add(topCooperation);
        }
        //降序排序
        sortResult(result);
        // 打印结果
        //System.out.println(result);
        return result;
    }

    public List<CooperationModel> searchAAComment(Map<String, Object> params, SqlSession sqlSession) {

        // 查询指定类型电影评论数最多的前 10 个电影
        List<String> topMovieIds = sqlSession.selectList("CooperationMapper.getTopMoviesByType", params);
        //System.out.println(topMovieIds);

        // 查询每个电影的 actor-actor 组合和合作次数，并计算评论数
        List<CooperationModel> result = new ArrayList<>();
        List<Map<String, String>> allCooperationList=new ArrayList<>();
        for (String movieId : topMovieIds) {
            Map<String,String> movieIds=new HashMap<>();
            movieIds.put("movieId",movieId);
            List<Map<String, String>> cooperationList = sqlSession.selectList("CooperationMapper.getAACombinations", movieIds);
            //System.out.println(cooperationList);
            allCooperationList.addAll(cooperationList);
        }

        for (Map<String, String> cooperation : allCooperationList) {
            int comments = 0;
            if(!cooperation.get("movieIds").isEmpty() && cooperation.get("movieIds")!=null){
                List<String> movieIdList=Arrays.asList(cooperation.get("movieIds").split(","));
                for(String movieId1 : movieIdList){
                    Map<String,String> movieIds1=new HashMap<>();
                    movieIds1.put("movieId1",movieId1);
                    String tempcomments = sqlSession.selectOne("CooperationMapper.getMovieComments",movieIds1);
                    //System.out.println(tempcomments);
                    if(tempcomments != null){
                        comments+=Integer.parseInt(tempcomments);
                    }
                }
            }
            cooperation.put("comments",Integer.toString(comments));
            CooperationModel topCooperation = new CooperationModel();
            Map<String,String> personID=new HashMap<>();
            personID.put("personID",cooperation.get("actor1").toString());
            topCooperation.setActor1(sqlSession.selectOne("CooperationMapper.getPersonName",personID).toString());
            personID.put("personID",cooperation.get("actor2").toString());
            topCooperation.setActor2(sqlSession.selectOne("CooperationMapper.getPersonName",personID).toString());
            topCooperation.setCount(Integer.parseInt(cooperation.get("comments")));
            result.add(topCooperation);
        }
        //降序排序
        sortResult(result);
        // 打印结果
        //System.out.println(result);
        return result;
    }
}
