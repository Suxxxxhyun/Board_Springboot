package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
//@Slf4j는 log를 위한 어노테이션
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //Article 데이터가 필요하기 때문에 ArticleRepository도 의존주입해준다.
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
//        // 1. 조회 : 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//
//        // 2. 변환 : 엔터티 -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for (int i=0; i<comments.size(); i++){
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }

        // for문을 사용했다면 [조회1], [변환2]이 필요했지만 stream을 이용한다면
        //위 코드를 모두 주석처리한다.

        // 3. 반환
        return commentRepository.findByArticleId(articleId)
                .stream() //Entity타입의 comment를 하나씩 꺼내게 되고
                .map(comment -> CommentDto.createCommentDto(comment))//여기서 Comment를 DTO로 바꾸어줌.
                .collect(Collectors.toList()); //바꾸어준 것들을 LIST화해줌.
    }

    @Transactional
    //해당 메소드를 트랜잭션으로 묶는다.
    public CommentDto create(Long articleId, CommentDto dto) {
        //log.info("입력값 => {}", articleId);
        //log.info("입력값 => {}", dto);

        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                //orElseThrow() 해당 게시글에 대한 댓글이 없다면 아래와 같은 예외를 발생시킨다.는 의미임.
                //댓글이 없다면 아래에 있는 모든 코드(2~4)들이 실행되지 않음
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        // 2. 댓글 엔터티 생성
        Comment comment = Comment.createComment(dto, article);

        // 3. 댓글 엔터티를 DB로 저장
        Comment created = commentRepository.save(comment);

        // 4. DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
        //CommentDto createdDto = CommentDto.createCommentDto(created);
        //log.info("반환값 => {}", createdDto);
        //return createdDto;
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {

        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 댓글이 없습니다."));

        // 2. 댓글 수정
        target.patch(dto);

        // 3. DB로 갱신
        Comment updated = commentRepository.save(target);

        // 4. 댓글 엔터티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

        // 2. 댓글 DB에서 삭제
        commentRepository.delete(target);

        // 3. 삭제할 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }
}
