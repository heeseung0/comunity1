package com.heeseung.community1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BoardPostReplyReqDto {
    private String writer;
    private String board;
    private String postnum;
    private String contents;
    private LocalDateTime date_post;
    private LocalDateTime date_update;
}
