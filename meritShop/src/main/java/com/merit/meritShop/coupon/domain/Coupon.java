package com.merit.meritShop.coupon.domain;

import com.merit.meritShop.common.domain.BaseEntity;
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
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coupon_id")
    private Long couponId;

    @Column(name="coupon_name") //테이블 컬럼 이름과 매칭
    private String couponName;




    @Column(name="discount_price")
    private int discountPrice;

    @Column(name="discount_rate")
    private float discountRate;

    private String description;



    private int startNum; // 쿠폰 생성일 + startNum = startDate
    private int endNum; // 쿠폰 만료료일 + ndNum = endDate

    @Column(name="min_bound")
    private int minBound;

    public Coupon update(CouponFormDto dto){
        if(dto.getCouponId()!=null){
            this.couponId = dto.getCouponId();
        }
        this.couponName = dto.getCouponName();
        this.discountPrice =dto.getDiscountPrice();
        this.discountRate = dto.getDiscountRate();
        this.description = dto.getDescription();
        this.startNum = dto.getStartNum();
        this.endNum = dto.getEndNum();
        this.minBound = dto.getMinBound();

        return this;
    }
}
