package com.example.guestbook.controller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    //final로 선언
    private final GuestbookService service;

    @GetMapping("/")
    public String index(){

        return "redirect:/guestbook/list";
    }

    /*
    * 목록 List
    * */

    @GetMapping("/list")
    //화면에서 page와 size 파라미터를 전달해주면 pageRequestDTO에 자동으로 담겨서 온다
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));

    }

    /*
     * 등록
     * */
    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto...." + dto);

        //새로 추가된 엔티티의 번호
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    /*
     * 조회 및 수정 화면
     * */
    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDto") PageRequestDTO requestDTO, Model model){

        log.info("gno: " + gno);

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
        model.addAttribute("requestDTO", requestDTO);

    }


    /*
     * 삭제 처리
     * */
    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){

        log.info("gno: " + gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){

        log.info("post modify........................... ");
        log.info("dto" + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }

}
