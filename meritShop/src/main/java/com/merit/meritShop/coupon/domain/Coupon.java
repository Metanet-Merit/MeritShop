package com.merit.meritShop.coupon.domain;

import com.merit.meritShop.user.domain.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coupon_id")
    private Long couponId;

    @Column(name="coupon_name") //테이블 컬럼 이름과 매칭
    private String couponName;

    @Column(name="serial_code")
    private String serialCode;

    @Column(name="discount_price")
    private int discountPrice;

    @Column(name="discount_rate")
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
