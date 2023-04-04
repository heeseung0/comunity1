package com.heeseung.community1.domain;

import com.heeseung.community1.dto.RecentPostRespDto;
import com.heeseung.community1.dto.RecentReplyRespDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RecentReply {
    private String contents;
    private String board;
    private int postNum;

    public RecentReplyRespDto toDto(){
        return RecentReplyRespDto.builder()
                .contents(this.contents)
                .board(this.board)
                .postNum(this.postNum)
                .build();
    }
}
