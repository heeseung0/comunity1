package com.heeseung.community1.service;

import com.heeseung.community1.dto.RecentPostRespDto;
import com.heeseung.community1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService{
    private final BoardRepository boardRepository;

    @Override
    public List<RecentPostRespDto> getRecentPost() throws Exception {
        List<RecentPostRespDto> dto = new ArrayList<>();

        boardRepository.recentPost().forEach(domain -> {
            dto.add(domain.toDto());
        });

        return dto;
    }
}
