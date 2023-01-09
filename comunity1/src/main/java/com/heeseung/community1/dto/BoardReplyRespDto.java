package com.heeseung.community1.dto;

import com.heeseung.community1.domain.BoardReply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class BoardReplyRespDto<T> {
    private String writer;
    private String contents;
    private LocalDateTime date_post;
    private LocalDateTime date_update;
}
