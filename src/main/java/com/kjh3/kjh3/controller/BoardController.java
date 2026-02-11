package com.kjh3.kjh3.controller;


import com.kjh3.kjh3.domain.Board;
import com.kjh3.kjh3.domain.Member;
import com.kjh3.kjh3.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
// 게시판 요청 처리
public class BoardController {

    private final BoardService boardService;

    // 🔹 게시글 목록
    @GetMapping
    public String list(Model model, HttpSession session) {

        // 로그인 체크
        if (session.getAttribute("loginMember") == null) {
            return "redirect:/logins";
        }

        model.addAttribute("boards", boardService.list());
        return "board/list";
    }

    // 🔹 글쓰기 페이지
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    // 🔹 글쓰기 처리
    @PostMapping("/write")
    public String write(Board board, HttpSession session) {

        Member member = (Member) session.getAttribute("loginMember");

        board.setWriter(member.getUsername());

        boardService.write(board);

        return "redirect:/board";
    }

    // ============================
    // 🔥 여기부터 수정 기능
    // ============================

    // 🔹 수정 페이지 이동
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {

        // 로그인 체크
        if (session.getAttribute("loginMember") == null) {
            return "redirect:/logins";
        }

        // 수정할 게시글 조회
        Board board = boardService.findById(id);

        model.addAttribute("board", board);

        return "board/edit";
    }

    // 🔹 수정 처리
    @PostMapping("/edit")
    public String edit(Board board, HttpSession session) {

        // 로그인 체크
        if (session.getAttribute("loginMember") == null) {
            return "redirect:/logins";
        }

        // DB 업데이트
        boardService.update(board);

        return "redirect:/board";
    }
}