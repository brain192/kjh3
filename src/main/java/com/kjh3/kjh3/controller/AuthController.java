package com.kjh3.kjh3.controller;

import com.kjh3.kjh3.domain.Member;
import com.kjh3.kjh3.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
// 로그인 / 회원가입 / 로그아웃 처리
public class AuthController {

    private final AuthService authService;

    // 🔹 로그인 페이지
    @GetMapping("/logins")
    public String loginForm() {
        return "login";
    }

    // 🔹 로그인 처리
    @PostMapping("/logins")
    public String login(String username, String password, HttpSession session) {

        Member member = authService.login(username, password);

        // 로그인 실패
        if (member == null) {
            return "login";
        }

        // 로그인 성공 → 세션 저장
        session.setAttribute("loginMember", member);

        return "redirect:/board";
    }

    // 🔹 회원가입 페이지
    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    // 🔹 회원가입 처리
    @PostMapping("/signup")
    public String signup(Member member, Model model) {

        boolean result = authService.signup(member);

        // 아이디 중복 등으로 실패
        if (!result) {
            model.addAttribute("error", "이미 존재하는 아이디입니다.");
            return "signup";
        }

        // 성공 → 로그인 페이지로 이동
        return "redirect:/logins";
    }

    // 🔹 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/logins";
    }
}