package com.heeseung.community1.dto;

import com.heeseung.community1.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
public class ModifyReqDto {
    private String username;
    private String password_now;
    private String password_new;

    public Account toEntity() {
        return Account.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password_new))
                .role(1)
                .build();
    }

}
