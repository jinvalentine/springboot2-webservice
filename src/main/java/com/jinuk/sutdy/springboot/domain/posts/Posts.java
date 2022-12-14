package com.jinuk.sutdy.springboot.domain.posts;

import com.jinuk.sutdy.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 주요 어노테이션을 클래스에 가깝게 두자
@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스이다. 카멜케이스 클래스 -> 언더스코어 네이밍 테이블 SalesManger.java -> sales_manager table
public class Posts extends BaseTimeEntity {

    @Id // 테이블의 pk 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment를 위해 IDENTITY옵션을 사용
    private Long id;

    @Column(length = 500, nullable = false) // 기본값 외에 추가로 변경이 필요한 옵션이 있으면 @Column을 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 빌더 패턴 클래스 생성
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Domain에서 비즈니스 처리를 담당해야 되기 때문에
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
