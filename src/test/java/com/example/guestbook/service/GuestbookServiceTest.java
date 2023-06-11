package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title")
                .content("Sample Content....")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }

    //Dto -> Entity
    //Entity -> Dto 변환 테스트
    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        //현재 PREV는 1페이지이기 때문에 FALSE
        System.out.println("PREV: " + resultDTO.isPrev());
        //다음 페이지로 가는 링크 필요
        System.out.println("NEXT: " + resultDTO.isNext());
        //전체 페이지 개수
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("---------------------------------------");
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        //화면에 출력 될 페이지 번호
        System.out.println("=========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}