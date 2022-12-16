package com.heeseung.community1.controller;

import com.heeseung.community1.dto.BoardReqDto;
import com.heeseung.community1.security.PrincipalDetails;
import com.heeseung.community1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/Notice")
    public String notice() {
        return "board/notice";
    }

    @GetMapping("/Notice/{postNum}")
    public String noticePost(@PathVariable String postNum) throws Exception {
        boardService.addViewCount("notice",Integer.valueOf(postNum));
        return "board/noticePost";
    }

    @GetMapping("/Notice/Post")
    public String test() {
        return "board/noticePostNew";
    }

    @PostMapping("/Notice/newPost")
    public String leave(@RequestParam @Nullable String type,
                        @RequestParam @Nullable String title,
                        @RequestParam @Nullable String contents,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        BoardReqDto boardReqDto = BoardReqDto.builder()
                .type(Integer.valueOf(type))
                .title(title)
                .contents(contents)
                .writer(principalDetails.getUsername())
                .build();
        boardService.newPost(boardReqDto,"notice");
        return "board/notice";
    }
}
