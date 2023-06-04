package com.example.firstproject.api;

import com.example.firstproject.annotation.RunningTime;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //RestAPI 용 컨트롤러! 데이터를 반환, 일반적으로는 DATA를 JSON형태로 반환한다!
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 게시글에 따른 댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){

        // 1. 서비스에게 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        // 2. 결과 응답
        //dtos가 null이면 bad, null이 아니면 good상태를 보내면 되지만,
        //이번에는 무조건적으로 dtos가 null이 아닐것이라는 가정하에 아래와 같이 코드를 작성한다.
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    // 게시글에 따른 댓글 생성
    // @RequestBody - form으로 받은 데이터를 JSON으로 변환하기 위한 어노테이션
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto){

        // 1. 서비스에게 위임
        CommentDto createdDto = commentService.create(articleId, dto);
        // 2. 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // 댓글 수정
    @PatchMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto){
        // 1. 서비스에게 위임
        CommentDto updatedDto = commentService.update(id, dto);
        // 2. 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    // 댓글 삭제
    @RunningTime
    @DeleteMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        // 1. 서비스에게 위임
        CommentDto updatedDto = commentService.delete(id);
        // 2. 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
}
