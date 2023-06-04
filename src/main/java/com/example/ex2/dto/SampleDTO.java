package com.example.ex2.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
/*
* @DATA는 Getter/Setter, toString(), equals(), hashCode()를 자동으로 생성
* */
@Builder(toBuilder = true)
public class SampleDTO {

    private Long sno;

    private String first;

    private String last;

    private LocalDateTime regTime;
}
