package com.heeseung.community1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class RecentReplyRespDto {
    private String contents;
    private String board;
    private int postNum;
}
