package com.heeseung.community1.dto;

import com.heeseung.community1.domain.Account;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class RegisterReqDto {
    private String username;
    private String password;

    public Account toEntity() {
        return Account.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .role(1)
                .build();
    }

}
