package com.merit.meritShop.board.repository;

import com.merit.meritShop.board.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
