package com.heeseung.community1.service;

import com.heeseung.community1.domain.Board;
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
    public int newPost(BoardReqDto boardReqDto, String boardURL) throws Exception {
        Board board = Board.builder()
                .type(boardReqDto.getType())
                .title(boardReqDto.getTitle())
                .writer(boardReqDto.getWriter())
                .contents(boardReqDto.getContents())
                .build();

        boardRepository.save(board, getTableNames(boardURL));
        return 1;
    }

    @Override
    public List<BoardReqDto> getPost(String boardURL) throws Exception {
        List<BoardReqDto> reqDtoList = new ArrayList<BoardReqDto>();

        boardRepository.get(getTableNames(boardURL)).forEach(board -> {
            reqDtoList.add(board.toDto());
        });

        return reqDtoList;
    }

    @Override
    public int addViewCount(String boardURL, int id) throws Exception {
        return boardRepository.addViewCount(getTableNames(boardURL),id);
    }
}
