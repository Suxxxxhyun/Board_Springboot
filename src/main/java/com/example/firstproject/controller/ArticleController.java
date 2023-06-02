package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결해줌!
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    //new입력폼열기
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    //new입력폼에 데이터받기
    //new입력폼에 작성된 데이터가 ArticleForm객체(dto)에 담아진다.
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //System.out.println(form.toString()); -> 로깅기능으로 대체!
        log.info(form.toString());

        // 1. Dto를 Entity로 변환하는 것은 다음과 같다.
        // -> form의 toEntity()메소드를 통해 DTO를 Entity로 변환한 후,
        //Article타입의 Entity로 받아준다.
        Article article = form.toEntity();
        //System.out.println(article);
        log.info(article.toString());

        // 2. Repository를 통해 Entity를 DB에 저장하게 한다.
        // -> articleRepository가 save메소드를 호출하여 (CrudRepository가 기본으로 제공해주므로 그냥 .save로 호출가능)
        // 위 article를 저장할 수 있게 하여 saved라는 이름으로 반환해준다.
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    //show() - localhost:8080/articles/{id}로 접근하면 그 id에 해당하는 데이터 조회 ! -> findById()사용

    //{id}로 작성해주면, 변하는 수! 라는 의미를 내포함.
    @GetMapping("/articles/{id}")
    //{id}를 Controller에서 받기 위해
    // 1. 파라미터에 @PathVariable Long id작성
    // 2. @PathVariable는 url로부터 받는다는 것을 의미함.
    // 3. 잘 받았는지 확인하기 위해서 log.info로 찍어본다.
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);

        // 1. id로 데이터를 가져옴 ! (Data를 Entity로 변환)
        /* 방법 1. Optional<Article> articleEntity = articleRepository.findById(id); */
        /* 방법 2. orElse - Id값이 없다면 null을 반환하라는 말임.
                  따라서, articleEntity는 id값이 있거나 null을 갖게 된다.
        */
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. 가져온 데이터를 모델에 등록(Entity를 Model에 등록)
        /*
            2 - 1. 파라미터에 Model model을 작성
            2 - 2. model.addAttribute("article", articleEntity);
         */
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3. 보여줄 페이지를 설정!
        return "articles/show";
    }

    //index() - Article Entity의 목록을 반환 ! -> findAll()사용
    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 Article을 가져온다!
        /* 방법1. */
        List<Article> articleEntityList = articleRepository.findAll();

        /*  방법2.
        Iterable<Article> articleEntityList = articleRepository.findAll();
        */

        // 2. 가져온 Article묶음을 뷰로 전달!
        // 2 - 1. Model파라미터 등록
        // 2 - 2. 아래 코드 작성
        model.addAttribute("articleList",articleEntityList);

        // 3. 뷰 페이지를 설정 !
        return "articles/index"; //articles/index.mustache
    }

    //edit() - show.mustache(상세보기)페이지에서 [Edit]버튼을 타면, articles/edit페이지로 이동시켜준다.
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 1. 수정할 데이터를 Repository를 이용해서 가져오기
        // 해당 id가 있다면 Enity를 반환, 없다면 null반환해주는데 id는 @GetMapping에 있는 id를 받을 것이므로
        // 파라미터에 @PathVariable Long id작성, Long "id" 와 {id}가 서로 같아야함!
        // @PathVariable는 url로부터 받는다는 것을 의미함.
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터를 등록!
        // 2 - 1. 파라미터에 Model model등록
        // 2 - 2.
        model.addAttribute("article",articleEntity);

        // 3. 뷰 페이지 설정
        return "articles/edit";
    }

    //@PatchMapping - 수정을 위한 어노테이션이지만, form이 post이기 때문에 임시로 @PostMapping으로 한다.
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        // 1. DTO를 엔터티로 변환한다!
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. 엔터티를 DB로 저장한다!
        // 2-1: DB에 기존 데이터를 가져온다.
        // 방법 1 : Optional<Article> target = articleRepository.findById(articleEntity.getId());
        // 방법 2
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        log.info(target.toString());

        // 2-2: 기존데이터가 있다면 값을 갱신한다.
        if(target != null){
            articleRepository.save(articleEntity);
        }

        // 3. 수정 결과 페이지로 리다이렉트 한다!
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!!");

        // 1. 삭제 대상을 가져온다!
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2. 대상을 삭제한다.
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        // 3. 결과 페이지로 리다이렉트한다.
        return "redirect:/articles";
    }

}
