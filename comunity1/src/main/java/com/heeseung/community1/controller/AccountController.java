package com.heeseung.community1.controller;

import com.heeseung.community1.dto.RegisterReqDto;
import com.heeseung.community1.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping ("/login")
    public String login(Model model,
                        @RequestParam @Nullable String username,
                        @RequestParam @Nullable String error){
        model.addAttribute("username", username == null ? "" : username);
        model.addAttribute("error", error == null ? "" : error);
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
    public String accountRegister (RegisterReqDto registerReqDto,
                                   Model model,
                                   @RequestParam @Nullable String username,
                                   @RequestParam @Nullable String error) throws Exception{
        int result = accountService.register(registerReqDto);

        model.addAttribute("username", username == null ? "" : username);

        if(result != 0) {
            switch(result){
                case 1: {
                    model.addAttribute("error", "validation_duplicated");
                    log.error("register error : duplicated");
                    break;
                }
                case 2: {
                    model.addAttribute("error", "validation_username");
                    log.warn("register error : validation_username");
                    break;
                }
                case 3: {
                    model.addAttribute("error", "validation_password");
                    log.warn("register error : validation_password");
                    break;
                }
                case 100: {
                    model.addAttribute("error", "error_server");
                    log.error("register error : server error");
                    log.info("\t>> SQL Error");
                    break;
                }
            }
            log.info("\t>> username : " + registerReqDto.getUsername());
            log.info("\t>> password : " + registerReqDto.getPassword());
        }

        //자동 로그인 로직
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                registerReqDto.toEntity().getUsername(),
                registerReqDto.toEntity().getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        return "member/newAccount";
    }
}
