package com.heeseung.community1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BoardReqDto {
    private int id;
    private int type;
    private String title;
    private String writer;
    private LocalDateTime date_post;
    private LocalDateTime date_update;
    private int view;
    private String contents;
}
