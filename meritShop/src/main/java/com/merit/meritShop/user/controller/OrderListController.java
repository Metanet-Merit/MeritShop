package com.merit.meritShop.user.controller;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.common.controller.AbstractController;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.user.service.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("myPage/")
public class OrderListController extends AbstractController {

    @Autowired
    OrderListService orderListService;

    @GetMapping("/orders")
    public Result getOrders(@RequestParam Long userId) {
        //  , @CookieValue(name = "userId", required = false) Long useId) { //userId는 쿠키에서 가져오기
        return orderListService.getOrders(userId);
    }

    @GetMapping("/MyOrderItems")
    public String getMyOrderItems(@CookieValue("userId") Long userId, Model model) {
        Result result = orderListService.getOderItems(userId);
        if (result.getResultCode().getCode() != 0) {
            return "redirect:/myPage";
        }


        model.addAttribute("result", result);
        return "myPage/myOrderPage";
    }

    @GetMapping("orderItems")
    public String getOrderItems() {
        return "redirect:/main";
    }
}
