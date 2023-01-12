package com.heeseung.community1.service;

import com.heeseung.community1.dto.RecentPostRespDto;

import java.util.List;

public interface IndexService {
    public List<RecentPostRespDto> getRecentPost() throws Exception;
}
