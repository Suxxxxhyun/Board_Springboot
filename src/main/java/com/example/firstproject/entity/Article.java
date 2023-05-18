package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
//@Entity는 해당 객체(DTO)를 DB가 인식할 수 있게 해준다!
@AllArgsConstructor
@ToString
public class Article {

    @Id // 대푯값을 지정하기 위한 어노테이션! like a 주민등록번호
    @GeneratedValue //자동생성해주게 한다. -> 1, 2, 3 .. 처럼 자동 생성
    private Long id;

    @Column //DB에 연결될 수 있게 @Column추가
    private String title;
    @Column
    private String content;

    /*public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/
}
