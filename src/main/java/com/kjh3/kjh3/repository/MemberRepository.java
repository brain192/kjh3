package com.kjh3.kjh3.repository;

import com.kjh3.kjh3.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
// 회원 관련 DB 접근 클래스
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    // 🔹 로그인용 (기존)
    public Member findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM member WHERE username=? AND password=?";
        return jdbcTemplate.query(
                sql,
                rs -> rs.next() ? map(rs) : null,
                username, password
        );
    }

    // 🔹 아이디 중복 체크
    public boolean existsByUsername(String username) {

        // COUNT(*) → 존재 여부 확인
        String sql = "SELECT COUNT(*) FROM member WHERE username=?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);

        // 1개 이상이면 이미 존재
        return count != null && count > 0;
    }

    // 🔹 회원 저장 (회원가입)
    public void save(Member member) {

        String sql = "INSERT INTO member(username, password) VALUES (?, ?)";

        jdbcTemplate.update(
                sql,
                member.getUsername(),
                member.getPassword()
        );
    }

    // ResultSet → Member 매핑
    private Member map(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setId(rs.getLong("id"));
        m.setUsername(rs.getString("username"));
        m.setPassword(rs.getString("password"));
        return m;
    }
}
