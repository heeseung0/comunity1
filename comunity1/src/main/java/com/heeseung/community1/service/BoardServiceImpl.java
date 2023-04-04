package com.heeseung.community1.service;

import com.heeseung.community1.domain.Board;
import com.heeseung.community1.domain.BoardReply;
import com.heeseung.community1.dto.*;
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
            case "free":
            case "Free":
                tableName = "post_free";
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
    public List<BoardReqDto> getRecentPosts(String boardURL, String count) throws Exception {
        List<BoardReqDto> reqDtoList = new ArrayList<BoardReqDto>();

        boardRepository.getsRecentCount(getTableNames(boardURL), Integer.parseInt(count)).forEach(board -> {
            reqDtoList.add(board.toDto());
        });

        return reqDtoList;
    }

    @Override
    public List<BoardReqDto> getPosts(String boardURL) throws Exception {
        List<BoardReqDto> reqDtoList = new ArrayList<BoardReqDto>();

        boardRepository.gets(getTableNames(boardURL), boardURL).forEach(board -> {
            reqDtoList.add(board.toDto());
        });

        return reqDtoList;
    }

    @Override
    public List<BoardReqDto> getPosts(String boardURL, int type, int searchType, String searchLike) throws Exception {
        List<BoardReqDto> reqDtoList = new ArrayList<BoardReqDto>();
        String likeTitle = "";
        String likeWriter = "";
        String likeContents = "";

        switch(searchType){
            case 0:
                likeTitle = searchLike;
                break;
            case 1:
                likeWriter = searchLike;
                break;
            case 2:
                likeContents = searchLike;
                break;
        }

        boardRepository.gets_search(getTableNames(boardURL), type, likeTitle, likeWriter, likeContents).forEach(board -> {
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

    @Override
    public boolean newPostReply(BoardPostReplyReqDto boardReplyReqDto) throws Exception {
        BoardReply boardReply = BoardReply.builder()
                .writer(boardReplyReqDto.getWriter())
                .board(boardReplyReqDto.getBoard())
                .postnum(Integer.parseInt(boardReplyReqDto.getPostnum()))
                .contents(boardReplyReqDto.getContents())
                .build();

        int result = boardRepository.reply_save(boardReply);

        return result != 0;
    }

    @Override
    public List<BoardReplyRespDto> getReply(String boardURL, int postNum) throws Exception {
        List<BoardReplyRespDto> responseList = new ArrayList<>();
        List<BoardReply> domainList = new ArrayList<>();

        domainList = boardRepository.reply_get(boardURL, postNum);
        domainList.forEach(domain -> {
            responseList.add(domain.toDto());
        });

        return responseList;
    }
}
