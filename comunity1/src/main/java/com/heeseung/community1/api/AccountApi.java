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

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApi {
    private final AccountService accountService;

    @GetMapping("/getLogin")
    public ResponseEntity<?> getLogin(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                      HttpSession session) {
        if(session.getAttribute("getLogin") == null){
            session.setAttribute("getLogin", "");
        }

        if (principalDetails != null) {
            if (!session.getAttribute("getLogin").equals(principalDetails.getRole())) {
                session.setAttribute("getLogin", principalDetails.getRole());
            }
            return ResponseEntity.ok().body(new CMRespDto<>("principalDetails", principalDetails));
        } else {
            session.setAttribute("getLogin", "anonymousUser");
            Object test = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok().body(new CMRespDto<>("principalDetails", test));
        }
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leave(@AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        if (accountService.leave(principalDetails.getUsername()) == 1) {
            return ResponseEntity.ok().body(new CMRespDto<>("success account leave", null));
        } else {
            return ResponseEntity.ok().body(new CMRespDto<>("failed account leave", null));
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modify(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                    @RequestParam @Nullable String password_now,
                                    @RequestParam @Nullable String password_new) throws Exception {
        accountService.modify(new ModifyReqDto(principalDetails.getUsername(), password_now, password_new));

        if (accountService.leave(principalDetails.getUsername()) == 1) {
            return ResponseEntity.ok().body(new CMRespDto<>("success account leave", null));
        } else {
            return ResponseEntity.ok().body(new CMRespDto<>("failed account leave", null));
        }
    }
}
