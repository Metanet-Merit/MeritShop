package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.user.service.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminOrderController {

    @Autowired
    OrderListService orderListService;

    @GetMapping("/OrderItems")
    public String getMyOrderItems(@CookieValue("userId") Long userId, Model model) {
        Result result = orderListService.getAllOrders();
        if (result.getResultCode().getCode() != 0) {
            return "redirect:/admin";
        }
        model.addAttribute("result", result);
        return "qna/adminOrderList";
    }
}
