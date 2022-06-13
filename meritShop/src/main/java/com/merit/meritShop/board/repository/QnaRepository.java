package com.merit.meritShop.board.repository;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByUser(User user);
}
