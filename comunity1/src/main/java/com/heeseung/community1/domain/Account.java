package com.heeseung.community1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    private String id;
    private String username;
    private String password;
    private int role;
    private LocalDateTime create_data;
    private LocalDateTime update_data;
}
