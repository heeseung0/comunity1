package com.heeseung.community1.controller;

import com.heeseung.community1.dto.ModifyReqDto;
import com.heeseung.community1.dto.RegisterReqDto;
import com.heeseung.community1.security.PrincipalDetails;
import com.heeseung.community1.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam @Nullable String username,
                        @RequestParam @Nullable String error) {
        model.addAttribute("username", username == null ? "" : username);
        model.addAttribute("error", error == null ? "" : error);
        return "index";
    }

    @GetMapping("/newAccount")
    public String accountRegister(Model model,
                                  @RequestParam @Nullable String username,
                                  @RequestParam @Nullable String error) {
        model.addAttribute("username", username == null ? "" : username);
        model.addAttribute("error", error == null ? "" : error);
        return "member/newAccount";
    }

    @PostMapping("/newAccount")
    public String accountRegister(RegisterReqDto registerReqDto,
                                  Model model,
                                  @RequestParam @Nullable String username) throws Exception {
        Boolean autoLogin = true;
        String rawUsername = registerReqDto.getUsername();
        String rawPassword = registerReqDto.getPassword();

        int result = accountService.register(registerReqDto);

        model.addAttribute("username", username == null ? "" : username);

        if (result != 0) {
            switch (result) {
                case 0: {   //success
                    break;
                }
                case 1: {
                    model.addAttribute("error", "validation_username");
                    log.warn("register error : validation_username");
                    break;
                }
                case 2: {
                    model.addAttribute("error", "validation_password");
                    log.warn("register error : validation_password");
                    break;
                }
                case 3: {
                    model.addAttribute("error", "validation_duplicated");
                    log.error("register error : duplicated");
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

        if (autoLogin) {
            //자동 로그인 로직
            /*
                1. username과 password로 UsernamePasswordAuthenticationToken 인스턴스 생성(토큰)
                2. 위 토큰은 검증을 위해 AuthenticationManager의 인스턴스로 전달
                3. AuthenticationManager는 인증에 성공하면 Authentication 인스턴스를 리턴
                4. 위 Authentication을 SecurityContextHolder.getContext().setAuthentication()으로 set
             */
            //로그인 인증에 필요한 토큰 얻어옴
            //원시 비밀번호가 없으면 토큰 인증에서 오류가 남(rawPassword)
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    rawUsername,
                    rawPassword);

            //AuthenticationManager에 로그인 함수 호출
            //AuthenticationManager을 사용하기 위해서는 Bean으로 등록(config - SecurityConfig)
            Authentication authentication = authenticationManager.authenticate(token);

            //로그인 토큰 저장(세션 등록)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return "index";
    }

    @GetMapping("/modify")
    public String modify(Model model,
                         @RequestParam @Nullable String error) {
        model.addAttribute("error", error == null ? "" : error);
        return "member/modify";
    }

    @PostMapping("/modify")
    public String modify(Model model,
                         ModifyReqDto modifyReqDto,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        modifyReqDto.setUsername(principalDetails.getUsername());
        switch(accountService.modify(modifyReqDto)){
            case 0:
                break;
            case 1:
                model.addAttribute("error", "validation_password_now");
                break;
        }


        model.addAttribute("error", error == null ? "" : error);
        return "member/modify";
    }

}
