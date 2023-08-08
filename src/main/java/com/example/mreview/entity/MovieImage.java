package com.example.mreview.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie")
public class MovieImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    //java.util.UUID를 이용해 고유번호 생성
    private String uuid;

    private String imgName;

    //년/월/일 폴더 구조를 의미
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

}
