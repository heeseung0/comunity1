package com.heeseung.community1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BoardModifyReqDto {
    private String type;
    private String title;
    private String writer;
    private String contents;
}
