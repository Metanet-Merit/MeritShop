package com.merit.meritShop.coupon.domain;

import com.merit.meritShop.common.domain.BaseEntity;
import com.merit.meritShop.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCase extends BaseEntity {
    @Id  @GeneratedValue(strategy = GenerationType.AUTO)
    private Long couponCaseId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Coupon coupon;

    private String serialCode;

    private LocalDateTime startDate; // 실제 날짜

    private LocalDateTime endDate;

    private Boolean used;


}
