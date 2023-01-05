package com.heeseung.community1.api;

import com.heeseung.community1.dto.BoardReplyReqDto;
import com.heeseung.community1.dto.CMRespDto;
import com.heeseung.community1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApi {
    private final BoardService boardService;

    @PostMapping("/postReply")
    public ResponseEntity<?> postReply(@RequestBody BoardReplyReqDto boardReplyReqDto) throws Exception {
        return ResponseEntity.ok().body(new CMRespDto<>("postReply", boardService.newPostReply(boardReplyReqDto)));
    }

    @GetMapping("/{boardURL}/getPosts")
    public ResponseEntity<?> getPosts(@PathVariable String boardURL) throws Exception {
        return ResponseEntity.ok().body(new CMRespDto<>("getPosts", boardService.getPosts(boardURL)));
    }

    @GetMapping("/{boardURL}/{postNum}/getPost")
    public ResponseEntity<?> getPost(@PathVariable String boardURL,
                                     @PathVariable String postNum) throws Exception {
        return ResponseEntity.ok().body(new CMRespDto<>("getPost", boardService.getPost(boardURL, Integer.valueOf(postNum))));
    }
}
