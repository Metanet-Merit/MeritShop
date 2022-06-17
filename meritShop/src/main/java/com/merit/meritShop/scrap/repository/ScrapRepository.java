package com.merit.meritShop.scrap.repository;

import com.merit.meritShop.scrap.domain.Scrap;
import com.merit.meritShop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap,Long> {
    List<Scrap> findScrapByUser(User user);
    Long countByUser(User user);
}
