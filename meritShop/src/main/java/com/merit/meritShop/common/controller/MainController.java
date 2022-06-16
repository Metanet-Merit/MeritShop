package com.merit.meritShop.common.controller;

import com.merit.meritShop.coupon.domain.Coupon;
import com.merit.meritShop.coupon.repository.CouponRepository;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.user.domain.User;
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

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @GetMapping("/main")
    public String getIndex(Model model)
    {
        Optional<Coupon> op = couponRepository.findById(1L);
        if(op.isEmpty()) {
            Coupon coupon = new Coupon();
            coupon.setCouponName("신규 회원 쿠폰 ");
            coupon.setDescription("자주 이용해 달라고 주는 뇌물~ ");
            coupon.setEndNum(7); // 7일 뒤 만료
            coupon.setStartNum(0); // 바로 사용가능
            coupon.setMinBound(30000); // 3만원 이상 결제 시 사용 가능
            coupon.setDiscountPrice(10000); // 만원 할인
            coupon.setDiscountRate(0);

            couponRepository.save(coupon);
        }
        User admin = userRepository.findByEmail("root@root");
        if(admin==null){
            admin = new User();
            admin.setEmail("root@root");
            admin.setPassword("1234");
            admin.setRole("ROLE_ADMIN");
            admin.setLoginType("local");
            admin.setUserName("root");
            userRepository.save(admin);
        }


        PageRequest pageRequest =  PageRequest.of(0,8, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<Item> itemList = itemRepository.findAll(pageRequest);
        model.addAttribute("items",itemList);

        return "mainPage/index";
    }

    @GetMapping("/main/search")
    public String search(String keyword, Model model, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) {
        PageRequest pageRequest =  PageRequest.of(0,8, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<Item> itemList = itemRepository.findByItemNameContaining(keyword, pageRequest);
        model.addAttribute("items",itemList);
        return "mainPage/searchIndex";
    }

    @GetMapping(value = "/err/denied-page")
    public String accessDenied(){
        return "err/deniedPage";
    }

}
