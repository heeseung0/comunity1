package com.heeseung.comunity1.controller;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class AccountController {

    @GetMapping ("/login")
    public String login(Model model,
                        @RequestParam @Nullable String username,
                        @RequestParam @Nullable String error){
        model.addAttribute("username", username == null ? "" : username);
        model.addAttribute("error", error == null ? "" : error);
        System.out.println(username);
        return "index";
    }
}
