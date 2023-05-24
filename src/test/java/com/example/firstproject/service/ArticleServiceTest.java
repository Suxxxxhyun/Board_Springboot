package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest //@SpringBootTest - 해당 클래스(ArticleServiceTest)는 스프링부트와 연동되어 테스팅된다.
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    @Test
    void index() {
        // 예상 시나리오
        Article a =new Article(1L, "가가가가", "1111");
        Article b =new Article(2L, "나나나나", "2222");
        Article c =new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c));

        // 실제의 결과
        List<Article> articles = articleService.index();

        // assertEquals를 이용하여 예상과 실제를 비교하여 검증한다.
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공__존재하는_id_입력() {
        //예상
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패__존재하지_않는_id_입력() {
        //예상
        // id가 -1인 것을 하면 우리가 예상하는 값은 null임.
        Long id = -1L;
        Article expected = null;

        //실제
        Article article = articleService.show(id);

        //비교
        //null은 toString()으로 비교할 수 없기때문에 아래와 같이 코드를 작성한다.
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공__title과_content만_있는_dto_입력() {
        //예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected.toString(), article.toString());
    }


    @Test
    @Transactional
    void create_실패__id가_포함된_dto_입력() {
        //예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        //실제
        Article article = articleService.create(dto);

        //비교
        //null은 toString()으로 비교할 수 없기때문에 아래와 같이 코드를 작성한다.
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공__존재하는_id와_title과_content만_있는_dto_입력(){

    }

    @Test
    @Transactional
    void update_성공__존재하는_id와_title만_있는_dto_입력(){

    }

    @Test
    @Transactional
    void update_실패__존재하지_않는_id의_dto_입력(){

    }

    @Test
    @Transactional
    void delete_성공__존재하지_않는_id의_dto_입력(){

    }

    @Test
    @Transactional
    void delete_실패__존재하지_않는_id의_dto_입력(){

    }


}