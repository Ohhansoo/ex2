package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("user"+ i +"@aaa.com").build();

                    Board board = Board.builder()
                            .title("Title..." + i)
                            .content("Content..." + i)
                            .writer(member)
                            .build();

            boardRepository.save(board);
        });
    }

    //Lazy 방식 단순 조회 테스트
    @Transactional
    @Test
    public void testRead1(){

        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
    }

    //JPQL Left join 테스트(연관관계 O)
    @Test
    public void testReadWithWriter(){

        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[]) result;

        System.out.println("--------------------------------------");
        System.out.println(Arrays.toString(arr));

    }

    //JPQL Left join 테스트(연관관계 X)
    @Test
    public void testGetBoardWithReply(){

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }

    }

    //목록화면 JPQL
    @Test
    public void testWithReplyCount(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = row;

            System.out.println(Arrays.toString(arr));
        });

    }

    @Test
    public void testRead3(){

        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    //JPQL 프로젝트 테스트
    @Test
    public void testSearch1(){

        boardRepository.search1();

    }
    @Test
    public void testSearchPage(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending().and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);

    }
}