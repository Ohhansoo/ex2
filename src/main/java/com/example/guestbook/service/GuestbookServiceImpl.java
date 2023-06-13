package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.function.Function;


@Service
@Log4j2
//의존성 주입
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    //반드시 final 선언
    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO----------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        //요청 처리
        Page<Guestbook> result = repository.findAll(pageable);

        //받은 Entity -> DTO 변환
        Function<Guestbook, GuestbookDTO> fn = (entity ->
            entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    /*
    * 조회
    * */
    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()): null;
    }

    /*
    * 삭제
    * */
    @Override
    public void remove(Long gno) {

        repository.deleteById(gno);

    }

    /*
     * 수정
     * */
    @Override
    public void modify(GuestbookDTO dto) {

        //업데이트 하는 항목은 '제목', '내용'
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()){
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }

    }


}
