package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //lombok의 생성자
@ToString //lombok의 toString()
public class ArticleForm {
    private String title;
    private String content;

    //생성자와 toString()은 lombok을 통해 작성한다.
    /*public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }*/

    /*@Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/

    //ArticleForm이라는 DTO객체를 Entity로 변환해주는 메소드
    public Article toEntity() {
        return new Article(null, title, content);
    }
}
