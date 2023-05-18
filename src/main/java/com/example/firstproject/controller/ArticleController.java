package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결해줌!
    private ArticleRepository articleRepository;

    //new입력폼열기
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    //new입력폼에 데이터받기
    //new입력폼에 작성된 데이터가 ArticleForm객체(dto)에 담아진다.
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //System.out.println(form.toString()); -> 로깅기능으로 대체!
        log.info(form.toString());

        // 1. Dto를 Entity로 변환하는 것은 다음과 같다.
        // -> form의 toEntity()메소드를 통해 DTO를 Entity로 변환한 후,
        //Article타입의 Entity로 받아준다.
        Article article = form.toEntity();
        //System.out.println(article);
        log.info(article.toString());

        // 2. Repository를 통해 Entity를 DB에 저장하게 한다.
        // -> articleRepository가 save메소드를 호출하여 (CrudRepository가 기본으로 제공해주므로 그냥 .save로 호출가능)
        // 위 article를 저장할 수 있게 하여 saved라는 이름으로 반환해준다.
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());
        return "";
    }
}
