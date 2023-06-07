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
@RequestMapping("/layout")
@Log4j2
public class LayoutController {

    @GetMapping({"/layout1", "/layout2"})
    public void layout1(){
        log.info("layout.............");
    }
}
