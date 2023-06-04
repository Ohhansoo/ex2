package com.example.ex2.repository;

import com.example.ex2.entity.Memo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    /*
    * 쿼리 메서드 : 메서드의 이름 자체가 질의(qurey)
    * */
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    /*
     * 쿼리메서드와 Pageable의 결합
     * */
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    /*
    * deleteBy로 시작하는 삭제처리
    * num보다 작은 데이터 삭제
    * 데이터를 한 번에 삭제하는게 아니라 각 엔티티 객체를 하나씩 삭제하기 때문에 잘 사용은 X
    * */
    void deleteMemoByMnoLessThan(Long num);

    /*
    * @Query 사용 1
    * */
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno ")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText );

    /*
     * @Query 사용 2
     * */
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno} ")
    int updateMemoText(@Param("param") Memo memo);

    /*
     * @Query와 페이징 처리 결합
     * */
    @Query(value = "select m from Memo m where m.mno > :mno",
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

    /*
     * Object[] 이용
     * */
    @Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno"
            , countQuery = "select count(m) from Memo m where m.mno > :mno ")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

    /*
     * Native SQL 처리
     * */
    @Query(value = "select * from memo where mno > 0", nativeQuery = true)
    List<Object[]> getNativeResult();

}
