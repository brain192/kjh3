package com.kjh3.kjh3.service;

import com.kjh3.kjh3.domain.Member;
import com.kjh3.kjh3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// 인증(로그인/회원가입) 서비스
public class AuthService {

    private final MemberRepository memberRepository;

    // 🔹 로그인
    public Member login(String username, String password) {
        return memberRepository.findByUsernameAndPassword(username, password);
    }

    // 🔹 회원가입
    public boolean signup(Member member) {

        // 1️⃣ 아이디 중복 검사
        if (memberRepository.existsByUsername(member.getUsername())) {
            return false; // 이미 존재
        }

        // 2️⃣ 회원 저장
        memberRepository.save(member);

        return true;
    }
}