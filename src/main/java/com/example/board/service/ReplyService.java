package com.example.board.service;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    //등록처리
    Long register(ReplyDTO replyDTO);

    //특정 게시물의 댓글 목록
    List<ReplyDTO> getList(Long bno);

    //수정처리
    void modify(ReplyDTO replyDTO);

    //삭제처리
    void remove(Long rno);

    default Reply dtoToEntity(ReplyDTO replyDTO){

        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
        return reply;
    }

    default ReplyDTO entityToDTO(Reply reply){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return replyDTO;
    }
}
