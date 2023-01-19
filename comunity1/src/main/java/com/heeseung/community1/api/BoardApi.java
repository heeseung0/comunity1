package com.heeseung.community1.api;

import com.heeseung.community1.dto.BoardPostReplyReqDto;
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
    public ResponseEntity<?> postReply(@RequestBody BoardPostReplyReqDto boardReplyReqDto) throws Exception {
        return ResponseEntity.ok().body(new CMRespDto<>("postReply", boardService.newPostReply(boardReplyReqDto)));
    }

    @GetMapping("/getReply/{boardURL}/{postNum}")
    public ResponseEntity<?> getReply(@PathVariable String boardURL,
                                      @PathVariable String postNum) throws Exception {
        return ResponseEntity.ok().body(new CMRespDto<>("getReply",boardService.getReply(boardURL, Integer.valueOf(postNum))));
    }

    @GetMapping("/{boardURL}/getRecentPosts/{count}")
    public ResponseEntity<?> getRecentPosts(@PathVariable String boardURL,
                                            @PathVariable String count) throws Exception {
        return ResponseEntity.ok().body(new CMRespDto<>("getRecentPosts "+count, boardService.getRecentPosts(boardURL,count)));
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
