package com.example.guestbook.controller;

import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/list")
    //화면에서 page와 size 파라미터를 전달해주면 pageRequestDTO에 자동으로 담겨서 온다
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));

    }
}
