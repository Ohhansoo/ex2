package com.example.ex2.repository;

import com.example.ex2.entity.Memo;
import jakarta.transaction.Transactional;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoRepositoryTest {
    /*
    * insert 테스트
    *  */
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }

    /*
     * select 테스트
     *  */
    //데이터베이스에 존재하는 mno

    @Test
    public void testSelect(){
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=============================================");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }


    /*
     * update 테스트
     *  */
    //데이터베이스에 존재하는 mno

    @Test
    public void testUpdate(){

        Memo memo = Memo.builder().mno(100L).memoText("Update Test").build();

        System.out.println(memoRepository.save(memo));
    }

    /*
     * delete 테스트
     *  */
    //데이터베이스에 존재하는 mno

    @Test
    public void testDelete(){

        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    /*
     * 페이징 테스트 및 페이징관련 메서드
     *  */
    @Test
    public void testPageDefault(){

        //1페이지 10개
        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);
        System.out.println("---------------------------------------------");
        System.out.println("Total Pages: " + result.getTotalPages()); //총 몇 페이지
        System.out.println("Total Count: " + result.getTotalElements()); //전체 개수
        System.out.println("Page Number: " + result.getNumber()); //현재 페이지 번호
        System.out.println("Page Size: " + result.getSize()); //페이지당 데이터 개수
        System.out.println("has next page?: " + result.hasNext()); //다음 페이지 존재 여부
        System.out.println("fisrt page?: " + result.isFirst()); //시작 페이지(0) 여부

        System.out.println("----------------------------------------------");

        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    /*
     * 정렬
     *  */
    @Test
    public void testSort(){

        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    /*
     * 2개 이상 정렬
     *  */
    @Test
    public void testMultiSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0, 10, sortAll);
    }

    /*
     * 쿼리메서드
     *  */
    @Test
    public void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list){
            System.out.println(memo);
        }

    }

    /*
     * Pagable과 쿼리메서드결합
     *  */
    @Test
    public void testQueryMethodsWithPagable(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });

    }


    @Commit //최종 결과를 커밋하기 위하여 사용, 이를 적용하지 않으면 기본적으로 롤백처리되어 결과반영 X
    @Transactional
    @Test
    public void testDeleteQueryMethods(){
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

}