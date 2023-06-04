package com.example.ex2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
//해당 클래스가 엔티티를 위한 클래스이며, 해당 클래스의 인스턴스들이 JPA로 관리되는 엔티티 객체라는 것을 의미
@Table(name= "tbl_memo")
//데이터베이스 상에서 엔티티 클래스를 어떠한 테이블로 생성할 것인지에 대한 정보를 담기 위한 어노테이션
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    @Id
    //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*자동으로 생성되는 번호, 키전략을 설정한다.
    * 1. AUTO(default) : JPA 구현체(스프링 부트에서는 Hibernate)가 생성 방식을 결정
    * 2. IDENTITY : 사용하는 데이터베이스가 키 생성을 결정 MYSQL이나 MariaDB의 경우 auto increment 방식 이용
    * 3. SEQUENCE : 데이터베이스의 sequence를 이용해서 키 생성. @SequenceGenerator와 같이 사용
    * 4. TABLE : 키 생성 전용 테이블을 생성해서 키 생성. @TableGenerator와 함께 사용
    * */
    private Long mno;

    @Column(length = 200, nullable = false)
    //nullable, name, length 등을 이용해서 데이터베이스의 칼럼에 필요한 정보 제공
    //Column과 반대로 데이터베이스 테이블에는 칼럼으로 생성되지 않는 필드의 경우에는 @Transient 어노테이션 적용
    //또한 기본값을 지정하기 위해서 columnDefinition을 이용하기도 한다.
    private String memoText;
}

