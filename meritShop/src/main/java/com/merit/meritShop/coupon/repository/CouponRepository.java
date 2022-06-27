package com.merit.meritShop.coupon.repository;

import com.merit.meritShop.coupon.domain.Coupon;
import com.merit.meritShop.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Page<Coupon> findByCouponNameContaining(String keyword, Pageable pageable);
}
