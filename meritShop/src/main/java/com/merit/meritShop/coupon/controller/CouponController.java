package com.merit.meritShop.coupon.controller;

import com.merit.meritShop.coupon.domain.Coupon;
import com.merit.meritShop.coupon.domain.CouponCase;
import com.merit.meritShop.coupon.domain.CouponFormDto;
import com.merit.meritShop.coupon.repository.CouponCaseRepository;
import com.merit.meritShop.coupon.repository.CouponRepository;
import com.merit.meritShop.coupon.service.CouponService;
import com.merit.meritShop.user.dto.UserViewDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final CouponCaseRepository couponCaseRepository;

    @GetMapping("/myPage/myCoupon")
    public String getMyCoupon(HttpServletRequest request,Model model){
        Long userId = getIdFromCookies(request.getCookies());

        List<CouponCase> couponCases = couponCaseRepository.findAllByUserUserIdAndUsedIsFalse(userId);
        model.addAttribute("couponList",couponCases);

        if(couponCases.isEmpty()) {
            model.addAttribute("stat", "empty");
        }else
            {
                model.addAttribute("stat", "good");
            }
        return "myPage/couponList";
    }

    @GetMapping("/admin/couponForm")
    public String getCouponForm(){

        return "coupon/couponForm";
    }

    @PostMapping("/admin/newCoupon")
    public String createCoupon(CouponFormDto dto){

        couponService.createCoupon(dto);


        return "redirect:/admin";
    }

    @GetMapping("/admin/couponList")
    public String getCouponList(Model model, @PageableDefault Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);
        Page<Coupon> couponList = couponRepository.findAll(pageable);
        model.addAttribute("couponList",couponList);

        return "coupon/couponList";
    }
    @GetMapping("/admin/coupon/search")
    public String search(String keyword, Model model, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);
        Page<Coupon> searchList = couponRepository.findByCouponNameContaining(keyword, pageable);
        model.addAttribute("couponList", searchList);
        return "coupon/couponList";
    }

    @GetMapping("/admin/updateCoupon/{id}")
    public String update(@PathVariable("id")Long couponId,Model model){

        Coupon coupon = couponRepository.findById(couponId).get();

        model.addAttribute("coupon",coupon);
        return "coupon/couponUpdateForm";
    }

    @PostMapping("/admin/updateCoupon")
    public String updateCoupon(CouponFormDto dto){

        couponService.updateCoupon(dto);

        return "redirect:/admin";
    }

    public Long getIdFromCookies(Cookie[] cookies) {
        if (cookies == null)
            return null;
        for(Cookie c : cookies) {
            if (c.getName().equals("userId")) {
                return Long.parseLong(c.getValue());
            }
        }
        return null;
    }

}
