package com.heeseung.community1.api;

import com.heeseung.community1.dto.CMRespDto;
import com.heeseung.community1.dto.ModifyReqDto;
import com.heeseung.community1.security.PrincipalDetails;
import com.heeseung.community1.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Board")
@RequiredArgsConstructor
public class BoardApi {
    private final AccountService accountService;

    @PostMapping("/testPost")
    public ResponseEntity<?> leave(@RequestParam @Nullable String testText) throws Exception {
        return ResponseEntity.ok().body(new CMRespDto<>(testText, null));
    }

}
