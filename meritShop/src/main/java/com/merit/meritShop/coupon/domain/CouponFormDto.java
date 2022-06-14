package com.merit.meritShop.coupon.domain;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class CouponFormDto {

    private Long couponId;

    private String couponName;

    private int discountPrice;

    private float discountRate;

    private String description;

    private int startNum; // 쿠폰 생성일 + startNum = startDate

    private int endNum; // 쿠폰 만료료일 + ndNum = endDate

    private int minBound;
}
