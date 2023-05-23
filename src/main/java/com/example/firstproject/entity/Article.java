package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
//@Entity는 해당 객체(DTO)를 DB가 인식할 수 있게 해준다!, 즉 해당 클래스로 테이블을 만든다.
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자를 추가하는 어노테이션 !
@ToString
@Getter
public class Article {

    @Id // 대푯값을 지정하기 위한 어노테이션! like a 주민등록번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동 생성!
    // @GeneratedValue()는 우리가 데이터를 넣을때마다 자동생성해주게 한다. -> 1, 2, 3 .. 처럼 자동 생성
    private Long id;

    @Column //DB에 연결될 수 있게 @Column추가
    private String title;
    @Column
    private String content;

    /*public Long getId() {
        return id;
    }*/

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

    /* 디폴트 생성자.
    Article(){

    }*/
}
