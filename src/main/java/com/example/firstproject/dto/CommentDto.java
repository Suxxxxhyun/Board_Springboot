package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor // - 기본생성자
@Getter
@ToString
public class CommentDto {

    //comment기본키
    private Long id;

    //comment의 게시글id
    @JsonProperty("article_id")
    //@JsonProperty - json에서 article_id로 보낸것을 articleId와 매핑시켜주는 어노테이션!
    private Long articleId;
    private String nickname;
    private String body;

    //Entity를 DTO객체로 바꿔주는 메서드
    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                //Comment Entity에 있는 Article중 Article의 id값만 필요함. 그래서 아래처럼 코드 작성
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
