package com.heeseung.community1.domain;

import com.heeseung.community1.dto.BoardReplyReqDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardReply {
    private int id;
    private String writer;
    private String board;
    private int postnum;
    private String contents;
    private LocalDateTime date_post;
    private LocalDateTime date_update;

    public BoardReplyReqDto toDto(){
        return BoardReplyReqDto.builder()
                .writer(this.writer)
                .board(this.board)
                .contents(this.contents)
                .date_post(this.date_post)
                .date_update(this.date_update)
                .build();
    }
}
