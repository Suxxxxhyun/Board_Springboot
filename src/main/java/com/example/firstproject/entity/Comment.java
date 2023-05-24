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
public class Comment {

    @Id // 대푯값을 지정하기 위한 어노테이션! like a 주민등록번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동 생성!
    // @GeneratedValue()는 우리가 데이터를 넣을때마다 자동생성해주게 한다. -> 1, 2, 3 .. 처럼 자동 생성
    private Long id;

    @ManyToOne // 하나의 Article에 여러개의 Comment가 달림.
    @JoinColumn(name = "article_id")
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;
}
