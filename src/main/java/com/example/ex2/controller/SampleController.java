package com.example.ex2.controller;

import com.example.ex2.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample2")
@Log4j2
public class SampleController {
    @GetMapping("/ex1")
    public void ex1(){
        log.info("ex1........................");
    }

    /*
    * thymeleaf 기본기능
    * */

    //GetMapping에서 {}로 감싸는 것도 아닌 것의 차이를 알아보자
    @GetMapping({"/ex2"})
    public void exModel(Model model){
        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(
                i -> {
                SampleDTO dto = SampleDTO.builder()
                        .sno(i)
                        .first("First.." +i)
                        .last("Last.."+i)
                        .regTime(LocalDateTime.now())
                        .build();
                    return dto;
                }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }


    /*
    *  inline 속성
    * */
    @GetMapping({"/exInline"})
    public String exInline(RedirectAttributes redirectAttributes){

        log.info("exInline.............");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First..100")
                .last("Last..100")
                .regTime(LocalDateTime.now())
                .build();

        //redirectAttributes를 이용하여 /ex3으로 result와 dto라는 이름의 데이터를 전달
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3(Model model){
        
        log.info("ex3");
    }

    /*
     *  링크 처리
     * */
    //GetMapping에 배열을 이용하여 하나 이상의 url 처리 가능
    @GetMapping({"/ex4", "/exLink"})
    public void exModelLink(Model model){

        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(
                i -> {
                    SampleDTO dto = SampleDTO.builder()
                            .sno(i)
                            .first("Fisrt.." + i)
                            .last("Last.." + i)
                            .regTime(LocalDateTime.now())
                            .build();
                    return dto;
                }
        ).collect(Collectors.toList());

        model.addAttribute("list", list);
    }

    /*
     *  fragment
     * */
    @GetMapping({"/exLayout1", "/exLayout2", "/exTemplate", "/exSidebar"})
    public void exLayout1(){
        log.info("exLayout.............");
    }
}
