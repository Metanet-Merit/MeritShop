package com.merit.meritShop.user.repository;

import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.user.domain.Review;
import com.merit.meritShop.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findReviewByUser(User user);

    Page<Review> findReviewByUser(User user, Pageable pageable);

    List<Review> findReviewByOrderItem(OrderItem orderItem);
}
