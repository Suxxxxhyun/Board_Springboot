package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 선언! (서비스 객체를 스프링부트에 생성)
public class ArticleService {

    @Autowired // DI (Dependency Injection), 생성객체를 가져와서 연결한다.
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        //기존에 이미 있는 데이터라면, (ex: {id = 1, title = 1111, content = 가가가가}
        //는 이미 있는 데이터인데, id = 1, title = 2222, content = 가가가가 로 데이터를 던졌다면,
        //POST에서 논리가 모순됨(POST는 생성해주는 것이지 수정하는 코드가 아니니까!)
        //따라서, 이미 있는 ID인 경우에는 NULL을 반환한다.
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {

        //1. 수정용 엔터티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        //2. 대상 엔터티를 조회
        Article target = articleRepository.findById(id).orElse(null);

        //3. 잘못된 요청처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()){
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }


        //4. 잘못된 요청이 아니라면 업데이트 및 정상응답(200)
        //HttpStatus.OK는 200을 의미함.
        //body에는 updated를 실어서 보낼 것임.
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if(target == null){
            return null;
        }

        // 대상 삭제
        articleRepository.delete(target);

        return target;
    }

    @Transactional // @Transactional - 해당 메소드를 트랜잭션으로 묶는다.
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto묶음을 entity묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        /*
            List<Article> articleList = new ArrayList<>();
            for(int i=0; i<dtos.size(); i++){
                ArticleForm dto = dtos.get(i);
                Article entity = dto.toEntity();
                articleList.add(entity);
            }
            위 코드를 for문으로 고친 형태
        */

        // entity묶음을 db로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        /*
            for(int i=0; i<articleList.size(); i++){
                Article article = articleList.get(i);
                articleRepository.save(article);
            }
        위 코드를 for문으로 고친 형태
        */

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        // 결과값 반환
        return articleList;
    }
}
