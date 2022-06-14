package com.merit.meritShop.board.repository;

import com.merit.meritShop.board.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Modifying
    @Query("update Notice n set n.views = n.views + 1 where n.noticeId = :noticeId")
    int updateViews(Long noticeId);

    @Query("SELECT n FROM Notice n ORDER BY n.noticeId DESC")
    Page<Notice> findAll(Pageable pageable);
}
