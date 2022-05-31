package com.merit.meritShop.coupon.domain;

import com.merit.meritShop.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="coupon_name") //테이블 컬럼 이름과 매칭
    private String couponName;

    @Column(name="serial_code")
    private String serialCode;

    private int discountPrice;

    private float discountRate;

    private String description;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;

    @Column(name="pub_date")
    private LocalDateTime publishDate;

    @Column(name="min_bound")
    private int minBound;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
