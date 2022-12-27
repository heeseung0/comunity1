package com.heeseung.community1.service;

import com.heeseung.community1.domain.Board;
import com.heeseung.community1.dto.BoardModifyReqDto;
import com.heeseung.community1.dto.BoardReqDto;
import com.heeseung.community1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    private String getTableNames(String boardURL) {
        String tableName = "post_notice";

        switch (boardURL) {
            case "notice":
            case "Notice":
                tableName = "post_notice";
                break;

        }

        return tableName;
    }

    @Override
    public int newPost(String boardURL, BoardReqDto boardReqDto) throws Exception {
        Board board = Board.builder()
                .type(boardReqDto.getType())
                .title(boardReqDto.getTitle())
                .writer(boardReqDto.getWriter())
                .contents(boardReqDto.getContents())
                .build();

        boardRepository.save(getTableNames(boardURL), board);
        return 1;
    }

    @Override
    public int deletePost(String boardURL, int postNum) throws Exception {
        return boardRepository.delete(getTableNames(boardURL), postNum);
    }

    @Override
    public int updatePost(String boardURL, int postNum, BoardModifyReqDto boardModifyReqDto) throws Exception {
        Board board = Board.builder()
                .type(Integer.valueOf(boardModifyReqDto.getType()))
                .title(boardModifyReqDto.getTitle())
                .writer(boardModifyReqDto.getWriter())
                .contents(boardModifyReqDto.getContents())
                .build();

        boardRepository.update(getTableNames(boardURL), postNum, board);
        return 0;
    }

    @Override
    public List<BoardReqDto> getPosts(String boardURL) throws Exception {
        List<BoardReqDto> reqDtoList = new ArrayList<BoardReqDto>();

        boardRepository.gets(getTableNames(boardURL)).forEach(board -> {
            reqDtoList.add(board.toDto());
        });

        return reqDtoList;
    }

    @Override
    public BoardReqDto getPost(String boardURL, int postNum) throws Exception {
        Board board = boardRepository.get(getTableNames(boardURL), postNum);
        return BoardReqDto.builder()
                .type(board.getType())
                .title(board.getTitle())
                .writer(board.getWriter())
                .contents(board.getContents())
                .build();

    }

    @Override
    public int addViewCount(String boardURL, int id) throws Exception {
        return boardRepository.addViewCount(getTableNames(boardURL), id);
    }


}