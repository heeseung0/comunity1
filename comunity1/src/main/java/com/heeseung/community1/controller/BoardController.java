package com.heeseung.community1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/Notice")
    public String notice(){
        return "board/notice";
    }
}
