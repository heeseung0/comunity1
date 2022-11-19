package com.heeseung.community1.api;

import com.heeseung.community1.dto.CMRespDto;
import com.heeseung.community1.security.PrincipalDetails;
import com.heeseung.community1.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApi {
    private final AccountService accountService;

    @GetMapping("/getLogin")
    public ResponseEntity<?> getLogin(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.ok().body(new CMRespDto<>("principalDetails",principalDetails));
    }
}
