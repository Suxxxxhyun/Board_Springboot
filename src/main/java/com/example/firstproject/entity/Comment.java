package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
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

    @ManyToOne
    // 하나의 Article에 여러개의 Comment가 달림. 해당 댓글 엔터티가 하나의 Article에 연관된다.
    @JoinColumn(name = "article_id")
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        // 1. 예외 발생
        // 1-1. 이미 같은 id를 가진 댓글이 있는경우
        if(dto.getId() != null){
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야합니다.");
        }
        // 1-2. api/article/{articleId}/comments에 있는 articleId와 댓글의 articleId가 다른경우
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");


        // 2. 엔터티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        // 1. 예외 발생
        // 1-1. url에서 받은 id와 json으로 던진 데이터의 id가 서로 다른경우
        if(this.id != dto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        }

        // 2. 객체를 갱신
        if(dto.getNickname() != null){
            this.nickname = dto.getNickname();
        }

        if(dto.getBody() != null){
            this.body = dto.getBody();
        }
    }
}
