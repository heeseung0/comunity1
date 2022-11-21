package com.heeseung.community1.api;

import com.heeseung.community1.dto.CMRespDto;
import com.heeseung.community1.security.PrincipalDetails;
import com.heeseung.community1.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApi {
    private final AccountService accountService;

    @GetMapping("/getLogin")
    public ResponseEntity<?> getLogin(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails != null) {
            return ResponseEntity.ok().body(new CMRespDto<>("principalDetails", principalDetails));
        } else {
            Object test = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok().body(new CMRespDto<>("principalDetails", test));
        }
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leave(@AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        if(accountService.leave(principalDetails.getUsername()) == 1) {
            return ResponseEntity.ok().body(new CMRespDto<>("success account leave", null));
        }else{
            return ResponseEntity.ok().body(new CMRespDto<>("failed account leave", null));
        }
    }
}
