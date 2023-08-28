package com.example.mreview.dto;

import com.example.mreview.entity.MovieImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long mno;

    private String ttitle;

    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();

}
