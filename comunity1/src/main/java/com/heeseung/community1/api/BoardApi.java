package com.heeseung.community1.api;

import com.heeseung.community1.dto.CMRespDto;
import com.heeseung.community1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApi {
    private final BoardService boardService;

    @GetMapping("/{boardURL}/getPost")
    public ResponseEntity<?> getPosts(@PathVariable String boardURL) throws Exception{
        return ResponseEntity.ok().body(new CMRespDto<>("getPosts",boardService.getPost(boardURL)));
    }
}
