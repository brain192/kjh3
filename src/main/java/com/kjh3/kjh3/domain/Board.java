package com.kjh3.kjh3.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
// 게시글 정보를 담는 DTO
public class Board {

    // 게시글 번호 (PK)
    private Long id;

    // 게시글 제목
    private String title;

    // 게시글 내용
    private String content;

    // 작성자 (로그인한 회원의 username)
    private String writer;

    // 작성 시간
    private LocalDateTime createdAt;
}