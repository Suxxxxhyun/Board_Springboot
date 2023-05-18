package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        //model파라미터를 niceToMeetYou파라미터에 생성 및 model에 username이라는 이름으로 suhyun값을 넣어줌.
        model.addAttribute("username","Suhyun");
        return "greetings"; //templates > greetings.mustache를 찾아서 브라우저로 전송해준다.
    }

    @GetMapping("bye")
    public String seeTouNext(Model model){
        model.addAttribute("nickname","Suhyun");
        return "goodbye";
    }
}
