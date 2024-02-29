package org.group05.police.controller;

import org.apache.ibatis.session.SqlSession;
import org.group05.police.DTO.ProductModel;
import org.group05.police.Mapper.ProductMapper;
import org.group05.police.tools.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {
    @GetMapping("/movieTitle")
    public List<ProductModel> searchProductByTitle(@RequestParam String title){
        SqlSession sqlSession = SessionFactory.getSqlSession();
        if (sqlSession == null) {
            throw new RuntimeException("数据库连接失败");
        }
        System.out.println("searchProductByTitle");
        ProductMapper productMapper=new ProductMapper();
        Map<String,String> params=new HashMap<>();
        params.put("movieTitle",title);
        System.out.println("searchProductByTitle");
        return productMapper.searchProductByTitle(params,sqlSession);
    }
}
