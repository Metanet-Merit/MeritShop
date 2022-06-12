package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.controller.AbstractController;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.user.service.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("myPage/")
public class OrderListController extends AbstractController {

    @Autowired
    OrderListService orderListService;

    @GetMapping("/orders")
    public Result getOrders(@RequestParam Long userId) {
        //  , @CookieValue(name = "userId", required = false) Long useId) { //userId는 쿠키에서 가져오기
        return orderListService.getOders(userId);
    }

    @GetMapping("/orderItems")
    public Result getOrderItems(@RequestParam Long orderId) {
        return orderListService.getOderItems(orderId);
    }

}
