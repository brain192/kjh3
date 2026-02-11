package com.kjh3.kjh3.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
// 회원 정보를 담는 DTO
// DB의 member 테이블과 1:1로 대응
public class Member {

    // 회원 고유 번호 (PK)
    private Long id;

    // 로그인 아이디
    private String username;

    // 비밀번호
    private String password;
}
