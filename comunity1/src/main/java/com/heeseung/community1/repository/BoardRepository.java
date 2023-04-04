package com.heeseung.community1.repository;

import com.heeseung.community1.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {
    public int save(String table, Board board) throws Exception;
    public int delete(String table, int id) throws Exception;
    public int update(String table, int id, Board board) throws Exception;
    public List<BoardBasic> getsRecentCount(String table, int count) throws Exception;
    public List<Board> gets(String table, String board) throws Exception;
    public List<Board> gets_search(String table, int type, String likeTitle, String likeWriter, String likeContents, String board) throws Exception;
    public Board get(String table, int id) throws Exception;
    public int addViewCount(String table, int id) throws Exception;
    public int reply_save(BoardReply boardReply) throws Exception;
    public List<BoardReply> reply_get(String board, int postnum) throws Exception;
    public List<RecentPost> recentPost() throws Exception;
    public List<RecentReply> recentReply() throws Exception;
}
