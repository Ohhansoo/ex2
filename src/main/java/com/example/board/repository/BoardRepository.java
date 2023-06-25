package com.example.board.repository;

import com.example.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //엔티티 클래스 내부에 연관관계가 있는 경우
    // 한개의 로우(Object) 내에 Object[]로 나옴
    @Query("SELECT b, w FROM Board b LEFT JOIN b.writer w WHERE b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    //엔티티 클래스 내부에 연관관계가 없는 경우
    @Query("SELECT b, r from Board b LEFT JOIN Reply r on r.board = b WHERE b.bno =:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    //목록 화면에 필요한 데이터
    @Query(value = "SELECT b, w, count(r) " +
                   "  FROM Board b " +
                   "   LEFT OUTER JOIN b.writer w " +
                   "   LEFT OUTER JOIN Reply r ON r.board = b " +
                   "  GROUP BY b ",
                countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    //조회 화면에 필요한 데이터
    @Query(value = "SELECT b, w, count(r) " +
                   "  FROM Board b " +
                   "   LEFT JOIN b.writer w " +
                   "   LEFT OUTER JOIN Reply r ON r.board = b " +
                   "  WHERE b.bno = :bno ")
    Object getBoardByBno(@Param("bno") Long bno);
}
