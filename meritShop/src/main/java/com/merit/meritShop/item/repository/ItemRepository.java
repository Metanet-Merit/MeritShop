package com.merit.meritShop.item.repository;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Page<Item> findAll(Pageable pageable);

    Page<Item> findByItemNameContaining(String keyword, Pageable pageable);
}
