package com.example.mreview.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    //review num
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    //Movie mno
    private Long mno;

    //Member id
    private Long mid;

    //Member nickname
    private String nickname;

    //Member email;
    private String email;

    private int grade;

    private String text;

    private LocalDateTime regDate, modDate;
}
