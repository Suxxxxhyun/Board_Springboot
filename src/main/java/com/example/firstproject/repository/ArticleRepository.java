package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

/*
1. 우리가 repository를 직접 작성할 수도 있지만,
JPA에서 제공하는 repository 인터페이스(CrudRepository)를 활용하도록 하자.
CrudRepository는 CRUD를 제공해준다.
2. CrudRepository를 상속받을 때는 <>꺽쇄안에
첫번째는 관리대상Entity(Article)를, 두번째는 Article의 대푯값(Id)의 타입을 넣어준다.
 */
public interface ArticleRepository extends CrudRepository<Article, Long> {
}
