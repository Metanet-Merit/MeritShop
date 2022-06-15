package com.merit.meritShop.coupon.service;

import com.merit.meritShop.coupon.domain.Coupon;
import com.merit.meritShop.coupon.domain.CouponCase;
import com.merit.meritShop.coupon.domain.CouponFormDto;
import com.merit.meritShop.coupon.repository.CouponCaseRepository;
import com.merit.meritShop.coupon.repository.CouponRepository;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponCaseRepository couponCaseRepository;
    private final UserRepository userRepository;

    public void createCoupon(CouponFormDto dto){
        couponRepository.save(new Coupon().update(dto));
    }

    public void publishCouponToUser(User user, Long couponId){

        Coupon coupon = couponRepository.findById(couponId).get();
        if(coupon!=null) {
            CouponCase cc = new CouponCase();
            cc.setCoupon(coupon);
            cc.setUser(user);
            cc.setSerialCode(UUID.randomUUID().toString());
            cc.setStartDate(LocalDateTime.now().plusDays(coupon.getStartNum()));
            cc.setEndDate(LocalDateTime.now().plusDays(coupon.getEndNum()));
            cc.setUsed(false);

            couponCaseRepository.save(cc);
        }
    }


    @Transactional
    public void updateCoupon(CouponFormDto dto){
        Coupon coupon = couponRepository.findById(dto.getCouponId()).get();
        coupon.update(dto);
    }


}
