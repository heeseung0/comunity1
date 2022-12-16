package com.heeseung.community1.service;

import com.heeseung.community1.dto.BoardReqDto;

import java.util.List;

public interface BoardService {
    public int newPost(BoardReqDto boardReqDto, String boardURL) throws Exception;
    public List<BoardReqDto> getPost(String boardURL) throws Exception;
    public int addViewCount(String boardURL, int id) throws Exception;
}
