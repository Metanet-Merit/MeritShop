package com.merit.meritShop.coupon.repository;

import com.merit.meritShop.coupon.domain.CouponCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponCaseRepository extends JpaRepository<CouponCase,Long> {
    List<CouponCase> findAllByUserUserIdAndUsedIsFalse(Long userId);
}
