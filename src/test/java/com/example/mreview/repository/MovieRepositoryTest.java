package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.MovieImage;
import com.example.mreview.entity.QMovieImage;
import com.example.mreview.entity.Review;
import jakarta.transaction.Transactional;
import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository imageRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Commit
    @Transactional
    @Test
    public void insertMovies(){

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder().title("Movie..." + i).build();

            System.out.println("---------------------------------------");

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1; //1,2,3,4

            for(int j = 0; j < count; j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg").build();
                imageRepository.save(movieImage);
            }
        });
    }

    //페이지 처리되는 영화별 평균 점수/리뷰 개수 구하기
    @Test
    public void testListPage(){

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for(Object[] objects : result.getContent()){
            System.out.println(Arrays.toString(objects));
        }
    }


    //특정 영화의 모든 이미지와 평균 평점/리뷰 개수
    @Test
    public void testGetMovieWithAll(){

        List<Object[]> result = movieRepository.getMovieWithAll(99L);

        System.out.println(result);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember(){

        Long mid = 3L; //Member의 mid

        Member member = Member.builder().mid(mid).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);

    }
}