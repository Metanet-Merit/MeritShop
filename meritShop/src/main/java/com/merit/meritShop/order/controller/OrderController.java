package com.merit.meritShop.order.controller;

import com.merit.meritShop.coupon.domain.CouponCase;
import com.merit.meritShop.coupon.repository.CouponCaseRepository;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.order.domain.*;
import com.merit.meritShop.order.service.OrderService;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CouponCaseRepository couponCaseRepository;


    @PostMapping("/order")
    public ResponseEntity getOrderPage( HttpServletRequest request,@RequestBody PayFormDto dto ){

           // System.out.println(dto);
        User user = userRepository.findById(getIdFromCookies(request.getCookies())).get();

           Long code= orderService.order(user,dto);


        return new ResponseEntity<Long>(code, HttpStatus.OK);
    }

    @GetMapping("/orderHistory")
    public String getOrderHistory(HttpServletRequest request,Model model){
        // 사용자 불러와야 댐
        PageRequest pageRequest =  PageRequest.of(0,10);

        User user = userRepository.findById(getIdFromCookies(request.getCookies())).get();

        List<OrderItemHistDto> dtolist= orderService.getOrderHistory(user);

        final int start = (int)pageRequest.getOffset();
        final int end = Math.min((start + pageRequest.getPageSize()), dtolist.size());
        final Page<OrderItemHistDto> page = new PageImpl<>(dtolist.subList(start, end), pageRequest, dtolist.size());

        model.addAttribute("orderItemHistList",dtolist);
        model.addAttribute("maxPage",10);

        return "order/orderHistory";
    }


    @GetMapping("/pay")
    public String getPayForm( HttpServletRequest request,PayFormDto dtoList, Model model){

        Long userId = getIdFromCookies(request.getCookies());
        if(userId!=null) {
            User user = userRepository.findById(userId).get();
            model.addAttribute("user",user);
        }else {
            model.addAttribute("user",new User());
        }
        List<OrderItemDto> dtos = dtoList.getOrderItemDtoList();
        List<PayItemDto> payList = new ArrayList<>();
        int totalPrice = 0;
        for(OrderItemDto dto:dtos) {
            Item item = itemRepository.findById(dto.getItemId()).get();
            ItemOption option = itemOptionRepository.findById(dto.getItemOptionId()).get();
            int remain = option.getQuantity() - dto.getCount();
            if (remain < 0) {
                throw new RuntimeException("재고부족");
            }


            payList.add(new PayItemDto(item.getItemId(),option.getItemOptionId(),item.getItemName(), dto.getCount(), item.getPrice()));


        }
        for (PayItemDto p : payList) {
            totalPrice += (p.getPrice() * p.getCount());
        }
        List<CouponCase> couponCases = couponCaseRepository.findAllByUserUserIdAndUsedIsFalse(userId);

        model.addAttribute("transactionCode", LocalDateTime.now().toString()+ UUID.randomUUID().toString());
        model.addAttribute("payList",payList);
        model.addAttribute("totalPrice",totalPrice);
        if(couponCases.isEmpty()) {
            model.addAttribute("couponList",couponCases );
            model.addAttribute("couponStat","empty" );

        }else{
            model.addAttribute("couponList",couponCases );
            model.addAttribute("couponStat","" );
        }


        return "pay/pay";
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
