package com.example.mreview.repository;

import com.example.mreview.entity.MovieImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

}
