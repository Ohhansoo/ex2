package com.example.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//@ToString은 항상 exclude
@ToString(exclude = "writer")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    //명시적으로 Lazy 로딩 지정
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

}
