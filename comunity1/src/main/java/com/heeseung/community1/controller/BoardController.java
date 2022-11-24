package com.heeseung.community1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @GetMapping("/Notice")
    public String notice(){
        return "board/notice";
    }

    @GetMapping("/Notice/{postNum}")
    public String noticePost(@PathVariable String postNum){
        return "board/noticePost";
    }
}
