package com.kjh3.kjh3.repository;

import com.kjh3.kjh3.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
// 게시판 DB 접근 클래스
public class BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    // 🔹 전체 목록 조회 (기존)
    public List<Board> findAll() {
        String sql = "SELECT * FROM board ORDER BY id DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Board b = new Board();
            b.setId(rs.getLong("id"));
            b.setTitle(rs.getString("title"));
            b.setContent(rs.getString("content"));
            b.setWriter(rs.getString("writer"));
            b.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
            return b;
        });
    }

    // 🔹 게시글 단건 조회 (수정 화면용)
    public Board findById(Long id) {

        String sql = "SELECT * FROM board WHERE id=?";

        return jdbcTemplate.query(
                sql,
                rs -> rs.next() ? map(rs) : null,
                id
        );
    }

    // 게시글 저장
    public void save(Board board) {

        String sql = "INSERT INTO board(title, content, writer) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                sql,
                board.getTitle(),
                board.getContent(),
                board.getWriter()
        );
    }

    // 🔹 게시글 수정
    public void update(Board board) {

        String sql = "UPDATE board SET title=?, content=? WHERE id=?";

        jdbcTemplate.update(
                sql,
                board.getTitle(),
                board.getContent(),
                board.getId()
        );
    }

    // ResultSet → Board 객체 변환
    private Board map(ResultSet rs) throws SQLException {
        Board b = new Board();
        b.setId(rs.getLong("id"));
        b.setTitle(rs.getString("title"));
        b.setContent(rs.getString("content"));
        b.setWriter(rs.getString("writer"));
        b.setCreatedAt(
                rs.getTimestamp("created_at").toLocalDateTime()
        );
        return b;
        /*
        ResultSet
        DB에서 SELECT 조회한 결과를 담는 “결과표 객체”
        
        php에서 DB에서 읽어들이는것과 비슷함

        예시
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {   // 다음 행으로 이동
            Long id = rs.getLong("id");
            String title = rs.getString("title");
        }
         */
    }
}