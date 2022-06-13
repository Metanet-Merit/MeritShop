package com.merit.meritShop.cart.repository;

import com.merit.meritShop.cart.domain.Cart;
import com.merit.meritShop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
}
