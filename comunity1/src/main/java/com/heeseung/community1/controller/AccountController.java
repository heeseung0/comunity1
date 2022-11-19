package com.heeseung.comunity1.controller;

import com.heeseung.comunity1.dto.RegisterReqDto;
import com.heeseung.comunity1.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class AccountController {

    private final AccountService accountService;

    @GetMapping ("/login")
    public String login(Model model,
                        @RequestParam @Nullable String username,
                        @RequestParam @Nullable String error){
        model.addAttribute("username", username == null ? "" : username);
        model.addAttribute("error", error == null ? "" : error);
        System.out.println(username + ", " +error);
        return "index";
    }

    @GetMapping ("/newAccount")
    public String newAccount(Model model,
                             @RequestParam @Nullable String username,
                             @RequestParam @Nullable String error){
        model.addAttribute("username", username == null ? "" : username);
        model.addAttribute("error", error == null ? "" : error);
        return "member/newAccount";
    }

    @PostMapping("/newAccount")
    public String accountRegister (RegisterReqDto registerReqDto) throws Exception{
        accountService.register(registerReqDto);
        return "member/newAccount";
    }
}
