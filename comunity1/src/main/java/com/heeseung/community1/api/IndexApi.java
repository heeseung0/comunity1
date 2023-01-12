package com.heeseung.community1.api;

import com.heeseung.community1.dto.CMRespDto;
import com.heeseung.community1.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexApi {
    private final IndexService indexService;

    @GetMapping("/recentPost")
    public ResponseEntity<?> getReply() throws Exception {
        return ResponseEntity.ok().body(new CMRespDto<>("getRecentPost",indexService.getRecentPost()));
    }
}
