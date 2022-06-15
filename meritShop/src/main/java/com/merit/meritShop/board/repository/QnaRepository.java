package com.merit.meritShop.board.repository;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    Page<Qna> findByUser(User user,Pageable pageable);

    @Query("SELECT q FROM Qna q ORDER BY q.qnaId DESC")
    Page<Qna> findAll(Pageable pageable);

    List<Qna> findAllByItem(Item item);
}
