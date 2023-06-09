package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //이제 @Controller가 아닌 @RestController - Rest API용 컨트롤러로 JSON을 반환해주는 컨트롤러
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello world!";
    }
}
