package com.kjh3.kjh3.service;

import com.kjh3.kjh3.domain.Board;
import com.kjh3.kjh3.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
// 게시판 비즈니스 로직 처리
public class BoardService {

    private final BoardRepository boardRepository;

    // 🔹 목록 조회
    public List<Board> list() {
        return boardRepository.findAll();
    }

    // 🔹 게시글 작성
    public void write(Board board) {
        boardRepository.save(board);
    }

    // 🔹 게시글 단건 조회
    public Board findById(Long id) {
        return boardRepository.findById(id);
    }

    // 🔹 게시글 수정
    public void update(Board board) {
        boardRepository.update(board);
    }
}