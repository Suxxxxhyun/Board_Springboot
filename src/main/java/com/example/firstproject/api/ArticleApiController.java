package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //RestAPI 용 컨트롤러! 데이터를 반환, 일반적으로는 DATA를 JSON형태로 반환한다!
public class ArticleApiController {

    @Autowired // DI (Dependency Injection)
    private ArticleRepository articleRepository;

    //GET
    //Article의 목록을 반환
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    //Article의 단일 데이터를 반환
    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //PATCH

    //DELETE
}
