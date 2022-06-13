package com.merit.meritShop.cart.controller;

import com.merit.meritShop.cart.domain.Cart;
import com.merit.meritShop.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    CartService cartService;

    //목록
    @GetMapping("/cart")
    public String cartList(Model model, @RequestParam Long userId) {
        List<Cart> cartList = cartService.cartList(userId);

        //model.addAttribute("list", cartList);
        return "cart/cart";

    }
}
