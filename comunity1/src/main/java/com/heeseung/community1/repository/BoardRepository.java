package com.heeseung.community1.repository;

import com.heeseung.community1.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {
    public int save(Board board, String table) throws Exception;
    public List<Board> get(String table) throws Exception;
    public int addViewCount(String table, int id) throws Exception;
}
