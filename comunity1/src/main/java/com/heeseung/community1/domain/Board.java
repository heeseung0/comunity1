package com.heeseung.community1.domain;

import com.heeseung.community1.dto.BoardReqDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Board {
    private int id;
    private int type;
    private String title;
    private String writer;
    private LocalDateTime date_post;
    private LocalDateTime date_update;
    private int view;
    private String contents;

    public BoardReqDto toDto(){
        return BoardReqDto.builder()
                .id(this.id)
                .type(this.type)
                .title(this.title)
                .writer(this.writer)
                .date_post(this.date_post)
                .date_update(this.date_update)
                .view(this.view)
                .contents(this.contents)
                .build();
    }
}
