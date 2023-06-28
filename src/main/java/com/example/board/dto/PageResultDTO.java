package com.example.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
//JPA를 이용하는 Repository에서는 페이지 처리 결과를 Page<Entity> 타입으로 반환하게 된다.
//따라서 서비스 계층에서 이를 처리하기 위해 별도의 클래스를 만들어 처리해야 한다.
public class PageResultDTO<DTO, EN> {

    //DTO리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;

    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지 번호 목록
    private List<Integer> pageList;

    //Page<Entity>타입을 이용해 생성할 수 있도록 생성자 작성
    //Function<EN, DTO>는 엔티티 객체들을 DTO로 변환해주는 기능
    //이렇게 하면 어떤 종류의 Page<E> 타입이 생성되더라도, PageResultDTO를 이용해 처리 할 수 있다는 장점이 있다.
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());

    }

    private void makePageList(Pageable pageable){

        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }

}
