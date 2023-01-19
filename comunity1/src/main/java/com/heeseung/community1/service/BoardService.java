package com.heeseung.community1.service;

import com.heeseung.community1.dto.*;

import java.util.List;

public interface BoardService {
    public int newPost(String boardURL, BoardReqDto boardReqDto) throws Exception;
    public int deletePost(String boardURL, int postNum) throws Exception;
    public int updatePost(String boardURL, int postNum, BoardModifyReqDto boardModifyReqDto) throws Exception;

    public List<BoardReqDto> getRecentPosts(String boardURL, String count) throws Exception;
    public List<BoardReqDto> getPosts(String boardURL) throws Exception;
    public BoardReqDto getPost(String boardURL,int postNum) throws Exception;

    public int addViewCount(String boardURL, int postNum) throws Exception;

    public boolean newPostReply(BoardPostReplyReqDto boardReplyReqDto) throws Exception;
    public List<BoardReplyRespDto> getReply(String boardURL, int postNum) throws Exception;
}
