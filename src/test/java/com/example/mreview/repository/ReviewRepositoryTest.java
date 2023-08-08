package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private  ReviewRepository reviewRepository;

    @Test
    public void insertMovieReview(){

        //200개의 리뷰 등록
        IntStream.rangeClosed(1, 200).forEach(i -> {
            //영화 번호
            Long mno = (long)(Math.random()*100) + 1;

            //리뷰어 번호
            Long mid = ((long)(Math.random()*100) + 1);
            Member member = Member.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌..." + i)
                    .build();

            reviewRepository.save(movieReview);
        });
    }

    //특정 영화의 모든 리뷰와 회원의 닉네임
    @Test
    public void testGetMovieReviews(){

        Movie movie = Movie.builder().mno(99L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println("\t" + movieReview.getGrade());
            System.out.println("\t" +movieReview.getText());
            //밑에 getMember().getEmail()에서 에러 발생
            System.out.println("\t" +movieReview.getMember().getEmail());
            System.out.println("================================");
        });
    }

}