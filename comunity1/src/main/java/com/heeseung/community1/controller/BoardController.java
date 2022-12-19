package com.heeseung.community1.controller;

import com.heeseung.community1.dto.BoardModifyReqDto;
import com.heeseung.community1.dto.BoardReqDto;
import com.heeseung.community1.security.PrincipalDetails;
import com.heeseung.community1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardURL}")
    public String board(@PathVariable String boardURL) {
        return whereImGoing(boardURL);
    }

    @GetMapping("/{boardURL}/newPost")
    public String newPost(@PathVariable String boardURL) {
        return whereImGoing(boardURL) + "NewPost";
    }

    @PostMapping("/{boardURL}/NewPost")
    public String newPost(@PathVariable String boardURL,
                          @RequestParam @Nullable String type,
                          @RequestParam @Nullable String title,
                          @RequestParam @Nullable String contents,
                          @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        BoardReqDto boardReqDto = BoardReqDto.builder()
                .type(Integer.valueOf(type))
                .title(title)
                .contents(contents)
                .writer(principalDetails.getUsername())
                .build();
        boardService.newPost(boardURL, boardReqDto);

        return whereImGoing(boardURL);
    }

    @GetMapping("/{boardURL}/{postNum}")
    public String boardPost(@PathVariable String boardURL, @PathVariable String postNum) throws Exception {
        boardService.addViewCount(boardURL, Integer.valueOf(postNum));
        return "board/post/posts";
    }

    @GetMapping("/{boardURL}/{postNum}/PostModify")
    public String modify(@PathVariable String boardURL,
                         @PathVariable String postNum) {
        return whereImGoing(boardURL) + "ModifyPost";
    }

    @PutMapping("/{boardURL}/{postNum}/PostModify")
    public String modify(@PathVariable String boardURL,
                         @PathVariable String postNum,
                         @RequestBody BoardModifyReqDto boardModifyReqDto) throws Exception {
        boardService.updatePost(boardURL, Integer.valueOf(postNum), boardModifyReqDto);

        return whereImGoing(boardURL);
    }

    @DeleteMapping("/{boardURL}/{postNum}/PostDelete")
    public String delete(@PathVariable String boardURL,
                         @PathVariable String postNum) throws Exception {
        boardService.deletePost(boardURL, Integer.valueOf(postNum));
        return whereImGoing(boardURL);
    }

    private String whereImGoing(String boardURL) {
        if (boardURL.equalsIgnoreCase("Notice")) {
            return "board/notice";
        } else {
            return "index";
        }
    }
}
